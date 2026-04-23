package br.com.folhapag.dao;

import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.enums.Parentesco;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            ps.setString(4, d.getParentesco().name()); 
            ps.setString(5, d.getFuncionario().getCpf());
            ps.executeUpdate();
        }
    }

    public List<Dependente> buscarPorFuncionario(Funcionario titular) throws SQLException {
        List<Dependente> dependentes = new ArrayList<>();
        String sql = "SELECT * FROM dependente WHERE cpf_funcionario = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, titular.getCpf());
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try {
                        // Capturando os dados do banco
                        String nome = rs.getString("nome");
                        String cpf = rs.getString("cpf");
                        LocalDate nascimento = rs.getDate("nascimento").toLocalDate();
                        Parentesco parentesco = Parentesco.valueOf(rs.getString("parentesco").toUpperCase());

                        // CRIANDO O OBJETO NA ORDEM EXATA: Nome, CPF, Nascimento, Parentesco, Funcionario
                        Dependente dep = new Dependente(
                            nome, 
                            cpf, 
                            nascimento, 
                            parentesco, 
                            titular // Passamos o titular que veio por parâmetro
                        );
                        
                        dependentes.add(dep);

                    } catch (Exception e) {
                        // Captura NomeInvalido, CPFInvalido, etc., e pula o dependente ruim
                        System.err.println("Erro ao carregar dependente: " + e.getMessage());
                    }
                }
            }
        }
        return dependentes;
    }
}