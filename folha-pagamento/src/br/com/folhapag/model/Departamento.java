package br.com.folhapag.model;

public class Departamento {
    private int id;
    private String nome;

    public Departamento() {}

    public Departamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("ID: %d | Departamento: %-20s", id, nome);
    }
}