package br.com.folhapag.service;

import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Funcionario;

public class CalcularINSS implements CalcularImposto{
	private double aliquota;
	private double parcela;
	private double salario;
	private final double TETO_INSS_RETIDO = 951.62;
	
	@Override
	public double calcularImposto() {
		if(this.aliquota == 0) {
			return TETO_INSS_RETIDO ;
		}
		 return (salario * this.aliquota) - this.parcela;
		}
	
	public void calcAliquota(double salario) {
		if(salario <= 1.518) {
			this.aliquota = 7.5/100;
			
		}else if(salario <= 2793.88) {
			this.aliquota = 9/100;
			
		}else if(salario <= 4190.83) {
			this.aliquota = 12/100;
			
		}else if(salario <= 8157.41) {
			this.aliquota = 14/100;
			
		}else {
			this.aliquota = 0;
		}
		
	}
	
	public void calcParcela(double salario) {
		if(salario <= 1.518) {
			
			this.parcela = 0;	
		}else if(salario <= 2793.88) {
			
			this.parcela = 22.77;
		}else if(salario <= 4190.83) {
			
			this.parcela = 106.60;
		}else if(salario <= 8157.41) {
			
			this.parcela = 190.42;
		} 
		
	}
}
