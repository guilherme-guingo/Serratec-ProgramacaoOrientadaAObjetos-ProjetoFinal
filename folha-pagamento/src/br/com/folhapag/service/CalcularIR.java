package br.com.folhapag.service;

import java.util.List;

import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Dependente;

public class CalcularIR implements CalcularImposto{
	private double base;
	private double aliquota;
	private double parcela;
	private double salario;
	private double INSS;
	private int dependentes;
	private final double DEDUCAO_DEPENDENTE = 189.59;
	
	public CalcularIR() {}
	
	public CalcularIR(double salario, double INSS, List<Dependente> dependentes) {
		this.salario = salario;
		this.INSS = INSS;
		this.dependentes = dependentes.size();
	}
	
	@Override
	public double calcularImposto() {
		this.base = salario - INSS -(dependentes * DEDUCAO_DEPENDENTE);
		return (this.base * this.aliquota) - this.parcela ;
	}
	
	public void setINSS(double INSS) {
		this.INSS = INSS;
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
