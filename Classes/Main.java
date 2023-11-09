package Classes;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Studente studente1 = new Studente(186245, "Alessandro", "Verlanti");
        Studente studente2 = new Studente(175436, "Matteo", "Cappa");
        Studente studente3 = new Studente(157923, "Giacomo", "Paltrinieri");

        Esame[] esami = new Esame[3];

        EsameComposto esameComposto1 = new EsameComposto(studente3, "Analisi Matematica", 9);
        esameComposto1.aggiungiTipologia(new Tipologia("Scritto", 70, 26));
        esameComposto1.aggiungiTipologia(new Tipologia("Orale", 30,30));

        esami[0] = new EsameSemplice(studente1, 26, "Fisica", false, 9);
        esami[1] = esameComposto1;
        esami[2] = new EsameSemplice(studente2, 26, "Architettura dei calcolatori", false, 9);

        for(int i=0; i< esami.length; i++){

            esami[i].visualizza();
            System.out.println("\n");
        }
    }
}
