package classi;

import java.util.ArrayList;

/**
 * ArchivioStudenti
 * Questa classe rappresenta un archivio di studenti
 * @param <E> Il tipo degli studenti memorizzati nell'archivio
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class ArchivioStudenti<E> {

    /** Lista che contiene gli studenti */
    private ArrayList<E> studenti;

    /**
     * ArchivioStudenti
     * Costruttore per creare un nuovo archivio di studenti
     * Inizializza la lista degli studenti
     */
    public ArchivioStudenti(){
        studenti = new ArrayList<>();
    }

    /**
     * add
     * Metodo che aggiunge un nuovo studente all'archivio
     * @param studente Lo studente da aggiungere all'archivio
     */
    public void add(E studente){
        studenti.add(studente);
    }

    /**
     * delete
     * Metodo che elimina uno studente dall'archivio
     * @param studente Lo studente da eliminare dall'archivio
     */
    public void delete(E studente){
        studenti.remove(studente);
    }

    /**
     * get
     * Metodo che ottiene uno studente dall'archivio in base all'indice specificato
     * @param index L'indice dello studente da ottenere
     * @return Lo studente corrispondente all'indice specificato
     */
    public E get(int index){
        return studenti.get(index);
    }

    /**
     * size
     * Metodo che restituisce il numero di studenti presenti nell'archivio
     * @return Il numero di studenti presenti nell'archivio
     */
    public int size(){
        return studenti.size();
    }

    /**
     * isEmpty
     * Metodo che verifica se l'archivio degli studenti è vuoto
     * @return True se l'archivio è vuoto, false altrimenti
     */
    public boolean isEmpty(){
        return studenti.isEmpty();
    }

}
