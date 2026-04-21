package br.com.folhapag.views;

import java.util.Scanner;

import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.service.FolhaPagamentoService;

public class MenuView {

    public void executarMenu() {

        Scanner scanner = new Scanner(System.in);
        boolean executando = true; // Controla a execução do sistema (loop principal)

        while (executando) { // Mantém o menu ativo até o usuário decidir sair

            exibirMenu(); // Exibe as opções disponíveis

            int opcao = lerOpcaoMenu(scanner); // Lê e valida a entrada do usuário

            switch (opcao) {

                case 1 -> {
                    // Fluxo 1: cálculo em lote via CSV (será implementado futuramente)
                    System.out.println(">> [Fluxo 1] Cálculo em lote (CSV) - em desenvolvimento");
                    executando = continuarMenu(scanner); // Define se o sistema continua ou encerra
                }

                case 2 -> {
                    // Fluxo 2: entrada manual de dados, cálculo da folha e exibição do resultado
                    FolhaManualView folha = new FolhaManualView();
                    Funcionario funcionario = folha.entradaDadosManual();
                    FolhaPagamentoService folhaPag = new FolhaPagamentoService();
                    FolhaPagamento folhaFinal = folhaPag.folhaCalculoManual(funcionario);
                    folha.exibirDadosFolha(folhaFinal);
                    executando = continuarMenu(scanner);
                }

                case 3 -> {
                    // Fluxo 3: listagem de departamentos (integração futura com banco de dados)
                    System.out.println(">> [Fluxo 3] Listar departamentos - em desenvolvimento");
                    executando = continuarMenu(scanner);
                }

                case 4 -> {
                    // Fluxo 4: listagem de funcionários por departamento (integração futura)
                    System.out.println(">> [Fluxo 4] Funcionários por departamento - em desenvolvimento");
                    executando = continuarMenu(scanner);
                }

                case 5 -> {
                    // Fluxo 5: histórico de folhas de pagamento (implementação futura)
                    System.out.println(">> [Fluxo 5] Histórico de folhas - em desenvolvimento");
                    executando = continuarMenu(scanner);
                }

                case 0 -> {
                    // Solicita confirmação antes de encerrar o sistema
                    if (confirmarSaida(scanner)) {
                        executando = false;
                        System.out.println("Encerrando o sistema...");
                    }
                }
            }
        }
    }

    private void exibirMenu() {
        // Responsável apenas por exibir o menu principal
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
            String entrada = scanner.nextLine(); // Lê como texto para evitar erro direto

            try {
                int opcao = Integer.parseInt(entrada); // Converte para número

                // Valida se está dentro das opções disponíveis
                if (opcao >= 0 && opcao <= 5) {
                    return opcao;
                } else {
                    System.out.println("Erro: opção inválida. Reveja as opções no menu.");
                }

            } catch (NumberFormatException e) {
                // Trata erro caso o usuário digite letras ou símbolos
                System.out.println("Erro: digite apenas números.");
            }
        }
    }

    private boolean continuarMenu(Scanner scanner) {
        // Pergunta se o usuário deseja retornar ao menu principal
        System.out.println("Deseja acessar o menu novamente? S/N ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("n")) {
            System.out.println(" Encerrando o Sistema...");
            return false; // Encerra o sistema
        }

        // Simula limpeza de tela para melhor visualização
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
        return true; // Continua no menu
    }

    private boolean confirmarSaida(Scanner scanner) {
        // Garante confirmação antes de sair do sistema
        while (true) {
            System.out.print("Deseja realmente sair? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equals("S"))
                return true;
            if (resposta.equals("N"))
                return false;

            System.out.println("Entrada inválida. Digite S ou N.");
        }
    }
}