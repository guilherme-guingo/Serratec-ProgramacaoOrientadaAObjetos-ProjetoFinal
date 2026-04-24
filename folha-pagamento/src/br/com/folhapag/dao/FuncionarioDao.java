package br.com.folhapag.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.exceptions.CPFInvalido;
import br.com.folhapag.exceptions.DataInvalida;
import br.com.folhapag.exceptions.NomeInvalido;
import br.com.folhapag.exceptions.SalarioInvalido;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
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
            ps.setDate(3, Date.valueOf(f.getNascimento()));
            ps.setDouble(4, f.getSalarioBruto());
            ps.setInt(5, f.getDepartamento().getId());
            ps.executeUpdate();
        }
    }
    
    public boolean existeCpf(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM funcionario WHERE cpf = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public Funcionario buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE cpf = ?";
        
        DepartamentoDao deptoDao = new DepartamentoDao(this.conn);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idDepto = rs.getInt("id_departamento");
                    Departamento depto = deptoDao.buscarPorId(idDepto);

                    Date dataSql = rs.getDate("nascimento");
                    LocalDate dataLocal = (dataSql != null) ? dataSql.toLocalDate() : null;

                    return new Funcionario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        dataLocal,
                        rs.getDouble("salario_bruto"),
                        depto
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar funcionário por CPF: " + e.getMessage());
        }
        return null;
    }

    public List<Funcionario> listarTodosComDependetes() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.*, d.nome AS nome_dep FROM funcionario f " +
                "JOIN departamento d ON f.id_departamento = d.id ORDER BY f.nome";

        DependenteDao depDao = new DependenteDao(this.conn);

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                try {
                    Departamento depto = new Departamento(rs.getInt("id_departamento"));
                    depto.setNome(rs.getString("nome_dep"));

                    Funcionario f = new Funcionario(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getDate("nascimento").toLocalDate(),
                            rs.getDouble("salario_bruto"),
                            depto
                    );

                    f.setDependentes(depDao.buscarPorFuncionario(f));
                    lista.add(f);

                } catch (Exception e) {
                    System.out.println("Erro ao carregar funcionário " + rs.getString("nome") + ": " + e.getMessage());
                }
            }
        }
        return lista;
    }

    public List<Funcionario> listarPorDepartamento(int idDepto) throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario WHERE id_departamento = ? ORDER BY nome";

        DepartamentoDao deptoDao = new DepartamentoDao(this.conn);
        DependenteDao depDao = new DependenteDao(this.conn);

        Departamento deptoCompleto = deptoDao.buscarPorId(idDepto);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDepto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    try {
                        Date dataSql = rs.getDate("nascimento");
                        LocalDate dataLocal = (dataSql != null) ? dataSql.toLocalDate() : null;

                        Funcionario f = new Funcionario(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            dataLocal,
                            rs.getDouble("salario_bruto"),
                            deptoCompleto
                        );
                        
                        List<Dependente> dependentes = depDao.buscarPorFuncionario(f);
                        
                        f.setDependentes(dependentes); 
                        
                        lista.add(f);

                    } catch (SalarioInvalido | CPFInvalido | NomeInvalido | DataInvalida e) {
                        System.err.println("Pulo de registro: " + e.getMessage());
                    }
                }
            }
        }
        return lista;
    }
    
    public void listarResumoFuncionarios() throws SQLException {
        String sql = "SELECT f.nome AS funcionario, f.cpf, f.salario_bruto, d.nome AS departamento " +
                     "FROM funcionario f " +
                     "JOIN departamento d ON f.id_departamento = d.id " +
                     "ORDER BY f.nome";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== RESUMO DE FUNCIONÁRIOS POR SETOR ==========");
            System.out.printf("%-20s | %-15s | %-12s | %-15s%n", 
                              "NOME", "CPF", "SALÁRIO", "DEPARTAMENTO");
            System.out.println("----------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-20s | %-15s | R$ %-10.2f | %-15s%n",
                    rs.getString("funcionario"),
                    rs.getString("cpf"),
                    rs.getDouble("salario_bruto"),
                    rs.getString("departamento")
                );
            }
            System.out.println("======================================================================\n");
        }
    }
}
