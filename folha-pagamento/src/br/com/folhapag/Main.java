package br.com.folhapag;

import br.com.folhapag.config.SetupBanco;
import br.com.folhapag.views.MenuView;

public class Main {
    public static void main(String[] args) {
        SetupBanco.inicializarTabelas();

        MenuView menu = new MenuView();
        menu.executarMenu();
    }
}