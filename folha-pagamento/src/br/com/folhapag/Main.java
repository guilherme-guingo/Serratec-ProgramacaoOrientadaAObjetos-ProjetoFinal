package br.com.folhapag;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.service.ImportacaoCsvService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Caminho do arquivo csv
        String caminhoArquivo = "/home/jose/Documents/TRABALHO_POO_FOLHAPAG/Serratec-ProgramacaoOrientadaAObjetos-ProjetoFinal/dados.csv"; 

        System.out.println("=== SISTEMA DE FOLHA DE PAGAMENTO: INICIANDO ===");

        // 2. Executa a Importação
        try {
            ImportacaoCsvService service = new ImportacaoCsvService();
            System.out.println("-> Importando dados do CSV...");
            service.importar(caminhoArquivo);
            System.out.println("-> Importação concluída com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro crítico na importação: " + e.getMessage());
        }

        // 3. Executa as Pesquisas para Validação
        System.out.println("\n=== RELATÓRIO DE CONFERÊNCIA (BANCO DE DADOS) ===");
        
        try (Connection conn = Conexao.getConexaoDB()) {
            DepartamentoDao deptoDAO = new DepartamentoDao(conn);
            FuncionarioDao funcDAO = new FuncionarioDao(conn);

            // Busca todos os departamentos
            List<Departamento> departamentos = deptoDAO.listarTodos();

            for (Departamento d : departamentos) {
                System.out.println("\nDEPARTAMENTO: " + d.getNome().toUpperCase());
                
                // Busca funcionários do departamento atual
                List<Funcionario> funcionarios = funcDAO.listarPorDepartamento(d.getId());

                if (funcionarios.isEmpty()) {
                    System.out.println("   [Sem funcionários registados]");
                } else {
                    for (Funcionario f : funcionarios) {
                        System.out.printf("   ID: %d | Nome: %-15s | Salário: R$ %.2f%n", 
                            d.getId(), f.getNome(), f.getSalarioBruto());
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao aceder ao banco de dados: " + e.getMessage());
        }

        System.out.println("\n=== PROCESSO FINALIZADO ===");
    }
}