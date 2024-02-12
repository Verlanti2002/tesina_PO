package classi;

import java.util.ArrayList;

/**
 * Esame
 * Classe principale che definisce l'entità Esame
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public abstract class Esame {

    /** Attributo privato di classe Studente che rappresenta l'oggetto studente */
    private Studente studente;
    /** Attributo privato di tipo String che rappresenta il nome dell'esame */
    private String nome;
    /** Attributo privato di tipo intero che rappresenta il voto finale dell'esame */
    private int voto;
    /** Attributo privato di tipo boolean che indica se il voto è con lode (true) o meno (false) */
    private boolean lode;
    /** Attributo privato di tipo int che indica il numero di cfu totali dell'esame */
    private int cfu;

    /**
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * @param studente studente che ha conseguito l'esame
     * @param nome nome del corso di cui si è sostenuto l'esame
     * @param lode assegnazione o meno della lode
     * @param cfu numero di cfu totali dell'esame
     */
    public Esame(Studente studente, String nome, boolean lode, int cfu){
        this.studente = studente;
        this.nome = nome;
        this.lode = lode;
        this.cfu = cfu;
    }

    /** 
     * Metodo che permette di visualizzare le informazioni principali relative ad un esame di uno studente
     */
    public void visualizza(){

        System.out.println(studente.getMatricola() + " " + studente.getNome() + " " + studente.getCognome());
        System.out.println(nome + " " + voto + " " + lode + " " + cfu + " " );
    }

    /** Getter Methods */

    /** Metodi necessari per accedere agli attributi dell'oggetto Studente in quanto privati
     * in modo da garantirne l'incapsulamento
    */

    /**
     * Metodo getter che restituisce lo studente che ha conseguito l'esame
     * @return oggetto Studente
     */
    public Studente getStudente(){
        return this.studente;
    }

    /** 
     * Metodo getter che restituisce il nome dell'esame
     * @return nome dell'esame
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Metodo getter che restituisce il voto totale dell'esame
     * @return voto finale
     */
    public int getVoto() {
        return this.voto;
    }

    /**
     * Metodo getter che restituisce un valore booleano
     * @return true (se il voto finale è con lode) false (altrimenti)
     */
    public boolean getLode() {
        return this.lode;
    }

    /**
     * Metodo getter che restituisce il numero di cfu dell'esame
     * @return cfu totali
     */
    public int getCfu() {
        return this.cfu;
    }

    /** Setter Methods */

    /** Metodi necessari per poter accedere e modificare gli attributi dell'oggetto Studente in quanto privati
     * in modo da garantirne l'incapsulamento
    */

    /**
     * Metodo setter che modifica il nome dell'esame
     * @param nome nuovo nome dell'esame
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo setter che modifica il voto finale dell'esame
     * @param voto nuovo voto dell'esame
     */
    public void setVoto(int voto) {
        this.voto = voto;
    }

    /**
     * Metodo setter che modifica l'assegnazione della lode
     * @param lode nuova assegnazione della lode
     */
    public void setLode(boolean lode) {
        this.lode = lode;
    }

    /**
     * Metodo setter che modifica il numero di cfu dell'esame
     * @param cfu nuovo valore dei cfu dell'esame
     */
    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    public abstract ArrayList<TipologiaProva> getEsami_parziali();

    public abstract void voto();
}
