package br.com.folhapag.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;

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

    public List<FolhaPagamento> listarTodos() throws SQLException {
        List<FolhaPagamento> lista = new ArrayList<>();

        String sql = "SELECT f.nome AS nome_func, f.salario_bruto, f.id_departamento, " +
                "d.nome AS nome_dep, fp.* " +
                "FROM folha_pagamento fp " +
                "JOIN funcionario f ON fp.cpf_funcionario = f.cpf " +
                "JOIN departamento d ON f.id_departamento = d.id " +
                "ORDER BY fp.data_emissao DESC, f.nome ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                try {
                    Departamento depto = new Departamento(rs.getInt("id_departamento"));
                    depto.setNome(rs.getString("nome_dep"));

                    Funcionario func = new Funcionario(
                            rs.getString("nome_func"),
                            rs.getString("cpf_funcionario"),
                            rs.getDate("data_emissao").toLocalDate(), // Usando a data da folha como referência
                            rs.getDouble("salario_bruto"),
                            depto
                    );

                    FolhaPagamento fp = new FolhaPagamento();
                    fp.setFuncionario(func);
                    fp.setData(rs.getDate("data_emissao").toLocalDate());
                    fp.setINSS(rs.getDouble("valor_inss"));
                    fp.setIR(rs.getDouble("valor_irrf"));
                    fp.setSalarioLiquido(rs.getDouble("salario_liquido"));

                    lista.add(fp);

                } catch (Exception e) {
                    System.out.println("⚠️ Registro pulado: " + e.getMessage());
                }
            }
        }
        return lista;
    }
}