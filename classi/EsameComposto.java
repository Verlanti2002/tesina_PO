package classi;

import java.util.ArrayList;

/**
 * <strong>EsameComposto</strong>
 * <br>
 * Sottoclasse derivata della superclasse Esame
 * Definisce gli esami composti da più prove 
 * @see Esame
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class EsameComposto extends Esame{

    /**
     * Array di classe TipologiaProva
     * <br>
     * Necessario per memorizzare in ogni oggetto voto e peso
     * di un determinato esame parziale (scritto, orale o pratico) di un esame composto
     */
    private final ArrayList<EsameParziale> esami_parziali;

    /**
     * <strong>EsameComposto</strong>
     * <br>
     * Costruttore che inizializza gli attributi con i valori passati dall'utente <br>
     * Viene richiamato il costruttore della superclasse Esame
     * @param studente Studente che ha conseguito l'esame
     * @param nome Nome del corso di cui si è sostenuto l'esame
     * @param lode Valore della lode
     * @param cfu Numero di cfu totali dell'esame
     */
    public EsameComposto(Studente studente, String nome, boolean lode, int cfu) {
        super(studente, nome, lode, cfu);
        esami_parziali = new ArrayList<>();
    }

    /**
     * <strong>getEsamiParziali</strong>
     * <br>
     * Implementazione del metodo astratto della classe Esame <br>
     * Metodo in Override che restituisce l'array degli esami parziali di un determinato esame composto
     * @return Array contenente gli esami parziali
     */
    @Override
    public ArrayList<EsameParziale> getEsamiParziali() {
        return esami_parziali;
    }

    /**
     * <strong>voto</strong>
     * <br>
     * Implementazione del metodo astratto della classe Esame <br>
     * Metodo in Override che permette di calcolare il voto finale di un esame composto <br>
     * Dati gli esami parziali, va a moltiplicare il voto dei singoli esami parziali
     * con i loro relativi pesi, somamndo poi voto per voto delle prove parziali <br>
     * Infine, va a modificare direttamente il voto finale nella superclasse Esame
     */
    @Override
    public void voto(){

        if (esami_parziali != null) {
            int voto_finale = 0;

            for (EsameParziale esameParziale : esami_parziali) {
                if (esameParziale != null) {
                    voto_finale += (esameParziale.getVoto() * esameParziale.getPeso()) / 100;
                }
            }
            super.setVoto(voto_finale);
        }
    }
}
