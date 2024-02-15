package classi;

import java.util.ArrayList;

/**
 * Esame
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
    /** Attribuzione o meno della lode */
    private boolean lode;
    /** Numero di cfu totali dell'esame */
    private int cfu;

    /**
     * Esame
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
     * getStudente
     * Metodo getter che restituisce lo studente che ha conseguito l'esame
     * @return Oggetto Studente
     */
    public Studente getStudente(){
        return this.studente;
    }

    /**
     * getNome
     * Metodo getter che restituisce il nome dell'esame
     * @return Nome dell'esame
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * getVoto
     * Metodo getter che restituisce il voto totale dell'esame
     * @return Voto finale
     */
    public int getVoto() {
        return this.voto;
    }

    /**
     * getLode
     * Metodo getter che restituisce un valore booleano
     * @return True (se il voto finale è con lode) False (altrimenti)
     */
    public boolean getLode() {
        return this.lode;
    }

    /**
     * getCfu
     * Metodo getter che restituisce il numero di cfu dell'esame
     * @return Cfu totali
     */
    public int getCfu() {
        return this.cfu;
    }

    /**
     * setNome
     * Metodo setter che modifica il nome dell'esame
     * @param nome Nuovo nome dell'esame
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * setVoto
     * Metodo setter che modifica il voto finale dell'esame
     * @param voto Nuovo voto dell'esame
     */
    public void setVoto(int voto) {
        this.voto = voto;
    }

    /**
     * setLode
     * Metodo setter che modifica l'assegnazione della lode
     * @param lode Nuova assegnazione della lode
     */
    public void setLode(boolean lode) {
        this.lode = lode;
    }

    /**
     * setCfu
     * Metodo setter che modifica il numero di cfu dell'esame
     * @param cfu Nuovo valore dei cfu dell'esame
     */
    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    /**
     * getEsamiParziali
     * Metodo astratto per ottenere l'array degli esami parziali di un determinato esame
     */
    public abstract ArrayList<EsameParziale> getEsamiParziali();

    /**
     * voto
     * Metodo astratto per il calcolo del voto finale
     */
    public abstract void voto();
}
