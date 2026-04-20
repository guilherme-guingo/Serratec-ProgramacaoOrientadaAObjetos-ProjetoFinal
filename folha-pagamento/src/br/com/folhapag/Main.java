package br.com.folhapag;

import java.util.Scanner;
import java.util.List; // OBRIGATÓRIO
import br.com.folhapag.model.FolhaPagamento; // OBRIGATÓRIO
import br.com.folhapag.service.ProcessamentoService;
import br.com.folhapag.util.LeitorCsv;
import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.dao.FolhaPagamentoDao;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Instanciando os serviços e DAOs
        ProcessamentoService processamentoService = new ProcessamentoService();
        LeitorCsv leitorCsv = new LeitorCsv();
        DepartamentoDao deptoDao = new DepartamentoDao();
        FuncionarioDao funcDao = new FuncionarioDao();
        FolhaPagamentoDao folhaDao = new FolhaPagamentoDao();

        int opcao;

        do {
            System.out.println("\n========================================================");
            System.out.println("            SISTEMA DE PAGAMENTOS - SERRATEC            ");
            System.out.println("========================================================");
            System.out.println("\n--- PROCESSAMENTO DE DADOS ---");
            System.out.println(" 1. Calcular Folha em Lote (Via Arquivo CSV)");
            System.out.println(" 2. Calcular Folha Avulsa (Cadastro Manual)");
            System.out.println("\n--- RELATÓRIOS DO BANCO DE DADOS ---");
            System.out.println(" 3. Listar Todos os Departamentos");
            System.out.println(" 4. Listar Funcionários por Departamento");
            System.out.println(" 5. Listar Histórico de Folhas de Pagamento");
            System.out.println("\n 0. Sair do Sistema");
            System.out.println("========================================================");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Informe o caminho do arquivo CSV: ");
                    String caminho = scanner.nextLine();
                    leitorCsv.importar(caminho);
                    // O service agora processa o que o leitor acabou de salvar
                    processamentoService.calcularTodos(); 
                    break;

                case 3:
                    deptoDao.listarTodos().forEach(System.out::println);
                    break;

                case 4:
                    System.out.print("Informe o ID do Departamento: ");
                    int idDepto = scanner.nextInt();
                    funcDao.buscarPorDepartamento(idDepto).forEach(System.out::println);
                    break;
                
                case 5:
                    // Como o seu folhaDao.listarHistorico() já tem os System.out.printf,
                    // basta chamar o método. Ele fará o JOIN e mostrará os nomes.
                    List<FolhaPagamento> historico = folhaDao.listarHistorico();
                    if (historico.isEmpty()) {
                        System.out.println("Nenhum registro de folha encontrado no banco.");
                    }
                    break;
                    
                case 0:
                    System.out.println("Encerrando o sistema SERRATEC. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}