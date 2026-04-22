package br.com.folhapag.service;

import br.com.folhapag.contexts.FolhaContexto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import br.com.folhapag.dao.FolhaPagamentoDao;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;

public class FolhaPagamentoService {
	private CalcularINSS INSS = new CalcularINSS();
	private CalcularIR IRRF = new CalcularIR();
	public FolhaPagamentoService() {
	}
	
	public FolhaPagamento folhaCalculo(Funcionario funcionario) {
		return folhaCalculo(funcionario, new FolhaContexto());
	}
	
	public FolhaPagamento folhaCalculo(Funcionario funcionario, FolhaContexto contexto) {
		contexto.setFuncionario(funcionario);
		INSS.calcularImposto(contexto);
		IRRF.calcularImposto(contexto);
		double salarioLiquido = funcionario.getSalarioBruto() - contexto.getValorINSS() - contexto.getValorIRRF();
		
		FolhaPagamento folha = new FolhaPagamento(funcionario, contexto.getValorINSS(), contexto.getValorIRRF(), salarioLiquido);
		return folha;
	}
}
