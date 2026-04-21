package br.com.folhapag.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.DataInvalida;
import br.com.folhapag.exception.NomeInvalido;
import br.com.folhapag.exception.SalarioInvalido;
import br.com.folhapag.utils.ValidarSalario;

public class Funcionario extends Pessoa {
    private double salarioBruto;
    private Departamento departamento;
    private List<Dependente> dependentes;
    
    public Funcionario(String nome, LocalDate nascimento, String cpf, double salarioBruto, Departamento departamento) 
            throws CPFInvalido, DataInvalida, NomeInvalido, SalarioInvalido {
        
        super(nome, nascimento, cpf); 
        this.departamento = departamento;
        this.dependentes = new ArrayList<>();

        setSalarioBruto(salarioBruto);
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(double salarioBruto) throws SalarioInvalido {
        this.salarioBruto = ValidarSalario.validar(salarioBruto);
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

    public void setDependentes(List<Dependente> dependentes)  {
        this.dependentes = dependentes;
    }
}