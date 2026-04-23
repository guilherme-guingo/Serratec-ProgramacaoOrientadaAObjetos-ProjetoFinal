package br.com.folhapag.views;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.model.Departamento;

public class DepartamentoView {

    public void listar(Connection conn) {
        DepartamentoDao depDao = new DepartamentoDao(conn);

        try {
            List<Departamento> lista = depDao.listarTodos();

            System.out.println("\n|==========================================================|");
            System.out.println("|                LISTAGEM DE DEPARTAMENTOS                 |");
            System.out.println("|==========================================================|");
            System.out.printf("| %-10s | %-43s |\n", "ID", "NOME DO DEPARTAMENTO");
            System.out.println("|------------|---------------------------------------------|");

            if (lista.isEmpty()) {
                System.out.println("|          Nenhum departamento cadastrado.                 |");
            } else {
                for (Departamento d : lista) {
                    System.out.printf("| %-10d | %-43s |\n", d.getId(), d.getNome());
                }
            }

            System.out.println("|==========================================================|\n");

        } catch (SQLException e) {
            System.out.println("Erro ao buscar departamentos no banco de dados.");
        }
    }
}