package classi;

/**
* EsameParziale
* Classe che permette di definire gli esami parziali di un esame composto
* (prova scritta, prova orale, prova pratica) 
* @author Alessandro Verlanti
* @version java 21.0.1 2023-10-17 LTS
*/
public class EsameParziale {

    /**
     * Nome dell'esame parziale
     * Dominio dell'attributo: scritto, orale, pratico
     * Attributo facoltativo (non strettamente necessario)
     */
    private String nome;
    /** Peso dell'esame parziale */
    private int peso;
    /** Voto dell'esame parziale */
    private int voto;

    /**
     * EsameParziale
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * @param nome Nome dell'esame parziale (scritto, orale, pratico)
     * @param peso Peso dell'esame parziale
     * @param voto Voto dell'esame parziale
     */
    public EsameParziale(String nome, int peso, int voto){
        this.nome = nome;
        this.peso = peso;
        this.voto = voto;
    }

    /**
     * getNome
     * Metodo getter che restituisce il nome di un esame parziale
     * @return nome dell'esame parziale
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * getPeso
     * Metodo getter che restituisce il peso di un esame parziale
     * @return peso dell'esame parziale
     */
    public int getPeso() {
        return this.peso;
    }

    /**
     * getVoto
     * Metodo getter che restituisce il voto di un esame parziale
     * @return voto dell'esame parziale
     */
    public int getVoto() {
        return this.voto;
    }

    /**
     * setNome
     * Metodo setter che modifica il nome di un esame parziale
     * @param nome nuovo nome dell'esame parziale
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * setPeso
     * Metodo setter che modifica il peso di un esame parziale
     * @param peso nuovo peso dell'esame parziale
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * setVoto
     * Metodo setter che modifica il voto di un esame parziale
     * @param voto nuovo voto dell'esame parziale
     */
    public void setVoto(int voto) {
        this.voto = voto;
    }
}
