package br.com.folhapag.views;

import java.sql.Connection;
import java.util.List;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.service.FolhaPagamentoService;

public class HistoricoFolhasView {

    public void exibir(Connection conn) {
        FuncionarioDao funcDao = new FuncionarioDao(conn);
        FolhaPagamentoService service = new FolhaPagamentoService();

        try {
            List<Funcionario> funcionarios = funcDao.listarTodosComDependetes();

            System.out.println("\n|================================================================================|");
            System.out.println("|                PROCESSAMENTO GERAL - TODAS AS FOLHAS CADASTRADAS               |");
            System.out.println("|================================================================================|");
            System.out.printf("| %-18s | %-12s | %-10s | %-10s | %-15s |\n",
                    "FUNCIONÁRIO", "DEPENDENTES", "INSS", "IRRF", "LÍQUIDO");
            System.out.println("|--------------------|--------------|------------|------------|-----------------|");

            if (funcionarios.isEmpty()) {
                System.out.println("|                Nenhum funcionário cadastrado no sistema.                       |");
            } else {
                for (Funcionario f : funcionarios) {
                    FolhaPagamento fp = service.folhaCalculo(f);

                    int qtdDep = f.getDependentes().size();
                    String termoDep = (qtdDep == 1) ? "dep." : "deps.";

                    System.out.printf("| %-18s | %2d %-8s | R$ %-7.2f | R$ %-7.2f | R$ %-10.2f |\n",
                            f.getNome(),
                            qtdDep,
                            termoDep,
                            fp.getINSS(),
                            fp.getIR(),
                            fp.getSalarioLiquido()
                    );
                }
            }
            System.out.println("|================================================================================|\n");

        } catch (Exception e) {
            System.out.println("Erro ao processar cálculo geral: " + e.getMessage());
        }
    }
}