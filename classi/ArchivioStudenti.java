package classi;

import java.util.ArrayList;

public class ArchivioStudenti<E> {

    private ArrayList<E> studenti;

    public ArchivioStudenti(){
        studenti = new ArrayList<>();
    }

    public void add(E studente){
        studenti.add(studente);
    }

    public void delete(E studente){
        studenti.remove(studente);
    }

    public E get(int index){
        return studenti.get(index);
    }

    public int size(){
        return studenti.size();
    }

    public boolean isEmpty(){
        return studenti.isEmpty();
    }

}
