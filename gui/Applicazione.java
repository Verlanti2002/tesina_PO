package gui;

import classi.*;
import gui.my_components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Applicazione{

    private MyFrame mainFrame;
    private MyFrame dataFrame;
    private MyPanel mainPanel;

    //private Controllore controllore;

    private ArchivioStudenti<Studente> archivioStudenti;

    private ArchivioEsami<Esame> archivioEsami;

    public Applicazione(){
        archivioStudenti = new ArchivioStudenti<>();
        archivioEsami = new ArchivioEsami<>();
        MainWindow();
    }

    public void disposeMainFrame(String titolo){
        //mainFrame.setVisible(false);
        dataFrame = new MyFrame(titolo);
        mainPanel = new MyPanel();
    }

    public void disposeDataFrame(String titolo){
        //dataFrame.setVisible(false);
        dataFrame = new MyFrame(titolo);
        mainPanel = new MyPanel();
    }

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

    public void MainWindow(){

        mainFrame = new MyFrame("Gestione Esami");

        mainPanel = new MyPanel();
        mainPanel.setLayout(new GridLayout(4,2));

        MyButton registra_studente_b = new MyButton("Registra Studente");
        registra_studente_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistraStudente registraStudente = new RegistraStudente();
            }
        });
        MyButton registra_esame_b = new MyButton("Registra Esame");
        registra_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistraEsame registraEsame = new RegistraEsame();
            }
        });
        MyButton carica_esame_b = new MyButton("Carica Esami");
        carica_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                // Caricamento caricamento = new Caricamento();
            }
        });
        MyButton salva_esame_b = new MyButton("Salva Esami");

        if(archivioEsami.isEmpty())
            salva_esame_b.setEnabled(false);
        salva_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Salvataggio salvataggio = new Salvataggio(archivioEsami);
            }
        });

        MyButton visualizza_studenti_b = new MyButton("Visualizza Studenti Registrati");

        if(archivioStudenti.isEmpty())
            visualizza_studenti_b.setEnabled(false);

        visualizza_studenti_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i< archivioStudenti.size(); i++){
                    archivioStudenti.get(i).visualizza();
                }
            }
        });

        visualizza_studenti_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<archivioEsami.size(); i++){
                    archivioEsami.get(i).visualizza();
                }
            }
        });

        MyButton visualizza_esami_b = new MyButton("Visualizza Esami Registrati");
        if(archivioEsami.isEmpty())
            visualizza_esami_b.setEnabled(false);

        /** Servirà per visualizzare in tempo reale la lista di esami registrati **/
        MyTable esami_t = new MyTable();

        mainPanel.add(registra_studente_b);
        mainPanel.add(registra_esame_b);
        mainPanel.add(carica_esame_b);
        mainPanel.add(salva_esame_b);
        mainPanel.add(visualizza_studenti_b);
        mainPanel.add(visualizza_esami_b);
        mainPanel.add(esami_t);
        mainFrame.add(mainPanel);
        mainFrame.pack();
    }

    public Studente ricercaStudente(int matricola){
        for(int i=0; i< archivioStudenti.size(); i++){
            if (archivioStudenti.get(i).getMatricola() == matricola)
                return  archivioStudenti.get(i);
        }
        return null;
    }
    public MyFrame getMain_frame() {
        return mainFrame;
    }

    public MyFrame getData_frame() {
        return dataFrame;
    }

    public MyPanel getMain_panel() {
        return mainPanel;
    }

    public ArchivioEsami<Esame> getArchivioEsami(){
        return archivioEsami;
    }

    public ArchivioStudenti<Studente> getArchivioStudenti(){
        return archivioStudenti;
    }
}
