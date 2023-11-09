package Classi;

public class EsameComposto extends Esame{
    private EsameParziale[] esami_parziali;

    public EsameComposto(Studente studente, String nome, int cfu) {
        super(studente, nome, false, cfu);
    }

    public void voto(){

        int voto_finale = 0;

        for(int i=0; i< esami_parziali.length; i++){
            voto_finale += (esami_parziali[i].getVoto() * esami_parziali[i].getPeso())/100;
        }

        super.setVoto(voto_finale);
    }

    public void aggiungiTipologia(EsameParziale esameParziale){

        if(esami_parziali == null){
            esami_parziali = new EsameParziale[1];
            esami_parziali[0] = esameParziale;
            return;
        }
        EsameParziale[] tmp = new EsameParziale[esami_parziali.length+1];
        System.arraycopy(esami_parziali,0,tmp,0, esami_parziali.length);
        tmp[tmp.length-1] = esameParziale;
        esami_parziali = tmp;

        voto();
    }
}
