package br.com.folhapag.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.folhapag.config.Conexao;
import br.com.folhapag.model.Funcionario;

public class FuncionarioDao {

    public int salvar(Funcionario f) {
        // SQL atualizado com a coluna nascimento
        String sqlPessoa = "INSERT INTO pessoa (nome, cpf, nascimento) VALUES (?, ?, ?) RETURNING id";
        String sqlFunc = "INSERT INTO funcionario (pessoa_id, departamento_id, salario_bruto) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexaoDB()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtP = conn.prepareStatement(sqlPessoa)) {
                stmtP.setString(1, f.getNome());
                stmtP.setString(2, f.getCpf());
                // Conversão crucial: LocalDate -> java.sql.Date
                stmtP.setDate(3, java.sql.Date.valueOf(f.getNascimento()));
                
                ResultSet rsP = stmtP.executeQuery();
                if (rsP.next()) {
                    int pessoaId = rsP.getInt(1);

                    try (PreparedStatement stmtF = conn.prepareStatement(sqlFunc, Statement.RETURN_GENERATED_KEYS)) {
                        stmtF.setInt(1, pessoaId);
                        stmtF.setInt(2, f.getDepartamentoId());
                        stmtF.setDouble(3, f.getSalarioBruto());
                        stmtF.executeUpdate();

                        conn.commit();
                        ResultSet rsF = stmtF.getGeneratedKeys();
                        return rsF.next() ? rsF.getInt(1) : 0;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("❌ Erro na transação: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro de conexão: " + e.getMessage());
        }
        return 0;
    }

    public List<Funcionario> buscarPorDepartamento(int idDepto) {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.id, p.nome, p.cpf, p.nascimento, f.salario_bruto " +
                     "FROM funcionario f " +
                     "INNER JOIN pessoa p ON f.pessoa_id = p.id " +
                     "WHERE f.departamento_id = ?";

        try (Connection conn = Conexao.getConexaoDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idDepto);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setNome(rs.getString("nome"));
                    f.setCpf(rs.getString("cpf"));
                    // Volta do banco para o Java
                    f.setNascimento(rs.getDate("nascimento").toLocalDate());
                    f.setSalarioBruto(rs.getDouble("salario_bruto"));
                    f.setDepartamentoId(idDepto);
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar: " + e.getMessage());
        }
        return lista;
    }
}