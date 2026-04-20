package br.com.folhapag.service;

import br.com.folhapag.interfaces.Calculo;
import br.com.folhapag.model.Funcionario;

public class IrrfService implements Calculo {

    private static final double DEDUCAO_POR_DEPENDENTE = 189.59;

    // MÉTODO 1: Implementação obrigatória da Interface (Contrato com Objeto)
    @Override
    public double calcular(Funcionario f) {
        // Aproveita o objeto para pegar tudo o que precisa
        double inss = new InssService().calcular(f);
        int dependentes = (f.getDependentes() != null) ? f.getDependentes().size() : 0;
        
        return calcularLogicaReal(f.getSalarioBruto(), inss, dependentes);
    }

    // MÉTODO 2: Implementação obrigatória da Interface (Contrato com Valor)
    @Override
    public double calcular(double valorBruto) {
        // Se só temos o valor bruto, calculamos o INSS sobre ele e assumimos 0 dependentes
        double inss = new InssService().calcular(valorBruto);
        return calcularLogicaReal(valorBruto, inss, 0);
    }

    // MÉTODO PRIVADO: Onde a matemática realmente acontece
    private double calcularLogicaReal(double bruto, double inss, int numDependentes) {
        double baseCalculo = bruto - inss - (numDependentes * DEDUCAO_POR_DEPENDENTE);

        if (baseCalculo <= 2259.20) {
            return 0.0;
        } else if (baseCalculo <= 2826.65) {
            return (baseCalculo * 0.075) - 169.44;
        } else if (baseCalculo <= 3751.05) {
            return (baseCalculo * 0.15) - 381.44;
        } else if (baseCalculo <= 4664.68) {
            return (baseCalculo * 0.225) - 662.77;
        } else {
            return (baseCalculo * 0.275) - 896.00;
        }
    }
}