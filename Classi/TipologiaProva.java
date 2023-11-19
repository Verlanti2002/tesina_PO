package Classi;

/**
* TipologiaProva
* Classe che definisce la tipologia di prova di un esame
* (prova scritta, prova orale, prova pratica) 
* @author Alessandro Verlanti
* @version java 21.0.1 2023-10-17 LTS
*/
public class TipologiaProva {

    /** Attributo privato di tipo String che rappresenta il nome dell'esame parziale 
     * Campo dell'attributo: scritto, orale, pratico
     * Attributo facoltativo (non strettamente necessario)
     */
    private String nome;
    /** Attributo privato di tipo int che rappresenta il peso dell'esame parziale */
    private int peso;
    /** Attributo privato di tipo int che rappresenta il voto dell'esame parziale */
    private int voto;

    /**
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * @param nome nome dell'esame parziale (scritto, orale, pratico)
     * @param peso peso dell'esame parziale
     * @param voto voto dell'esame parziale
     */
    public TipologiaProva(String nome, int peso, int voto){
        this.nome = nome;
        this.peso = peso;
        this.voto = voto;
    }

    /** Getter Methods */

    /** Metodi necessari per accedere agli attributi dell'oggetto Studente in quanto privati
     * in modo da garantirne l'incapsulamento 
    */

    /** 
     * Metodo getter che restituisce il nome dell'esame parziale
     * @return nome dell'esame parziale
     */
    public String getNome() {
        return nome;
    }

    /** 
     * Metodo getter che restituisce il peso dell'esame parziale
     * @return peso dell'esame parziale
     */
    public int getPeso() {
        return peso;
    }

    /** 
     * Metodo getter che restituisce il voto dell'esame parziale
     * @return voto dell'esame parziale
     */
    public int getVoto() {
        return voto;
    }
}
