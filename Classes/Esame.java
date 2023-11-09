package Classes;

public abstract class Esame {

    private Studente studente;
    private String nome;
    private int voto;
    private boolean lode;
    private int cfu;

    public Esame(){
        this.studente = null;
        this.nome = "";
        this.voto = 0;
        this.lode = false;
        this.cfu = 0;
    }

    public Esame(Studente studente, String nome, boolean lode, int cfu){
        this.studente = studente;
        this.nome = nome;
        this.voto = voto();
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

    public abstract int voto();

    public abstract boolean isSemplice();

}
