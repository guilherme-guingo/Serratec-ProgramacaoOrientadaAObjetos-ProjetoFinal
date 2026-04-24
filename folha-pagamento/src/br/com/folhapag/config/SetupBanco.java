package br.com.folhapag.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupBanco {

    public static void inicializarTabelas() {
        String sql = """
            CREATE TABLE IF NOT EXISTS departamento (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL UNIQUE
            );
        
            CREATE TABLE IF NOT EXISTS funcionario (
                cpf CHAR(11) PRIMARY KEY,
                nome VARCHAR(150) NOT NULL,
                nascimento DATE NOT NULL,
                salario_bruto DECIMAL(10, 2) NOT NULL,
                id_departamento INTEGER NOT NULL,
                CONSTRAINT fk_depto FOREIGN KEY (id_departamento) REFERENCES departamento(id)
            );
            
            
            CREATE TABLE IF NOT EXISTS dependente (
            	    cpf CHAR(11) PRIMARY KEY,
            	    nome VARCHAR(150) NOT NULL,
            	    nascimento DATE NOT NULL,
            	    parentesco VARCHAR(30) NOT NULL, 
            	    cpf_funcionario CHAR(11) NOT NULL,
            	    CONSTRAINT fk_func_dep FOREIGN KEY (cpf_funcionario) REFERENCES funcionario(cpf) ON DELETE CASCADE,
            	    CONSTRAINT chk_parentesco CHECK (parentesco IN ('PAIS', 'FILHOS', 'CONJUGE', 'OUTROS'))
            );

             CREATE TABLE IF NOT EXISTS folha_pagamento (
                id SERIAL PRIMARY KEY,
                cpf_funcionario CHAR(11) NOT NULL,
                data_emissao DATE NOT NULL,
                valor_inss DECIMAL(10, 2) NOT NULL,
                valor_irrf DECIMAL(10, 2) NOT NULL,
                salario_liquido DECIMAL(10, 2) NOT NULL,
                CONSTRAINT fk_func_folha FOREIGN KEY (cpf_funcionario) REFERENCES funcionario(cpf) ON DELETE CASCADE,
                CONSTRAINT folha_func_data UNIQUE (cpf_funcionario, data_emissao)
            );
            CREATE UNIQUE INDEX IF NOT EXISTS uq_folha_mensal on folha_pagamento(cpf_funcionario, EXTRACT (MONTH FROM data_emissao));
            
            INSERT INTO departamento (id, nome) VALUES 
            (1, 'Recursos Humanos'),
            (2, 'Tecnologia'), 
            (3, 'Financeiro'),
            (4, 'Marketing'), 
            (5, 'Logística'), 
            (6, 'Vendas'),
            (7, 'Diretoria'), 
            (8, 'Operações')
            ON CONFLICT (nome) do nothing;
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