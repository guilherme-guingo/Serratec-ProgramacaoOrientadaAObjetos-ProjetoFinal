package br.com.folhapag.utils;

import br.com.folhapag.exceptions.SalarioInvalido;

public class ValidarSalario {
	
	 public static double validar(double salarioBruto) throws SalarioInvalido {
		 if (salarioBruto < 1412.00) {
	            throw new SalarioInvalido("Salário inválido. Mínimo é R$ 1.412,00.");
	        }
		 return salarioBruto;
	 }
 }
