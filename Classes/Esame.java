package Classes;

/**
 * Classe principale che definisce l'entità Esame
 * @author Alessandro Verlanti
 * @version 17.0.8.1 - July 18, 2023
 */
public abstract class Esame {

    private Studente studente;
    private String nome;
    private int voto;
    private boolean lode;
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

    public void visualizza(){

        System.out.println(studente.getMatricola() + " " + studente.getNome() + " " + studente.getCognome());
        System.out.println(nome + " " + voto);
    }

    public String getNome() {
        return nome;
    }

    public int getVoto() {
        return voto;
    }

    public boolean isLode() {
        return lode;
    }

    public int getCfu() {
        return cfu;
    }

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

    public abstract boolean isSemplice();

}
