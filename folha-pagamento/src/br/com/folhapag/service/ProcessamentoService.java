package br.com.folhapag.service;

import java.util.List;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.dao.FolhaPagamentoDao;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.model.FolhaPagamento;

public class ProcessamentoService {

    private FuncionarioDao funcionarioDao = new FuncionarioDao();
    private FolhaPagamentoDao folhaDao = new FolhaPagamentoDao();
    private InssService inssService = new InssService();
    private IrrfService irrfService = new IrrfService();

    /**
     * Processa todos os funcionários cadastrados de uma única vez.
     */
    public void calcularTodos() {
        List<Funcionario> funcionarios = funcionarioDao.buscarTodos();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado no banco para processar.");
            return;
        }

        System.out.println("Iniciando processamento geral da folha...");

        for (Funcionario f : funcionarios) {
            try {
                // Cálculos baseados no objeto completo (com dependentes)
                double valorInss = inssService.calcular(f);
                double valorIr = irrfService.calcular(f);
                double valorLiquido = f.getSalarioBruto() - valorInss - valorIr;

                // Montagem do registro de saída
                FolhaPagamento folha = new FolhaPagamento();
                folha.setFuncionarioId(f.getId());
                folha.setValorInss(valorInss);
                folha.setValorIr(valorIr);
                folha.setValorLiquido(valorLiquido);

                folhaDao.salvar(folha);
                
                System.out.println(f.getNome() + " | Líquido: R$ " + String.format("%.2f", valorLiquido));

            } catch (Exception e) {
                System.err.println("Erro ao calcular " + f.getNome() + ": " + e.getMessage());
            }
        }
        System.out.println("--- Processamento Finalizado ---");
    }
}