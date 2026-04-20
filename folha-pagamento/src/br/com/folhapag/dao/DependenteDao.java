package br.com.folhapag.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import br.com.folhapag.config.Conexao;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.enums.Parentesco; // Importando o seu enum existente

public class DependenteDao {

    /**
     * SALVAR: Responsável pela inserção (Uso: Importação do CSV)
     */
    public void salvar(Dependente d, int funcionarioId) {
        String sqlPessoa = "INSERT INTO pessoa (nome, cpf, nascimento) VALUES (?, ?, ?)";
        String sqlDep = "INSERT INTO dependente (pessoa_id, funcionario_id, parentesco) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = Conexao.getConexaoDB();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtP = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                stmtP.setString(1, d.getNome());
                stmtP.setString(2, d.getCpf());
                stmtP.setObject(3, d.getDataNascimento());
                stmtP.executeUpdate();

                try (ResultSet rs = stmtP.getGeneratedKeys()) {
                    if (rs.next()) {
                        int pessoaId = rs.getInt(1);

                        try (PreparedStatement stmtD = conn.prepareStatement(sqlDep)) {
                            stmtD.setInt(1, pessoaId);
                            stmtD.setInt(2, funcionarioId);
                            // Salvando o nome do seu Enum (ex: 'FILHO')
                            stmtD.setString(3, d.getParentesco().name()); 
                            stmtD.executeUpdate();
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            throw new RuntimeException("Erro ao salvar dependente: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    /**
     * BUSCAR: Responsável pela leitura (Uso: Cálculo da Folha)
     */
    public List<Dependente> buscarPorFuncionario(int funcionarioId) {
        List<Dependente> lista = new ArrayList<>();
        
        // Faz o JOIN para pegar o nome/CPF na tabela pessoa e o parentesco na tabela dependente
        String sql = "SELECT p.nome, p.cpf, p.nascimento, d.parentesco " +
                     "FROM dependente d " +
                     "INNER JOIN pessoa p ON d.pessoa_id = p.id " +
                     "WHERE d.funcionario_id = ?";

        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionarioId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Dependente d = new Dependente();
                    d.setNome(rs.getString("nome"));
                    d.setCpf(rs.getString("cpf"));
                    d.setDataNascimento(rs.getObject("nascimento", LocalDate.class));
                    
                    // Converte o texto do banco de volta para o seu enum Parentesco
                    String pStr = rs.getString("parentesco");
                    if (pStr != null) {
                        d.setParentesco(Parentesco.valueOf(pStr.toUpperCase()));
                    }
                    
                    lista.add(d);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dependentes: " + e.getMessage());
        }
        return lista;
    }
}