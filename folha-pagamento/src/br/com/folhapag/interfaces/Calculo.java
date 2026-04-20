package br.com.folhapag.interfaces;

import br.com.folhapag.model.Funcionario;

public interface Calculo {
    // Versão original (necessária para o padrão de projeto)
    double calcular(Funcionario f);

    // Nova versão sobrecarregada (útil para flexibilidade e testes)
    double calcular(double valorBruto);
}