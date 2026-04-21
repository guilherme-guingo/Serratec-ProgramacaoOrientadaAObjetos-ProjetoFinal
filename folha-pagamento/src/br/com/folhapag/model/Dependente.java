package br.com.folhapag.model;

import java.time.LocalDate;

import br.com.folhapag.enums.Parentesco;
import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.DataInvalida;
import br.com.folhapag.exception.DependenteSemTitular;
import br.com.folhapag.exception.NomeInvalido;
import br.com.folhapag.utils.ValidarDependente;


public class Dependente extends Pessoa {
    private Parentesco parentesco;
    private Funcionario funcionario; 

    public Dependente(String nome, LocalDate nascimento, String cpf, Parentesco parentesco, Funcionario funcionario)
            throws CPFInvalido, DataInvalida, NomeInvalido, DependenteSemTitular { 
        super(nome, nascimento, cpf); 
        this.parentesco = parentesco;
        setFuncionario(funcionario);
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) throws DependenteSemTitular {
        this.funcionario = ValidarDependente.validarTitular(funcionario);
    }
}
