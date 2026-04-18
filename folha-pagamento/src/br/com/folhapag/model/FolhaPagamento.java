package br.com.folhapag.model;

import java.time.LocalDate;

public class FolhaPagamento {
	private Funcionario funcionario;
	private LocalDate data;
	private double INSS;
	private double IRRF;
	private double salarioLiquido;
	
	public FolhaPagamento(Funcionario funcionario, double INSS, double IRRF, double salarioLiquido) {
		this.funcionario = funcionario;
		this.data = LocalDate.now();
		this.INSS = INSS;
		this.IRRF = IRRF;
		this.salarioLiquido = salarioLiquido;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public double getINSS() {
		return INSS;
	}
	public void setINSS(double iNSS) {
		INSS = iNSS;
	}
	public double getIR() {
		return IRRF;
	}
	public void setIR(double IRRF) {
		IRRF = IRRF;
	}
	public double getSalarioLiquido() {
		return salarioLiquido;
	}
	public void setSalarioLiquido(double liquido) {
		salarioLiquido = liquido;
	}
}
