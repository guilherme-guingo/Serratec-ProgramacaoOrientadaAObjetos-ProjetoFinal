package br.com.folhapag.dao;

import br.com.folhapag.model.Dependente;
import java.sql.*;

public class DependenteDao {
    private Connection conn;

    public DependenteDao(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Dependente d) throws SQLException {
        String sql = "INSERT INTO dependente (cpf, nome, nascimento, parentesco, cpf_funcionario) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getCpf());
            ps.setString(2, d.getNome());
            ps.setDate(3, Date.valueOf(d.getNascimento()));
            // CONVERSÃO: Pega o nome da constante do Enum Parentesco
            ps.setString(4, d.getParentesco().name()); 
            ps.setString(5, d.getFuncionario().getCpf());
            ps.executeUpdate();
        }
    }
}