package br.com.folhapag.views;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;

public class FolhaManualView {
	Scanner sc = new Scanner(System.in);

	public Funcionario entradaDadosManual() {

		String nome = lerNome();
		LocalDate data = lerData();
		String cpf = lerCPF();
		double salario = lerSalario();
		Departamento departamento = lerDepartamento();

		Funcionario funcionario = new Funcionario(nome, data, cpf, salario, departamento);
		List<Dependente> dependentes = lerDependentes(funcionario);
		funcionario.setDependentes(dependentes);
		
		return funcionario;
	}
	
	public void exibirDadosFolha(FolhaPagamento folha) {
		System.out.println("RELATÓRIO DA FOLHA DE PAGAMENTO");
		System.out.printf("Funcionário: %s\n"
				+ "Salário Bruto: R$ %.2f\n"
				+ "INSS: R$ %.2f\n"
				+ "IRRF: R$ %.2f\n"
				+ "Salário Líquido: R$ %.2f\n", folha.getFuncionario().getNome(), folha.getFuncionario().getSalarioBruto(), folha.getINSS(), folha.getIR(), folha.getSalarioLiquido());
	}

	private String lerNome() {
		while (true) {
			try {
				System.out.println("Informe o nome: ");
				String nome = sc.nextLine();
				return nome;
			} catch (Exception e) { // VOU ADICIONAR EXCEPTION PERSONALIZADA
				System.err.println("Erro");
			}
		}
	}

	private LocalDate lerData() {
		while (true) {
			try {
				System.out.println("Informe a data de nascimento: (ano-mes-dia)");
				String data = sc.nextLine();
				LocalDate dataFinal = LocalDate.parse(data);
				return dataFinal;
			} catch (DateTimeParseException e) {
				System.err.println("Formato da data inválido!");
			}
		}
	}

	private String lerCPF() {
		while (true) {
			try {
				System.out.println("Informe o CPF: ");
				String cpf = sc.nextLine();
				return cpf;
			} catch (Exception e) { // EXCEPTION PERSONALIZADA PARA CPF
				System.err.println("Erro");
			}
		}
	}

	private double lerSalario() {
		while (true) {
			try {
				System.out.println("Informe o salário: ");
				double salario = sc.nextDouble();
				sc.nextLine();
				return salario;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.err.println("Erro! Digite apenas números!");
			}
		}
	}

	private Departamento lerDepartamento() {
		while (true) {
			try {
				System.out.println("Informe o departamento: ");
				String nomeDepartamento = sc.nextLine();
				Departamento departamento = new Departamento(nomeDepartamento);
				return departamento;
			} catch (Exception e) {
				System.err.println("Erro");
			}
		}
	}

	public List<Dependente> lerDependentes(Funcionario funcionario) {
		List<Dependente> dependentes = new ArrayList();
		while (true) {
			try {
				System.out.println("O funcionário possui dependentes? [S/N]");
				String resp = sc.nextLine();
				while (resp.equalsIgnoreCase("S")) {
					
					String nome = lerNome();
					LocalDate data = lerData();
					String cpf = lerCPF();
					Parentesco p = lerParentesco();
					
					dependentes.add(new Dependente(nome, data, cpf, p, funcionario));
					System.out.println("Deseja adicionar mais um dependente? [S/N]");
					resp = sc.nextLine();
					
					if(resp.equalsIgnoreCase("N")) {
						return dependentes;
					}
				}
			} catch (Exception e) {
				System.err.println("Erro");
				sc.nextLine();
			}
		}
	}
	
	private Parentesco lerParentesco() {
		while(true) {
		System.out.println("SELECIONE O GRAU DE PARENTESCO\n" 
				+ "1 - PAI \n" 
				+ "2 - MÃE \n/"
				+ "3 - FILHO \n" 
				+ "4 - CONJUGE \n" 
				+ "5 - OUTROS \n");
		int respParentesco = sc.nextInt();
		sc.nextLine();
		Parentesco p = switch (respParentesco) {
		case 1 -> Parentesco.PAI;
		case 2 -> Parentesco.MAE;
		case 3 -> Parentesco.FILHO;
		case 4 -> Parentesco.CONJUGE;
		case 5 -> Parentesco.OUTROS;
		default -> throw new IllegalArgumentException("Opção inválida");
		};
		return p;
	}
}
}
