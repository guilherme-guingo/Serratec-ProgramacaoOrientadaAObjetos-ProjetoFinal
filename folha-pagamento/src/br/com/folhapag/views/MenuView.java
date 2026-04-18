package br.com.folhapag.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.folhapag.model.Departamento;
import br.com.folhapag.model.Dependente;
import br.com.folhapag.model.Funcionario;

public class MenuView {
	
	Scanner sc = new Scanner(System.in);
	
	public Funcionario entradaDadosManual() {
		List<Dependente> dependentes = new ArrayList();
		
		System.out.println("Informe o nome do funcionário: ");
		String nome = sc.nextLine();
		
		System.out.println("Informe a data de nascimento: (ano-mes-dia)");
		String data = sc.nextLine();
		LocalDate dataFinal = LocalDate.parse(data);
		
		System.out.println("Informe o CPF: ");
		String cpf = sc.nextLine();
		
		System.out.println("Informe o salário: ");
		double salario = sc.nextDouble();
		sc.next();
		
		System.out.println("Informe o departamento: ");
		String nomeDepartamento = sc.nextLine();
		Departamento departamento = new Departamento(nomeDepartamento);
		
		Funcionario funcionario = new Funcionario(nome, dataFinal, cpf, salario, departamento);
		
		System.out.println("O funcionário possui dependentes? [S/N]");
		String resp = sc.next();
		while(resp.equalsIgnoreCase("S")) {
			System.out.println("Informe o nome do dependente: ");
			String nomeDependente = sc.nextLine();
			
			System.out.println("Informe a data de nascimento do dependente: ");
			String dataDependente = sc.nextLine();
			LocalDate dataFinalDep = LocalDate.parse(dataDependente);
			
		}
		return funcionario;
	}
}
