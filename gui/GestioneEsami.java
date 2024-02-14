package gui;

import classi.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

public class GestioneEsami{

    private JFrame mainFrame, jFrameSemplice, jFrameComposto, jFrameProve, jFrameInfo;
    private JPanel mainPanel, jPanelSemplice, jPanelComposto, jPanelProve, jPanelInfo;
    private JLabel matricola_l, nome_l, cognome_l, corso_l, voto_l, lode_l, cfu_l, tipologia_prova_l, peso_l, tipologia_l;
    private JTextField matricola_tf, nome_tf, cognome_tf, corso_tf, voto_tf, cfu_tf, filtro_tf;
    private JTextField[] tipologia_prova_tf, voto_prova_tf, peso_prova_tf;
    private JCheckBox lode_cb, semplice_cb, composto_cb;
    private JButton modifica_btn, registra_esame_btn;
    private JComboBox n_prove_cb;
    private JComboBox[] tipologia_prova_cb;
    private TipologiaProva[] datiProve;
    private boolean modificheNonSalvate;
    private SalvaEsami salvaEsami = null;

    public GestioneEsami(Applicazione applicazione) {

        /** Creazione del frame principale per la finestra "Gestione Esami" */
        mainFrame = new JFrame();
        mainFrame.setTitle("Gestione esami");
        mainFrame.setSize(800,500);
        /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame

        /** Creazione del pannello principale */
        mainPanel = new JPanel(new GridLayout(2, 1));

        /** Creazione dello scroll per visualizzare tutte le righe della tabella */
        JScrollPane jScrollPane = new JScrollPane(applicazione.getTabella().getTable());

        /** Creazione del pannello per i controlli */
        JPanel controlPanel = new JPanel(new FlowLayout());

        JLabel filtro_l = new JLabel("Filtro:");
        JTextField filtro_tf = new JTextField(10);
        /** Creazione del bottone per calcolare la media dei voti sui filtrati */
        JButton media_btn = new JButton("Media");

        /** Creazione del bottone per generare un grafico sui record filtrati */
        JButton grafico_btn = new JButton("Grafico");

        /** Aggiungo un listener per tracciare le modifiche sulla tabella
         *  Il listener è definito tramite una lambda expression che imposta la variabile booleana modificheNonSalvate
         * su true ogni volta che viene rilevata una modifica al modello di tabella
         */
        applicazione.getTabella().getDefaultTableModel().addTableModelListener(e -> modificheNonSalvate = true);

        /** Ascoltatore degli eventi di finestra per il frame principale */
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(modificheNonSalvate){ // Se non sono state salvate le modifiche già effettuate...
                    /** Mostra un messaggio di conferma per avvisare l'utente che ci sono modifiche non salvate prima di chiudere il frame principale */
                    int result = JOptionPane.showConfirmDialog(mainFrame, "Ci sono modifiche non salvate. Vuoi salvarle prima di chiudere?", "Modifiche non salvate", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) { // Se decide di salvare le modifiche...
                        /** Salvataggio delle modifiche */
                        salvaEsami = new SalvaEsami(mainFrame, applicazione);
                        /** Imposta a false la variabile "modificheNonSalvate" */
                        modificheNonSalvate = false;
                        /** Chiude il frame */
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else if (result == JOptionPane.NO_OPTION) { // Se decide di non salvare le modifiche...
                        /** Chiude il frame principale senza salvare le modifiche */
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        /** Annulla la chiusura del frame */
                        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    /** Chiude il frame se non ci sono modifiche non salvate */
                    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                }
            }
        });

