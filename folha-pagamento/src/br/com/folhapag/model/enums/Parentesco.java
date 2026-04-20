package br.com.folhapag.model.enums;

public enum Parentesco {
    CONJUGE("Cônjuge"),
    FILHO("Filho(a)"),
    PAI_MAE("Pai/Mãe"),
    OUTRO("Outro");

    private final String descricao;

    // Construtor do Enum (sempre privado)
    Parentesco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
