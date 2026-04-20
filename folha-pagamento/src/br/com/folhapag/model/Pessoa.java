package br.com.folhapag.model;

import java.time.LocalDate;

import br.com.folhapag.exception.CPFInvalido;

public abstract class Pessoa {
    private String nome;
    private LocalDate nascimento;
    private String cpf;
    
    public Pessoa(String nome, LocalDate nascimento, String cpf) throws CPFInvalido {
        super();
        this.nome = nome;
        this.nascimento = nascimento;
        setCpf(cpf);
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

    public void setCpf(String cpf) throws CPFInvalido {
        String cpfLimpo = (cpf != null) ? cpf.replaceAll("\\D", "") : ""; 

        if (cpfLimpo.length() != 11) {
            throw new CPFInvalido("O CPF " + cpf + " é inválido! Deve conter 11 dígitos.");
        }
        this.cpf = cpfLimpo;
    }
}