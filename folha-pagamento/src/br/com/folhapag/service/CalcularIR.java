package br.com.folhapag.service;

import java.util.List;

import br.com.folhapag.contexts.FolhaContexto;
import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Dependente;

public class CalcularIR implements CalcularImposto{
	private final double DEDUCAO_DEPENDENTE = 189.59;
	
	public CalcularIR() {}
	
	
	@Override
	public void calcularImposto(FolhaContexto contexto) {
		double base = contexto.getFuncionario().getSalarioBruto() - contexto.getValorINSS() - (contexto.getFuncionario().getDependentes().size() * DEDUCAO_DEPENDENTE);
		
		double aliquota = calcAliquota(base);
		double parcela = calcParcela(base);
		
		double resultado = (base * aliquota) - parcela ;
		contexto.setValorIRRF(Math.max(0, resultado));
	}
	
	private double calcAliquota(double base) {
		if(base <= 2259.20) {
			return 0;
			
		}else if(base <= 2826.65) {
			return 7.5/100.0;
			
		}else if(base <= 3751.05) {
			return 15.0/100.0;
			
		}else if(base <= 4664.68) {
			return 22.5/100.0;
			
		}else {
			return 27.5/100.0;
		}
		
	}
	
	private double calcParcela(double base) {
		if(base <= 2259.20) {
			return 0;
			
		}else if(base <= 2826.65) {
			return 169.44;
			
		}else if(base <= 3751.05) {
			return 381.44;
			
		}else if(base <= 4664.68) {
			return 662.77;
			
		}else {
			return 896.00;
		}
	}

}
