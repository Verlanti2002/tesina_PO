package classi;

import java.util.ArrayList;

/**
 * ArchivioEsami
 * Questa classe rappresenta un archivio di esami
 * @param <E> Il tipo degli esami memorizzati nell'archivio
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class ArchivioEsami<E> {

    /** Lista che contiene gli esami */
    private ArrayList<E> esami;

    /**
     * ArchivioEsami
     * Costruttore per creare un nuovo archivio di esami
     * Inizializza la lista degli esami
     */
    public ArchivioEsami(){
        esami = new ArrayList<>();
    }

    /**
     * add
     * Metodo che aggiunge un nuovo esame all'archivio
     * @param esame L'esame da aggiungere all'archivio
     */
    public void add (E esame){
        esami.add(esame);
    }

    /**
     * delete
     * Metodo che elimina un esame dall'archivio
     * @param esame L'esame da eliminare dall'archivio
     */
    public void delete(E esame){
        esami.remove(esame);
    }

    /**
     * get
     * Metodo che ottiene un esame dall'archivio in base all'indice specificato
     * @param index L'indice dell'esame da ottenere
     * @return L'esame corrispondente all'indice specificato
     */
    public E get(int index){
        return esami.get(index);
    }

    /**
     * size
     * Metodo che restituisce il numero di esami presenti nell'archivio
     * @return Il numero di esami presenti nell'archivio
     */
    public int size(){
        return esami.size();
    }

    /**
     * isEmpty
     * Metodo che verifica se l'archivio degli esami è vuoto.
     * @return True se l'archivio è vuoto, false altrimenti.
     */
    public boolean isEmpty(){
        return esami.isEmpty();
    }
}
