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
import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.dao.FolhaPagamentoDao;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;

public class FolhaLoteService {

	public void processarLote(String entrada, String saida) {
		try(BufferedReader leitor = new BufferedReader(new FileReader(entrada));
			BufferedWriter escritor = new BufferedWriter(new FileWriter(saida));Connection connection = Conexao.getConexaoDB();) {
			
			DepartamentoDao depDao = new DepartamentoDao(connection);
			FuncionarioDao funcDao = new FuncionarioDao(connection);
			
			String linha;
			Funcionario funcionario = null;
			List<Dependente> dependentes = new ArrayList();
			Departamento dep = null;
			
			FolhaPagamento folha = new FolhaPagamento();
			FolhaPagamentoService folhaService = new FolhaPagamentoService();
			FolhaContexto contexto = new FolhaContexto();

			while ((linha = leitor.readLine()) != null) {
				String[] dados = linha.split(";");

				if (dados.length == 5) {
					if (funcionario != null) {
						processarEscrever(folha, folhaService, funcionario, contexto, escritor, connection);
					}
					
					dependentes = new ArrayList<>();
					funcionario = new Funcionario(dados[0], dados[1], LocalDate.parse(dados[2]),
							Double.parseDouble(dados[3]), dep = depDao.buscarPorId(Integer.parseInt(dados[4]))); // query com dao para departamento, por enquanto essa é
					System.out.println(funcionario.getDepartamento());											
					funcionario.setDependentes(dependentes);
					funcDao.salvar(funcionario);

				} else if (dados.length == 4) {
					Dependente dependente = new Dependente(dados[0], dados[1], LocalDate.parse(dados[2]),
							Parentesco.validarParentesco(dados[3]), funcionario);
					dependentes.add(dependente);
				}
			}

			if (funcionario != null) {
				processarEscrever(folha, folhaService, funcionario, contexto, escritor, connection);
			}
			
			System.out.println("Arquivo enviado com sucesso!");

		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	private void processarEscrever(FolhaPagamento folha, FolhaPagamentoService folhaService, Funcionario funcionario,
			FolhaContexto contexto, BufferedWriter escritor, Connection conn) {
		FolhaPagamentoDao folhaDao = new FolhaPagamentoDao(conn);
		folha = folhaService.folhaCalculo(funcionario, contexto);
		try {
			folhaDao.salvar(folha);
		}catch(SQLException e) {
			System.out.println("Erro ao salvar folha no banco de dados");
		}
		
		
		String escrita = escreverCSV(folha);
 
		try {
			escritor.write(escrita);
			escritor.newLine();
		} catch (Exception e) {
			System.out.println("Erro");
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
