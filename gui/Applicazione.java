package gui;

import classi.*;

/**
 * <strong>Applicazione</strong>
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
     * Metodo che restituisce l'archivio degli esami
     * @return L'archivio degli esami
     */
    public ArchivioEsami<Esame> getArchivioEsami(){
        return archivioEsami;
    }

    /**
     * <strong>getStudenti</strong>
     * Metodo che restituisce l'archivio degli studenti
     * @return L'archivio degli studenti
     */
    public ArchivioStudenti<Studente> getArchivioStudenti(){
        return archivioStudenti;
    }

    /**
     * <strong>getTabella</strong>
     * Metodo che restituisce la tabella utilizzata dall'applicazione
     * @return La tabella utilizzata dall'applicazione
     */
    public Tabella getTabella() {
        return tabella;
    }

    /**
     * <strong>ricercaStudente</strong>
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
     * <strong>verificaEsistenzaEsame</strong>
     * Metodo che verifica se un esame è gia stato registrato o meno
     * @param esame Esame da controllare
     * */
    public boolean verificaEsistenzaEsame(Esame esame){
        for(int i=0; i< archivioEsami.size(); i++){
            if(esame.getStudente().getMatricola() == archivioEsami.get(i).getStudente().getMatricola()) {
                if (esame.getNome().equalsIgnoreCase(archivioEsami.get(i).getNome()))
                    return true;
            }
        }
        return false;
    }
}


