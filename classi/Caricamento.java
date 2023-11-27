package classi;

import gui.Applicazione;

import java.io.*;
import java.util.ArrayList;

public class Caricamento {

    public Caricamento(File file, Applicazione applicazione){

        ObjectInputStream objectInputStream = null;

        try{
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            
            Esame esame = (Esame) objectInputStream.readObject();
            applicazione.getStudenti().add(esame.getStudente());
            applicazione.getEsami().add(esame);
            objectInputStream.close();
        }catch (Exception e){
            System.err.println("Errore in fase di apertura o lettura del file ");
            System.exit(1);
        }
        System.out.println("Caricamento avvenuto correttamente!");
    }
}
