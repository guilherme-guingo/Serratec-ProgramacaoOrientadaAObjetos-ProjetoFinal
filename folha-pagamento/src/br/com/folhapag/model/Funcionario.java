package br.com.folhapag.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.SalarioInvalido;

public class Funcionario extends Pessoa {
    private double salarioBruto;
    private Departamento departamento;
    private List<Dependente> dependentes;
    
    public Funcionario(String nome, LocalDate nascimento, String cpf, double salarioBruto, Departamento departamento) 
            throws CPFInvalido, SalarioInvalido {
        
        super(nome, nascimento, cpf); 
        this.departamento = departamento;
        this.dependentes = new ArrayList<>();

        setSalarioBruto(salarioBruto);
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(double salarioBruto) throws SalarioInvalido {
        if (salarioBruto < 1412.00) {
            throw new SalarioInvalido("Salário inválido. Mínimo é R$ 1.412,00.");
        }
        this.salarioBruto = salarioBruto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Dependente> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependente> dependentes) {
        this.dependentes = dependentes;
    }
}