package br.com.folhapag.model;

public class Departamento {
    private int id;
    private String nome;

    public Departamento(String nome) {
        this.nome = nome;
    }

    // ADICIONE este para resolver o erro do DAO/CSV
    public Departamento(int id) {
        this.id = id;
    }
    
    public Departamento(int id, String nome) {
    	this.id = id;
    	this.nome = nome;
    }

    // O DAO precisa deste método para o ps.setInt(5, ...)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nome=" + nome + "]";
	}
}