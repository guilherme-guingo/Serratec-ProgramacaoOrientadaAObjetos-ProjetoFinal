package br.com.folhapag.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.List;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.contexts.FolhaContexto;

import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.dao.DependenteDao;

import br.com.folhapag.dao.FolhaPagamentoDao;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.exceptions.CPFInvalido;
import br.com.folhapag.exceptions.DataInvalida;
import br.com.folhapag.exceptions.DependenteInvalido;
import br.com.folhapag.exceptions.FormatoDataInvalido;
import br.com.folhapag.exceptions.NomeInvalido;
import br.com.folhapag.exceptions.SalarioInvalido;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.utils.*;

public class FolhaLoteService {

	public void processarLote(String entrada, String saida) {
		try (BufferedReader leitor = new BufferedReader(new FileReader(entrada));
				BufferedWriter escritor = new BufferedWriter(new FileWriter(saida));
				Connection connection = Conexao.getConexaoDB()) {

			FolhaPagamentoDao folhaDao = new FolhaPagamentoDao(connection);
			DepartamentoDao deptoDao = new DepartamentoDao(connection);

			FuncionarioDao funcDao = new FuncionarioDao(connection);
			DependenteDao dependenteDao = new DependenteDao(connection);

			int numeroLinha = 0;
			String linha;
			Funcionario funcionario = null;
			List<Dependente> dependentes = null;

			FolhaPagamentoService folhaService = new FolhaPagamentoService();
			FolhaContexto contexto = new FolhaContexto();

			while ((linha = leitor.readLine()) != null) {
				numeroLinha++;
				String[] dados = linha.split(";");
				
				try {

					if (dados.length == 5) {
						if (funcionario != null) {
							processarEscrever(folhaService, funcionario, contexto, escritor, folhaDao);
						}

						funcionario = verificaFuncionario(funcDao, dados, dependenteDao, deptoDao);
						dependentes = funcionario.getDependentes();

					} else if (dados.length == 4) {
						if (funcionario == null) {
							throw new IllegalStateException(
									"Dependente encontrado sem um funcionário titular acima dele.");
						}
						processarDependente(dados, funcionario, dependentes, dependenteDao);
					}

				} catch (Exception e) {
					System.out.println("Aviso: Linha " + numeroLinha + " ignorada. Motivo: " + e.getMessage());
				}
			}

			if (funcionario != null) {

				processarEscrever(folhaService, funcionario, contexto, escritor, folhaDao);

			}
			
			System.out.println("Arquivo enviado com sucesso!");

		} catch (IOException | SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void processarEscrever(FolhaPagamentoService folhaService, Funcionario funcionario, FolhaContexto contexto,
			BufferedWriter escritor, FolhaPagamentoDao folhaDao) {

		FolhaPagamento folha = folhaService.folhaCalculo(funcionario, contexto);
		try {
			folhaDao.salvar(folha);
		} catch (SQLException e) {
			System.out.println("Aviso: A folha do CPF " + funcionario.getCpf()
					+ " já existe ou não pôde ser salva. Ignorando exibição.");
			return;
		}

		try {
			escritor.write(escreverCSV(folha));
			escritor.newLine();
		} catch (Exception e) {
			System.out.println("Erro ao escrever no arquivo de saída.");
		}
	}

	private Funcionario verificaFuncionario(FuncionarioDao funcDao, String[] dados, DependenteDao dependenteDao,
			DepartamentoDao deptoDao) throws SQLException, NumberFormatException, NomeInvalido, CPFInvalido,
			FormatoDataInvalido, DataInvalida, SalarioInvalido {

		Funcionario funcExistente = funcDao.buscarPorCpf(dados[1]);

		if (funcExistente == null) {
			Departamento dep = deptoDao.buscarPorId(Integer.parseInt(dados[4]));

			String nome = ValidarNome.validarNome(dados[0]);
			String cpf = ValidarCPF.validarCPF(dados[1]);
			LocalDate data = ValidarData.validar(dados[2]);
			double salario = ValidarSalario.validar(dados[3]);

			Funcionario funcionario = new Funcionario(nome, cpf, data, salario, dep);
			funcDao.salvar(funcionario);

			return funcionario;

		} else {
			funcExistente.setDependentes(dependenteDao.buscarPorFuncionario(funcExistente));
			return funcExistente;
		}

	}

	private void processarDependente(String[] dados, Funcionario funcionario, List<Dependente> dependentes,
			DependenteDao dependenteDao)
			throws SQLException, NomeInvalido, CPFInvalido, FormatoDataInvalido, DataInvalida, DependenteInvalido {
		
		String cpfDep = dados[1];
		boolean existe = dependentes.stream().anyMatch(d -> d.getCpf().equalsIgnoreCase(cpfDep));
		
		if (!existe) {

			String nome = ValidarNome.validarNome(dados[0]);
			String cpf = ValidarCPF.validarCPF(dados[1]);
			LocalDate data = ValidarData.validar(dados[2]);
			Parentesco p = Parentesco.validarParentesco(dados[3]);

			Dependente dependente = ValidarDependente.validarECriar(nome, cpf, data, p, funcionario);
			dependentes.add(dependente);

			dependenteDao.salvar(dependente);

		}
	}

	private String escreverCSV(FolhaPagamento folha) {
		return String.format("%s;%s;%.2f;%.2f;%.2f", 
				folha.getFuncionario().getNome(), 
				folha.getFuncionario().getCpf(),
				folha.getINSS(), folha.getIR(), 
				folha.getSalarioLiquido());
	}
}