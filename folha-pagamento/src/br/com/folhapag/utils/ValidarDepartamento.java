package br.com.folhapag.utils;

import java.sql.Connection;

import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.exceptions.DepartamentoNaoEncontrado;
import br.com.folhapag.model.Departamento;

public class ValidarDepartamento {

    public static Departamento validar(String entrada, Connection conn) throws DepartamentoNaoEncontrado {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new DepartamentoNaoEncontrado("A identificação do departamento não pode estar vazia.");
        }

        DepartamentoDao depDao = new DepartamentoDao(conn);
        Departamento departamentoEncontrado = null;

        try {
            int idBusca = Integer.parseInt(entrada.trim());
            departamentoEncontrado = depDao.buscarPorId(idBusca);

        } catch (NumberFormatException e) {
            int idBusca = depDao.buscarIdPorNome(entrada);

            if (idBusca != -1) {
                departamentoEncontrado = depDao.buscarPorId(idBusca);
            }
        }

        if (departamentoEncontrado == null) {
            throw new DepartamentoNaoEncontrado("Não foi possível encontrar um departamento com o ID ou Nome: '" + entrada + "'.");
        }

        return departamentoEncontrado;
    }
}