package br.com.folhapag.enums;

public enum Parentesco {
	PAIS, FILHOS, CONJUGE, OUTROS;
	
	public static Parentesco validarParentesco(String p) {
		try {
			return Parentesco.valueOf(p.toUpperCase());
		}catch(IllegalArgumentException e) {
			return Parentesco.OUTROS;
		}
		
	}
}
