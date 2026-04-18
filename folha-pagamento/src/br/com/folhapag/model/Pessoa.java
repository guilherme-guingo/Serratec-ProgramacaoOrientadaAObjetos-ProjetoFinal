package br.com.folhapag.model;

import java.time.LocalDate;

public abstract class Pessoa {
	private String nome;
	private LocalDate nascimento;
	private String cpf;
	
	public Pessoa(String nome, LocalDate nascimento, String cpf) {
		super();
		this.nome = nome;
		this.nascimento = nascimento;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
}
