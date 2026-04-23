package br.com.folhapag.utils;

import br.com.folhapag.model.FolhaPagamento;

public class FolhaVisualizacaoUtils {

    public static void exibirRelatorio(FolhaPagamento folha) {
        int qtdDependentes = folha.getFuncionario().getDependentes().size();

        String textoDependente = (qtdDependentes == 1) ? "dependente" : "dependentes";

        System.out.println("\n|==========================================================|");
        System.out.println("|             RELATÓRIO DA FOLHA DE PAGAMENTO              |");
        System.out.println("|==========================================================|");
        System.out.printf(
                "  Funcionário: %s (%d %s)\n" +
                        "  Salário Bruto: R$ %.2f\n" +
                        "  INSS: R$ %.2f\n" +
                        "  IRRF: R$ %.2f\n" +
                        "  Salário Líquido: R$ %.2f\n",
                folha.getFuncionario().getNome(),
                qtdDependentes,
                textoDependente,
                folha.getFuncionario().getSalarioBruto(),
                folha.getINSS(),
                folha.getIR(),
                folha.getSalarioLiquido());
        System.out.println("|==========================================================|");
    }
}