package gui;

import classi.*;
import gui.my_components.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Applicazione {

    //private Controllore controllore;
    private ArchivioStudenti<Studente> archivioStudenti = new ArchivioStudenti<>();
    private ArchivioEsami<Esame> archivioEsami = new ArchivioEsami<>();

    public Applicazione(){}

    /** Da mettere a posto
    public void caricaEsamiPanel(){
        disposeMainFrame("Caricamento Esame");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(main_frame);
        Caricamento caricamento = new Caricamento(jFileChooser.getName());
        main_panel.add(jFileChooser);
        data_frame.add(main_panel);
        data_frame.pack();
        //Caricamento carica = new Caricamento(jFileChooser.getF);
    }**/

    public ArchivioEsami<Esame> getEsami(){
        return archivioEsami;
    }

    public ArchivioStudenti<Studente> getStudenti(){
        return archivioStudenti;
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


