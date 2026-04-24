package br.com.folhapag.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.model.Departamento;

public class DepartamentoDao {
    private Connection conn;

    public DepartamentoDao(Connection conn) {
        this.conn = conn;
    }

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

    
    public int buscarIdPorNome(String nome) {
        String sql = "SELECT id FROM departamento WHERE UPPER(nome) = UPPER(?)";
        
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, nome.trim());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID do departamento [" + nome + "]: " + e.getMessage());
        }
        return -1;
    }

    public Departamento buscarPorId(int id) {
        String sql = "SELECT id, nome FROM departamento WHERE id = ?";
        
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Departamento d = new Departamento(rs.getInt("id"));
                    d.setNome(rs.getString("nome"));
                    return d;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar departamento [ID: " + id + "]: " + e.getMessage());
        }
        return null;
    }

}