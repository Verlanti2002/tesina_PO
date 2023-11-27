package gui;

import classi.Studente;
import gui.my_components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistraStudente{

    public RegistraStudente(Applicazione applicazione){

        MainFrame mainFrame = new MainFrame("Registrazione studente");
        DataPanel dataPanel = new DataPanel();
        dataPanel.setPreferredSize(new Dimension(400,200));

        JLabel matricola_l = new JLabel("Matricola");
        matricola_l.setBounds(110,10,100,20);
        JTextField matricola_tf = new JTextField();
        matricola_tf.setBounds(110,30,200,20);

        JLabel nome_l = new JLabel("Nome");
        nome_l.setBounds(110,50,100,20);
        JTextField nome_tf = new JTextField(20);
        nome_tf.setBounds(110,70,200,20);

        JLabel cognome_l = new JLabel("Cognome");
        cognome_l.setBounds(110,90,100,20);
        JTextField cognome_tf = new JTextField();
        cognome_tf.setBounds(110,110, 200,20);

        DataButton registra_b = new DataButton("Registra Studente");
        registra_b.setBounds(140,150,140,25);

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                String nome = nome_tf.getText();
                String cognome = cognome_tf.getText();
                applicazione.getStudenti().add(new Studente(matricola, nome,cognome));
                mainFrame.remove(dataPanel);
                new Menu(mainFrame,applicazione);
            }
        });

        dataPanel.add(matricola_l);
        dataPanel.add(matricola_tf);
        dataPanel.add(nome_l);
        dataPanel.add(nome_tf);
        dataPanel.add(cognome_l);
        dataPanel.add(cognome_tf);
        dataPanel.add(registra_b);
        mainFrame.add(dataPanel);
        mainFrame.pack();
    }
}
