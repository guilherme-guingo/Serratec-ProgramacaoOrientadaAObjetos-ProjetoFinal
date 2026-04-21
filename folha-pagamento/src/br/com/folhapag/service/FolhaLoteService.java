package br.com.folhapag.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.folhapag.contexts.FolhaContexto;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;

public class FolhaLoteService {

	public void lerLote(String entrada, String saida) {
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(entrada));
			BufferedWriter escritor = new BufferedWriter(new FileWriter(saida));
			String linha;
			Funcionario funcionario = null;
			List<Dependente> dependentes = new ArrayList();

			Departamento dep = new Departamento("abc"); // apenas para teste

			FolhaPagamento folha = new FolhaPagamento();
			FolhaPagamentoService folhaService = new FolhaPagamentoService();
			FolhaContexto contexto = new FolhaContexto();

			while ((linha = leitor.readLine()) != null) {
				String[] dados = linha.split(";");

				if (dados.length == 5) {
					if (funcionario != null) {
						processarEscrever(folha, folhaService, funcionario, contexto, escritor);
					}
					
					dependentes = new ArrayList<>();
					funcionario = new Funcionario(dados[0], dados[1], LocalDate.parse(dados[2]),
							Double.parseDouble(dados[3]), dep); // query com dao para departamento, por enquanto essa é
																// apenas teste
					funcionario.setDependentes(dependentes);

				} else if (dados.length == 4) {
					Dependente dependente = new Dependente(dados[0], dados[1], LocalDate.parse(dados[2]),
							Parentesco.validarParentesco(dados[3]), funcionario);
					dependentes.add(dependente);
				}
			}

			if (funcionario != null) {
				processarEscrever(folha, folhaService, funcionario, contexto, escritor);
			}
			
			JOptionPane.showMessageDialog(null, "Arquivo enviado com sucesso!");
			escritor.close();
			leitor.close();

		} catch (Exception e) {
			System.out.println("Erro");
		}
	}

	private void processarEscrever(FolhaPagamento folha, FolhaPagamentoService folhaService, Funcionario funcionario,
			FolhaContexto contexto, BufferedWriter escritor) {
		
		folha = folhaService.folhaCalculo(funcionario, contexto);
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
