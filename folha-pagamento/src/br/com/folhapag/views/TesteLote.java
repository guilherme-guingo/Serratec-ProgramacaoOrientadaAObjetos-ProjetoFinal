package br.com.folhapag.views;

import br.com.folhapag.config.SetupBanco;

import java.util.Scanner;

public class TesteLote {

	public static void main(String[] args) {
		SetupBanco.inicializarTabelas();

		MenuView menu = new MenuView();
		menu.executarMenu();

	}

}
