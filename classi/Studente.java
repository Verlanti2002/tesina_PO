package classi;

/**
 * Studente
 * Classe che definisce l'entità Studente
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Studente {

    /** Attributo private di tipo int che rappresenta la matricola dello studente */
    private int matricola;
    /** Attributo private di tipo String che rappresenta il nome dello studente */
    private String nome;
    /** Attributo private di tipo String che rappresenta il cognome dello studente */
    private String cognome;

    /**
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * @param matricola matricola dello studente
     * @param nome nome dello studente
     * @param cognome cognome dello studente
     */
    public Studente(int matricola, String nome, String cognome){

        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
    }

    /** Getter Methods */

    /** Metodi necessari per accedere agli attributi dell'oggetto Studente in quanto privati
     * in modo da garantirne l'incapsulamento
    */

    /**
     * Metodo getter che restituisce la matricola dello studente
     * @return matricola dello studente
     */
    public int getMatricola() {
        return this.matricola;
    }

    /**
     * Metodo getter che restituisce il nome dello studente
     * @return nome dello studente
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Metodo getter che restituisce il cognome dello studente
     * @return cognome dello studente
     */
    public String getCognome() {
        return this.cognome;
    }

    /** Setter Methods */

    /** Metodi necessari per poter accedere e modificare gli attributi dell'oggetto Studente in quanto privati
     * in modo da garantirne l'incapsulamento
    */

    /**
     * Metodo setter che modifica la matricola dello studente 
     * @param matricola nuova matricola dello studente
     */
    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    /**
     * Metodo setter che modifica il nome dello studente
     * @param nome nuovo nome dello studente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo setter che modifica il cognome dello studente
     * @param cognome nuovo cognome dello studente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
