package br.com.folhapag.utils;

import java.util.Scanner;

public class ConsoleUtils {

    public static boolean perguntarSimNao(Scanner scanner, String pergunta) {
        while (true) {
            System.out.print(pergunta + " (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equals("S")) {
                return true;
            } else if (resposta.equals("N")) {
                return false;
            }

            System.out.println("Entrada inválida. Digite apenas S ou N.");
        }
    }
}