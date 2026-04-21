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
}