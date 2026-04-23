package br.com.folhapag.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.contexts.FolhaContexto;
import br.com.folhapag.dao.FolhaPagamentoDao;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.utils.*;

public class FolhaLoteService {

	public void processarLote(String entrada, String saida) throws Exception {

		try (BufferedReader leitor = new BufferedReader(new FileReader(entrada));
			 BufferedWriter escritor = new BufferedWriter(new FileWriter(saida));
			 Connection connection = Conexao.getConexaoDB()) {

			FuncionarioDao funcDao = new FuncionarioDao(connection);
			FolhaPagamentoService folhaService = new FolhaPagamentoService();
			FolhaContexto contexto = new FolhaContexto();

			String linha;
			Funcionario funcionario = null;
			List<Dependente> dependentes = new ArrayList<>();

			int numeroLinha = 0;

			while ((linha = leitor.readLine()) != null) {
				numeroLinha++;
				String[] dados = linha.split(";");

				try {
					if (dados.length == 5) { //Funcionario
						if (funcionario != null) {
							processarEscrever(folhaService, funcionario, contexto, escritor, connection);
						}

						dependentes = new ArrayList<>();

						String nome = ValidarNome.validarNome(dados[0]);
						String cpf = ValidarCPF.validarCPF(dados[1]);
						LocalDate data = ValidarData.validar(dados[2]);
						double salario = ValidarSalario.validar(dados[3]);
						Departamento dep = ValidarDepartamento.validar(dados[4], connection);

						funcionario = new Funcionario(nome, cpf, data, salario, dep);
						funcionario.setDependentes(dependentes);

						funcDao.salvar(funcionario);

					} else if (dados.length == 4) { //Dependente
						if (funcionario == null) {
							throw new IllegalStateException("Dependente encontrado sem um funcionário titular acima dele.");
						}

						String nome = ValidarNome.validarNome(dados[0]);
						String cpf = ValidarCPF.validarCPF(dados[1]);
						LocalDate data = ValidarData.validar(dados[2]);

						Parentesco p = Parentesco.valueOf(dados[3].trim().toUpperCase());

						Dependente dependente = ValidarDependente.validarECriar(nome, cpf, data, p, funcionario);
						dependentes.add(dependente);
					}
				} catch (Exception e) {
					System.out.println("Aviso: Linha " + numeroLinha + " ignorada. Motivo: " + e.getMessage());
				}
			}

			if (funcionario != null) {
				processarEscrever(folhaService, funcionario, contexto, escritor, connection);
			}


		}
	}

	private void processarEscrever(FolhaPagamentoService folhaService, Funcionario funcionario,
								   FolhaContexto contexto, BufferedWriter escritor, Connection conn) {

		FolhaPagamentoDao folhaDao = new FolhaPagamentoDao(conn);
		FolhaPagamento folha = folhaService.folhaCalculo(funcionario, contexto);

		try {
			folhaDao.salvar(folha);
		} catch(SQLException e) {
			System.out.println("Aviso: A folha do CPF " + funcionario.getCpf() + " já existe ou não pôde ser salva. Ignorando exibição.");
			return;
		}
		FolhaVisualizacaoUtils.exibirRelatorio(folha);

		try {
			escritor.write(escreverCSV(folha));
			escritor.newLine();
		} catch (Exception e) {
			System.out.println("Erro ao escrever no arquivo de saída.");
		}
	}

	private String escreverCSV(FolhaPagamento folha) {
		return String.format("%s;%s;%.2f;%.2f;%.2f",
				folha.getFuncionario().getNome(),
				folha.getFuncionario().getCpf(),
				folha.getINSS(),
				folha.getIR(),
				folha.getSalarioLiquido());
	}
}