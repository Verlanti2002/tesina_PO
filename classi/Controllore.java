package classi;

import java.util.ArrayList;

public class Controllore {

    public Controllore(){};

    public boolean controlloMatricola(ArrayList<Studente> studenti, int matricola){
        for(int i=0; i<studenti.size();i++){
            if(studenti.get(i).getMatricola() == matricola)
                return true;
        }
        return false;
    }


}
