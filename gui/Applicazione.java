package gui;

import classi.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Applicazione{

    private MyFrame main_frame;
    private MyFrame data_frame;
    private MyPanel main_panel;
    private MyLabel matricola_l,nome_esame_l, voto_l, cfu_l;
    private JTextField matricola_tf,nome_esame_tf, voto_tf, cfu_tf;

    private JButton registra_b;

    private Controllore controllore;

    private ArrayList<Studente> studenti;
    private ArrayList<Esame> esami;

    public Applicazione(){
        studenti = new ArrayList<>();
        esami = new ArrayList<>();
        MainWindow();
    }

    public void disposeMainFrame(String titolo){
        main_frame.dispose();
        data_frame = new MyFrame(titolo);
        main_panel = new MyPanel();
    }

    public void disposeDataFrame(String titolo){
        data_frame.dispose();
        data_frame = new MyFrame(titolo);
        main_panel = new MyPanel();
    }

    public void registraStudentePanel(){
        disposeMainFrame("Registrazione studente");

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        JLabel nome_studente_l = new MyLabel("Nome studente");
        JTextField nome_studente_tf = new JTextField(20);

        JLabel cognome_l = new MyLabel("Cognome");
        JTextField cognome_tf = new JTextField(20);

        registra_b = new MyButton("Registra Studente");
        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                String nome = nome_studente_tf.getText();
                String cognome = cognome_tf.getText();
                studenti.add(new Studente(matricola, nome,cognome));
                MainWindow();
            }
        });
        main_panel.add(matricola_l);
        main_panel.add(matricola_tf);
        main_panel.add(nome_studente_l);
        main_panel.add(nome_studente_tf);
        main_panel.add(cognome_l);
        main_panel.add(cognome_tf);
        main_panel.add(registra_b);
        data_frame.add(main_panel);
        data_frame.pack();
    }

    public void registraEsameSemplice(){
        disposeDataFrame("Registrazione Esame Semplice");

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        /** Controllo sull'esistenza della matricola inserita **/
        /*if(matricola_tf.getText() != null && !controllore.controlloMatricola(studenti,Integer.parseInt(matricola_tf.getText())))
            JOptionPane.showMessageDialog(main_frame, "Errore, matricola non esistente!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
        */

        nome_esame_l = new MyLabel("Nome esame");
        nome_esame_tf = new JTextField(20);

        /** Mancano i controlli sull'inserimento **/
        voto_l = new MyLabel("Voto");
        voto_tf = new JTextField(2);

        JLabel lode_l = new MyLabel("Lode");
        JTextField lode_tf = new JTextField(5);

        cfu_l = new MyLabel("CFU");
        cfu_tf = new JTextField(2);

        registra_b = new MyButton("Registra Esame");
        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = ricercaStudente(matricola);
                String nome = nome_esame_tf.getText();
                int voto = Integer.parseInt(voto_tf.getText());
                Boolean lode = Boolean.parseBoolean(lode_tf.getText());
                int cfu = Integer.parseInt(cfu_tf.getText());
                esami.add(new EsameSemplice(studente,nome,voto,lode,cfu));
                MainWindow();
            }
        });
        main_panel.add(matricola_l);
        main_panel.add(matricola_tf);
        main_panel.add(nome_esame_l);
        main_panel.add(nome_esame_tf);
        main_panel.add(voto_l);
        main_panel.add(voto_tf);
        main_panel.add(lode_l);
        main_panel.add(lode_tf);
        main_panel.add(cfu_l);
        main_panel.add(cfu_tf);
        main_panel.add(registra_b);
        data_frame.add(main_panel);
        data_frame.pack();
    }

    public void registraEsameComposto(){
        disposeDataFrame("Registrazione Esame Composto");
        main_panel.setLayout(new GridLayout(2,1));

        JMenuBar jMenuBar = new JMenuBar();

        matricola_l = new MyLabel("Matricola");
        matricola_tf = new JTextField(6);

        /** Controllo sull'esistenza della matricola inserita **/
        /*if(matricola_tf.getText() != null && !controllore.controlloMatricola(studenti,Integer.parseInt(matricola_tf.getText())))
            JOptionPane.showMessageDialog(main_frame, "Errore, matricola non esistente!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
        */
        nome_esame_l = new MyLabel("Nome Esame");
        nome_esame_tf = new JTextField(20);

        /** Mancano i controlli sull'inserimento **/
        cfu_l = new MyLabel("CFU");
        cfu_tf = new JTextField(2);

        String[] tipologia_prova = {"Scritta", "Orale", "Pratica"};
        JComboBox jComboBox = new JComboBox(tipologia_prova);
        jComboBox.setEditable(false);

        JLabel peso_l = new MyLabel("Peso");
        JTextField peso_tf = new JTextField(2);

        voto_l = new MyLabel("Voto");
        voto_tf = new JTextField(2);

        registra_b = new MyButton("Registra Esame");

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = ricercaStudente(matricola);
                String nome = nome_esame_tf.getText();
                int cfu = Integer.parseInt(cfu_tf.getText());
                jComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tipologia = (String) jComboBox.getSelectedItem();
                        int peso = Integer.parseInt(peso_tf.getText());
                        int voto = Integer.parseInt(voto_tf.getText());
                        EsameComposto esameComposto = new EsameComposto(studente,nome,cfu);
                        esameComposto.aggiungiTipologia(new TipologiaProva(tipologia,peso,voto));
                        esami.add(esameComposto);
                    }
                });
                MainWindow();
            }
        });

        main_panel.add(jMenuBar);
        main_panel.add(matricola_l);
        main_panel.add(matricola_tf);
        main_panel.add(nome_esame_l);
        main_panel.add(nome_esame_tf);
        main_panel.add(cfu_l);
        main_panel.add(cfu_tf);
        main_panel.add(jComboBox);
        main_panel.add(peso_l);
        main_panel.add(peso_tf);
        main_panel.add(voto_l);
        main_panel.add(voto_tf);
        main_panel.add(registra_b);
        data_frame.add(main_panel);
        data_frame.pack();
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
        main_panel.add(jLabel);
        main_panel.add(jComboBox);
        data_frame.add(main_panel);
        data_frame.pack();
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
        if(data_frame !=  null)
            data_frame.dispose();
        main_frame = new MyFrame("Gestione Esami");
        main_panel = new MyPanel();
        main_panel.setLayout(new GridLayout(4,4));
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
        MyButton carica_esame_b = new MyButton("Carica Esami");
        carica_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // caricaEsamiPanel();
            }
        });
        MyButton salva_esame_b = new MyButton("Salva Esami");
        salva_esame_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // salvaEsamiPanel();
            }
        });
        /** Servirà per visualizzare in tempo reale la lista di esami registrati **/
        JTable esami_t = new JTable();

        main_panel.add(registra_studente_b);
        main_panel.add(registra_esame_b);
        main_panel.add(carica_esame_b);
        main_panel.add(salva_esame_b);
        main_panel.add(esami_t);
        main_frame.add(main_panel);
        main_frame.pack();
    }

    public Studente ricercaStudente(int matricola){
        for(int i=0; i< studenti.size(); i++){
            if (studenti.get(i).getMatricola() == matricola)
                return  studenti.get(i);
        }
        return null;
    }

    public ArrayList<Esame> getEsami(){
        return esami;
    }

    public ArrayList<Studente> getStudenti(){
        return studenti;
    }
}
