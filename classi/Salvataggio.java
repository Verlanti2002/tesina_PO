package classi;

import java.io.*;
import java.util.ArrayList;

/**
 * Esempio di salvataggio su file
 * Matricola
 * Nome esame
 * Voto
 * Lode
 * Cfu
 * **/

public class Salvataggio {

    public Salvataggio(ArchivioEsami<Esame> esami){

        File file = new File("esami.txt");
        ObjectOutputStream objectOutputStream = null;

        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            for(int i=0; i< esami.size(); i++){
                objectOutputStream.writeObject(esami.get(i).getStudente().getMatricola());
                objectOutputStream.writeObject(esami.get(i).getNome());
                objectOutputStream.writeObject(esami.get(i).getVoto());
                objectOutputStream.writeObject(esami.get(i).getLode());
                objectOutputStream.writeObject(esami.get(i).getCfu());
            }
            objectOutputStream.close();
        }catch (IOException e){
            System.err.println("Errore in fase di apertura o scrittura su file");
            System.exit(1);
        }
        System.out.println("Salvataggio avvenuto correttamente");
    }
}
