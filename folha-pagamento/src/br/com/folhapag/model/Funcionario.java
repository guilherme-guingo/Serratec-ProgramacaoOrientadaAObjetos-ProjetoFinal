package br.com.folhapag.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoa{
	private double salarioBruto;
	private Departamento departamento;
	private List<Dependente> dependentes;
	
	public Funcionario(String nome, String cpf, LocalDate nascimento, double salarioBruto, Departamento departamento) {
		super(nome, nascimento, cpf);
		this.salarioBruto = salarioBruto;
		this.departamento = departamento;
		this.dependentes = new ArrayList();
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}
	
}
