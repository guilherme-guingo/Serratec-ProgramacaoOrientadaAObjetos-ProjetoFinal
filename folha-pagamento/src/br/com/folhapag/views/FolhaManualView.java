package br.com.folhapag.views;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.DependenteSemTitular;
import br.com.folhapag.exception.NomeInvalido;
import br.com.folhapag.exception.SalarioInvalido;
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
		try {
			Funcionario funcionario = new Funcionario(nome, data, cpf, salario, departamento);
			List<Dependente> dependentes = lerDependentes(funcionario);
			funcionario.setDependentes(dependentes);
			return funcionario;
		} catch (CPFInvalido | SalarioInvalido e) {
			System.err.println("Erro inesperado: " + e.getMessage());
			return null;
		}
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
				if (nome == null) {
					throw new NomeInvalido("Nome inválido!");
				}
				return nome;
			} catch (NomeInvalido e) { 
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
				if (cpf == null || cpf.length() != 11) {
					throw new CPFInvalido("O CPF deve conter exatamente 11 dígitos.");
				}
				return cpf;
			} catch (CPFInvalido e) { 
				System.err.println(e.getMessage());
			}
		}
	}

	private double lerSalario() {
		while (true) {
			try {
				System.out.println("Informe o salário: ");
				double salario = sc.nextDouble();
				sc.nextLine();
				if (salario < 1412.00) {
	                throw new SalarioInvalido("Salário inválido. Mínimo é R$ 1.412,00.");
	            }
				return salario;
			} catch (InputMismatchException e) {
				System.err.println("Erro! Digite apenas números!");
				sc.nextLine();
			} catch (SalarioInvalido e) {
				System.err.println(e.getMessage());
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
				
				if (funcionario == null) {
					throw new DependenteSemTitular("Não é possível cadastrar dependentes, pois o funcionário titular não foi encontrado.");
				}
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
			} catch (DependenteSemTitular e) {
				System.err.println(e.getMessage());
			}
			catch (Exception e) {
				System.err.println("Erro");
				sc.nextLine();
			}
		}
	}
	
	private Parentesco lerParentesco() {
		while(true) {
		System.out.println("SELECIONE O GRAU DE PARENTESCO\n" 
				+ "1 - PAIS \n" 
				+ "2 - FILHOS \n" 
				+ "3 - CONJUGE \n" 
				+ "4 - OUTROS \n");
		int respParentesco = sc.nextInt();
		sc.nextLine();
		Parentesco p = switch (respParentesco) {
		case 1 -> Parentesco.PAIS;
		case 2 -> Parentesco.FILHOS;
		case 3 -> Parentesco.CONJUGE;
		case 4 -> Parentesco.OUTROS;
		default -> throw new IllegalArgumentException("Opção inválida");
		};
		return p;
	}
}
}
