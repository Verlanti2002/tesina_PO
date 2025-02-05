package classi;

import classi.*;

/**
 * <strong>Applicazione</strong>
 * <br>
 * Classe che permette la gestione della tabella e degli archivi dati <br>
 * Viene condivisa alle classi che utilizzano la tabella e/o gli archivi dati
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Applicazione {

    /** Archivio degli studenti */
    private final ArchivioStudenti archivioStudenti;
    /** Archivio degli studenti */
    private final ArchivioEsami<Esame> archivioEsami;
    /** Tabella per la visualizzazione dei dati */
    private final Tabella tabella;

    /**
     * <strong>Applicazione</strong>
     * <br>
     * Costruttore che inizializza la tabella e gli archivi dati
     * @param tabella La tabella da utilizzare per la visualizzazione dei dati
     */
    public Applicazione(Tabella tabella){
        archivioStudenti = new ArchivioStudenti();
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
    public ArchivioStudenti getArchivioStudenti(){
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

    /** Metodo per il debugging del codice **/
    public void visualizzaStudenti(){
        for(int i=0; i< archivioStudenti.size(); i++){
            System.out.println(archivioStudenti.get(i).getMatricola());
            System.out.println(archivioStudenti.get(i).getNome());
            System.out.println(archivioStudenti.get(i).getCognome());
        }
    }

    /** Metodo per il debugging del codice **/
    public void visualizzaEsami(){
        for(int i=0; i< archivioEsami.size(); i++){
            System.out.println(archivioEsami.get(i).getStudente().getMatricola());
            System.out.println(archivioEsami.get(i).getNome());
        }
    }

    /** Metodo per il debugging del codice **/
    public void visualizzaEsamiParziali(){
        for(int i=0; i< archivioEsami.size(); i++){
            for(int j=0; j<archivioEsami.get(i).getEsamiParziali().size(); j++){
                System.out.println(archivioEsami.get(i).getEsamiParziali().get(j).getNome());
            }
        }
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
     * @param matricola Matricola dello studente da controllare
     * @return True se lo studente non ha sostenuto altri esami (quindi eliminabile), False altrimenti
     */
    public boolean checkEliminaStudente(int matricola){
        int counter = 0;
        for(int i=0; i<getArchivioEsami().size(); i++){
            if(getArchivioEsami().get(i).getStudente().getMatricola() == matricola)
                counter++;
        }
        return counter == 1;
    }

    /**
     * <strong>checkEsistenzaEsame</strong>
     * <br>
     * Metodo che verifica se un esame è gia stato registrato o meno
     * @param esame Esame da controllare
     * @return True se l'esame è gia stato registrato, False altrimenti
     */
    public boolean checkEsistenzaEsame(Esame esame){
        for(int i=0; i< archivioEsami.size(); i++){
            if(esame.getStudente().getMatricola() == archivioEsami.get(i).getStudente().getMatricola()) {
                if (esame.getNome().equalsIgnoreCase(archivioEsami.get(i).getNome()))
                    return true;
            }
        }
        return false;
    }

    /**
     * <strong>caricaTabella</strong>
     * <br>
     * Metodo che ricarica la tabella ogni volta che viene modificata
     */
    public void caricaTabella(){
        for(int i=0; i< archivioEsami.size(); i++){
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getStudente().getMatricola(), i, 0);
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getStudente().getNome(), i, 1);
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getStudente().getCognome(), i, 2);
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getNome(), i, 3);
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getVoto(), i, 4);
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getLode(), i, 5);
            tabella.getDefaultTableModel().setValueAt(archivioEsami.get(i).getCfu(), i, 6);
        }
    }
}


