package br.com.folhapag.utils;

import br.com.folhapag.exception.CPFInvalido;

public class ValidarCPF {
    
    public static String validar(String cpf) throws CPFInvalido {
        String correto = (cpf != null) ? cpf.replaceAll("\\D", "") : "";
        
        if (correto.length() != 11) {
            throw new CPFInvalido("CPF deve ter 11 dígitos.");
        }
        
        return correto; 
    }
}

