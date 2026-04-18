package br.com.folhapag.service;

import br.com.folhapag.model.Funcionario;

public class FolhaPagamentoService {
	
	
	public double calculoManual(Funcionario funcionario) {
		CalcularINSS INSS = new CalcularINSS(funcionario.getSalarioBruto());
		CalcularIR IR = new CalcularIR(funcionario.getSalarioBruto(), INSS.calcularImposto(), funcionario.getDependentes());
		return 0;
	}
}	
