package classi;

import java.util.ArrayList;

/**
 * <strong>Esame</strong>
 * <br>
 * Classe principale che definisce l'entità Esame
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public abstract class Esame {

    /** Studente che ha sostenuto l'esame */
    private Studente studente;
    /** Nome dell'esame (o corso) */
    private String nome;
    /** Voto finale dell'esame */
    private int voto;
    /** Attribuzione della lode */
    private boolean lode;
    /** Numero di cfu totali dell'esame */
    private int cfu;

    /**
     * <strong>Esame</strong>
     * <br>
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * @param studente Studente che ha conseguito l'esame
     * @param nome Nome del corso di cui si è sostenuto l'esame
     * @param lode Assegnazione o meno della lode
     * @param cfu Numero di cfu totali dell'esame
     */
    public Esame(Studente studente, String nome, boolean lode, int cfu){
        this.studente = studente;
        this.nome = nome;
        this.lode = lode;
        this.cfu = cfu;
    }

    /**
     * <strong>getStudente</strong>
     * <br>
     * Metodo getter che restituisce lo studente che ha conseguito l'esame
     * @return Oggetto Studente
     */
    public Studente getStudente(){
        return this.studente;
    }

    /**
     * <strong>getNome</strong>
     * <br>
     * Metodo getter che restituisce il nome dell'esame
     * @return Nome dell'esame
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * <strong>getVoto</strong>
     * <br>
     * Metodo getter che restituisce il voto totale dell'esame
     * @return Voto finale
     */
    public int getVoto() {
        return this.voto;
    }

    /**
     * <strong>getLode</strong>
     * <br>
     * Metodo getter che restituisce un valore booleano
     * @return True se il voto finale è con lode, False altrimenti
     */
    public boolean getLode() {
        return this.lode;
    }

    /**
     * <strong>getCfu</strong>
     * <br>
     * Metodo getter che restituisce il numero di cfu dell'esame
     * @return Cfu totali dell'esame
     */
    public int getCfu() {
        return this.cfu;
    }

    /**
     * <strong>setNome</strong>
     * <br>
     * Metodo setter che modifica il nome dell'esame
     * @param nome Nuovo nome dell'esame
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * <strong>setVoto</strong>
     * <br>
     * Metodo setter che modifica il voto finale dell'esame
     * @param voto Nuovo voto dell'esame
     */
    public void setVoto(int voto) {
        this.voto = voto;
    }

    /**
     * <strong>setLode</strong>
     * <br>
     * Metodo setter che modifica l'assegnazione della lode
     * @param lode Nuova assegnazione della lode
     */
    public void setLode(boolean lode) {
        this.lode = lode;
    }

    /**
     * <strong>setCfu</strong>
     * <br>
     * Metodo setter che modifica il numero di cfu dell'esame
     * @param cfu Nuovo valore dei cfu dell'esame
     */
    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    /**
     * <strong>getEsamiParziali</strong>
     * <br>
     * Metodo astratto per accedere all'array degli esami parziali di un determinato esame
     * Le due sottoclassi di Esame implementano questo metodo in quanto astratto
     */
    public abstract ArrayList<EsameParziale> getEsamiParziali();

    /**
     * <strong>voto</strong>
     * <br>
     * Metodo astratto per il calcolo del voto finale
     * Le due sottoclassi di Esame implementano questo metodo in quanto astratto
     */
    public abstract void voto();
}
