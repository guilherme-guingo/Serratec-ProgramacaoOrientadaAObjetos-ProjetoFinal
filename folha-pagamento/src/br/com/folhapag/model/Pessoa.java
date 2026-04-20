package br.com.folhapag.model;

import java.time.LocalDate;

public class Pessoa {
    
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    // 1. CONSTRUTOR VAZIO (O segredo para resolver o seu erro!)
    // Ele permite que a classe Funcionario seja criada com "new Funcionario()"
    public Pessoa() {
    }

    // 2. CONSTRUTOR COM PARÂMETROS
    // Útil quando você já tem todos os dados em mãos
    public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // 3. GETTERS E SETTERS
    // Essenciais para o LeitorCSV conseguir preencher os dados um por um
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}