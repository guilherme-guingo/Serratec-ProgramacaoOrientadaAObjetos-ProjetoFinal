package br.com.folhapag.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupBanco {

    public static void inicializarTabelas() {
        String sql = """
            CREATE TABLE IF NOT EXISTS departamento (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL
            );

            CREATE TABLE IF NOT EXISTS funcionario (
                cpf VARCHAR(14) PRIMARY KEY,
                nome VARCHAR(150) NOT NULL,
                nascimento DATE NOT NULL,
                salario_bruto NUMERIC(10, 2) NOT NULL,
                id_departamento INT REFERENCES departamento(id)
            );

            CREATE TABLE IF NOT EXISTS dependente (
                cpf VARCHAR(14) PRIMARY KEY,
                nome VARCHAR(150) NOT NULL,
                nascimento DATE NOT NULL,
                parentesco VARCHAR(50) NOT NULL,
                cpf_funcionario VARCHAR(14) REFERENCES funcionario(cpf)
            );

            CREATE TABLE IF NOT EXISTS folha_pagamento (
                id SERIAL PRIMARY KEY,
                cpf_funcionario VARCHAR(14) REFERENCES funcionario(cpf),
                data_emissao DATE NOT NULL,
                valor_inss NUMERIC(10, 2) NOT NULL,
                valor_irrf NUMERIC(10, 2) NOT NULL,
                salario_liquido NUMERIC(10, 2) NOT NULL
            );

            INSERT INTO departamento (id, nome) VALUES 
            (1, 'Recursos Humanos'), (2, 'Tecnologia'), (3, 'Financeiro'),
            (4, 'Marketing'), (5, 'Logística'), (6, 'Vendas'),
            (7, 'Diretoria'), (8, 'Operações')
            ON CONFLICT (id) DO NOTHING;
            """;

        try (Connection conn = Conexao.getConexaoDB();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Banco de dados verificado e inicializado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas no banco de dados: " + e.getMessage());
        }
    }
}