package br.com.folhapag.views;

import java.io.FileNotFoundException;
import java.util.Scanner;

import br.com.folhapag.service.FolhaLoteService;

public class FolhaLoteView {
	public FolhaLoteView() {}

	public void entradaLote(Scanner sc) {
		FolhaLoteService folhaLoteService = new FolhaLoteService();

		System.out.print("Escreva o local/nome do arquivo de entrada: ");
		String entrada = sc.nextLine();

		System.out.print("Escreva o local/nome do arquivo de saída: ");
		String saida = sc.nextLine();

		try {
			folhaLoteService.processarLote(entrada, saida);
			System.out.println("Arquivo processado com sucesso!");

		} catch (Exception e) {
			System.out.println("Erro ao ler o arquivo: Não foi possível encontrar '" + entrada + "'.");
		}
	}
}