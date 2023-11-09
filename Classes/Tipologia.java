package Classes;

public class Tipologia {

    private String nome;
    private int peso;
    private int voto;

    public Tipologia(){
        this.nome = "";
        this.peso = 0;
        this.voto = 0;
    }

    public Tipologia(String nome, int peso, int voto){
        this.nome = nome;
        this.peso = peso;
        this.voto = voto;
    }


    public String getNome() {
        return nome;
    }

    public int getPeso() {
        return peso;
    }

    public int getVoto() {
        return voto;
    }
}
