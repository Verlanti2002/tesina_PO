package gui.my_components;

import classi.Salvataggio;
import gui.Applicazione;
import gui.RegistraEsame;
import gui.RegistraStudente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    public Menu(MyFrame mainFrame, Applicazione applicazione){

        MyPanel mainPanel = new MyPanel();
        mainPanel.setLayout(new GridLayout(4,2));

        MyButton registra_studente_b = new MyButton("Registra Studente");
        registra_studente_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(mainPanel);
                RegistraStudente registraStudente = new RegistraStudente(mainFrame, applicazione);
            }
        });

        MyButton registra_esame_b = new MyButton("Registra Esame");

        if(applicazione.getStudenti().isEmpty())
            registra_esame_b.setEnabled(false);
        registra_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(mainPanel);
                RegistraEsame registraEsame = new RegistraEsame(mainFrame, applicazione);
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

        if(applicazione.getEsami().isEmpty())
            salva_esame_b.setEnabled(false);
        salva_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Salvataggio salvataggio = new Salvataggio(applicazione.getEsami());
            }
        });

        MyButton visualizza_studenti_b = new MyButton("Visualizza Studenti Registrati");

        if(applicazione.getStudenti().isEmpty())
            visualizza_studenti_b.setEnabled(false);

        visualizza_studenti_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i< applicazione.getStudenti().size(); i++){
                    applicazione.getStudenti().get(i).visualizza();
                }
            }
        });

        visualizza_studenti_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<applicazione.getEsami().size(); i++){
                    applicazione.getEsami().get(i).visualizza();
                }
            }
        });

        MyButton visualizza_esami_b = new MyButton("Visualizza Esami Registrati");
        if(applicazione.getEsami().isEmpty())
            visualizza_esami_b.setEnabled(false);

        mainPanel.add(registra_studente_b);
        mainPanel.add(registra_esame_b);
        mainPanel.add(carica_esame_b);
        mainPanel.add(salva_esame_b);
        mainPanel.add(visualizza_studenti_b);
        mainPanel.add(visualizza_esami_b);

        mainFrame.add(mainPanel);
        mainFrame.pack();
    }
}
