package br.com.folhapag.model;

import java.time.LocalDate;

public class Funcionario {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate nascimento; // <-- Campo adicionado para satisfazer o NOT NULL do banco
    private double salarioBruto;
    private int departamentoId;

    public Funcionario() {}

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getNascimento() { return nascimento; }
    public void setNascimento(LocalDate nascimento) { this.nascimento = nascimento; }

    public double getSalarioBruto() { return salarioBruto; }
    public void setSalarioBruto(double salarioBruto) { this.salarioBruto = salarioBruto; }

    public int getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(int departamentoId) { this.departamentoId = departamentoId; }

    @Override
    public String toString() {
        return String.format("Nome: %-20s | CPF: %s | Nasc: %s | Salário: R$ %.2f", 
                             nome, cpf, nascimento, salarioBruto);
    }
}