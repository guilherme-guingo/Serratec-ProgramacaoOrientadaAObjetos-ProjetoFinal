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
            
            --Inserção dos membros do grupo para testes
            INSERT INTO funcionario (nome, cpf, nascimento, salario_bruto, id_departamento) VALUES
            ('Guilherme',    '10120230344', '1995-04-10', 2100.00, 8), -- Isenção Total
            ('Patrick',      '40450560677', '1988-11-22', 3500.00, 2), -- Faixa 2 + 1 Dep.
            ('Jose Ricardo', '20230340455', '1980-01-30', 9200.00, 1), -- Teto de INSS
            ('Liliane',      '50560670788', '1990-06-12', 5800.00, 3), -- Faixa Média + 3 Deps.
            ('Nicolas',      '30340450566', '1992-09-15', 4200.00, 5)  -- Comercial + 1 Dep.
            ON CONFLICT (cpf) DO NOTHING;

            --Inserção de Dependentes para os membros
            INSERT INTO dependente (nome, cpf, nascimento, parentesco, cpf_funcionario) VALUES
            -- Dependente do Patrick
            ('Pedro Silva', '70780890911', '2018-05-15', 'FILHOS', '40450560677'),

            --Dependentes da Liliane (Múltiplos)
            ('Enzo Souza', '80890910122', '2015-03-20', 'FILHOS', '50560670788'),
            ('Valentina Souza', '90910120233', '2020-08-10', 'FILHOS', '50560670788'),
            ('Jorge Souza', '12123234354', '1988-10-05', 'CONJUGE', '50560670788'),

            --Dependente do Nicolas
            ('Maria Santos', '60670780899', '2012-12-01', 'FILHOS', '30340450566')
            ON CONFLICT (cpf) DO NOTHING;
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