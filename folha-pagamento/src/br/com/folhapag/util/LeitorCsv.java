package br.com.folhapag.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.dao.DependenteDao;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.enums.Parentesco;

public class LeitorCsv {

    private FuncionarioDao funcDao = new FuncionarioDao();
    private DependenteDao depDao = new DependenteDao();

    public void importar(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int contador = 0;
            
            // Pula o cabeçalho do CSV
            br.readLine(); 

            while ((linha = br.readLine()) != null) {
                contador++;
                if (linha.trim().isEmpty()) continue;

                // 1. SEPARADOR: Usando ";" que é o padrão que causou o erro anterior
                String[] campos = linha.split(";");

                // 2. VALIDAÇÃO: Garante que a linha tem os campos mínimos
                if (campos.length < 3) {
                    System.err.println("⚠️ Linha " + contador + " ignorada: colunas insuficientes.");
                    continue;
                }

                try {
                    String nome = campos[0].trim();
                    String cpf  = campos[1].trim();
                    
                    // 3. TRATAMENTO DO SALÁRIO:
                    // Se o seu CSV for: Nome;CPF;Salario;Parentesco, o salário é campos[2]
                    double salario = 0.0;
                    String valorSalario = campos[2].trim().replace(",", "."); // Troca vírgula por ponto
                    
                    try {
                        salario = Double.parseDouble(valorSalario);
                    } catch (NumberFormatException e) {
                        // Se falhar (ex: encontrou uma data ou texto), define um padrão para não travar
                        salario = 2500.00;
                        System.out.println("ℹ️ Salário inválido na linha " + contador + " (" + valorSalario + "). Usando R$ 2500,00.");
                    }

                    // 4. PERSISTÊNCIA DO FUNCIONÁRIO
                    Funcionario f = new Funcionario(0, nome, cpf, salario);
                    int idGerado = funcDao.salvar(f);

                    // 5. LÓGICA DE DEPENDENTE: Só executa se houver a 4ª coluna (índice 3)
                    if (idGerado > 0 && campos.length >= 4 && !campos[3].trim().isEmpty()) {
                        salvarDependente(campos[3].trim(), idGerado);
                    }

                } catch (Exception e) {
                    System.err.println("❌ Erro ao processar linha " + contador + ": " + e.getMessage());
                }
            }
            System.out.println("✅ Importação finalizada!");

        } catch (IOException e) {
            System.err.println("❌ Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private void salvarDependente(String parentescoTexto, int idFunc) {
        try {
            Dependente d = new Dependente();
            d.setNome("Dependente de " + idFunc); // Nome genérico para teste
            d.setParentesco(Parentesco.valueOf(parentescoTexto.toUpperCase()));
            depDao.salvar(d, idFunc);
        } catch (Exception e) {
            System.err.println("⚠️ Erro ao salvar dependente: " + e.getMessage());
        }
    }
}