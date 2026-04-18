package br.com.folhapag.service;

import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Funcionario;

public class CalcularINSS implements CalcularImposto{
	private double salario;
	private final double TETO_INSS_RETIDO = 951.62;
	
	public CalcularINSS() {}
	
	public CalcularINSS(double salario) {
		this.salario = salario;
	}
	
	@Override
	public double calcularImposto() {
		
		double aliquota = calcAliquota();
		double parcela = calcParcela();
		if(aliquota == 0) {
			return TETO_INSS_RETIDO ;
		}
		 return (salario * aliquota) - parcela;
		}
	
	public double calcAliquota() {
		if(salario <= 1.518) {
			return 7.5/100;
			
		}else if(salario <= 2793.88) {
			return 9/100;
			
		}else if(salario <= 4190.83) {
			return 12/100;
			
		}else if(salario <= 8157.41) {
			return 14/100;
			
		}else {
			return 0;
		}
		
	}
	
	public double calcParcela() {
		if(salario <= 1.518) {
			return 0;	
			
		}else if(salario <= 2793.88) {
			return 22.77;
			
		}else if(salario <= 4190.83) {
			return 106.60;
			
		}else if(salario <= 8157.41) {
			return 190.42;
		}
		else {
			return 0; 
		}
	}
}
