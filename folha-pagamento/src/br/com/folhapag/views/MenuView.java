package br.com.folhapag.views;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.dao.FolhaPagamentoDao;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.service.FolhaPagamentoService;

public class MenuView {

	public void executarMenu() {
		try(Connection connection = Conexao.getConexaoDB();
			Scanner scanner = new Scanner(System.in)){

			boolean executando = true; // Controla a execução do sistema (loop principal)

			while (executando) { // Mantém o menu ativo até o usuário decidir sair

				exibirMenu(); // Exibe as opções disponíveis

				int opcao = lerOpcaoMenu(scanner); // Lê e valida a entrada do usuário

				switch (opcao) {

					case 1 -> {
						FolhaLoteView folhaView = new FolhaLoteView();
						folhaView.entradaLote(scanner);

						executando = processarFimDeFluxo(scanner);
					}

					case 2 -> {
						FolhaManualView folhaView = new FolhaManualView();
						Funcionario funcionario = folhaView.entradaDadosManual();

						FuncionarioDao funcDao = new FuncionarioDao(connection);
						funcDao.salvar(funcionario);

						FolhaPagamentoService folhaService = new FolhaPagamentoService();
						FolhaPagamento folha = folhaService.folhaCalculo(funcionario);

						FolhaPagamentoDao folhaDAO = new FolhaPagamentoDao(connection);
						folhaDAO.salvar(folha);

						folhaView.exibirDadosFolha(folha);

						executando = processarFimDeFluxo(scanner);
					}

					case 3 -> {
						System.out.println(">> [Fluxo 3] Listar departamentos - em desenvolvimento");
						executando = processarFimDeFluxo(scanner);
					}

					case 4 -> {
						System.out.println(">> [Fluxo 4] Funcionários por departamento - em desenvolvimento");
						executando = processarFimDeFluxo(scanner);
					}

					case 5 -> {
						System.out.println(">> [Fluxo 5] Histórico de folhas - em desenvolvimento");
						executando = processarFimDeFluxo(scanner);
					}

					case 0 -> {
						// Reutiliza o método validador genérico para a confirmação de saída
						if (perguntarSimNao(scanner, "Deseja realmente sair?")) {
							executando = false;
							System.out.println("Encerrando o sistema...");
						}
					}
				}
			}
		}catch(SQLException e) {
			System.err.println("Erro ao conectar ao banco de dados.");
			e.printStackTrace();
		}
	}

	private void exibirMenu() {
		System.out.println("|==========================================================|");
		System.out.println("|                                                          |");
		System.out.println("|          SISTEMA DE CÁLCULO - FOLHA DE PAGAMENTO         |");
		System.out.println("|                                                          |");
		System.out.println("|==========================================================|");
		System.out.println("|                                                          |");
		System.out.println("|          PROCESSAMENTO DE DADOS                          |");
		System.out.println("|          ------------------------                        |");
		System.out.println("|          1 - Calcular Folha em Lote (CSV)                |");
		System.out.println("|          2 - Calcular Folha Avulsa ( Manual )            |");
		System.out.println("|                                                          |");
		System.out.println("|==========================================================|");
		System.out.println("|                                                          |");
		System.out.println("|          RELATÓRIOS DO BANCO DE DADOS                    |");
		System.out.println("|          -----------------------------                   |");
		System.out.println("|          3 - Listar Departamentos                        |");
		System.out.println("|          4 - Funcionários por Departamento               |");
		System.out.println("|          5 - Histórico de Folhas                         |");
		System.out.println("|                                                          |");
		System.out.println("|==========================================================|");
		System.out.println("|                        0 - SAIR                          |");
		System.out.println("|==========================================================|");
	}

	private int lerOpcaoMenu(Scanner scanner) {
		while (true) {
			System.out.print("Escolha uma opção: ");
			String entrada = scanner.nextLine();

			try {
				int opcao = Integer.parseInt(entrada);
				if (opcao >= 0 && opcao <= 5) {
					return opcao;
				} else {
					System.out.println("Erro: opção inválida. Reveja as opções no menu.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Erro: digite apenas números inteiros.");
			}
		}
	}

	// ====================================================================
	// MÉTODOS UNIFICADOS (Substituem continuarMenu e confirmarSaida)
	// ====================================================================

	/**
	 * Método genérico que garante que o usuário só consiga digitar S ou N.
	 * Retorna true para 'S' e false para 'N'.
	 */
	private boolean perguntarSimNao(Scanner scanner, String pergunta) {
		while (true) {
			System.out.print(pergunta + " (S/N): ");
			String resposta = scanner.nextLine().trim().toUpperCase();

			if (resposta.equals("S")) {
				return true;
			} else if (resposta.equals("N")) {
				return false;
			}

			System.out.println("Entrada inválida. Digite apenas S ou N.");
		}
	}

	/**
	 * Usa a pergunta genérica acima para tratar o fim de uma tarefa (Cases 1 ao 5)
	 */
	private boolean processarFimDeFluxo(Scanner scanner) {
		boolean querContinuar = perguntarSimNao(scanner, "Deseja acessar o menu novamente?");

		if (querContinuar) {
			// Simula limpeza de tela para melhor visualização antes de voltar ao menu
			for (int i = 0; i < 10; i++) {
				System.out.println();
			}
			return true; // Mantém executando = true
		} else {
			System.out.println("Encerrando o sistema...");
			return false; // Define executando = false, quebrando o loop principal
		}
	}
}