package br.com.folhapag.model;

import java.time.LocalDate;

import br.com.folhapag.exception.CPFInvalido;
import br.com.folhapag.exception.DataInvalida;
import br.com.folhapag.exception.NomeInvalido;
import br.com.folhapag.utils.ValidarCPF;
import br.com.folhapag.utils.ValidarData;
import br.com.folhapag.utils.ValidarNome;

public abstract class Pessoa {
    private String nome;
    private LocalDate nascimento;
    private String cpf;
    
    public Pessoa(String nome, LocalDate nascimento, String cpf) throws CPFInvalido, DataInvalida, NomeInvalido {
        super();
        setNome(nome);
        setNascimento(nascimento);
        setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws NomeInvalido {
        this.nome = ValidarNome.validar(nome);
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) throws DataInvalida {
        this.nascimento = ValidarData.validar(nascimento);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws CPFInvalido {
        this.cpf = ValidarCPF.validar(cpf);
    }
}