package Classes;

public class EsameComposto extends Esame{
    private Tipologia[] tipologie = null;

    public EsameComposto(Studente studente, String nome, int cfu) {
        super(studente, nome, false, cfu);
    }

    public int voto(){

        int voto_finale = 0;

        for(int i=0; i< tipologie.length; i++){
            voto_finale += (tipologie[i].getVoto() * tipologie[i].getPeso())/100;
        }

        return voto_finale;
    }

    public void aggiungiTipologia(Tipologia tipologia){

        if(tipologie == null){
            tipologie = new Tipologia[1];
            tipologie[0] = tipologia;
            return;
        }
        Tipologia[] tmp = new Tipologia[tipologie.length+1];
        System.arraycopy(tipologie,0,tmp,0, tipologie.length);
        tmp[tmp.length-1] = tipologia;
        tipologie = tmp;
    }

    @Override
    public boolean isSemplice() {
        return false;
    }
}
