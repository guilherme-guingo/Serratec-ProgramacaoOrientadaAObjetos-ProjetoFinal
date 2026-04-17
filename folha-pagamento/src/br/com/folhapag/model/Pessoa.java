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
	
	
}
