package classi;

import java.util.ArrayList;

/**
 * <strong>ArchivioStudenti</strong>
 * <br>
 * Questa classe rappresenta un archivio di studenti
 * @param <E> Il tipo degli studenti memorizzati nell'archivio
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class ArchivioStudenti<E> {

    /** Lista che contiene gli studenti */
    private final ArrayList<E> studenti;

    /**
     * <strong>ArchivioStudenti</strong>
     * <br>
     * Costruttore per creare un nuovo archivio di studenti
     * Inizializza la lista degli studenti
     */
    public ArchivioStudenti(){
        studenti = new ArrayList<>();
    }

    /**
     * <strong>getStudenti</strong>
     * Restituisce l'elenco degli studenti presenti nell'ArrayList
     * ArrayList contenente gli studenti
     */
    public ArrayList<E> getStudenti() {
        return studenti;
    }

    /**
     * <strong>add</strong>
     * <br>
     * Metodo che aggiunge un nuovo studente all'archivio
     * @param studente Lo studente da aggiungere all'archivio
     */
    public void add(E studente){
        studenti.add(studente);
    }

    /**
     * <strong>delete</strong>
     * <br>
     * Metodo che elimina uno studente dall'archivio
     * @param studente Lo studente da eliminare dall'archivio
     */
    public void delete(E studente){
        studenti.remove(studente);
    }

    /**
     * <strong>get</strong>
     * <br>
     * Metodo che ottiene uno studente dall'archivio in base all'indice specificato
     * @param index L'indice dello studente da ottenere
     * @return Lo studente corrispondente all'indice specificato
     */
    public E get(int index){
        return studenti.get(index);
    }

    /**
     * <strong>size</strong>
     * <br>
     * Metodo che restituisce il numero di studenti presenti nell'archivio
     * @return Il numero di studenti presenti nell'archivio
     */
    public int size(){
        return studenti.size();
    }

    /**
     * <strong>isEmpty</strong>
     * <br>
     * Metodo che verifica se l'archivio degli studenti è vuoto
     * @return True se l'archivio è vuoto, false altrimenti
     */
    public boolean isEmpty(){
        return studenti.isEmpty();
    }
}
