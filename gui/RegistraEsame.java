package gui;

import classi.*;
import gui.my_components.*;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistraEsame {

    public RegistraEsame(Applicazione applicazione){

        MainFrame mainFrame = new MainFrame("Registrazione esame");
        DataPanel dataPanel = new DataPanel();
        dataPanel.setPreferredSize(new Dimension(500,300));

        JLabel jLabel = new JLabel("Seleziona la tipologia d'esame");
        jLabel.setBounds(170,10,200,20);

        String[] comboList = {"Esame Semplice", "Esame Composto"};

        JComboBox jComboBox = new JComboBox(comboList);
        jComboBox.setBounds(183,30,150,20);
        jComboBox.setEditable(false);

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel semplicePanel = null;
                if(e.getSource() == jComboBox) {
                    String scelta = (String) jComboBox.getSelectedItem();
                    if (scelta.equals("Esame Semplice")) {
                        System.out.println("Sono esame semplice");
                        if (semplicePanel != null)
                            dataPanel.remove(semplicePanel);
                        semplicePanel = semplice(dataPanel, applicazione);
                    } else {
                        System.out.println("Sono esame composto");
                        if (semplicePanel != null)
                            dataPanel.remove(semplicePanel);
                        semplicePanel = composto(dataPanel, applicazione);
                    }
                    dataPanel.add(semplicePanel);
                }
            }
        });

        dataPanel.add(jLabel);
        dataPanel.add(jComboBox);
        mainFrame.add(dataPanel);
        mainFrame.pack();
    }

    public JPanel semplice(DataPanel dataPanel, Applicazione applicazione){

        JPanel semplicePanel = new JPanel();
        semplicePanel.setLayout(null);

        MyLabel matricola_l = new MyLabel("Matricola");
        JTextField matricola_tf = new JTextField(6);

        /** Controllo sull'esistenza della matricola inserita **/
        /*if(matricola_tf.getText() != null && !controllore.controlloMatricola(studenti,Integer.parseInt(matricola_tf.getText())))
            JOptionPane.showMessageDialog(main_frame, "Errore, matricola non esistente!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
        */

        MyLabel nome_l = new MyLabel("Nome esame");
        JTextField nome_tf = new JTextField(20);

        /** Mancano i controlli sull'inserimento **/
        MyLabel voto_l = new MyLabel("Voto");
        JTextField voto_tf = new JTextField(2);

        JLabel lode_l = new MyLabel("Lode");
        JTextField lode_tf = new JTextField(5);

        MyLabel cfu_l = new MyLabel("CFU");
        JTextField cfu_tf = new JTextField(2);

        DataButton registra_b = new DataButton("Registra Esame");

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
        dataPanel.add(semplicePanel);
        return semplicePanel;
    }

    public JPanel composto(DataPanel dataPanel, Applicazione applicazione){

        JPanel compostoPanel = new DataPanel();

        MyLabel matricola_l = new MyLabel("Matricola");
        JTextField matricola_tf = new JTextField(6);

        /** Controllo sull'esistenza della matricola inserita **/
        /*if(matricola_tf.getText() != null && !controllore.controlloMatricola(studenti,Integer.parseInt(matricola_tf.getText())))
            JOptionPane.showMessageDialog(main_frame, "Errore, matricola non esistente!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
        */
        MyLabel nome_l = new MyLabel("Nome Esame");
        JTextField nome_tf = new JTextField(20);

        /** Mancano i controlli sull'inserimento **/
        MyLabel cfu_l = new MyLabel("CFU");
        JTextField cfu_tf = new JTextField(2);

        String[] tipologia_prova = {"Scritta", "Orale", "Pratica"};
        JComboBox jComboBox = new JComboBox(tipologia_prova);

        MyLabel peso_l = new MyLabel("Peso");
        JTextField peso_tf = new JTextField(2);

        MyLabel voto_l = new MyLabel("Voto");
        JTextField voto_tf = new JTextField(2);

        DataButton registra_b = new DataButton("Registra Esame");

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipologia = (String) jComboBox.getSelectedItem();
                int peso = Integer.parseInt(peso_tf.getText());
                int voto = Integer.parseInt(voto_tf.getText());

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
        dataPanel.add(compostoPanel);
        return compostoPanel;
    }
}
