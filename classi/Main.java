package classi;

import gui.Menu;

/**
 * <strong>Main</strong>
 * <br>
 * La classe Main è la classe di avvio dell'applicazione <br>
 * Questa classe contiene il metodo principale che avvia il programma
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Main {

    /**
     * Il metodo principale avvia l'applicazione creando un'istanza di Menu
     * e passando un'istanza di Applicazione con una Tabella al costruttore
     * @param args Gli argomenti della riga di comando (non utilizzati in questo caso)
     */
    public static void main(String[] args) {
        Menu menu = new Menu(new Applicazione(new Tabella()));
    }
}
