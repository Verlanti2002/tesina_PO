package Classi;

/**
 * Classe principale che definisce l'entità Esame
 * @author Alessandro Verlanti
 * @version 17.0.8.1 - July 18, 2023
 */
public class Esame {

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
     * @param studente oggetto che descrive lo studente da esaminare
     * @param nome nome del corso di cui si è sostenuto l'esame
     * @param lode campo boolean per l'assegnamento della lode
     * @param cfu numero di cfu totali dell'esame
     */
    public Esame(Studente studente, String nome, boolean lode, int cfu){
        this.studente = studente;
        this.nome = nome;
        this.lode = lode;
        this.cfu = cfu;
    }

    /** Metodo che permette di visualizzare le informazioni principali relative ad un esame di uno studente */
    public void visualizza(){

        System.out.println(studente.getMatricola() + " " + studente.getNome() + " " + studente.getCognome());
        System.out.println(nome + " " + voto);
    }

    /** Getter Methods **/

    /** Getter che restituisce il nome dell'esame
     * @return nome dell'esame
     */
    public String getNome() {
        return nome;
    }

    /** Getter che restituisce il voto totale dell'esame
     * @return voto finale
     */
    public int getVoto() {
        return voto;
    }

    public boolean isLode() {
        return lode;
    }

    public int getCfu() {
        return cfu;
    }

    /** Setter Methods **/

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setLode(boolean lode) {
        this.lode = lode;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }
}
