package classi;

/**
* <strong>EsameParziale</strong>
 * <br>
* Classe che permette di definire gli esami parziali di un esame composto <br>
* Esempi: prova scritta, prova orale, prova pratica
* @author Alessandro Verlanti
* @version java 21.0.1 2023-10-17 LTS
*/
public class EsameParziale {

    /**
     * Nome dell'esame parziale <br>
     * Dominio dell'attributo: scritto, orale, pratico <br>
     * Attributo facoltativo (non strettamente necessario)
     */
    private String nome;
    /** Peso dell'esame parziale */
    private int peso;
    /** Voto dell'esame parziale */
    private int voto;

    /**
     * <strong>EsameParziale</strong>
     * <br>
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
     * <strong>getNome</strong>
     * <br>
     * Metodo getter che restituisce il nome di un esame parziale
     * @return Nome dell'esame parziale
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * <strong>getPeso</strong>
     * <br>
     * Metodo getter che restituisce il peso di un esame parziale
     * @return Peso dell'esame parziale
     */
    public int getPeso() {
        return this.peso;
    }

    /**
     * <strong>getVoto</strong>
     * <br>
     * Metodo getter che restituisce il voto di un esame parziale
     * @return Voto dell'esame parziale
     */
    public int getVoto() {
        return this.voto;
    }

    /**
     * <strong>setNome</strong>
     * <br>
     * Metodo setter che modifica il nome di un esame parziale
     * @param nome Nuovo nome dell'esame parziale
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * <strong>setPeso</strong>
     * <br>
     * Metodo setter che modifica il peso di un esame parziale
     * @param peso Nuovo peso dell'esame parziale
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * <strong>setVoto</strong>
     * <br>
     * Metodo setter che modifica il voto di un esame parziale
     * @param voto Nuovo voto dell'esame parziale
     */
    public void setVoto(int voto) {
        this.voto = voto;
    }
}
