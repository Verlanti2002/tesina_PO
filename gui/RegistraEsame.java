package gui;

import classi.EsameComposto;
import classi.EsameSemplice;
import classi.Studente;
import classi.TipologiaProva;
import gui.my_components.MyButton;
import gui.my_components.MyLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistraEsame extends Applicazione{

    String tipologia;
    int peso, voto;
    MyLabel matricola_l, nome_l, voto_l, cfu_l, peso_l;
    JTextField matricola_tf, nome_tf, voto_tf, cfu_tf, peso_tf;
    MyButton registra_b;

    public RegistraEsame(){
        disposeMainFrame("Registrazione Esame");

        JLabel jLabel = new JLabel("Seleziona la tipologia d'esame");

        String[] tipologia_esami = {"Esame Semplice", "Esame Composto"};
        JComboBox jComboBox = new JComboBox(tipologia_esami);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scelta = (String) jComboBox.getSelectedItem();
                if(scelta.equals("Esame Semplice")){
                    semplice();
                } else{
                    composto();
                }
            }
        });
        getMain_panel().add(jLabel);
        getMain_panel().add(jComboBox);
        getData_frame().add(getMain_panel());
        getData_frame().pack();
    }

    public void semplice(){

        disposeDataFrame("Registrazione Esame Semplice");

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

        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = ricercaStudente(matricola);
                String nome = nome_tf.getText();
                int voto = Integer.parseInt(voto_tf.getText());
                Boolean lode = Boolean.parseBoolean(lode_tf.getText());
                int cfu = Integer.parseInt(cfu_tf.getText());
                getEsami().add(new EsameSemplice(studente,nome,voto,lode,cfu));
                getData_frame().dispose();
                MainWindow();
            }
        });

        getMain_panel().add(matricola_l);
        getMain_panel().add(matricola_tf);
        getMain_panel().add(nome_l);
        getMain_panel().add(nome_tf);
        getMain_panel().add(voto_l);
        getMain_panel().add(voto_tf);
        getMain_panel().add(lode_l);
        getMain_panel().add(lode_tf);
        getMain_panel().add(cfu_l);
        getMain_panel().add(cfu_tf);
        getMain_panel().add(registra_b);
        getData_frame().add(getMain_panel());
        getData_frame().pack();
    }

    public void composto(){

        disposeDataFrame("Registrazione Esame Composto");

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
        JComboBox jComboBox = new JComboBox(tipologia_prova);
        //jComboBox.addItem("Scritta"); jComboBox.addItem("Orale"); jComboBox.addItem("Pratica");

        peso_l = new MyLabel("Peso");
        peso_tf = new JTextField(2);

        voto_l = new MyLabel("Voto");
        voto_tf = new JTextField(2);

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipologia = (String) jComboBox.getSelectedItem();
                peso = Integer.parseInt(peso_tf.getText());
                voto = Integer.parseInt(voto_tf.getText());
            }
        });
        registra_b = new MyButton("Registra Esame");
        registra_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                Studente studente = ricercaStudente(matricola);
                String nome = nome_tf.getText();
                int cfu = Integer.parseInt(cfu_tf.getText());
                EsameComposto esameComposto = new EsameComposto(studente,nome,cfu);
                esameComposto.aggiungiTipologia(new TipologiaProva(tipologia,peso,voto));
                getEsami().add(esameComposto);
                getData_frame().dispose();
                MainWindow();
            }
        });

        getMain_panel().add(matricola_l);
        getMain_panel().add(matricola_tf);
        getMain_panel().add(nome_l);
        getMain_panel().add(nome_tf);
        getMain_panel().add(cfu_l);
        getMain_panel().add(cfu_tf);
        getMain_panel().add(jComboBox);
        getMain_panel().add(peso_l);
        getMain_panel().add(peso_tf);
        getMain_panel().add(voto_l);
        getMain_panel().add(voto_tf);
        getMain_panel().add(registra_b);
        getData_frame().add(getMain_panel());
        getData_frame().pack();
    }
}
