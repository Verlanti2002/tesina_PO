package classi;

import gui.Applicazione;

import java.io.*;
import java.util.ArrayList;

public class Caricamento extends Applicazione {

    public Caricamento(String path){

        File file = new File(path);
        ObjectInputStream objectInputStream = null;

        try{
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Esame esame = (Esame) objectInputStream.readObject();
            getArchivioStudenti().add(esame.getStudente());
            getArchivioEsami().add(esame);
            objectInputStream.close();
        }catch (Exception e){
            System.err.println("Errore in fase di apertura o lettura del file ");
            System.exit(1);
        }
        System.out.println("Caricamento avvenuto correttamente!");
    }
}
