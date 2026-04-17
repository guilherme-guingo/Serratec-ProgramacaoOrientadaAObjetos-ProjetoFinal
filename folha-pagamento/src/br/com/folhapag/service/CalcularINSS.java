package br.com.folhapag.service;

import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Funcionario;

public class CalcularINSS implements CalcularImposto{
	private double aliquota;
	private double parcela;
	
	@Override
	public double calcularImposto(Funcionario funcionario) {
		double salario = funcionario.getSalarioBruto();
		if(salario <= 1.518) {
			aliquota = 7.5/100;
			parcela = 0;	
		}else if(salario <= 2793.88) {
			aliquota = 9/100;
			parcela = 22.77;
		}else if(salario <= 4190.83) {
			aliquota = 12/100;
			parcela = 106.60;
		}else if(salario <= 8157.41) {
			aliquota = 14/100;
			parcela = 190.42;
		}else {
			return 951.62;
		}
		return (salario*aliquota) - parcela;
		
		
	}
}
