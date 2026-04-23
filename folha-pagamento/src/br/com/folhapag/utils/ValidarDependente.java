package br.com.folhapag.utils;

import br.com.folhapag.exceptions.DependenteSemTitular;
import br.com.folhapag.model.Funcionario;

public class ValidarDependente {
	
	public static Funcionario validarTitular(Funcionario titular) throws DependenteSemTitular {
	    if (titular == null) {
	        throw new DependenteSemTitular("Titular nulo.");
	    }
	    return titular;
	}

}

