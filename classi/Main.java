package classi;

import gui.Applicazione;
import gui.Menu;

public class Main {

    public static void main(String[] args) {
        String[] columnNames = new String[]{"Matricola", "Nome", "Cognome", "Corso", "Voto", "Lode", "CFU"};
        Tabella tabella = new Tabella(columnNames);
        Applicazione applicazione = new Applicazione(tabella);
        Menu menu = new Menu(applicazione);
    }
}
