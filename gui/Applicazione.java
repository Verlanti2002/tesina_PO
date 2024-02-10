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
         final String[] columnNames = {"Nome", "Cognome", "Corso", "Voto", "Lode", "CFU"};
        creaTabella(columnNames);
    }

    public void creaTabella(String[] columnNames){
        for(int i=0; i< columnNames.length; i++){
            defaultTableModel.addColumn(columnNames[i]);
        }
        jTable = new JTable(defaultTableModel);
    }

    public void aggiungiRigaAllaTabella(String[] rowData){

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


