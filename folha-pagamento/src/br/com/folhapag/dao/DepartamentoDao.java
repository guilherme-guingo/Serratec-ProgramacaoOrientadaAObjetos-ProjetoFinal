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

    /**
     * Insere um novo departamento no banco de dados.
     */
    public void salvar(Departamento d) {
        String sql = "INSERT INTO departamento (nome) VALUES (?)";
        
        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, d.getNome());
            stmt.executeUpdate();
            System.out.println("Departamento salvo com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar departamento: " + e.getMessage());
        }
    }

    /**
     * Retorna todos os departamentos para a Opção 3 do Menu.
     * Retorna uma lista vazia em caso de erro para evitar quebras no Main.
     */
    public List<Departamento> listarTodos() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT id, nome FROM departamento ORDER BY nome";

        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departamentos.add(new Departamento(
                    rs.getInt("id"),
                    rs.getString("nome")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar departamentos: " + e.getMessage());
        }
        return departamentos;
    }

    /**
     * Busca o ID pelo nome (usado na importação do CSV).
     * Retorna -1 se não encontrar.
     */
    public int buscarIdPorNome(String nome) {
        String sql = "SELECT id FROM departamento WHERE UPPER(nome) = UPPER(?)";
        
        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nome.trim());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID do departamento: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Busca um objeto Departamento completo pelo ID.
     */
    public Departamento buscarPorId(int id) {
        String sql = "SELECT id, nome FROM departamento WHERE id = ?";
        
        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Departamento(rs.getInt("id"), rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar departamento por ID: " + e.getMessage());
        }
        return null;
    }
}