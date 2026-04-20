package br.com.folhapag.model;

import java.time.LocalDate;
import br.com.folhapag.model.enums.Parentesco;

public class Dependente extends Pessoa {
    
    private Parentesco parentesco;

    // CONSTRUTOR VAZIO: Resolve o erro do LeitorCSV
    public Dependente() {
        super(); // Chama o construtor vazio que acabamos de criar em Pessoa
    }

    // CONSTRUTOR COM PARÂMETROS (O que você provavelmente já tinha)
    public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) {
        super(nome, cpf, dataNascimento);
        this.parentesco = parentesco;
    }

    // Getter e Setter
    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }
}