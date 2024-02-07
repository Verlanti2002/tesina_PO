package gui;

import classi.EsameSemplice;
import classi.Studente;
import gui.my_components.MyLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestioneEsami extends JFrame {

    private JPanel mainPanel;
    private DefaultTableModel defaultTableModel;
    private JLabel matricola_l, nome_l, cognome_l, corso_l, voto_finale_l, cfu_l;
    private JTextField matricola_tf, nome_tf, cognome_tf, corso_tf, voto_finale_tf, cfu_tf;
    private JCheckBox lode_cb;
    private JButton aggiungi_btn, modifica_btn, elimina_btn;

    JComboBox<String> tipologia_cb;

    JTable jTable;

    public GestioneEsami(Applicazione applicazione) {

        setTitle("Gestione esami");
        setBounds(700,200,800,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new GridLayout(2, 1));

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Matricola");
        defaultTableModel.addColumn("Nome");
        defaultTableModel.addColumn("Cognome");
        defaultTableModel.addColumn("Corso");
        defaultTableModel.addColumn("Voto finale");
        defaultTableModel.addColumn("Lode");
        defaultTableModel.addColumn("CFU");

        jTable = new JTable(defaultTableModel);

        JScrollPane jScrollPane = new JScrollPane(jTable);

        // Pannello per i controlli
        JPanel controlPanel = new JPanel();

        aggiungi_btn = new JButton("Aggiungi");
        modifica_btn = new JButton("Modifica");
        elimina_btn = new JButton("Elimina");

        JLabel tipologia_l = new JLabel("Tipologia esame");
        tipologia_cb = new JComboBox<>(new String[]{"Semplice", "Composto"});
        tipologia_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) tipologia_cb.getSelectedItem();
                // String selectedType = (String) tipologia_cb.getActionCommand();
                if(selectedType.equals("Semplice")){
                    //controlPanel.removeAll();
                    matricola_l = new JLabel("Matricola:");
                    matricola_l.setBounds(110,10,100,20);
                    matricola_tf = new JTextField(5);
                    matricola_tf.setBounds(110,30,200,20);
                    nome_l = new JLabel("Nome:");
                    cognome_l = new JLabel("Cognome:");
                    corso_l = new JLabel("Corso:");
                    voto_finale_l = new JLabel("Voto finale:");
                    cfu_l = new JLabel("CFU:");
                    nome_tf = new JTextField(10);
                    cognome_tf = new JTextField(10);
                    corso_tf = new JTextField(10);
                    voto_finale_tf = new JTextField(5);
                    lode_cb = new JCheckBox("Lode");
                    cfu_tf = new JTextField(5);
                    controlPanel.add(matricola_l);
                    controlPanel.add(matricola_tf);
                    controlPanel.add(nome_l);
                    controlPanel.add(nome_tf);
                    controlPanel.add(cognome_l);
                    controlPanel.add(cognome_tf);
                    controlPanel.add(corso_l);
                    controlPanel.add(corso_tf);
                    controlPanel.add(voto_finale_l);
                    controlPanel.add(voto_finale_tf);
                    controlPanel.add(lode_cb);
                    controlPanel.add(cfu_l);
                    controlPanel.add(cfu_tf);
                    controlPanel.add(aggiungi_btn);
                    controlPanel.add(modifica_btn);
                    controlPanel.add(elimina_btn);
                } else{
                    //controlPanel.removeAll();
                    matricola_l = new JLabel("Matricola:");
                    matricola_tf = new JTextField(5);
                    nome_l = new JLabel("Nome:");
                    nome_tf = new JTextField(10);
                    cognome_l = new JLabel("Cognome:");
                    cognome_tf = new JTextField(10);
                    corso_l = new JLabel("Corso:");
                    corso_tf = new JTextField(10);
                    String[] tipologia_prova = {"Scritta", "Orale", "Pratica"};
                    JComboBox tipologia_prova_cb = new JComboBox(tipologia_prova);
                    MyLabel peso_l = new MyLabel("Peso");
                    JTextField peso_tf = new JTextField(5);cfu_l = new JLabel("CFU:");
                    MyLabel voto_l = new MyLabel("Voto");
                    JTextField voto_tf = new JTextField(2);
                    voto_tf = new JTextField(5);
                    lode_cb = new JCheckBox("Lode");
                    controlPanel.add(matricola_l);
                    controlPanel.add(matricola_tf);
                    controlPanel.add(nome_l);
                    controlPanel.add(nome_tf);
                    controlPanel.add(cognome_l);
                    controlPanel.add(cognome_tf);
                    controlPanel.add(corso_l);
                    controlPanel.add(corso_tf);
                    controlPanel.add(tipologia_prova_cb);
                    controlPanel.add(peso_l);
                    controlPanel.add(peso_tf);
                    controlPanel.add(voto_l);
                    controlPanel.add(voto_tf);
                    controlPanel.add(lode_cb);
                    controlPanel.add(aggiungi_btn);
                    controlPanel.add(modifica_btn);
                    controlPanel.add(elimina_btn);
                }
            }
        });

        controlPanel.add(tipologia_l);
        controlPanel.add(tipologia_cb);

        mainPanel.add(jScrollPane);
        mainPanel.add(controlPanel);

        add(mainPanel);
        setVisible(true);

        aggiungi_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == aggiungi_btn) {
                    // Aggiungi un'entry alla tabella
                    addEntry(applicazione);
                }
            }
        });
        modifica_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == modifica_btn) {
                    // Aggiungi un'entry alla tabella
                    editEntry(applicazione);
                }
            }
        });
        elimina_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == elimina_btn) {
                    // Abilita/disabilita i campi per inserimento del voto finale in base al tipo di esame
                    deleteEntry(applicazione);
                }
            }
        });
    }

    public void addEntry(Applicazione applicazione){
        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        String corso = corso_tf.getText();
        int voto = Integer.parseInt(voto_finale_tf.getText());
        boolean lode = lode_cb.isSelected();
        int cfu = Integer.parseInt(cfu_tf.getText());
        defaultTableModel.addRow(new Object[]{matricola, nome, cognome, corso, voto, lode, cfu});
    }

    public void editEntry(Applicazione applicazione){
        // Modifica un'entry nella tabella
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            int matricola = Integer.parseInt(matricola_tf.getText());
            String nome = nome_tf.getText();
            String cognome = cognome_tf.getText();
            String corso = corso_tf.getText();
            String voto = voto_finale_tf.getText();
            boolean lode = lode_cb.isSelected();
            String cfu = cfu_tf.getText();
            defaultTableModel.setValueAt(matricola, selectedRow, 0);
            defaultTableModel.setValueAt(nome, selectedRow, 1);
            defaultTableModel.setValueAt(cognome, selectedRow, 2);
            defaultTableModel.setValueAt(corso, selectedRow, 3);
            defaultTableModel.setValueAt(voto, selectedRow, 4);
            defaultTableModel.setValueAt(lode, selectedRow, 5);
            defaultTableModel.setValueAt(cfu, selectedRow, 6);
        }
    }

    public void deleteEntry(Applicazione applicazione) {
        // Elimina un'entry dalla tabella
        int selectedRow = jTable.getSelectedRow();
        // Recupero lo studente da eliminare
        Studente studente = applicazione.getStudenti().get(selectedRow);
        if (selectedRow != -1) {
            // Rimuovo la riga
            defaultTableModel.removeRow(selectedRow);
            // Rimuovo lo studente dal vettore
            applicazione.getStudenti().delete(studente);
        }
    }
}
