package br.com.folhapag.utils;

import br.com.folhapag.exceptions.SalarioInvalido;

public class ValidarSalario {

	public static double validar(String salarioTexto) throws SalarioInvalido {
		if (salarioTexto == null || salarioTexto.trim().isEmpty()) {
			throw new SalarioInvalido("O salário não pode estar em branco.");
		}

		try {
			String formatado = salarioTexto.replace(",", ".");
			double salarioBruto = Double.parseDouble(formatado);

			return validar(salarioBruto);

		} catch (NumberFormatException e) {
			throw new SalarioInvalido("Formato inválido. Digite apenas números (ex: 1500.00).");
		}
	}

	public static double validar(double salarioBruto) throws SalarioInvalido {
		if (salarioBruto < 1412.00) {
			throw new SalarioInvalido("Salário inválido. Mínimo é R$ 1.412,00.");
		}
		return salarioBruto;
	}
}