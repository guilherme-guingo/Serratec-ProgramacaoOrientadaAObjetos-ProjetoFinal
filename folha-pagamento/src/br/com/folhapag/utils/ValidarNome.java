package br.com.folhapag.utils;

import br.com.folhapag.exceptions.NomeInvalido;

public class ValidarNome {

    public static String validarNome(String nome) throws NomeInvalido {

        if (nome == null) {
            throw new NomeInvalido("O nome não pode estar vazio.");
        }

        if (nome.length() < 3) {
            throw new NomeInvalido("O nome deve conter pelo menos 3 caracteres.");
        }

        return nome; 
    }
}