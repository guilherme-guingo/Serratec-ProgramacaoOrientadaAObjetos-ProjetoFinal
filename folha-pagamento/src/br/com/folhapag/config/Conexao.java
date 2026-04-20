package br.com.folhapag.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // No Linux, o PostgreSQL geralmente roda na porta 5432
    private static final String URL = "jdbc:postgresql://localhost:5432/dbfolhapag";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "asdqwe";

    public static Connection getConexaoDB() {
        try {
            // Esta linha registra o tradutor (driver) que o Java estava sentindo falta
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do PostgreSQL não encontrado no projeto!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco! Verifique se o Postgres está rodando e se a senha está correta.", e);
        }
    }
}
