package br.com.folhapag.model;

import java.time.LocalDate;

public class FolhaPagamento {
    private int id;
    private int funcionarioId;
    private LocalDate dataReferencia; // Use SEMPRE LocalDate aqui
    private double valorInss;
    private double valorIr;
    private double valorLiquido;

    // Construtor Vazio (Necessário para o DAO preencher na listagem)
    public FolhaPagamento() {}

    // Construtor com Parâmetros (Usado pelo Service após o cálculo)
    public FolhaPagamento(int funcionarioId, LocalDate dataReferencia, double valorInss, double valorIr, double valorLiquido) {
        this.funcionarioId = funcionarioId;
        this.dataReferencia = dataReferencia;
        this.valorInss = valorInss;
        this.valorIr = valorIr;
        this.valorLiquido = valorLiquido;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate dataReferencia) { this.dataReferencia = dataReferencia; }
    public double getValorInss() { return valorInss; }
    public void setValorInss(double valorInss) { this.valorInss = valorInss; }
    public double getValorIr() { return valorIr; }
    public void setValorIr(double valorIr) { this.valorIr = valorIr; }
    public double getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(double valorLiquido) { this.valorLiquido = valorLiquido; }

    @Override
    public String toString() {
        return String.format("ID: %3d | Data: %s | Líquido: R$ %8.2f", 
                funcionarioId, dataReferencia, valorLiquido);
    }
}