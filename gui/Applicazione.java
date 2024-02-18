package gui;

import classi.*;

/**
 * <strong>Applicazione</strong>
 * <br>
 * Classe condivisa in ogni altra classe per la gestione degli archivi dati e della tabella
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Applicazione {

    /** Archivio degli studenti */
    private final ArchivioStudenti<Studente> archivioStudenti;
    /** Archivio degli studenti */
    private final ArchivioEsami<Esame> archivioEsami;
    /** Tabella per la visualizzazione dei dati */
    private final Tabella tabella;

    /**
     * <strong>Applicazione</strong>
     * <br>
     * Costruttore per inizializzare l'applicazione con una tabella
     * @param tabella La tabella da utilizzare per la visualizzazione dei dati
     */
    public Applicazione(Tabella tabella){
        archivioStudenti = new ArchivioStudenti<>();
        archivioEsami = new ArchivioEsami<>();
        this.tabella = tabella;
    }

    /**
     * <strong>getEsami</strong>
     * <br>
     * Metodo che restituisce l'archivio degli esami
     * @return L'archivio degli esami
     */
    public ArchivioEsami<Esame> getArchivioEsami(){
        return archivioEsami;
    }

    /**
     * <strong>getStudenti</strong>
     * <br>
     * Metodo che restituisce l'archivio degli studenti
     * @return L'archivio degli studenti
     */
    public ArchivioStudenti<Studente> getArchivioStudenti(){
        return archivioStudenti;
    }

    /**
     * <strong>getTabella</strong>
     * <br>
     * Metodo che restituisce la tabella utilizzata dall'applicazione
     * @return La tabella utilizzata dall'applicazione
     */
    public Tabella getTabella() {
        return tabella;
    }

    /**
     * <strong>ricercaStudente</strong>
     * <br>
     * Cerca uno studente nell'archivio degli studenti utilizzando la matricola
     * @param matricola La matricola dello studente da cercare
     * @return Lo studente corrispondente alla matricola, o null se non trovato
     */
    public Studente ricercaStudente(int matricola){
        for(int i=0; i< archivioStudenti.size(); i++){
            if (archivioStudenti.get(i).getMatricola() == matricola)
                return archivioStudenti.get(i);
        }
        return null;
    }

    /**
     * <strong>checkEliminaStudente</strong>
     * <br>
     * Metodo che verifica se è possibile eliminare l'utente quando si vuole eliminare un esame <br>
     * Controlla se lo studente ha sostenuto altri esami oltre a quello che si vuole eliminare
     * @param selectedRow Indice dello studente da controllare
     * @return True se lo studente non ha sostenuto altri esami (quindi eliminabile), false altrimenti
     */
    public boolean checkEliminaStudente(int selectedRow){
        for(int i=0; i<getArchivioEsami().size(); i++){
            if(getArchivioEsami().get(i).getStudente().getMatricola() == getArchivioEsami().get(selectedRow).getStudente().getMatricola())
                return false;
        }
        return true;
    }

    /**
     * <strong>checkEsistenzaEsame</strong>
     * <br>
     * Metodo che verifica se un esame è gia stato registrato o meno
     * @param esame Esame da controllare
     * */
    public boolean checkEsistenzaEsame(Esame esame){
        for(int i=0; i< archivioEsami.size(); i++){
            if(esame.getStudente().getMatricola() == archivioEsami.get(i).getStudente().getMatricola()) {
                if (esame.getNome().equalsIgnoreCase(archivioEsami.get(i).getNome()))
                    return true;
            }
        }
        return false;
    }
}


