package gui;

import classi.ArchivioStudenti;
import classi.Main;
import classi.Studente;
import gui.my_components.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistraStudente{

    private MyLabel matricola_l, nome_l, cognome_l;
    private JTextField matricola_tf, nome_tf, cognome_tf;
    private MyButton registra_b;

    public RegistraStudente(MyFrame mainFrame, Applicazione applicazione){

        MyPanel mainPanel = new MyPanel();

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        nome_l = new MyLabel("Nome studente");
        nome_tf = new JTextField(20);

        cognome_l = new MyLabel("Cognome studente");
        cognome_tf = new JTextField(20);

        registra_b = new MyButton("Registra Studente");

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                String nome = nome_tf.getText();
                String cognome = cognome_tf.getText();
                applicazione.getStudenti().add(new Studente(matricola, nome,cognome));
                mainFrame.remove(mainPanel);
                new Menu(mainFrame,applicazione);
            }
        });

        mainPanel.add(matricola_l);
        mainPanel.add(matricola_tf);
        mainPanel.add(nome_l);
        mainPanel.add(nome_tf);
        mainPanel.add(cognome_l);
        mainPanel.add(cognome_tf);
        mainPanel.add(registra_b);
        mainFrame.add(mainPanel);
        mainFrame.pack();
    }
}
