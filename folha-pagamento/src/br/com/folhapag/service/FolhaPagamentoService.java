package br.com.folhapag.service;

import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;

public class FolhaPagamentoService {

	public FolhaPagamentoService() {
	}

	public FolhaPagamento folhaCalculoManual(Funcionario funcionario) {
		double salarioBruto = funcionario.getSalarioBruto();
		
		CalcularINSS INSSEntrada = new CalcularINSS(salarioBruto);
		double INSS = INSSEntrada.calcularImposto();
		
		CalcularIR IRRFEntrada = new CalcularIR(salarioBruto, INSS, funcionario.getDependentes());
		double IRRF = IRRFEntrada.calcularImposto();
		
		double salarioLiquido = salarioBruto - INSS - IRRF; 		
		
		FolhaPagamento folha = new FolhaPagamento(funcionario, INSS, IRRF, salarioLiquido);
		
		return folha;
	}
}
