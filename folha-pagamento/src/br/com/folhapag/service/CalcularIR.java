package br.com.folhapag.service;

import java.util.List;

import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Dependente;

public class CalcularIR implements CalcularImposto{
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
		double base = salario - INSS -(dependentes * DEDUCAO_DEPENDENTE);
		
		double aliquota = calcAliquota();
		double parcela = calcParcela();
		
		return (base * aliquota) - parcela ;
	}
	
	public double calcAliquota() {
		if(salario <= 2259.20) {
			return 0;
			
		}else if(salario <= 2826.65) {
			return 7.5/100;
			
		}else if(salario <= 3751.05) {
			return 15/100;
			
		}else if(salario <= 4664.68) {
			return 22.5/100;
			
		}else {
			return 27.5/100;
		}
		
	}
	
	public double calcParcela() {
		if(salario <= 2259.20) {
			return 0;
			
		}else if(salario <= 2826.65) {
			return 169.44;
			
		}else if(salario <= 3751.05) {
			return 381.44;
			
		}else if(salario <= 4664.68) {
			return 662.77;
			
		}else {
			return 896.00;
		}
	}

}
