package gui;

import classi.EsameComposto;
import classi.EsameSemplice;
import classi.Studente;
import classi.TipologiaProva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestioneEsami{

    private JFrame jFrameSemplice, jFrameComposto;
    private JPanel mainPanel;
    private DefaultTableModel defaultTableModel;
    private JLabel matricola_l, nome_l, cognome_l, corso_l, voto_l, lode_l, cfu_l, tipologia_prova_l, peso_l, tipologia_l;
    private JTextField matricola_tf, nome_tf, cognome_tf, corso_tf, voto_tf, cfu_tf, peso_tf;
    private JCheckBox lode_cb, semplice_cb, composto_cb;
    private JButton aggiungi_btn, modifica_btn, elimina_btn, registra_btn;
    private JComboBox tipologia_prova_cb;
    private JTable jTable;

    public GestioneEsami(Applicazione applicazione) {

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Gestione esami");
        mainFrame.setSize(800,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new GridLayout(2, 1));

        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Nome");
        defaultTableModel.addColumn("Cognome");
        defaultTableModel.addColumn("Corso");
        defaultTableModel.addColumn("Voto finale");
        defaultTableModel.addColumn("Lode");
        defaultTableModel.addColumn("CFU");

        jTable = new JTable(defaultTableModel);

        JScrollPane jScrollPane = new JScrollPane(jTable);

        // Pannello per i controlli
        JPanel controlPanel = new JPanel(new FlowLayout());

        aggiungi_btn = new JButton("Aggiungi");
        modifica_btn = new JButton("Modifica");
        elimina_btn = new JButton("Elimina");

        controlPanel.add(aggiungi_btn);
        controlPanel.add(modifica_btn);
        controlPanel.add(elimina_btn);

        mainPanel.add(jScrollPane);
        mainPanel.add(controlPanel);

        mainFrame.add(mainPanel);

        // Imposta la finestra al centro dello schermo
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);

        aggiungi_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == aggiungi_btn) {
                    jFrameSemplice = new JFrame("Registrazione Esame Semplice");
                    jFrameSemplice.setSize(600,300);
                    jFrameSemplice.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(null);
                    jPanel.setPreferredSize(new Dimension(600,300));

                    generaEsameSemplice(jPanel, jFrameSemplice);

                    registra_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(semplice_cb.isSelected()){
                                // Aggiungi un'entry alla tabella
                                addEntry(applicazione);
                                jFrameSemplice.dispose();
                            }
                            if(composto_cb.isSelected()){
                                addEntry(applicazione);
                                jFrameComposto.dispose();
                            } else{
                                // Messaggio di errore (è obbligatorio selezionare la tipologia di esame)
                            }
                        }
                    });

                    semplice_cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (semplice_cb.isSelected()) {
                                jFrameComposto.dispose();
                                jFrameSemplice = new JFrame("Registrazione Esame Semplice");
                                jFrameSemplice.setSize(600,300);
                                jFrameSemplice.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                JPanel jPanel = new JPanel();
                                jPanel.setLayout(null);
                                jPanel.setPreferredSize(new Dimension(600,300));

                                generaEsameSemplice(jPanel, jFrameSemplice);
                            }
                        }
                    });
                    composto_cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (composto_cb.isSelected()) {
                                jFrameSemplice.dispose();
                                jFrameComposto = new JFrame("Registrazione Esame Composto");
                                jFrameComposto.setSize(600,300);
                                jFrameComposto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                JPanel jPanel = new JPanel();
                                jPanel.setLayout(null);
                                jPanel.setPreferredSize(new Dimension(600,300));

                                JLabel tipologia_l = new JLabel("Tipologia esame:");
                                tipologia_l.setBounds(150,20, 100,20);
                                semplice_cb = new JCheckBox("Semplice");
                                semplice_cb.setBounds(250,20, 100, 20);
                                composto_cb = new JCheckBox("Composto");
                                composto_cb.setBounds(350,20, 100, 20);
                                composto_cb.setSelected(true);

                                if (composto_cb.isSelected())
                                    semplice_cb.setSelected(false); // Deseleziona checkBox2 quando checkBox1 è selezionata

                                matricola_l = new JLabel("Matricola:");
                                matricola_l.setBounds(20, 60, 100, 20);
                                matricola_tf = new JTextField();
                                matricola_tf.setBounds(80, 60, 100, 20);

                                nome_l = new JLabel("Nome:");
                                nome_l.setBounds(220, 60, 100, 20);
                                nome_tf = new JTextField();
                                nome_tf.setBounds(260, 60, 100, 20);

                                cognome_l = new JLabel("Cognome:");
                                cognome_l.setBounds(400, 60, 100, 20);
                                cognome_tf = new JTextField();
                                cognome_tf.setBounds(460, 60, 100, 20);

                                corso_l = new JLabel("Corso:");
                                corso_l.setBounds(20, 110, 100, 20);
                                corso_tf = new JTextField();
                                corso_tf.setBounds(100, 110, 100, 20);

                                cfu_l = new JLabel("CFU:");
                                cfu_l.setBounds(200, 110, 100, 20);
                                cfu_tf = new JTextField();
                                cfu_tf.setBounds(260, 110, 100, 20);

                                JLabel n_prove_l = new JLabel("N. prove:");
                                n_prove_l.setBounds(360, 110, 100, 20);;
                                Integer[] options = {1, 2 ,3};
                                JComboBox n_prove_cb = new JComboBox(options);
                                n_prove_cb.setBounds(420, 110, 100, 20);

                                registra_btn = new JButton("Registra Esame");
                                registra_btn.setBounds(230,200,140,25);

                                registra_btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(semplice_cb.isSelected()){
                                            // Aggiungi un'entry alla tabella
                                            addEntry(applicazione);
                                            jFrameSemplice.dispose();
                                        }
                                        if(composto_cb.isSelected()){
                                            addEntry(applicazione);
                                            jFrameComposto.dispose();
                                        } else{
                                            // Messaggio di errore (è obbligatorio selezionare la tipologia di esame)
                                        }
                                    }
                                });

                                jPanel.add(tipologia_l);
                                jPanel.add(semplice_cb);
                                jPanel.add(composto_cb);
                                jPanel.add(matricola_l);
                                jPanel.add(matricola_tf);
                                jPanel.add(nome_l);
                                jPanel.add(nome_tf);
                                jPanel.add(cognome_l);
                                jPanel.add(cognome_tf);
                                jPanel.add(corso_l);
                                jPanel.add(corso_tf);
                                jPanel.add(cfu_l);
                                jPanel.add(cfu_tf);
                                jPanel.add(n_prove_l);
                                jPanel.add(n_prove_cb);
                                jPanel.add(registra_btn);

                                jFrameComposto.add(jPanel);
                                jFrameComposto.setLocationRelativeTo(null);
                                jFrameComposto.setVisible(true);

                            }
                        }
                    });

                    jFrameSemplice.add(jPanel);
                    jFrameSemplice.setLocationRelativeTo(null);
                    jFrameSemplice.setVisible(true);
                }
            }
        });
        modifica_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == modifica_btn) {
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

    public void generaEsameSemplice(JPanel jPanel, JFrame jFrame){
        JLabel tipologia_l = new JLabel("Tipologia esame:");
        tipologia_l.setBounds(150,20, 100,20);
        semplice_cb = new JCheckBox("Semplice");
        semplice_cb.setBounds(250,20, 100, 20);
        composto_cb = new JCheckBox("Composto");
        composto_cb.setBounds(350,20, 100, 20);
        semplice_cb.setSelected(true);

        if (semplice_cb.isSelected())
            composto_cb.setSelected(false); // Deseleziona checkBox2 quando checkBox1 è selezionata

        matricola_l = new JLabel("Matricola:");
        matricola_l.setBounds(200, 60, 100,20);
        matricola_tf = new JTextField();
        matricola_tf.setBounds(260,60,100,20);

        nome_l = new JLabel("Nome:");
        nome_l.setBounds(40, 100, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(80, 100, 100, 20);

        cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 100, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(260, 100, 100, 20);

        corso_l = new JLabel("Corso:");
        corso_l.setBounds(380, 100, 100, 20);
        corso_tf = new JTextField();
        corso_tf.setBounds(420, 100, 100, 20);

        voto_l = new JLabel("Voto:");
        voto_l.setBounds(40, 140, 100, 20);
        voto_tf = new JTextField();
        voto_tf.setBounds(80, 140, 100, 20);

        lode_l = new JLabel("Lode:");
        lode_l.setBounds(200, 140, 100, 20);
        lode_cb = new JCheckBox();
        lode_cb.setBounds(260, 140, 100, 20);

        cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(380, 140, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(420, 140, 100, 20);

        registra_btn = new JButton("Registra Esame");
        registra_btn.setBounds(230,200,140,25);

        jPanel.add(tipologia_l);
        jPanel.add(semplice_cb);
        jPanel.add(composto_cb);
        jPanel.add(matricola_l);
        jPanel.add(matricola_tf);
        jPanel.add(nome_l);
        jPanel.add(nome_tf);
        jPanel.add(cognome_l);
        jPanel.add(cognome_tf);
        jPanel.add(corso_l);
        jPanel.add(corso_tf);
        jPanel.add(voto_l);
        jPanel.add(voto_tf);
        jPanel.add(lode_l);
        jPanel.add(lode_cb);
        jPanel.add(cfu_l);
        jPanel.add(cfu_tf);
        jPanel.add(registra_btn);

        jFrame.add(jPanel);
    }


    public void addEntry(Applicazione applicazione){
        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        String corso = corso_tf.getText();
        int voto = Integer.parseInt(voto_tf.getText());
        boolean lode = lode_cb.isSelected();
        int cfu = Integer.parseInt(cfu_tf.getText());

        defaultTableModel.addRow(new Object[]{nome, cognome, corso, voto, lode, cfu});
        Studente studente = new Studente(matricola, nome, cognome);
        applicazione.getStudenti().add(studente);
        if (semplice_cb.isSelected())
            applicazione.getEsami().add(new EsameSemplice(studente, corso, voto, lode, cfu));
        if(composto_cb.isSelected()) {
            String tipologia = (String) tipologia_prova_cb.getSelectedItem();
            int peso = Integer.parseInt(peso_tf.getText());
            EsameComposto esameComposto = new EsameComposto(studente, corso, cfu);
            esameComposto.aggiungiTipologia(new TipologiaProva(tipologia, peso, voto));
            applicazione.getEsami().add(esameComposto);
        }

    }

    public void editEntry(Applicazione applicazione){
        // Modifica un'entry nella tabella
        int selectedRow = jTable.getSelectedRow();

        System.out.println(selectedRow);
        if (selectedRow != -1) {
            String nome = nome_tf.getText();
            String cognome = cognome_tf.getText();
            String corso = corso_tf.getText();
            String voto = voto_tf.getText();
            boolean lode = lode_cb.isSelected();
            String cfu = cfu_tf.getText();
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
        if (selectedRow != -1) {
            // Rimuovo la riga
            defaultTableModel.removeRow(selectedRow);
            // Rimuovo lo studente dal vettore
            applicazione.getStudenti().delete(applicazione.getStudenti().get(selectedRow));
            applicazione.getEsami().delete(applicazione.getEsami().get(selectedRow));
        }
    }
}
