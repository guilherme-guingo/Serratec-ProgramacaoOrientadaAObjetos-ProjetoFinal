package br.com.folhapag.model;

import java.time.LocalDate;

public class FolhaPagamento {
	private Funcionario funcionario;
	private LocalDate data;
	private double INSS;
	private double IR;
	private double Liquido;
	
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
		return IR;
	}
	public void setIR(double iR) {
		IR = iR;
	}
	public double getLiquido() {
		return Liquido;
	}
	public void setLiquido(double liquido) {
		Liquido = liquido;
	}
}
