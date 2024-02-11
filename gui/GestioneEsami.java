package gui;

import classi.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestioneEsami{

    private JFrame jFrameSemplice, jFrameComposto, jFrameProve, jFrameInfo;
    private JPanel mainPanel, jPanelSemplice, jPanelComposto, jPanelProve, jPanelInfo;
    private JLabel matricola_l, nome_l, cognome_l, corso_l, voto_l, lode_l, cfu_l, tipologia_prova_l, peso_l, tipologia_l;
    private JTextField matricola_tf, nome_tf, cognome_tf, corso_tf, voto_tf, cfu_tf;
    private JTextField[] tipologia_prova_tf, voto_prova_tf, peso_prova_tf;
    private JCheckBox lode_cb, semplice_cb, composto_cb;
    private JButton aggiungi_btn, modifica_btn, elimina_btn, registra_esame_btn;
    private JComboBox n_prove_cb;
    private JComboBox[] tipologia_prova_cb;
    private TipologiaProva[] datiProve;

    public GestioneEsami(Applicazione applicazione) {

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Gestione esami");
        mainFrame.setSize(800,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new GridLayout(2, 1));

        JScrollPane jScrollPane = new JScrollPane(applicazione.getjTable());

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

        applicazione.getjTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = applicazione.getjTable().rowAtPoint(e.getPoint());
                int col = applicazione.getjTable().columnAtPoint(e.getPoint());
                String tipologia_esame = String.valueOf(applicazione.getEsami().get(row).getClass());
                // Verifica se è stato fatto clic sulla colonna "Voto"
                if (col == 2) {
                    if(tipologia_esame.contains("Semplice")){
                        EsameSemplice esameSemplice = (EsameSemplice) applicazione.getEsami().get(row);

                        jFrameInfo = new JFrame("Informazioni");
                        jFrameInfo.setSize(600, 250);
                        jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jPanelInfo = new JPanel();
                        jPanelInfo.setLayout(null);
                        jPanelInfo.setPreferredSize(new Dimension(600, 250));

                        formInfoExam(esameSemplice);

                        modifica_btn = new JButton("Modifca");
                        modifica_btn.setBounds(230,150,140,25);

                        modifica_btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                            }
                        });

                        jPanelInfo.add(modifica_btn);
                        jFrameInfo.add(jPanelInfo);
                    }

                    if (tipologia_esame.contains("Composto")) {
                        EsameComposto esameComposto = (EsameComposto) applicazione.getEsami().get(row);

                        jFrameInfo = new JFrame("Informazioni");
                        jFrameInfo.setSize(600, 400);
                        jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        jPanelInfo = new JPanel();
                        jPanelInfo.setLayout(null);
                        jPanelInfo.setPreferredSize(new Dimension(600, 400));

                        // Apre un nuovo JFrame (specchietto) quando viene cliccata la cella "Voto"
                        formInfoExam(esameComposto);

                        int n_prove = esameComposto.getEsami_parziali().size();
                        tipologia_prova_tf = new JTextField[n_prove];
                        peso_prova_tf = new JTextField[n_prove];
                        voto_prova_tf = new JTextField[n_prove];

                        for (int i = 0, y = 170; i < esameComposto.getEsami_parziali().size(); i++, y = y + 50) {
                            tipologia_prova_l = new JLabel("Tipologia prova:");
                            tipologia_prova_l.setBounds(20, y, 100, 20);
                            tipologia_prova_tf[i] = new JTextField();
                            tipologia_prova_tf[i].setBounds(120, y, 100, 20);
                            tipologia_prova_tf[i].setText(esameComposto.getEsami_parziali().get(i).getNome());
                            peso_l = new JLabel("Peso:");
                            peso_l.setBounds(240, y, 100, 20);
                            peso_prova_tf[i] = new JTextField();
                            peso_prova_tf[i].setBounds(280, y, 100, 20);
                            peso_prova_tf[i].setText(Integer.toString(esameComposto.getEsami_parziali().get(i).getPeso()));
                            voto_l = new JLabel("Voto:");
                            voto_l.setBounds(400, y, 100, 20);
                            voto_prova_tf[i] = new JTextField();
                            voto_prova_tf[i].setBounds(440, y, 100, 20);
                            voto_prova_tf[i].setText(Integer.toString(esameComposto.getEsami_parziali().get(i).getVoto()));

                            modifica_btn = new JButton("Modifca");
                            modifica_btn.setBounds(230,320,140,25);

                            jPanelInfo.add(tipologia_prova_l);
                            jPanelInfo.add(tipologia_prova_tf[i]);
                            jPanelInfo.add(peso_l);
                            jPanelInfo.add(peso_prova_tf[i]);
                            jPanelInfo.add(voto_l);
                            jPanelInfo.add(voto_prova_tf[i]);
                            jPanelInfo.add(modifica_btn);
                        }
                    }
                }

                jPanelInfo.add(modifica_btn);
                jFrameInfo.add(jPanelInfo);
            }
        });

        aggiungi_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == aggiungi_btn) {
                    formSimpleExam();
                    registra_esame_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(semplice_cb.isSelected()){
                                // Aggiungi un'entry alla tabella
                                addEntry(applicazione);
                                jFrameSemplice.dispose();
                            }
                        }
                    });

                    semplice_cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (semplice_cb.isSelected())
                                composto_cb.setSelected(false);
                            jFrameComposto.dispose();
                            formSimpleExam();
                        }
                    });
                    composto_cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (composto_cb.isSelected())
                                semplice_cb.setSelected(false);
                            jFrameSemplice.dispose();
                            jFrameComposto = new JFrame("Registrazione Esame Composto");
                            jFrameComposto.setSize(600, 300);
                            jFrameComposto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jPanelComposto = new JPanel();
                            jPanelComposto.setLayout(null);
                            jPanelComposto.setPreferredSize(new Dimension(600, 300));

                            tipologia_l = new JLabel("Tipologia esame:");
                            tipologia_l.setBounds(150, 20, 100, 20);
                            semplice_cb = new JCheckBox("Semplice");
                            semplice_cb.setBounds(250, 20, 100, 20);
                            composto_cb = new JCheckBox("Composto");
                            composto_cb.setBounds(350, 20, 100, 20);
                            composto_cb.setSelected(true);

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
                            corso_tf.setBounds(80, 110, 100, 20);

                            cfu_l = new JLabel("CFU:");
                            cfu_l.setBounds(220, 110, 100, 20);
                            cfu_tf = new JTextField();
                            cfu_tf.setBounds(260, 110, 100, 20);

                            JLabel n_prove_l = new JLabel("N. prove:");
                            n_prove_l.setBounds(400, 110, 100, 20);
                            Integer[] options = {2, 3};
                            n_prove_cb = new JComboBox(options);
                            n_prove_cb.setBounds(460, 110, 100, 20);

                            n_prove_cb.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                                        System.out.println("Devi prima compilare tutti i campi!");
                                    } else {
                                        jFrameProve = new JFrame("Registrazione Prove Parziali");
                                        jFrameProve.setSize(600, 300);
                                        jFrameProve.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                        jPanelProve = new JPanel();
                                        jPanelProve.setLayout(null);
                                        jPanelProve.setPreferredSize(new Dimension(600, 300));

                                        Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();

                                        datiProve = new TipologiaProva[selectedValue];

                                        String[] options = {"Scritta", "Orale", "Pratica"};
                                        tipologia_prova_cb = new JComboBox[3];
                                        peso_prova_tf = new JTextField[selectedValue];
                                        voto_prova_tf = new JTextField[selectedValue];

                                        for (int i = 0, y = 20; i < selectedValue; i++, y = y + 50) {
                                            tipologia_prova_l = new JLabel("Tipologia prova:");
                                            tipologia_prova_l.setBounds(20, y, 100, 20);
                                            tipologia_prova_cb[i] = new JComboBox(options);
                                            tipologia_prova_cb[i].setBounds(120, y, 100, 20);
                                            peso_l = new JLabel("Peso:");
                                            peso_l.setBounds(240, y, 100, 20);
                                            peso_prova_tf[i] = new JTextField();
                                            peso_prova_tf[i].setBounds(280, y, 100, 20);
                                            voto_l = new JLabel("Voto:");
                                            voto_l.setBounds(400, y, 100, 20);
                                            voto_prova_tf[i] = new JTextField();
                                            voto_prova_tf[i].setBounds(440, y, 100, 20);

                                            jPanelProve.add(tipologia_prova_l);
                                            jPanelProve.add(tipologia_prova_cb[i]);
                                            jPanelProve.add(peso_l);
                                            jPanelProve.add(peso_prova_tf[i]);
                                            jPanelProve.add(voto_l);
                                            jPanelProve.add(voto_prova_tf[i]);
                                        }

                                        registra_esame_btn = new JButton("Registra Esame");
                                        registra_esame_btn.setBounds(230, 170, 140, 25);

                                        registra_esame_btn.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                jFrameComposto.dispose();
                                                for(int i=0; i<selectedValue; i++){
                                                    String nome = (String) tipologia_prova_cb[i].getSelectedItem();
                                                    int peso = Integer.parseInt(peso_prova_tf[i].getText());
                                                    int voto = Integer.parseInt(voto_prova_tf[i].getText());
                                                    datiProve[i] = new TipologiaProva(nome,peso,voto);
                                                }
                                                jFrameProve.dispose();
                                                addEntry(applicazione);
                                            }
                                        });
                                    }

                                    jPanelProve.add(registra_esame_btn);

                                    jFrameProve.add(jPanelProve);
                                    jFrameProve.setLocationRelativeTo(null);
                                    jFrameProve.setVisible(true);
                                }
                            });

                            jPanelComposto.add(tipologia_l);
                            jPanelComposto.add(semplice_cb);
                            jPanelComposto.add(composto_cb);
                            jPanelComposto.add(matricola_l);
                            jPanelComposto.add(matricola_tf);
                            jPanelComposto.add(nome_l);
                            jPanelComposto.add(nome_tf);
                            jPanelComposto.add(cognome_l);
                            jPanelComposto.add(cognome_tf);
                            jPanelComposto.add(corso_l);
                            jPanelComposto.add(corso_tf);
                            jPanelComposto.add(cfu_l);
                            jPanelComposto.add(cfu_tf);
                            jPanelComposto.add(n_prove_l);
                            jPanelComposto.add(n_prove_cb);

                            jFrameComposto.add(jPanelComposto);
                            jFrameComposto.setLocationRelativeTo(null);
                            jFrameComposto.setVisible(true);
                        }
                    });
                }
            }
        });
        /*modifica_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == modifica_btn) {
                    //editEntry(applicazione);
                }
            }
        });*/
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

    public void formInfoExam(Esame esame){

        matricola_l = new JLabel("Matricola:");
        matricola_l.setBounds(200, 20, 100,20);
        matricola_tf = new JTextField();
        matricola_tf.setBounds(260,20,100,20);
        matricola_tf.setText(Integer.toString(esame.getStudente().getMatricola()));

        nome_l = new JLabel("Nome:");
        nome_l.setBounds(40, 60, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(80, 60, 100, 20);
        nome_tf.setText(esame.getStudente().getNome());

        cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 60, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(260, 60, 100, 20);
        cognome_tf.setText(esame.getStudente().getCognome());

        corso_l = new JLabel("Corso:");
        corso_l.setBounds(380, 60, 100, 20);
        corso_tf = new JTextField();
        corso_tf.setBounds(420, 60, 100, 20);
        corso_tf.setText(esame.getNome());

        voto_l = new JLabel("Voto:");
        voto_l.setBounds(40, 100, 100, 20);
        voto_tf = new JTextField();
        voto_tf.setBounds(80, 100, 100, 20);
        voto_tf.setText(Integer.toString(esame.getVoto()));

        lode_l = new JLabel("Lode:");
        lode_l.setBounds(200, 100, 100, 20);
        JTextField lode_tf = new JTextField();
        lode_tf.setBounds(260, 100, 100, 20);
        lode_tf.setText(Boolean.toString(esame.getLode()));

        cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(380, 100, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(420, 100, 100, 20);
        cfu_tf.setText(Integer.toString(esame.getCfu()));

        jPanelInfo.add(matricola_l);
        jPanelInfo.add(matricola_tf);
        jPanelInfo.add(nome_l);
        jPanelInfo.add(nome_tf);
        jPanelInfo.add(cognome_l);
        jPanelInfo.add(cognome_tf);
        jPanelInfo.add(corso_l);
        jPanelInfo.add(corso_tf);
        jPanelInfo.add(voto_l);
        jPanelInfo.add(voto_tf);
        jPanelInfo.add(lode_l);
        jPanelInfo.add(lode_tf);
        jPanelInfo.add(cfu_l);
        jPanelInfo.add(cfu_tf);

        jFrameInfo.setLocationRelativeTo(null);
        jFrameInfo.setVisible(true);
    }

    public void formSimpleExam(){

        jFrameSemplice = new JFrame("Registrazione Esame Semplice");
        jFrameSemplice.setSize(600, 300);
        jPanelSemplice = new JPanel();
        jFrameSemplice.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrameSemplice.setPreferredSize(new Dimension(600, 300));
        jPanelSemplice.setLayout(null);

        tipologia_l = new JLabel("Tipologia esame:");
        tipologia_l.setBounds(150,20, 100,20);
        semplice_cb = new JCheckBox("Semplice");
        semplice_cb.setBounds(250,20, 100, 20);
        composto_cb = new JCheckBox("Composto");
        composto_cb.setBounds(350,20, 100, 20);
        semplice_cb.setSelected(true);

        /*if (semplice_cb.isSelected())
            composto_cb.setSelected(false); // Deseleziona checkBox2 quando checkBox1 è selezionata*/

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

        registra_esame_btn = new JButton("Registra Esame");
        registra_esame_btn.setBounds(230,200,140,25);

        jPanelSemplice.add(tipologia_l);
        jPanelSemplice.add(semplice_cb);
        jPanelSemplice.add(composto_cb);
        jPanelSemplice.add(matricola_l);
        jPanelSemplice.add(matricola_tf);
        jPanelSemplice.add(nome_l);
        jPanelSemplice.add(nome_tf);
        jPanelSemplice.add(cognome_l);
        jPanelSemplice.add(cognome_tf);
        jPanelSemplice.add(corso_l);
        jPanelSemplice.add(corso_tf);
        jPanelSemplice.add(voto_l);
        jPanelSemplice.add(voto_tf);
        jPanelSemplice.add(lode_l);
        jPanelSemplice.add(lode_cb);
        jPanelSemplice.add(cfu_l);
        jPanelSemplice.add(cfu_tf);
        jPanelSemplice.add(registra_esame_btn);

        jFrameSemplice.add(jPanelSemplice);

        jFrameSemplice.setLocationRelativeTo(null);
        jFrameSemplice.setVisible(true);
    }

    public void addEntry(Applicazione applicazione){
        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        String corso = corso_tf.getText();
        int cfu = Integer.parseInt(cfu_tf.getText());
        Studente studente = new Studente(matricola, nome, cognome);
        if(!studente.equals(applicazione.ricercaStudente(matricola)))
            applicazione.getStudenti().add(studente);
        if (semplice_cb.isSelected()) {
            boolean lode = lode_cb.isSelected();
            int voto = Integer.parseInt(voto_tf.getText());
            applicazione.getDefaultTableModel().addRow(new Object[]{nome, cognome, corso, voto, lode, cfu});
            applicazione.getEsami().add(new EsameSemplice(studente, corso, voto, lode, cfu));
        }
        if(composto_cb.isSelected()) {
            EsameComposto esameComposto = new EsameComposto(studente, corso, cfu);
            Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();

            for(int i=0; i<selectedValue; i++){
                esameComposto.getEsami_parziali().add(datiProve[i]);
            }

            esameComposto.voto();
            applicazione.getEsami().add(esameComposto);
            applicazione.getDefaultTableModel().addRow(new Object[]{nome, cognome, corso, esameComposto.getVoto(), false, cfu});
        }
    }

    public void editEntry(Applicazione applicazione, int row){

        if (row != -1) {
            int matricola = Integer.parseInt(matricola_tf.getText());
            String nome = nome_tf.getText();
            String cognome = cognome_tf.getText();
            String corso = corso_tf.getText();
            int voto = Integer.parseInt(voto_tf.getText());
            boolean lode = lode_cb.isSelected();
            int cfu = Integer.parseInt(cfu_tf.getText());
            applicazione.getDefaultTableModel().setValueAt(nome, row, 1);
            applicazione.getDefaultTableModel().setValueAt(cognome, row, 2);
            applicazione.getDefaultTableModel().setValueAt(corso, row, 3);
            applicazione.getDefaultTableModel().setValueAt(voto, row, 4);
            applicazione.getDefaultTableModel().setValueAt(lode, row, 5);
            applicazione.getDefaultTableModel().setValueAt(cfu, row, 6);

            Studente studente = applicazione.ricercaStudente(matricola);
            studente.setMatricola(matricola);
            studente.setNome(nome);
            studente.setCognome(cognome);
            applicazione.getEsami().get(row).setNome(corso);
            applicazione.getEsami().get(row).setVoto(voto);
            applicazione.getEsami().get(row).setLode(lode);
            applicazione.getEsami().get(row).setCfu(cfu);

            String tipologia_esame = String.valueOf(applicazione.getEsami().get(row).getClass());
            if(tipologia_esame.contains("Composto")){

            }
        }
    }

    public void deleteEntry(Applicazione applicazione) {
        // Elimina un'entry dalla tabella
        int selectedRow = applicazione.getjTable().getSelectedRow();
        // Recupero lo studente da eliminare
        if (selectedRow != -1) {
            // Rimuovo la riga
            applicazione.getDefaultTableModel().removeRow(selectedRow);
            // Rimuovo l'esame specificato dal vettore
            applicazione.getEsami().delete(applicazione.getEsami().get(selectedRow));
        }
    }
}
