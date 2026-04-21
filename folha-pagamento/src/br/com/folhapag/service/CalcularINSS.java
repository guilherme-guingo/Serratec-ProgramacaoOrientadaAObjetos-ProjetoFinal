package br.com.folhapag.service;

import br.com.folhapag.contexts.FolhaContexto;
import br.com.folhapag.interfaces.CalcularImposto;

public class CalcularINSS implements CalcularImposto {

	private final double TETO_INSS_RETIDO = 951.62;

	public CalcularINSS() {
	}

	@Override
	public void calcularImposto(FolhaContexto contexto) {

		double aliquota = calcAliquota(contexto);
		double parcela = calcParcela(contexto);

		if (contexto.getFuncionario().getSalarioBruto() > 8157.41) {
			contexto.setValorINSS(TETO_INSS_RETIDO);
		} else {
			double resultado = (contexto.getFuncionario().getSalarioBruto() * aliquota) - parcela;
			contexto.setValorINSS(Math.max(0, resultado));
		}
	}

	private double calcAliquota(FolhaContexto contexto) {
		double salario = contexto.getFuncionario().getSalarioBruto();
		if (salario <= 1518.00) {
			return 7.5 / 100.0;

		} else if (salario <= 2793.88) {
			return 9 / 100.0;

		} else if (salario <= 4190.83) {
			return 12.0 / 100.0;

		} else {
			return 14.0/100.0;
		}

	}

	private double calcParcela(FolhaContexto contexto) {
		double salario = contexto.getFuncionario().getSalarioBruto();
		if (salario <= 1518.00) {
			return 0;

		} else if (salario <= 2793.88) {
			return 22.77;

		} else if (salario <= 4190.83) {
			return 106.60;
		} else {
			return 190.42;
		}
	}
}
