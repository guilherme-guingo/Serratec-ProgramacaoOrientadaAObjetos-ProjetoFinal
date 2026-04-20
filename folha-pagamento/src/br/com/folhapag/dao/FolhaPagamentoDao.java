package br.com.folhapag.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.folhapag.config.Conexao;
import br.com.folhapag.model.FolhaPagamento;

public class FolhaPagamentoDao {

    public void salvar(FolhaPagamento folha) {
        String sql = "INSERT INTO folha_pagamento (funcionario_id, data_referencia, valor_inss, valor_ir, valor_liquido) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, folha.getFuncionarioId());
            stmt.setDate(2, java.sql.Date.valueOf(folha.getDataReferencia()));
            stmt.setDouble(3, folha.getValorInss());
            stmt.setDouble(4, folha.getValorIr());
            stmt.setDouble(5, folha.getValorLiquido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar folha: " + e.getMessage());
        }
    }

    /**
     * Busca o histórico usando JOIN para trazer o nome do funcionário.
     */
    public List<FolhaPagamento> listarHistorico() {
        List<FolhaPagamento> lista = new ArrayList<>();
        
        // SQL que une a tabela de folha (f) com a de funcionario (fun)
        String sql = "SELECT f.*, fun.nome as nome_func " +
                     "FROM folha_pagamento f " +
                     "INNER JOIN funcionario fun ON f.funcionario_id = fun.id " +
                     "ORDER BY f.id DESC";

        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- HISTÓRICO DE LANÇAMENTOS ---");
            System.out.printf("%-5s | %-20s | %-12s | %-10s%n", "ID", "FUNCIONÁRIO", "DATA", "LÍQUIDO");
            System.out.println("------------------------------------------------------------");

            while (rs.next()) {
                FolhaPagamento f = new FolhaPagamento();
                f.setId(rs.getInt("id"));
                f.setFuncionarioId(rs.getInt("funcionario_id"));
                f.setValorLiquido(rs.getDouble("valor_liquido"));
                
                Date sqlDate = rs.getDate("data_referencia");
                if (sqlDate != null) f.setDataReferencia(sqlDate.toLocalDate());

                // Captura o nome que veio do JOIN
                String nomeFuncionario = rs.getString("nome_func");

                // Imprime a linha formatada diretamente
                System.out.printf("%-5d | %-20s | %-12s | R$ %8.2f%n", 
                    f.getId(), 
                    nomeFuncionario, 
                    f.getDataReferencia(), 
                    f.getValorLiquido()
                );

                lista.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar histórico: " + e.getMessage());
        }
        return lista;
    }
}
