package classi;

import java.util.ArrayList;

/**
 * EsameSemplice <br>
 * Sottoclasse derivata della superclasse Esame <br>
 * Definisce gli esami semplici (ossia composti da una singola prova) 
 * @see Esame
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class EsameSemplice extends Esame{

    /**
     * <strong>EsameSemplice</strong>
     * Costruttore che inizializza gli attributi con i valori passati dall'utente <br>
     * Viene richiamato il costruttore della superclasse Esame <br>
     * Viene settato l'attributo voto della superclasse Esame in quanto già voto finale dell'esame
     * @param studente Studente che ha conseguito l'esame
     * @param voto Voto finale dell'esame
     * @param nome Nome del corso di cui si è sostenuto l'esame
     * @param lode Assegnazione o meno della lode
     * @param cfu Numero di cfu totali dell'esame
     */
    public EsameSemplice(Studente studente, String nome, int voto, boolean lode, int cfu) {
        super(studente, nome, lode, cfu);
        super.setVoto(voto);
    }

    /**
     * <strong>getEsamiParziali</strong>
     * Restituisce l'array delle prove parziali di un esame composto
     * @return L'array di prove parziali, o null se non disponibile
     */
    @Override
    public ArrayList<EsameParziale> getEsamiParziali() {
        return null;
    }

    /**
     * <strong>voto</strong>
     * Implementazione del metodo astratto della classe Esame
     * Metodo in Override con corpo vuoto in quanto il voto finale di un Esame Semplice è determinato
     * da un'unica prova
     */
    @Override
    public void voto() {}
}
