package br.com.folhapag.dao;

import br.com.folhapag.model.Departamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDao {
    private Connection conn;

    public DepartamentoDao(Connection conn) {
        this.conn = conn;
    }

    // Método para a pesquisa de todos os departamentos
    public List<Departamento> listarTodos() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT id, nome FROM departamento ORDER BY nome";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Departamento d = new Departamento(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                departamentos.add(d);
            }
        }
        return departamentos;
    }
}