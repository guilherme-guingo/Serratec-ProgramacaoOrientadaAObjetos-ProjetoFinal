package br.com.folhapag.utils;

import java.time.LocalDate;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.exceptions.*;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.Funcionario;

public class ValidarDependente {

	public static Funcionario validarTitular(Funcionario titular) throws DependenteSemTitular {
		if (titular == null) {
			throw new DependenteSemTitular("Titular nulo.");
		}
		return titular;
	}

	public static Dependente validarECriar(String nome, String cpf, LocalDate data, Parentesco p, Funcionario titular) throws DependenteInvalido {
		try {
			return new Dependente(nome, cpf, data, p, titular);
		} catch (NomeInvalido | CPFInvalido | DataInvalida e) {
			throw new DependenteInvalido(e.getMessage());
		}
	}
}