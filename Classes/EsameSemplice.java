package Classes;

public class EsameSemplice extends Esame{

    private int voto;
    public EsameSemplice(Studente studente, int voto, String nome, boolean lode, int cfu) {
        super(studente, nome, lode, cfu);
        this.voto = voto;
    }

    @Override
    public int voto() {
        return voto;
    }

    @Override
    public boolean isSemplice() {
        return true;
    }
}
