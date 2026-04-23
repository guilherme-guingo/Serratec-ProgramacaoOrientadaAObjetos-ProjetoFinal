package br.com.folhapag.views;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.dao.DepartamentoDao;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.DataInvalida;
import br.com.folhapag.exception.DependenteSemTitular;
import br.com.folhapag.exception.NomeInvalido;
import br.com.folhapag.exception.SalarioInvalido;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.utils.ValidarCPF;
import br.com.folhapag.utils.ValidarData;
import br.com.folhapag.utils.ValidarDependente;

public class FolhaManualView {
	Scanner sc = new Scanner(System.in);

	public Funcionario entradaDadosManual() {

		String nome = lerNome();
		LocalDate data = lerData();
		String cpf = lerCPF();
		double salario = lerSalario();
		Departamento departamento = lerDepartamento();
		try {
			Funcionario funcionario = new Funcionario(nome, cpf, data, salario, departamento);
			List<Dependente> dependentes = lerDependentes(funcionario);
			funcionario.setDependentes(dependentes);
			return funcionario;
		}catch(CPFInvalido | DataInvalida | NomeInvalido | SalarioInvalido e) {
			System.err.println("Erro na criação do funcionário: " + e.getMessage());
	        return null;
		}
	}

	public void exibirDadosFolha(FolhaPagamento folha) {
		System.out.println("\n==================================================");
	    System.out.println("        RELATÓRIO DA FOLHA DE PAGAMENTO");
	    System.out.println("==================================================");
	    
	    System.out.printf("Funcionário:     %s\n", folha.getFuncionario().getNome());
	    System.out.println("--------------------------------------------------");
	    System.out.printf("Salário Bruto:   R$ %10.2f\n", folha.getFuncionario().getSalarioBruto());
	    System.out.printf("(-) INSS:        R$ %10.2f\n", folha.getINSS());
	    System.out.printf("(-) IRRF:        R$ %10.2f\n", folha.getIR());
	    System.out.println("--------------------------------------------------");
	    System.out.printf("SALÁRIO LÍQUIDO: R$ %10.2f\n", folha.getSalarioLiquido());
	    
	    System.out.println("==================================================\n");
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
				System.out.print("Informe a data (AAAA-MM-DD): ");
				LocalDate data = LocalDate.parse(sc.nextLine());

				ValidarData.validar(data);

				return data;
			} catch (DateTimeParseException e) {
				System.err.println("Formato inválido.\n Use AAAA-MM-DD.");
			} catch (DataInvalida e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private String lerCPF() {
		while (true) {
			try {
				System.out.println("Informe o CPF: ");
				String cpf = sc.nextLine();
				ValidarCPF.validar(cpf);
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
			try(Connection conn = Conexao.getConexaoDB()) {
				System.out.println("Informe o departamento: ");
				String nomeDepartamento = sc.nextLine();
				DepartamentoDao depDao = new DepartamentoDao(conn);
				Departamento departamento = new Departamento(depDao.buscarIdPorNome(nomeDepartamento));
				return departamento;
			} catch (Exception e) {
				System.err.println("Erro");
			}
		}
	}

	private Parentesco lerParentesco() {
		while (true) {
			System.out.println("SELECIONE O GRAU DE PARENTESCO\n" + "1 - PAIS \n" + "2 - FILHOS \n" + "3 - CONJUGE \n"
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

	public List<Dependente> lerDependentes(Funcionario funcionario){
		List<Dependente> dependentes = new ArrayList<>();

		try {
			ValidarDependente.validarTitular(funcionario);

			System.out.println("O funcionário possui dependentes? [S/N]");
			String resp = sc.nextLine();

			while (resp.equalsIgnoreCase("S")) {
				String nome = lerNome();
				LocalDate data = lerData();
				String cpf = lerCPF();
				Parentesco p = lerParentesco();
				try {
					dependentes.add(new Dependente(nome, cpf, data, p, funcionario));
				}catch(CPFInvalido | DataInvalida | NomeInvalido e) {
					System.err.println("Erro na criação de dependente: "+ e.getMessage());
				}
				System.out.println("Deseja adicionar mais um? [S/N]");
				resp = sc.nextLine();
			}
			
			if(resp.equalsIgnoreCase("N")) {
				return dependentes;
			}
		} catch (DependenteSemTitular e) {
			System.err.println(e.getMessage());
		}

		return dependentes;
	}
}
