package Classi;

/**
 * EsameComposto
 * Sottoclasse derivata della superclasse Esame
 * Definisce gli esami composti da più prove 
 * @see Esame.java
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class EsameComposto extends Esame{

    /**
     * Array di classe EsameParziale
     * Necessario per memorizzare in ogni oggetto voto e peso
     * di un determinato esame parziale (scritto, orale o pratico) di un esame composto
     */
    private EsameParziale[] esami_parziali;

    /**
     * Costruttore che inizializza gli attributi con i valori passati dall'utente
     * Viene richiamato il costruttore della superclasse Esame
     * @param studente studente che ha conseguito l'esame
     * @param nome nome del corso di cui si è sostenuto l'esame
     * @param cfu numero di cfu totali dell'esame
     * L'attributo relativo alla lode è settato a false in quanto esame di tipo composto
     */
    public EsameComposto(Studente studente, String nome, int cfu) {
        super(studente, nome, false, cfu);
    }

    /**
     * Metodo che permette di calcolare il voto finale di un esame composto
     * Dati gli esami parziali, va a moltiplicare il voto dei singoli esami parziali
     * con i loro relativi pesi, somamndo poi voto per voto delle prove parziali
     * Infine va a modificare direttamente il voto finale nella superclasse Esame
     */
    public void voto(){

        int voto_finale = 0;

        for(int i=0; i< esami_parziali.length; i++){
            voto_finale += (esami_parziali[i].getVoto() * esami_parziali[i].getPeso())/100;
        }

        super.setVoto(voto_finale);
    }

    /**
     * Metodo che aggiunge all'array degli esami parziali l'oggetto passato
     * e aggiorna il voto finale dell'esame a seconda degli esami parziali registrati in quel momento
     * @param esameParziale esame parziale
     */
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
