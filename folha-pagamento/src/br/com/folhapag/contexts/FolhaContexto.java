package br.com.folhapag.contexts;

import br.com.folhapag.model.Funcionario;

public class FolhaContexto {
	private Funcionario funcionario;
	private double valorINSS;
	private double valorIRRF;
	
	public FolhaContexto() {}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public double getValorINSS() {
		return valorINSS;
	}

	public void setValorINSS(double valorINSS) {
		this.valorINSS = valorINSS;
	}

	public double getValorIRRF() {
		return valorIRRF;
	}

	public void setValorIRRF(double valorIRRF) {
		this.valorIRRF = valorIRRF;
	};
	
	
}
