package Classi;

public class Main {

    public static void main(String[] args) {

        Studente studente1 = new Studente(186245, "Alessandro", "Verlanti");
        Studente studente2 = new Studente(175436, "Matteo", "Cappa");
        Studente studente3 = new Studente(157923, "Giacomo", "Paltrinieri");

        EsameParziale esameParziale1 = new EsameParziale("Scritto", 70, 26);
        EsameParziale esameParziale2 = new EsameParziale("Orale", 30,30);

        Esame[] esami = new Esame[3];

        EsameComposto esameComposto1 = new EsameComposto(studente3, "Analisi Matematica", 9);
        esameComposto1.aggiungiTipologia(esameParziale1);
        esameComposto1.aggiungiTipologia(esameParziale2);

        esami[0] = new EsameSemplice(studente1, 26, "Fisica", false, 9);
        // esami[1] = new EsameSemplice(studente3, 25,"Sistemi operativi", false, 9);
        esami[1] = esameComposto1;
        esami[2] = new EsameSemplice(studente2, 26, "Architettura dei calcolatori", false, 9);

        for(int i=0; i< esami.length; i++){

            esami[i].visualizza();
            System.out.println("\n");
        }
    }
}
