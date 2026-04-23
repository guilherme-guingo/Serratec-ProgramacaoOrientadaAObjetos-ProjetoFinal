package br.com.folhapag.dao;

import br.com.folhapag.model.FolhaPagamento;
import java.sql.*;

public class FolhaPagamentoDao {
    private Connection conn;

    public FolhaPagamentoDao(Connection conn) {
        this.conn = conn;
    }

    public void salvar(FolhaPagamento folha) throws SQLException {
        String sql = "INSERT INTO folha_pagamento (cpf_funcionario, data_emissao, valor_inss, valor_irrf, salario_liquido) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, folha.getFuncionario().getCpf());
            ps.setDate(2, Date.valueOf(folha.getData()));
            ps.setDouble(3, folha.getINSS());
            ps.setDouble(4, folha.getIR());
            ps.setDouble(5, folha.getSalarioLiquido());
            ps.executeUpdate();
        }
    }
    
    public void listarHistoricoFolhas() throws SQLException {
        String sql = "SELECT f.nome AS funcionario, fp.data_emissao, f.salario_bruto, " +
                     "fp.valor_inss, fp.valor_irrf, fp.salario_liquido, d.nome AS departamento " +
                     "FROM folha_pagamento fp " +
                     "JOIN funcionario f ON fp.cpf_funcionario = f.cpf " +
                     "JOIN departamento d ON f.id_departamento = d.id " +
                     "ORDER BY fp.data_emissao DESC, f.nome ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== HISTÓRICO DE FOLHAS DE PAGAMENTO ==========");
            System.out.printf("%-15s | %-12s | %-10s | %-10s | %-10s%n", 
                              "FUNCIONÁRIO", "DATA", "BRUTO", "LÍQUIDO", "DEPTO");
            System.out.println("------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s | %-12s | R$ %-8.2f | R$ %-8.2f | %-10s%n",
                    rs.getString("funcionario"),
                    rs.getDate("data_emissao").toString(),
                    rs.getDouble("salario_bruto"),
                    rs.getDouble("salario_liquido"),
                    rs.getString("departamento")
                );
            }
            System.out.println("======================================================\n");
        }
    }
}