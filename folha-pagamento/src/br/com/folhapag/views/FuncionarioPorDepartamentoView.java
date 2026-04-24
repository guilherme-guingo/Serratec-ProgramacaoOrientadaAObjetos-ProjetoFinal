package br.com.folhapag.views;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import br.com.folhapag.dao.FuncionarioDao;
import br.com.folhapag.exceptions.DepartamentoNaoEncontrado;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.utils.ValidarDepartamento;

public class FuncionarioPorDepartamentoView {

    public void exibir(Scanner sc, Connection conn) {
        System.out.println("\n--- FILTRAR FUNCIONÁRIOS POR SETOR ---");
        System.out.print("Informe o ID ou Nome do departamento: ");
        String entrada = sc.nextLine();

        try {
            Departamento depto = ValidarDepartamento.validar(entrada, conn);

            FuncionarioDao funcDao = new FuncionarioDao(conn);
            List<Funcionario> funcionarios = funcDao.listarPorDepartamento(depto.getId());

            System.out.println("\n|==========================================================|");
            System.out.printf("| SETOR: %-49s |\n", depto.getNome().toUpperCase());
            System.out.println("|==========================================================|");
            System.out.printf("| %-14s | %-25s | %-12s |\n", "CPF", "NOME", "DEPENDENTES");
            System.out.println("|----------------|---------------------------|--------------|");

            if (funcionarios.isEmpty()) {
                System.out.println("|        Nenhum funcionário alocado neste setor.           |");
            } else {
                for (Funcionario f : funcionarios) {
                    int qtdDep = f.getDependentes().size();
                    String termoDep = (qtdDep == 1) ? "dep." : "deps.";

                    System.out.printf("| %-14s | %-25s | %2d %-7s |\n",
                            f.getCpf(),
                            f.getNome(),
                            qtdDep,
                            termoDep
                    );
                }
            }
            System.out.println("|==========================================================|\n");

        } catch (DepartamentoNaoEncontrado e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro de banco de dados ao listar funcionários.");
        }
    }
}