        /** Ascoltatore degli eventi sul click del bottone per il calcolo della media dei voti sui record filtrati */
        media_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /** Chiamata della funzione per il calcolo della media dei voti sui record filtrati */
                calcolaMedia(applicazione);
                if(!filtro_tf.getText().isEmpty()) // Se il campo di testo del filtro non è vuoto...
                    /** Mostra la media attraverso un messaggio informativo */
                    JOptionPane.showMessageDialog(mainFrame, "Media pesata dei voti: " + calcolaMedia(applicazione), "Informazione", JOptionPane.INFORMATION_MESSAGE);
                else
                    /** Mostra un messaggio d'errore */
                    JOptionPane.showMessageDialog(mainFrame, "Per ottenere la media è necessario filtrare prima la tabella" + calcolaMedia(applicazione), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        /** Ascoltatore degli eventi per il campo di testo del filtro
         * Aggiunge un DocumentListener al campo di testo per monitorare i cambiamenti
         */
        filtro_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(applicazione, filtro_tf.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(applicazione, filtro_tf.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(applicazione, filtro_tf.getText());
            }

            /** Metodo per applicare il filtro alla tabella */
            private void filterTable(Applicazione applicazione, String query) {
                /** Creazione di un oggetto di classe TableRowSorter per ordinare e filtrare le righe della tabella */
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(applicazione.getTabella().getDefaultTableModel());
                /** Imposta il TableRowSorter sulla tabella */
                applicazione.getTabella().getTable().setRowSorter(sorter);

                /** Applica un filtro regex (ignorando le maiuscole/minuscole) sulle colonne 2 (Cognome) e 3 (Corso) in base al testo inserito nel campo di testo */
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query.trim(), 2, 3));
            }
        });

        /** Ascoltatore degli eventi sul click del bottone per la generazione del grafico sui record filtrati */
        grafico_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grafico datiStatistici = new Grafico(applicazione);
            }
        });

        /** Creazione del bottone per aggiungere un nuovo record alla tabella */
        JButton aggiungi_btn = new JButton("Aggiungi");
        /** Creazione del bottone per eliminare un record dalla tabella */
        JButton elimina_btn = new JButton("Elimina");
        /** Creazione del bottone per salvare i record della tabella su file */
        JButton salva_btn = new JButton("Salva");

        /** Ascoltatore degli eventi sul click del bottone per il salvataggio degli esami su file */
        salva_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /** Salvataggio degli esami su file */
                salvaEsami = new SalvaEsami(mainFrame, applicazione);
                /** Imposta a false la variabile "modificheNonSalvate" */
                modificheNonSalvate = false;
            }
        });

        controlPanel.add(filtro_l);
        controlPanel.add(filtro_tf);
        controlPanel.add(media_btn);
        controlPanel.add(grafico_btn);
        controlPanel.add(aggiungi_btn);
        controlPanel.add(elimina_btn);
        controlPanel.add(salva_btn);

        mainPanel.add(jScrollPane);
        mainPanel.add(controlPanel);

        mainFrame.add(mainPanel);

        /** Imposta la generazione del frame principale al centro dello schermo */
        mainFrame.setLocationRelativeTo(null);

        /** Rende visibile il frame principale */
        mainFrame.setVisible(true);

        /** Ascoltatore degli eventi sul click di un campo della tabella */
        applicazione.getTabella().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /** Recupera la riga del campo selezionato */
                int row = applicazione.getTabella().getTable().rowAtPoint(e.getPoint());
                /** Recupera la colonna del campo selezionato */
                int col = applicazione.getTabella().getTable().columnAtPoint(e.getPoint());
                /** Recupera la classe dell'esame selezionato (semplice o composto) */
                String tipologia_esame = String.valueOf(applicazione.getEsami().get(row).getClass());

                if (col == 3) { // Verifica se è stato fatto clic sulla colonna "Corso"
                    if(tipologia_esame.contains("Semplice")){ // Se la classe dell'esame selezionato è Semplice...
                        /** Recupera l'esame semplice selezionato */
                        EsameSemplice esameSemplice = (EsameSemplice) applicazione.getEsami().get(row);
                        /** Crea un nuovo frame per mostrare le informazioni relative all'esame selezionato */
                        jFrameInfo = new JFrame("Informazioni");
                        jFrameInfo.setSize(600, 250);
                        /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                        jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                        /** Creazione del pannello per contenere gli oggetti necessari a mostrare le informazioni relative all'esame selezionato */
                        jPanelInfo = new JPanel();
                        jPanelInfo.setLayout(null);
                        jPanelInfo.setPreferredSize(new Dimension(600, 250));

                        /** Richiama il metodo per la visualizzazione e/o modifica delle informazioni dell'esame passato */
                        getFormInfoExam(esameSemplice);

                        modifica_btn = new JButton("Modifca");
                        modifica_btn.setBounds(230,150,140,25);

                        /** Ascoltatore degli eventi sul click del bottone per la modifica dei dati di un esame */
                        modifica_btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                /** Richiama il metodo per la modifica del record della tabella */
                                editEntry(applicazione, row);
                                /** Chiude il frame */
                                jFrameInfo.dispose();
                            }
                        });

                        jFrameInfo.add(jPanelInfo);
                    }

                    if (tipologia_esame.contains("Composto")) { // Se la classe dell'esame selezionato è Composto...
                        /** Recupera l'esame semplice selezionato */
                        EsameComposto esameComposto = (EsameComposto) applicazione.getEsami().get(row);
                        /** Crea un nuovo frame per mostrare le informazioni relative all'esame selezionato */
                        jFrameInfo = new JFrame("Informazioni");
                        jFrameInfo.setSize(600, 400);
                        /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                        jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                        jPanelInfo = new JPanel();
                        jPanelInfo.setLayout(null);
                        jPanelInfo.setPreferredSize(new Dimension(600, 400));

                        /** Richiama il metodo per la visualizzazione e/o modifica delle informazioni dell'esame passato */
                        getFormInfoExam(esameComposto);

                        /** Recupero il numero di prove parziali dell'esame selezionato */
                        int n_prove = esameComposto.getEsami_parziali().size();
                        /** Creo l'array di campi di testo per l'inserimento della tipologia delle n_prove parziali relative all'esame composto selezionato */
                        tipologia_prova_tf = new JTextField[n_prove];
                        /** Creo l'array di campi di testo per l'inserimento dei pesi delle n_prove parziali relative all'esame composto selezionato*/
                        peso_prova_tf = new JTextField[n_prove];
                        /** Creo l'array di campi di testo per l'inserimento dei voti delle n_prove parziali relative all'esame composto selezionato */
                        voto_prova_tf = new JTextField[n_prove];

                        /** Itero le n_prove per la visualizzazione e/o modifica delle informazioni relative agli esami parziali dell'esame composto selezionato */
                        for (int i = 0, y = 170; i < n_prove; i++, y = y + 50) {
                            tipologia_prova_l = new JLabel("Tipologia prova:");
                            tipologia_prova_l.setBounds(20, y, 100, 20);
                            /** Creazione dell'i-esimo campo di testo per la visualizzazione della tipologia della i-esima prova intermedia */
                            tipologia_prova_tf[i] = new JTextField();
                            tipologia_prova_tf[i].setBounds(120, y, 100, 20);
                            /** Imposta la tipologia dell'i-esima prova parziale dell'esame composto selezioanto */
                            tipologia_prova_tf[i].setText(esameComposto.getEsami_parziali().get(i).getNome());
                            peso_l = new JLabel("Peso:");
                            peso_l.setBounds(240, y, 100, 20);
                            /** Creazione dell'i-esimo campo di testo per la visualizzazione del peso della i-esima prova intermedia */
                            peso_prova_tf[i] = new JTextField();
                            peso_prova_tf[i].setBounds(280, y, 100, 20);
                            /** Imposta il peso dell'i-esima prova parziale dell'esame composto selezioanto */
                            peso_prova_tf[i].setText(Integer.toString(esameComposto.getEsami_parziali().get(i).getPeso()));
                            voto_l = new JLabel("Voto:");
                            voto_l.setBounds(400, y, 100, 20);
                            /** Creazione dell'i-esimo campo di testo per la visualizzazione del voto della i-esima prova intermedia */
                            voto_prova_tf[i] = new JTextField();
                            voto_prova_tf[i].setBounds(440, y, 100, 20);
                            /** Imposta il voto dell'i-esima prova parziale dell'esame composto selezioanto */
                            voto_prova_tf[i].setText(Integer.toString(esameComposto.getEsami_parziali().get(i).getVoto()));

                            jPanelInfo.add(tipologia_prova_l);
                            jPanelInfo.add(tipologia_prova_tf[i]);
                            jPanelInfo.add(peso_l);
                            jPanelInfo.add(peso_prova_tf[i]);
                            jPanelInfo.add(voto_l);
                            jPanelInfo.add(voto_prova_tf[i]);
                        }

                        modifica_btn = new JButton("Modifca");
                        modifica_btn.setBounds(230,320,140,25);

                        /** Ascoltatore degli eventi sul click del bottone che permette di modificare le informazioni dell'esame composto selezionato*/
                        modifica_btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                /** Richiama il metodo che modifica il record selezionato */
                                editEntry(applicazione, row);
                                /** Chiude il frame */
                                jFrameInfo.dispose();
                            }
                        });
                    }
                }
                jPanelInfo.add(modifica_btn);
                jFrameInfo.add(jPanelInfo);
            }
        });

        /** Ascoltatore degli eventi sul click del bottone che permette di aggiungere un record nella tabella */
        aggiungi_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == aggiungi_btn) { // Se l'evento consiste nel click del bottone "aggiungi_btn"
                    /** Richiama il metodo per l'inserimento delle informazioni di un esame semplice */
                    formSimpleExam();

                    /** Ascoltatore degli eventi sul click del bottone che permette di registrare un esame semplice */
                    registra_esame_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(semplice_cb.isSelected()){ // Se la checkbox è settata su semplice...
                                /** Richiama il metodo per l'aggiunta di un nuovo record nella tabella */
                                addEntry(applicazione);
                                /** Chiude il frame */
                                jFrameSemplice.dispose();
                            }
                        }
                    });

                    /** Ascoltatore degli eventi sulla checkbox selezionata sull'esame semplice */
                    semplice_cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (semplice_cb.isSelected()) /** Da mettere a posto!!! **/
                                composto_cb.setSelected(false);
                            /** Chiude il frame relativo alla registrazione dell'esame composto */
                            jFrameComposto.dispose();
                            /** Richiama il metodo per la registrazione di un esame semplice */
                            formSimpleExam();
                        }
                    });

                    /** Ascoltatore degli eventi sulla checkbox selezionata sull'esame composto */
                    composto_cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (composto_cb.isSelected())
                                semplice_cb.setSelected(false);
                            /** Chiude il frame relativo alla registrazione dell'esame semplice */
                            jFrameSemplice.dispose();
                            /** Creazione del frame per la registrazione di un esame composto */
                            jFrameComposto = new JFrame("Registrazione Esame Composto");
                            jFrameComposto.setSize(600, 300);
                            /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                            jFrameComposto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
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
                            matricola_l.setBounds(200, 60, 100, 20);
                            matricola_tf = new JTextField();
                            matricola_tf.setBounds(260, 60, 100, 20);

                            nome_l = new JLabel("Nome:");
                            nome_l.setBounds(40, 100, 100, 20);
                            nome_tf = new JTextField();
                            nome_tf.setBounds(80, 100, 100, 20);

                            cognome_l = new JLabel("Cognome:");
                            cognome_l.setBounds(200, 100, 100, 20);
                            cognome_tf = new JTextField();
                            cognome_tf.setBounds(260, 100, 100, 20);

                            corso_l = new JLabel("Corso:");
                            corso_l.setBounds(390, 100, 100, 20);
                            corso_tf = new JTextField();
                            corso_tf.setBounds(450, 100, 100, 20);

                            lode_l = new JLabel("Lode:");
                            lode_l.setBounds(40, 140, 100, 20);
                            lode_cb = new JCheckBox();
                            lode_cb.setBounds(80, 140, 100, 20);

                            cfu_l = new JLabel("CFU:");
                            cfu_l.setBounds(200, 140, 100, 20);
                            cfu_tf = new JTextField();
                            cfu_tf.setBounds(260, 140, 100, 20);

                            JLabel n_prove_l = new JLabel("N. prove:");
                            n_prove_l.setBounds(390, 140, 100, 20);

                            /** Numero di prove parziali registrabili per un esame composto */
                            Integer[] options = {2, 3};
                            n_prove_cb = new JComboBox(options);
                            n_prove_cb.setBounds(450, 140, 100, 20);

                            /** Ascoltatore degli eventi per la combobox sul numero di prove parziali che si vogliono registrare */
                            n_prove_cb.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                                        JOptionPane.showMessageDialog(jFrameComposto, "E' necessario compilare tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        /** Creazione del frame per la registrazione delle prove parziali */
                                        jFrameProve = new JFrame("Registrazione Prove Parziali");
                                        jFrameProve.setSize(600, 300);
                                        /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                                        jFrameProve.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                                        jPanelProve = new JPanel();
                                        jPanelProve.setLayout(null);
                                        jPanelProve.setPreferredSize(new Dimension(600, 300));

                                        /** Recupero il numero di prove parziali registrate */
                                        Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();

                                        /** Creazione di un array di classe TipologiaProva che andrà a contenere temporaneamente le prove parziali registrate */
                                        datiProve = new TipologiaProva[selectedValue];

                                        /** Creazione degli oggetti per la form di inserimento dei dati relativi ad una prova parziale */
                                        String[] options = {"Scritta", "Orale", "Pratica"};
                                        tipologia_prova_cb = new JComboBox[3];
                                        peso_prova_tf = new JTextField[selectedValue];
                                        voto_prova_tf = new JTextField[selectedValue];

                                        /** Itero gli oggetti per l'inserimento della prova parziale n_prove-volte */
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

                                        /** Ascoltatore degli eventi sul click del bottone per la registrazione di un esame composto */
                                        registra_esame_btn.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                /** Chiude il frame per la registrazione dell'esame composto */
                                                jFrameComposto.dispose();
                                                /** Recupera i dati dagli oggetti grafici */
                                                for(int i=0; i<selectedValue; i++){
                                                    String nome = (String) tipologia_prova_cb[i].getSelectedItem();
                                                    int peso = Integer.parseInt(peso_prova_tf[i].getText());
                                                    int voto = Integer.parseInt(voto_prova_tf[i].getText());
                                                    /** Salva i dati in questo array temporaneo */
                                                    datiProve[i] = new TipologiaProva(nome,peso,voto);
                                                }
                                                /** Chiude il frame per la registrazione delle prove parziali */
                                                jFrameProve.dispose();
                                                /** Richiama il metodo per l'aggiunta di un nuovo record nella tabella */
                                                addEntry(applicazione);
                                            }
                                        });
                                    }

                                    jPanelProve.add(registra_esame_btn);

                                    jFrameProve.add(jPanelProve);

                                    /** Imposta la generazione del frame per la registrazione delle prove parziali al centro dello schermo */
                                    jFrameProve.setLocationRelativeTo(null);
                                    /** Rende visibile il frame per la registrazione delle prove parziali*/
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
                            jPanelComposto.add(lode_l);
                            jPanelComposto.add(lode_cb);
                            jPanelComposto.add(cfu_l);
                            jPanelComposto.add(cfu_tf);
                            jPanelComposto.add(n_prove_l);
                            jPanelComposto.add(n_prove_cb);

                            jFrameComposto.add(jPanelComposto);
                            /** Imposta la generazione del frame per la registrazione di un esame composto al centro dello schermo */
                            jFrameComposto.setLocationRelativeTo(null);
                            /** Rende visibile il frame per la registrazione di un esame composto */
                            jFrameComposto.setVisible(true);
                        }
                    });
                }
            }
        });

        /** Ascoltatore degli eventi sul click del bottone che permette di eliminare un record dalla tabella */
        elimina_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == elimina_btn) { // Se viene premuto il bottone "elimina_btn"
                    /** Richiama il metodo che permette di eliminare un record selezionato */
                    deleteEntry(applicazione);
                }
            }
        });
    }

    /**
     * calcolaMedia
     * Metodo che restituisce la media pesata dei voti in relazione ai record filtrati nella tabella
     * @param applicazione permette di gestire gli archivi dati e la tabella
     * @return media pesata dei voti
     * */
    public double calcolaMedia(Applicazione applicazione){

        int somma = 0;
        /** Recupero il numero di record filtrati nella tabella */
        int rowCount = applicazione.getTabella().getTable().getRowCount();

        // Se non ci sono righe nella tabella, mostra una media di 0
        if (rowCount == 0) {
            return 0.0;
        }

        /** Somma tutti i voti presenti nella colonna "Voti" */
        for (int i = 0; i < rowCount; i++) {
            int voto = Integer.parseInt((String) applicazione.getTabella().getTable().getValueAt(i, 4)); // La colonna "Voti" è la colonna 4
            somma += voto;
        }

        /** Restituisce la media arrotondando il numero */
        return  Math.round((double) somma / rowCount);
    }

    /**
     * getFormInfoExam
     * Metodo che crea la parte grafica per la visualizzazione e/o modifica dei dati relativi ad un esame
     * @param esame necessario per differenziare la form nel caso di esame semplice o composto
     */
    public void getFormInfoExam(Esame esame){

        String tipologia_esame = String.valueOf(esame.getClass());

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
        /** Se l'esame è composto nego la possibilità di modificare il voto
         * in quanto esso viene calcolato automaticamente con l'inserimento delle prove parziali */
        if(tipologia_esame.contains("Composto")){
            voto_tf.setEditable(false);
            voto_tf.setEnabled(false);
        }

        lode_l = new JLabel("Lode:");
        lode_l.setBounds(200, 100, 100, 20);
        lode_cb = new JCheckBox();
        lode_cb.setBounds(260, 100, 100, 20);
        lode_cb.setSelected(esame.getLode());

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
        jPanelInfo.add(lode_cb);
        jPanelInfo.add(cfu_l);
        jPanelInfo.add(cfu_tf);

        /** Imposta la generazione del frame principale al centro dello schermo */
        jFrameInfo.setLocationRelativeTo(null);
        /** Rende visibile il frame per la visualizzazione e/o modifica dei dati di un esame */
        jFrameInfo.setVisible(true);
    }

    /**
     * formSimpleExam
     * Metodo che crea la parte grafica per la registrazione di un esame semplice
     */
    public void formSimpleExam(){

        jFrameSemplice = new JFrame("Registrazione Esame Semplice");
        jFrameSemplice.setSize(600, 300);
        jPanelSemplice = new JPanel();
        /** Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
        jFrameSemplice.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
        jFrameSemplice.setPreferredSize(new Dimension(600, 300));
        jPanelSemplice.setLayout(null);

        tipologia_l = new JLabel("Tipologia esame:");
        tipologia_l.setBounds(150,20, 100,20);
        semplice_cb = new JCheckBox("Semplice");
        semplice_cb.setBounds(250,20, 100, 20);
        composto_cb = new JCheckBox("Composto");
        composto_cb.setBounds(350,20, 100, 20);
        /** Imposto la checkbox a true poichè il frame si apre direttamente con la form per l'inserimento di un esame semplice */
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

        /** Imposta la generazione del frame principale al centro dello schermo */
        jFrameSemplice.setLocationRelativeTo(null);
        /** Rende visibile il frame per la registrazione di un esame semplice */
        jFrameSemplice.setVisible(true);
    }

    /**
     * adddEntry
     * Metodo che permette di:
     *  - aggiungere un nuovo record alla tabella
     *  - aggiungere un nuovo studente nell'archivio studenti
     *  - aggiungere un nuovo esame nell'archivio esami
     * @param applicazione permette di gestire gli archivi dati e la tabella
     * */
    public void addEntry(Applicazione applicazione){
        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        String corso = corso_tf.getText();
        boolean lode = lode_cb.isSelected();
        int cfu = Integer.parseInt(cfu_tf.getText());
        Studente studente = new Studente(matricola, nome, cognome);
        /** Se lo studente registrato non è già presente nell'archivio allora viene aggiunto*/
        if(!studente.equals(applicazione.ricercaStudente(matricola)))
            applicazione.getStudenti().add(studente);
        if (semplice_cb.isSelected()) { // Se viene selezionata la checkbox dell'esame semplice
            int voto = Integer.parseInt(voto_tf.getText());
            /** Aggiunge l'esame semplice nell'archivio esami */
            applicazione.getEsami().add(new EsameSemplice(studente, corso, voto, lode, cfu));
            /** Aggiunge il nuovo record nella tabella */
            applicazione.getTabella().getDefaultTableModel().addRow(new Object[]{matricola, nome, cognome, corso, voto, lode, cfu});
        }
        if(composto_cb.isSelected()) { // Se viene selezionata la checkbox dell'esame composto
            EsameComposto esameComposto = new EsameComposto(studente, corso, lode, cfu);
            /** Recupero il numero di prove parziali */
            Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();

            /** Registro le prove parziali recuperandole dall'array temporaneo creato durante la loro registrazione */
            for(int i=0; i<selectedValue; i++){
                esameComposto.getEsami_parziali().add(datiProve[i]);
            }

            /** Calcolo il voto finale dell'esame composto da più prove parziali */
            esameComposto.voto();
            /** Aggiunge l'esame composto nell'archivio esami */
            applicazione.getEsami().add(esameComposto);
            /** Aggiunge il nuovo record nella tabella */
            applicazione.getTabella().getDefaultTableModel().addRow(new Object[]{matricola, nome, cognome, corso, esameComposto.getVoto(), lode, cfu});
        }
    }

    /**
     * editEntry
     * Metodo che permette di:
     *  - modificare i dati del record selezionato
     *  - salvare le modifiche nei relativi archivi studenti e/o esame
     * @param applicazione permette di gestire gli archivi dati e la tabella
     * @param row riga selezionata
     * */
    public void editEntry(Applicazione applicazione, int row){

        int voto = 0;
        /** Recupera la tipologia dell'esame (record) selezionato */
        String tipologia_esame = String.valueOf(applicazione.getEsami().get(row).getClass());

        if (row != -1) { // Se la riga ha un indice valido
            int matricola = Integer.parseInt(matricola_tf.getText());
            String nome = nome_tf.getText();
            String cognome = cognome_tf.getText();
            String corso = corso_tf.getText();
            /** Se l'esame è semplice recupero il voto, nel caso fosse composto esso non può essere modificato
             * in quanto calcolato automaticamente con la registrazione delle prove parziali */
            if(tipologia_esame.contains("Semplice"))
                voto = Integer.parseInt(voto_tf.getText());
            boolean lode = lode_cb.isSelected();
            int cfu = Integer.parseInt(cfu_tf.getText());

            /** Recupera lo studente dall'archivio studenti attraverso il metodo ricercaStudente */
            Studente studente = applicazione.ricercaStudente(matricola);
            /** Modifica i dati dello studente e del corrispettivo esame selezionato */
            studente.setMatricola(matricola);
            studente.setNome(nome);
            studente.setCognome(cognome);
            applicazione.getEsami().get(row).setNome(corso);
            applicazione.getEsami().get(row).setVoto(voto);
            applicazione.getEsami().get(row).setLode(lode);
            applicazione.getEsami().get(row).setCfu(cfu);

            if(tipologia_esame.contains("Composto")){ // Se l'esame è composto
                /** Recupera il numero di prove parziali dell'esame (record) selezionato */
                int n_prove = applicazione.getEsami().get(row).getEsami_parziali().size();
                /** Recupero i dati delle prove parziali */
                for(int i=0; i<n_prove; i++){
                    String tipologia_prova =  tipologia_prova_tf[i].getText();
                    int peso = Integer.parseInt(peso_prova_tf[i].getText());
                    int voto_prova = Integer.parseInt(voto_prova_tf[i].getText());

                    /** Modifico i dati delle prove parziali */
                    applicazione.getEsami().get(row).getEsami_parziali().get(i).setNome(tipologia_prova);
                    applicazione.getEsami().get(row).getEsami_parziali().get(i).setPeso(peso);
                    applicazione.getEsami().get(row).getEsami_parziali().get(i).setVoto(voto_prova);
                }
                /** Ricalcolo il voto finale dell'esame composto appena modificato */
                applicazione.getEsami().get(row).voto();
                /** Salvo in una variabile il voto in modo tale da aggiornare il record della tabella a seconda del tipo
                 *  di esame che è stato modificato */
                voto = applicazione.getEsami().get(row).getVoto();
            }
            /** Aggiorno i dati in tabella del record modificato */
            applicazione.getTabella().getDefaultTableModel().setValueAt(matricola, row, 0);
            applicazione.getTabella().getDefaultTableModel().setValueAt(nome, row, 1);
            applicazione.getTabella().getDefaultTableModel().setValueAt(cognome, row, 2);
            applicazione.getTabella().getDefaultTableModel().setValueAt(corso, row, 3);
            applicazione.getTabella().getDefaultTableModel().setValueAt(voto, row, 4);
            applicazione.getTabella().getDefaultTableModel().setValueAt(lode, row, 5);
            applicazione.getTabella().getDefaultTableModel().setValueAt(cfu, row, 6);
        }
    }

    /**
     * deleteEntry
     * Metodo che permette di:
     *  - eliminare un record della tabella
     *  - eliminare un esame dall'archivio esami
     *  - eliminare uno studente dall'archivio studenti
     * @param applicazione permette di gestire gli archivi dati e la tabella
     * */
    public void deleteEntry(Applicazione applicazione) {
        /** Recupera la riga selezionata */
        int selectedRow = applicazione.getTabella().getTable().getSelectedRow();
        if (selectedRow != -1) { // Se la riga ha un valore valido
            /** Rimuove il record selezionato dalla tabella */
            applicazione.getTabella().getDefaultTableModel().removeRow(selectedRow);
            /** Rimuove l'esame del record selezionato dall'archivio esami */
            applicazione.getEsami().delete(applicazione.getEsami().get(selectedRow));
            /** Rimuove lo studente del record selezionato dall'archivio studenti*/
            applicazione.getStudenti().delete(applicazione.getStudenti().get(selectedRow));
        }
    }
}