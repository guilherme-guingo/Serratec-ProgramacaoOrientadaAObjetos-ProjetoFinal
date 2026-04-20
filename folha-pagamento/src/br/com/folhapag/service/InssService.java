package br.com.folhapag.service;

import br.com.folhapag.interfaces.Calculo;
import br.com.folhapag.model.Funcionario;

public class InssService implements Calculo {
    
    private static final double TETO_INSS = 951.62;

    @Override
    public double calcular(Funcionario f) {
        // Apenas extrai o valor e chama o outro método
        return calcular(f.getSalarioBruto());
    }

    @Override
    public double calcular(double salario) {
        double desconto = salario * 0.11; 
        return Math.min(desconto, TETO_INSS);
    }
}