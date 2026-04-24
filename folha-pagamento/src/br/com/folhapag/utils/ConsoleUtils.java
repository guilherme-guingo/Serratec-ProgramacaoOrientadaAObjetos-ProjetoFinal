package br.com.folhapag.utils;

import java.util.Scanner;

public class ConsoleUtils {

    public static boolean perguntarSimNao(Scanner scanner, String pergunta) {
        while (true) {
            System.out.print(pergunta + " (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Entrada inválida. Digite apenas S ou N.");
        }
    }
}