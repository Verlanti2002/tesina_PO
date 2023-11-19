package gui;

import Classi.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Applicazione{

    private MyFrame mainFrame;
    private MyFrame dataFrame;
    private JPanel mainPanel;
    private ArrayList<Studente> studenti;
    private ArrayList<Esame> esami;
    TipologiaProva esameParziale;

    public Applicazione(){
        studenti = new ArrayList<>();
        esami = new ArrayList<>();
        MainWindow();
    }

    public void disposeMainFrame(String titolo){
        mainFrame.dispose();
        dataFrame = new MyFrame(titolo);
        mainPanel = new JPanel();
    }

    public void disposeDataFrame(String titolo){
        dataFrame.dispose();
        dataFrame = new MyFrame(titolo);
        mainPanel = new JPanel();
    }

    public void registraStudentePanel(){
        disposeMainFrame("Registrazione studente");
        JLabel matricola_l = new JLabel("Matricola");
        JTextField matricola_tf = new JTextField(6);
        JLabel nome_l = new JLabel("Nome");
        JTextField nome_tf = new JTextField(20);
        JLabel cognome_l = new JLabel("Cognome");
        JTextField cognome_tf = new JTextField(20);
        MyButton registra_b = new MyButton("Registra");
        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                String nome = nome_tf.getText();
                String cognome = cognome_tf.getText();
                studenti.add(new Studente(matricola, nome,cognome));
                MainWindow();
            }
        });
        mainPanel.add(matricola_l);
        mainPanel.add(matricola_tf);
        mainPanel.add(nome_l);
        mainPanel.add(nome_tf);
        mainPanel.add(cognome_l);
        mainPanel.add(cognome_tf);
        mainPanel.add(registra_b);
        dataFrame.add(mainPanel);
        dataFrame.pack();
    }

    public void registraEsameSemplice(){
        disposeDataFrame("Registrazione Esame Semplice");
        JLabel matricola_l = new JLabel("Matricola");
        JTextField matricola_tf = new JTextField(6);
        JLabel nome_l = new JLabel("Nome");
        JTextField nome_tf = new JTextField(20);
        /** Mancano i controlli sull'inserimento **/
        JLabel voto_l = new JLabel("Voto");
        JTextField voto_tf = new JTextField(2);
        JLabel lode_l = new JLabel("Lode");
        JTextField lode_tf = new JTextField(5);
        JLabel cfu_l = new JLabel("CFU");
        JTextField cfu_tf = new JTextField(2);
        MyButton registra_b = new MyButton("Registra");
        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = ricercaStudente(matricola);
                String nome = nome_tf.getText();
                int voto = Integer.parseInt(voto_tf.getText());
                Boolean lode = Boolean.parseBoolean(lode_tf.getText());
                int cfu = Integer.parseInt(cfu_tf.getText());
                esami.add(new EsameSemplice(studente,nome,voto,lode,cfu));
                MainWindow();
            }
        });
        mainPanel.add(matricola_l);
        mainPanel.add(matricola_tf);
        mainPanel.add(nome_l);
        mainPanel.add(nome_tf);
        mainPanel.add(voto_l);
        mainPanel.add(voto_tf);
        mainPanel.add(lode_l);
        mainPanel.add(lode_tf);
        mainPanel.add(cfu_l);
        mainPanel.add(cfu_tf);
        mainPanel.add(registra_b);
        dataFrame.add(mainPanel);
        dataFrame.pack();
    }

    public void registraEsameComposto(){
        disposeDataFrame("Registrazione Esame Composto");
        JLabel matricola_l = new JLabel("Matricola");
        JTextField matricola_tf = new JTextField(6);
        JLabel nome_l = new JLabel("Nome");
        JTextField nome_tf = new JTextField(20);
        /** Mancano i controlli sull'inserimento **/
        JLabel cfu_l = new JLabel("CFU");
        JTextField cfu_tf = new JTextField(2);
        String[] tipologia_prova = {"Scritta", "Orale", "Pratica"};
        JComboBox jComboBox = new JComboBox(tipologia_prova);
        jComboBox.setEditable(false);
        JLabel peso_l = new JLabel("Peso");
        JTextField peso_tf = new JTextField(2);
        JLabel voto_singolo_l = new JLabel("Voto prova");
        JTextField voto_singolo_tf = new JTextField(2);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = (String) jComboBox.getSelectedItem();
                int peso = Integer.parseInt(peso_tf.getText());
                int voto = Integer.parseInt(voto_singolo_tf.getText());
                esameParziale = new TipologiaProva(nome,peso,voto);
            }
        });
        MyButton registra_b = new MyButton("Registra");
        mainPanel.add(registra_b);
        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = ricercaStudente(matricola);
                String nome = nome_tf.getText();
                int cfu = Integer.parseInt(cfu_tf.getText());
                EsameComposto esameComposto = new EsameComposto(studente,nome,cfu);
                esameComposto.aggiungiTipologia(esameParziale);
                esami.add(esameComposto);
                MainWindow();
            }
        });

        mainPanel.add(matricola_l);
        mainPanel.add(matricola_tf);
        mainPanel.add(nome_l);
        mainPanel.add(nome_tf);
        mainPanel.add(cfu_l);
        mainPanel.add(cfu_tf);
        mainPanel.add(jComboBox);
        mainPanel.add(peso_l);
        mainPanel.add(peso_tf);
        mainPanel.add(voto_singolo_l);
        mainPanel.add(voto_singolo_tf);
        mainPanel.add(registra_b);
        dataFrame.add(mainPanel);
        dataFrame.pack();
    }

    public void registraEsamePanel(){
        disposeMainFrame("Registrazione Esame");
        String[] tipologia_esami = {"Esame Semplice", "Esame Composto"};
        JLabel jLabel = new JLabel("Seleziona la tipologia d'esame");
        JComboBox jComboBox = new JComboBox(tipologia_esami);
        jComboBox.setEditable(false);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scelta = (String) jComboBox.getSelectedItem();
                if(scelta.equals("Esame Semplice")){
                    registraEsameSemplice();
                } else{
                    registraEsameComposto();
                }
            }
        });
        mainPanel.add(jLabel);
        mainPanel.add(jComboBox);
        dataFrame.add(mainPanel);
        dataFrame.pack();
    }

    public void MainWindow(){
        if(dataFrame !=  null)
            dataFrame.dispose();
        mainFrame = new MyFrame("Gestione Esami");
        mainPanel = new JPanel();
        MyButton registra_studente_b = new MyButton("Registra Studente");
        registra_studente_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registraStudentePanel();
            }
        });
        MyButton registra_esame_b = new MyButton("Registra Esame");
        registra_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registraEsamePanel();
            }
        });
        MyButton carica_esame_b = new MyButton("Carica Esame");
        carica_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // caricaEsamePanel();
            }
        });
        MyButton salva_esame_b = new MyButton("Salva Esame");
        salva_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // salvaEsamePanel();
            }
        });
        /** Servirà per visualizzare in tempo reale la lista di esami registrati **/
        JTable esami = new JTable();
        mainPanel.add(registra_studente_b);
        mainPanel.add(registra_esame_b);
        mainPanel.add(carica_esame_b);
        mainPanel.add(salva_esame_b);
        mainPanel.add(esami);
        mainFrame.add(mainPanel);
        mainFrame.pack();
    }

    public Studente ricercaStudente(int matricola){
        for(int i=0; i< studenti.size(); i++){
            if (studenti.get(i).getMatricola() == matricola)
                return  studenti.get(i);
        }
        return null;
    }
}
