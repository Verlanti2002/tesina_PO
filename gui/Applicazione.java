package gui;

import classi.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Applicazione {

    private ArchivioStudenti<Studente> archivioStudenti;
    private ArchivioEsami<Esame> archivioEsami;
    private DefaultTableModel defaultTableModel;

    private JTable jTable;

    public Applicazione(){
        archivioStudenti = new ArchivioStudenti<>();
        archivioEsami = new ArchivioEsami<>();
        defaultTableModel = new DefaultTableModel();
    }

    public ArchivioEsami<Esame> getEsami(){
        return archivioEsami;
    }

    public ArchivioStudenti<Studente> getStudenti(){
        return archivioStudenti;
    }

    public JTable getjTable() {
        return jTable;
    }

    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

    public void initializeTable(){
        defaultTableModel.addColumn("Nome");
        defaultTableModel.addColumn("Cognome");
        defaultTableModel.addColumn("Corso");
        defaultTableModel.addColumn("Voto finale");
        defaultTableModel.addColumn("Lode");
        defaultTableModel.addColumn("CFU");
        jTable = new JTable(defaultTableModel);
    }

    public Studente searchStudent(int matricola){
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


