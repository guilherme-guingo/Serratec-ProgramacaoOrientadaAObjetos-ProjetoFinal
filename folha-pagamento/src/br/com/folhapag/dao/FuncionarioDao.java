package br.com.folhapag.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.DataInvalida;
import br.com.folhapag.exception.NomeInvalido;
import br.com.folhapag.exception.SalarioInvalido;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Funcionario;

public class FuncionarioDao {
    private Connection conn;

    public FuncionarioDao(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Funcionario f) throws SQLException {
        String sql = "INSERT INTO funcionario (cpf, nome, nascimento, salario_bruto, id_departamento) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getCpf());
            ps.setString(2, f.getNome());
            ps.setDate(3, Date.valueOf(f.getNascimento())); // Converte LocalDate para SQL Date
            ps.setDouble(4, f.getSalarioBruto());
            ps.setInt(5, f.getDepartamento().getId()); // ID vindo do campo 5 do CSV
            ps.executeUpdate();
        }
    }  
    
    public List<Funcionario> listarPorDepartamento(int idDepto) throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario WHERE id_departamento = ? ORDER BY nome";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDepto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try {
                        // Convertendo a data com segurança
                        Date dataSql = rs.getDate("nascimento");
                        LocalDate dataLocal = (dataSql != null) ? dataSql.toLocalDate() : null;

                        // Criando o objeto - Aqui ele pode lançar SalarioInvalido ou CPFInvalido
                        Funcionario f = new Funcionario(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            dataLocal,
                            rs.getDouble("salario_bruto"),
                            new Departamento(idDepto)
                        );
                        
                        lista.add(f);
                    } catch (SalarioInvalido | CPFInvalido | NomeInvalido | DataInvalida e) {
                        // Se um funcionário no banco estiver com dados inválidos, 
                        // logamos o erro e pulamos para o próximo
                        System.err.println("Pulo de registro: " + e.getMessage());
                    }
                }
            }
        }
        return lista;
    }
}