package gui;

import classi.*;

/**
 * Applicazione
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
     * Applicazione
     * Costruttore per inizializzare l'applicazione con una tabella
     * @param tabella La tabella da utilizzare per la visualizzazione dei dati
     */
    public Applicazione(Tabella tabella){
        archivioStudenti = new ArchivioStudenti<>();
        archivioEsami = new ArchivioEsami<>();
        this.tabella = tabella;
    }

    /**
     * getEsami
     * Metodo che restituisce l'archivio degli esami
     * @return L'archivio degli esami
     */
    public ArchivioEsami<Esame> getEsami(){
        return archivioEsami;
    }

    /**
     * getStudenti
     * Metodo che restituisce l'archivio degli studenti
     * @return L'archivio degli studenti
     */
    public ArchivioStudenti<Studente> getStudenti(){
        return archivioStudenti;
    }

    /**
     * getTabella
     * Metodo che restituisce la tabella utilizzata dall'applicazione
     * @return La tabella utilizzata dall'applicazione
     */
    public Tabella getTabella() {
        return tabella;
    }

    /**
     * ricercaStudente
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
     * studentExist
     * Verifica se uno studente con la matricola specificata esiste nell'archivio degli studenti
     * @param matricola La matricola dello studente da cercare
     * @return True se uno studente con la matricola specificata esiste, altrimenti false
     */
    public Studente studentExist(int matricola){
        for(int i=0; i< archivioStudenti.size(); i++){
            if(archivioStudenti.get(i).getMatricola() == matricola)
                return archivioStudenti.get(i);
        }
        return null;
    }
}


