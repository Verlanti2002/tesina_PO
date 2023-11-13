package Classi;

/**
 * EsameSemplice
 * Sottoclasse derivata della superclasse Esame
 * Definisce gli esami semplici (ossia composti da una singola prova) 
 * @see Esame
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class EsameSemplice extends Esame{

    /**
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * Viene richiamato il costruttore della superclasse Esame
     * Viene settato l'attributo voto della superclasse Esame in quanto già voto finale dell'esame
     * @param studente studente che ha conseguito l'esame
     * @param voto voto finale dell'esame
     * @param nome nome del corso di cui si è sostenuto l'esame
     * @param lode assegnazione o meno della lode
     * @param cfu numero di cfu totali dell'esame
     */
    public EsameSemplice(Studente studente, int voto, String nome, boolean lode, int cfu) {
        super(studente, nome, lode, cfu);
        super.setVoto(voto);
    }
}
