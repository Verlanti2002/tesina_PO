package classi;

import java.util.ArrayList;
import java.util.Vector;

public class ArchivioEsami<E> {

    private ArrayList<E> esami;

    public ArchivioEsami(){
        esami = new ArrayList<>();
    }

    public void add (E esame){
        esami.add(esame);
    }

    public void delete(E esame){
        esami.remove(esame);
    }

    public E get(int index){
        return esami.get(index);
    }

    public int size(){
        return esami.size();
    }

    public boolean isEmpty(){
        return esami.isEmpty();
    }
}
