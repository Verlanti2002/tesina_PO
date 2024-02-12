package gui;

import classi.*;

public class Applicazione {

    private ArchivioStudenti<Studente> archivioStudenti;
    private ArchivioEsami<Esame> archivioEsami;
    private Tabella tabella;

    public Applicazione(Tabella tabella){
        archivioStudenti = new ArchivioStudenti<>();
        archivioEsami = new ArchivioEsami<>();
        this.tabella = tabella;
    }

    public ArchivioEsami<Esame> getEsami(){
        return archivioEsami;
    }

    public ArchivioStudenti<Studente> getStudenti(){
        return archivioStudenti;
    }

    public Tabella getTabella() {
        return tabella;
    }

    public Studente ricercaStudente(int matricola){
        for(int i=0; i< archivioStudenti.size(); i++){
            if (archivioStudenti.get(i).getMatricola() == matricola)
                return archivioStudenti.get(i);
        }
        return null;
    }

    public boolean studentExist(int matricola){
        for(int i=0; i< archivioStudenti.size(); i++){
            if(archivioStudenti.get(i).getMatricola() == matricola)
                return true;
        }
        return false;
    }
}


