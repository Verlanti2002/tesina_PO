package Classes;

public class EsameSemplice extends Esame{

    public EsameSemplice(Studente studente, int voto, String nome, boolean lode, int cfu) {
        super(studente, nome, lode, cfu);
        super.setVoto(voto);
    }

    @Override
    public boolean isSemplice() {
        return true;
    }
}
