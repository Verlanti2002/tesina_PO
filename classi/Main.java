package classi;

import gui.Applicazione;
import gui.Menu;
import gui.Tabella;

public class Main {

    public static void main(String[] args) {

        /*Studente studente1 = new Studente(186245, "Alessandro", "Verlanti");
        Studente studente2 = new Studente(175436, "Matteo", "Cappa");
        Studente studente3 = new Studente(157923, "Giacomo", "Paltrinieri");

        TipologiaProva esameParziale1 = new TipologiaProva("Scritto", 70, 26);
        TipologiaProva esameParziale2 = new TipologiaProva("Orale", 30,30);

        Esame[] esami = new Esame[3];

        EsameComposto esameComposto1 = new EsameComposto(studente3, "Analisi Matematica", 9);
        esameComposto1.aggiungiTipologia(esameParziale1);
        esameComposto1.aggiungiTipologia(esameParziale2);

        esami[0] = new EsameSemplice(studente1, "Fisica", 26, false, 9);
        // esami[1] = new EsameSemplice(studente3, 25,"Sistemi operativi", false, 9);
        esami[1] = esameComposto1;
        esami[2] = new EsameSemplice(studente2, "Architettura dei calcolatori",26,false, 9);

        for(int i=0; i< esami.length; i++){

            esami[i].visualizza();
            System.out.println("\n");
        }*/

        String[] columnNames = new String[]{"Matricola", "Nome", "Cognome", "Corso", "Voto", "Lode", "CFU"};
        Tabella tabella = new Tabella(columnNames);
        Applicazione applicazione = new Applicazione(tabella);
        Menu menu = new Menu(applicazione);

    }
}
