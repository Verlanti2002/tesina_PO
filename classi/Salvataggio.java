package classi;

import java.io.*;

public class Salvataggio {

    public Salvataggio(Esame esame){

        File file = new File("esami.txt");
        ObjectOutputStream objectOutputStream = null;

        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(esame);
            objectOutputStream.close();
        }catch (IOException e){
            System.err.println("Errore in fase di apertura o scrittura su file");
            System.exit(1);
        }
        System.out.println("Salvataggio avvenuto correttamente");
    }
}
