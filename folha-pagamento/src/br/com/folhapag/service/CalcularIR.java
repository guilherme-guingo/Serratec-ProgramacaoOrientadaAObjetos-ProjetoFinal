package br.com.folhapag.service;

import br.com.folhapag.interfaces.CalcularImposto;
import br.com.folhapag.model.Funcionario;

public class CalcularIR implements CalcularImposto{
	private double base;
	private double aliquota;
	private double INSS;
	
	@Override
	public double calcularImposto(Funcionario funcionario) {
		CalcularINSS INSS = new CalcularINSS();
		base = funcionario.getSalarioBruto() - INSS.calcularImposto(funcionario) -(funcionario.getDependentes().size() * 189.59);
		return base;
	}

	public double getINSS() {
		return INSS;
	}

	public void setINSS(double INSS) {
		this.INSS = INSS;
	}
}
