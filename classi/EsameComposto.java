package classi;

import java.util.ArrayList;

/**
 * EsameComposto
 * Sottoclasse derivata della superclasse Esame
 * Definisce gli esami composti da più prove 
 * @see Esame
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class EsameComposto extends Esame{

    /**
     * Array di classe TipologiaProva
     * Necessario per memorizzare in ogni oggetto voto e peso
     * di un determinato esame parziale (scritto, orale o pratico) di un esame composto
     */
    private ArrayList<EsameParziale> esami_parziali;

    /**
     * EsameComposto
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * Viene richiamato il costruttore della superclasse Esame
     * @param studente Studente che ha conseguito l'esame
     * @param nome Nome del corso di cui si è sostenuto l'esame
     * @param cfu Numero di cfu totali dell'esame
     * L'attributo relativo alla lode è settato a false in quanto esame di tipo composto
     */
    public EsameComposto(Studente studente, String nome, boolean lode, int cfu) {
        super(studente, nome, lode, cfu);
        esami_parziali = new ArrayList<>();
    }

    /**
     * getEsamiParziali
     * Metodo che restituisce l'array degli esami parziali di un determinato esame
     * @return Array contenente gli esami parziali
     * */
    public ArrayList<EsameParziale> getEsamiParziali() {
        return esami_parziali;
    }

    /**
     * voto
     * Metodo che permette di calcolare il voto finale di un esame composto
     * Dati gli esami parziali, va a moltiplicare il voto dei singoli esami parziali
     * con i loro relativi pesi, somamndo poi voto per voto delle prove parziali
     * Infine, va a modificare direttamente il voto finale nella superclasse Esame
     */
    public void voto(){

        if (esami_parziali != null) {
            int voto_finale = 0;

            for (EsameParziale esameParziale : esami_parziali) {
                if (esameParziale != null) {
                    voto_finale += (esameParziale.getVoto() * esameParziale.getPeso()) / 100;
                }
            }

            super.setVoto(voto_finale);
        } else {
            System.out.println("Non è stata registrata alcuna prova parziale");
        }
    }
}
