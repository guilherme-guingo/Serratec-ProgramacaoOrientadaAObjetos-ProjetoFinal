package br.com.folhapag.model;

import java.time.LocalDate;

import br.com.folhapag.enums.Parentesco;

public class Dependente extends Pessoa {
	private Parentesco parentesco;
	private Funcionario funcionario;
	public Dependente(String nome, String cpf, LocalDate nascimento, Parentesco parentesco, Funcionario funcionario) {
		super(nome, nascimento, cpf);
		this.parentesco = parentesco;
		this.funcionario = funcionario;
	}
	public Parentesco getParentesco() {
		return parentesco;
	}
	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
}
