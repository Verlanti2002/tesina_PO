package gui;

import classi.*;
import gui.my_components.MyButton;
import gui.my_components.MyFrame;
import gui.my_components.MyLabel;
import gui.my_components.MyPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistraEsame {

    private String tipologia;
    private int peso, voto;
    private MyLabel matricola_l, nome_l, voto_l, cfu_l, peso_l;
    private JTextField matricola_tf, nome_tf, voto_tf, cfu_tf, peso_tf;
    private MyButton registra_b;
    private JComboBox jComboBox;

    public RegistraEsame(MyFrame mainFrame, Applicazione applicazione){

        MyPanel mainPanel = new MyPanel();
        MyPanel secondPanel = new MyPanel();

        JLabel jLabel = new JLabel("Seleziona la tipologia d'esame");

        String[] tipologia_esami = {"", "Esame Semplice", "Esame Composto"};
        JComboBox jComboBox = new JComboBox(tipologia_esami);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyPanel semplicePanel = null;
                String scelta = (String) jComboBox.getSelectedItem();
                if(scelta.equals("Esame Semplice")){
                    System.out.println("Sono esame semplice");
                    if (semplicePanel != null)
                        mainPanel.remove(semplicePanel);
                    semplicePanel = semplice(applicazione);
                } else{
                    System.out.println("Sono esame composto");
                    if (semplicePanel != null)
                        mainPanel.remove(semplicePanel);
                    semplicePanel = composto(applicazione);

                }
                mainPanel.add(semplicePanel);
            }
        });

        mainPanel.add(jLabel);
        mainPanel.add(jComboBox);
        mainFrame.add(mainPanel);
        mainFrame.pack();
    }

    public MyPanel semplice(Applicazione applicazione){


        MyPanel semplicePanel = new MyPanel();

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        /** Controllo sull'esistenza della matricola inserita **/
        /*if(matricola_tf.getText() != null && !controllore.controlloMatricola(studenti,Integer.parseInt(matricola_tf.getText())))
            JOptionPane.showMessageDialog(main_frame, "Errore, matricola non esistente!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
        */

        nome_l = new MyLabel("Nome esame");
        nome_tf = new JTextField(20);

        /** Mancano i controlli sull'inserimento **/
        voto_l = new MyLabel("Voto");
        voto_tf = new JTextField(2);

        JLabel lode_l = new MyLabel("Lode");
        JTextField lode_tf = new JTextField(5);

        cfu_l = new MyLabel("CFU");
        cfu_tf = new JTextField(2);

        registra_b = new MyButton("Registra Esame");

        /*if(!studentExist(Integer.parseInt(matricola_tf.getText())))
            registra_b.setEnabled(false);
         */

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = applicazione.ricercaStudente(matricola);
                String nome = nome_tf.getText();
                int voto = Integer.parseInt(voto_tf.getText());
                Boolean lode = Boolean.parseBoolean(lode_tf.getText());
                int cfu = Integer.parseInt(cfu_tf.getText());
                if(studente != null)
                    applicazione.getEsami().add(new EsameSemplice(studente,nome,voto,lode,cfu));
            }
        });

        semplicePanel.add(matricola_l);
        semplicePanel.add(matricola_tf);
        semplicePanel.add(nome_l);
        semplicePanel.add(nome_tf);
        semplicePanel.add(voto_l);
        semplicePanel.add(voto_tf);
        semplicePanel.add(lode_l);
        semplicePanel.add(lode_tf);
        semplicePanel.add(cfu_l);
        semplicePanel.add(cfu_tf);
        semplicePanel.add(registra_b);
        return semplicePanel;
    }

    public MyPanel composto(Applicazione applicazione){

        MyPanel compostoPanel = new MyPanel();

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        /** Controllo sull'esistenza della matricola inserita **/
        /*if(matricola_tf.getText() != null && !controllore.controlloMatricola(studenti,Integer.parseInt(matricola_tf.getText())))
            JOptionPane.showMessageDialog(main_frame, "Errore, matricola non esistente!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
        */
        nome_l = new MyLabel("Nome Esame");
        nome_tf = new JTextField(20);

        /** Mancano i controlli sull'inserimento **/
        cfu_l = new MyLabel("CFU");
        cfu_tf = new JTextField(2);

        String[] tipologia_prova = {"Scritta", "Orale", "Pratica"};
        jComboBox = new JComboBox(tipologia_prova);

        peso_l = new MyLabel("Peso");
        peso_tf = new JTextField(2);

        voto_l = new MyLabel("Voto");
        voto_tf = new JTextField(2);

        registra_b = new MyButton("Registra Esame");

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipologia = (String) jComboBox.getSelectedItem();
                peso = Integer.parseInt(peso_tf.getText());
                voto = Integer.parseInt(voto_tf.getText());

                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = applicazione.ricercaStudente(matricola);
                String nome = nome_tf.getText();
                int cfu = Integer.parseInt(cfu_tf.getText());
                EsameComposto esameComposto = new EsameComposto(studente,nome,cfu);
                esameComposto.aggiungiTipologia(new TipologiaProva(tipologia,peso,voto));
                applicazione.getEsami().add(esameComposto);
            }
        });

        /*if(!studentExist(Integer.parseInt(matricola_tf.getText())))
            registra_b.setEnabled(false);
         */

        compostoPanel.add(matricola_l);
        compostoPanel.add(matricola_tf);
        compostoPanel.add(nome_l);
        compostoPanel.add(nome_tf);
        compostoPanel.add(cfu_l);
        compostoPanel.add(cfu_tf);
        compostoPanel.add(jComboBox);
        compostoPanel.add(peso_l);
        compostoPanel.add(peso_tf);
        compostoPanel.add(voto_l);
        compostoPanel.add(voto_tf);
        compostoPanel.add(registra_b);
        return compostoPanel;
    }
}
