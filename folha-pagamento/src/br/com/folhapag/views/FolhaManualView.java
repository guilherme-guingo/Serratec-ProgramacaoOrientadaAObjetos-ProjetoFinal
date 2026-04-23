package br.com.folhapag.views;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.folhapag.config.Conexao;
import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.exceptions.*;
import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.FolhaPagamento;
import br.com.folhapag.model.Funcionario;
import br.com.folhapag.utils.*;

public class FolhaManualView {
	Scanner sc = new Scanner(System.in);

	public Funcionario entradaDadosManual() {
		String nome = lerNome("funcionário");
		LocalDate data = lerDataNascimento("funcionário");
		String cpf = lerCPF("funcionário");
		double salario = lerSalario();
		Departamento departamento = lerDepartamento();

		try {
			Funcionario funcionario = new Funcionario(nome, cpf, data, salario, departamento);
			List<Dependente> dependentes = lerDependentes(funcionario);
			funcionario.setDependentes(dependentes);
			return funcionario;
		} catch(CPFInvalido | DataInvalida | NomeInvalido | SalarioInvalido e) {
			System.out.println("Erro na criação do funcionário: " + e.getMessage());
			return null;
		}
	}

	public void exibirDadosFolha(FolhaPagamento folha) {
		FolhaVisualizacaoUtils.exibirRelatorio(folha);
	}

	private String lerNome(String tipoPessoa) {
		while (true) {
			System.out.print("Informe o nome do " + tipoPessoa + ": ");
			String nome = sc.nextLine();

			try {
				return ValidarNome.validarNome(nome);
			} catch (NomeInvalido e) {
				System.out.println("Erro: " + e.getMessage() + "\n");
			}
		}
	}

	private LocalDate lerDataNascimento(String tipoPessoa) {
		while (true) {
			System.out.print("Informe a data de nascimento do " + tipoPessoa + " (AAAA-MM-DD): ");
			String entrada = sc.nextLine();

			try {
				return ValidarData.validar(entrada);
			} catch (FormatoDataInvalido | DataInvalida e) {
				System.out.println("Erro: " + e.getMessage() + "\n");
			}
		}
	}

	private String lerCPF(String tipoPessoa) {
		while (true) {
			System.out.print("Informe o CPF do " + tipoPessoa + ": ");
			String cpf = sc.nextLine();

			try {
				ValidarCPF.validarCPF(cpf);
				return cpf;
			} catch (CPFInvalido e) {
				System.out.println("Erro: " + e.getMessage() + "\n");
			}
		}
	}

	private double lerSalario() {
		while (true) {
			System.out.print("Informe o salário: ");
			String entrada = sc.nextLine();

			try {
				return ValidarSalario.validar(entrada);
			} catch (SalarioInvalido e) {
				System.out.println("Erro: " + e.getMessage() + "\n");
			}
		}
	}

	private Departamento lerDepartamento() {
		try (Connection conn = Conexao.getConexaoDB()) {
			while (true) {
				System.out.print("Informe o departamento pelo ID ou pelo nome: ");
				String entrada = sc.nextLine();

				try {
					return ValidarDepartamento.validar(entrada, conn);
				} catch (DepartamentoNaoEncontrado e) {
					System.out.println("Erro: " + e.getMessage() + "\n");
				}
			}
		} catch (Exception e) {
			System.out.println("Erro crítico de conexão com o banco ao buscar departamento.");
			e.printStackTrace();
			return null;
		}
	}

	private Parentesco lerParentesco() {
		while (true) {
			System.out.print("\nSELECIONE O GRAU DE PARENTESCO\n1 - PAIS\n2 - FILHOS\n3 - CONJUGE\n4 - OUTROS\nOpção: ");
			String entrada = sc.nextLine();

			try {
				int respParentesco = Integer.parseInt(entrada);

				return switch (respParentesco) {
					case 1 -> Parentesco.PAIS;
					case 2 -> Parentesco.FILHOS;
					case 3 -> Parentesco.CONJUGE;
					case 4 -> Parentesco.OUTROS;
					default -> throw new IllegalArgumentException();
				};
			} catch (IllegalArgumentException e) {
				System.out.println("Opção inválida. Digite um número de 1 a 4.");
			}
		}
	}

	public List<Dependente> lerDependentes(Funcionario funcionario){
		List<Dependente> dependentes = new ArrayList<>();

		try {
			ValidarDependente.validarTitular(funcionario);

			boolean querAdicionar = ConsoleUtils.perguntarSimNao(sc, "O funcionário possui dependentes?");

			while (querAdicionar) {
				System.out.println("\n--- Adicionando Dependente ---");
				String nome = lerNome("dependente");
				LocalDate data = lerDataNascimento("dependente");
				String cpf = lerCPF("dependente");
				Parentesco p = lerParentesco();

				try {
					Dependente novo = ValidarDependente.validarECriar(nome, cpf, data, p, funcionario);
					dependentes.add(novo);
					System.out.println("Dependente cadastrado com sucesso!\n");

				} catch(DependenteInvalido e) {
					System.out.println("Erro na criação de dependente: " + e.getMessage() + "\n");
				}

				querAdicionar = ConsoleUtils.perguntarSimNao(sc, "Deseja adicionar mais um dependente?");
			}

		} catch (DependenteSemTitular e) {
			System.out.println("Erro Crítico: " + e.getMessage() + "\n");
		}

		return dependentes;
	}
}