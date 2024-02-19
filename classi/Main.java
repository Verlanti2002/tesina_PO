package classi;

import gui.Applicazione;
import gui.Menu;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu(new Applicazione(new Tabella()));
    }
}
