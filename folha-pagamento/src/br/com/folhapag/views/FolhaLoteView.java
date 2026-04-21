package br.com.folhapag.views;

import java.util.Scanner;

import br.com.folhapag.service.FolhaLoteService;

public class FolhaLoteView {
	public FolhaLoteView() {}
	
	public void entradaLote(Scanner sc) {
		FolhaLoteService folhaLoteService = new FolhaLoteService();
		
		System.out.println("Escreva o local de entrada do aquivo: ");
		String entrada = sc.nextLine();
		
		System.out.println("Escreva o local de saída: ");
		String saida = sc.nextLine();
		
		folhaLoteService.lerLote(entrada, saida);
	}
}
