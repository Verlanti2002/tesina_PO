package gui;

import classi.Studente;
import gui.my_components.MyButton;
import gui.my_components.MyLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistraStudente extends Applicazione implements ActionListener{

    private MyLabel matricola_l, nome_l, cognome_l;
    private JTextField matricola_tf, nome_tf, cognome_tf;
    private MyButton registra_b;

    public RegistraStudente(){

        disposeMainFrame("Registrazione Studente");

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        nome_l = new MyLabel("Nome studente");
        nome_tf = new JTextField(20);

        cognome_l = new MyLabel("Cognome studente");
        cognome_tf = new JTextField(20);

        registra_b = new MyButton("Registra Studente");

        registra_b.addActionListener(this);

        getMain_panel().add(matricola_l);
        getMain_panel().add(matricola_tf);
        getMain_panel().add(nome_l);
        getMain_panel().add(nome_tf);
        getMain_panel().add(cognome_l);
        getMain_panel().add(cognome_tf);
        getMain_panel().add(registra_b);
        getData_frame().add(getMain_panel());
        getData_frame().pack();
    }

    public void actionPerformed(ActionEvent e) {
        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        getArchivioStudenti().add(new Studente(matricola, nome,cognome));
        getData_frame().dispose();
        MainWindow();
    }
}
