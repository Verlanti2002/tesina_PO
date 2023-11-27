package gui;

import classi.*;
import gui.my_components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Menu {

    public Menu(MainFrame mainFrame, Applicazione applicazione){

        MainPanel mainPanel = new MainPanel();

        mainPanel.setLayout(new GridLayout(3,2));

        MainButton registra_studente_b = new MainButton("Registra Studente");
        registra_studente_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(mainPanel);
                RegistraStudente registraStudente = new RegistraStudente(applicazione);
            }
        });

        MainButton registra_esame_b = new MainButton("Registra Esame");

        /*if(applicazione.getStudenti().isEmpty())
            registra_esame_b.setEnabled(false);*/
        registra_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(mainPanel);
                RegistraEsame registraEsame = new RegistraEsame(applicazione);
            }
        });
        MainButton carica_esame_b = new MainButton("Carica Esami");
        carica_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);
                File file = jFileChooser.getSelectedFile();
                Caricamento caricamento = new Caricamento(file,applicazione);
            }
        });
        MainButton salva_esame_b = new MainButton("Salva Esami");

        if(applicazione.getEsami().isEmpty())
            salva_esame_b.setEnabled(false);
        salva_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Salvataggio salvataggio = new Salvataggio(applicazione.getEsami());
            }
        });

        MainButton visualizza_studenti_b = new MainButton("Visualizza Studenti Registrati");

        if(applicazione.getStudenti().isEmpty())
            visualizza_studenti_b.setEnabled(false);

        visualizza_studenti_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizzaStudenti visualizzaStudenti = new VisualizzaStudenti(applicazione);
            }
        });

        MainButton visualizza_esami_b = new MainButton("Visualizza Esami Registrati");
        visualizza_esami_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizzaEsami visualizzaEsami = new VisualizzaEsami(applicazione);
            }
        });

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
