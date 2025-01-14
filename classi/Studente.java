package classi;

/**
 * <strong>Studente</strong>
 * Classe che definisce l'entità Studente
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Studente {

    /** Matricola dello studente */
    private int matricola;
    /** Nome dello studente */
    private String nome;
    /** Cognome dello studente */
    private String cognome;

    /**
     * <strong>Studente</strong>
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * @param matricola Matricola dello studente
     * @param nome Nome dello studente
     * @param cognome Cognome dello studente
     */
    public Studente(int matricola, String nome, String cognome){

        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * <strong>getMatricola</strong>
     * Metodo getter che restituisce la matricola dello studente
     * @return Matricola dello studente
     */
    public int getMatricola() {
        return this.matricola;
    }

    /**
     * <strong>getNome</strong>
     * Metodo getter che restituisce il nome dello studente
     * @return Nome dello studente
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * <strong>getCognome</strong>
     * Metodo getter che restituisce il cognome dello studente
     * @return Cognome dello studente
     */
    public String getCognome() {
        return this.cognome;
    }

    /**
     * <strong>setMatricola</strong>
     * Metodo setter che modifica la matricola dello studente 
     * @param matricola Nuova matricola dello studente
     */
    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    /**
     * <strong>setNome</strong>
     * Metodo setter che modifica il nome dello studente
     * @param nome Nuovo nome dello studente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * <strong>setCognome</strong>
     * Metodo setter che modifica il cognome dello studente
     * @param cognome Nuovo cognome dello studente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * <strong>toString</strong>
     * Metodo che restituisce una rappresentazione testuale dello studente come una stringa
     * contenente il nome e il cognome dello studente separati da uno spazio
     * @return Una stringa contenente il nome e il cognome dello studente
     */
    public String toString(){ return nome + " " + cognome; }
}
