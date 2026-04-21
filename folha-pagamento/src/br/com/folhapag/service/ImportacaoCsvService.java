package br.com.folhapag.service;

import br.com.folhapag.dao.*;
import br.com.folhapag.model.*;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.config.Conexao;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

public class ImportacaoCsvService {

    public void importar(String caminhoArquivo) {
        // 1. Obtém a conexão
        try (Connection conn = Conexao.getConexaoDB()) {
            
            // 2. Desativa o auto-commit para garantir que o arquivo seja processado como um todo
            conn.setAutoCommit(false);

            FuncionarioDao funcDAO = new FuncionarioDao(conn);
            DependenteDao depDAO = new DependenteDao(conn);
            Funcionario funcionarioAtual = null;

            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    if (linha.trim().isEmpty()) continue; // Ignora linhas vazias

                    String[] campos = linha.split(";");

                    // LAYOUT: 5 CAMPOS = FUNCIONÁRIO
                    if (campos.length == 5) {
                        funcionarioAtual = new Funcionario(
                            campos[0],                         // Nome
                            LocalDate.parse(campos[2]),        // Nascimento
                            campos[1],                         // CPF
                            Double.parseDouble(campos[3]),     // Salário
                            new Departamento(Integer.parseInt(campos[4])) // ID do Depto
                        );
                        
                        // Salva o funcionário primeiro
                        funcDAO.salvar(funcionarioAtual);
                    } 
                    // LAYOUT: 4 CAMPOS = DEPENDENTE
                    else if (campos.length == 4 && funcionarioAtual != null) {
                        Dependente dep = new Dependente(
                            campos[0],                         // Nome
                            LocalDate.parse(campos[2]),        // Nascimento
                            campos[1],                         // CPF
                            Parentesco.valueOf(campos[3].toUpperCase()), // Enum
                            funcionarioAtual                   // Vínculo com o objeto em memória
                        );
                        
                        depDAO.salvar(dep);
                    }
                }
            }

            // 3. gravação no banco
            conn.commit();
            System.out.println("Importação do arquivo '" + caminhoArquivo + "' finalizada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro crítico na importação: " + e.getMessage());
            // fecha a conexão e o banco faz rollback automático se não houve commit
        }
    }
}