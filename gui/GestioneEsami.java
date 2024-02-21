package gui;

import classi.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <strong>GestioneEsami</strong>
 * <br>
 * Classe che permette la gestione completa degli esami
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class GestioneEsami{
    /** Frame principale */
    private final JFrame mainFrame;
    /** Frame secondari per la gestione delle procedure */
    private JFrame jFrameSemplice, jFrameComposto, jFrameProve, jFrameInfo;
    /** Pannelli secondari */
    private JPanel jPanelProve, jPanelInfo;
    /** Campi di testo per la registrazione degli esami */
    private JTextField matricola_tf, nome_tf, cognome_tf, corso_tf, voto_tf, cfu_tf;
    /** Campi di testo per la registrazione degli esami parziali */
    private JTextField[] voto_prova_tf, peso_prova_tf;
    /** Checkbox per l'inserimento della lode e la scelta sulla tipologia di esame da registrare */
    private JCheckBox lode_cb, semplice_cb, composto_cb;
    /** Bottoni per la modifica dei dati, la registrazione degli esami e il reset dei campi di testo */
    private JButton modifica_btn, reset_btn, elimina_btn, registra_esame_btn;
    /** ComboBox per l'inserimento del numero di prove parziali di un esame composto */
    private JComboBox<Integer> n_prove_cb;
    /** Array di Combobox per registrare le varie tipologie degli esami parziali registrati  */
    private JComboBox<String>[] tipologia_prova_cb;
    /** Array temporaneo per il salvataggio degli esami parziali registrati */
    private EsameParziale[] datiProve;
    /** Variabile necessaria per tenere traccia dell'ultima modifica effettuata sulla tabella */
    private boolean modificheNonSalvate;

    /**
     * <strong>GestioneEsami</strong>
     * <br>
     * Costruttore che gestisce completamente l'intera sezione per la gestione degli esami
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * @param menuFrame Frame di avvio dell'applicazione
     */
    public GestioneEsami(JFrame menuFrame, Applicazione applicazione) {

        /* Creazione del frame principale per la finestra "Gestione Esami" */
        mainFrame = new JFrame();
        mainFrame.setTitle("Gestione esami");
        mainFrame.setSize(800, 500);
        /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame

        /* Creazione del pannello principale */
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));

        /* Creazione dello scroll per visualizzare tutte le righe della tabella */
        JScrollPane jScrollPane = new JScrollPane(applicazione.getTabella().getTable());

        /* Creazione del pannello per i controlli */
        JPanel controlPanel = new JPanel(new FlowLayout());

        JLabel filtro_l = new JLabel("Filtro:");
        JTextField filtro_tf = new JTextField(10);
        /* Creazione del bottone per calcolare la media dei voti sui filtrati */
        JButton media_btn = new JButton("Media");

        /* Creazione del bottone per generare un grafico sui record filtrati */
        JButton grafico_btn = new JButton("Grafico");

        /* Ascoltatore degli eventi di finestra sulla chiusura del frame principale dell'applicazione */
        menuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (modificheNonSalvate) { // Se non sono state salvate le modifiche effettuate...
                    /* Mostra un messaggio di conferma per avvisare l'utente che ci sono modifiche non salvate */
                    int result = JOptionPane.showConfirmDialog(menuFrame, "Ci sono modifiche non salvate. Vuoi salvarle prima di chiudere?", "Modifiche non salvate", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) { // Se decide di salvare le modifiche...
                        /* Salvataggio delle modifiche */
                        SalvaEsami salvaEsami = new SalvaEsami(menuFrame, applicazione);
                        if(salvaEsami.getIsSaved()) { // Se il salvataggio è avvenuto con successo
                            /* Imposta a false la variabile "modificheNonSalvate" */
                            modificheNonSalvate = false;
                            /* Chiude il frame */
                            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            System.exit(0);
                        }
                    } else if (result == JOptionPane.NO_OPTION) { // Se decide di non salvare le modifiche...
                        /* Chiude il frame principale senza salvare le modifiche */
                        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        System.exit(0);
                    } else {
                        /* Annulla la chiusura del frame */
                        menuFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    /* Chiude il frame se non ci sono modifiche non salvate */
                    menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                    System.exit(0);
                }
            }
        });

        /* Ascoltatore degli eventi sul click del bottone per il calcolo della media dei voti sui record filtrati */
        media_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Chiamata della funzione per il calcolo della media dei voti sui record filtrati */
                if (applicazione.getTabella().getTable().getRowCount() != 0) // Se il campo di testo del filtro non è vuoto
                    /* Mostra la media attraverso un messaggio informativo */
                    JOptionPane.showMessageDialog(mainFrame, "Media pesata dei voti: " + calcolaMedia(applicazione), "Informazione", JOptionPane.INFORMATION_MESSAGE);
                else
                    /* Mostra un messaggio d'errore */
                    JOptionPane.showMessageDialog(mainFrame, "Errore: per ottenere la media è necessario registare almeno un esame", "Tabella vuota", JOptionPane.ERROR_MESSAGE);
            }
        });

        /* Ascoltatore degli eventi per il campo di testo del filtro
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

            /**
             * <strong>filterTable</strong>
             * <br>
             * Metodo per applicare il filtro alla tabella
             * @param applicazione Permette di gestire gli archivi dati e la tabella
             * @param query Stringa da filtrare
             */
            private void filterTable(Applicazione applicazione, String query) {
                /* Creazione di un oggetto di classe TableRowSorter per ordinare e filtrare le righe della tabella */
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(applicazione.getTabella().getDefaultTableModel());
                /* Imposta il TableRowSorter sulla tabella */
                applicazione.getTabella().getTable().setRowSorter(sorter);

                /* Applica un filtro regex (basato su espressione regolare) sulle colonne 0 (Matricola) e 3 (Corso) in base al testo inserito nel campo di testo
                 * "(?i)" è un'espressione regolare che indica al motore di ricerca di ignorare la distinzione tra maiuscole e minuscole (in altre parole, fa il matching case-insensitive)
                 * Il metodo trim() rimuove gli spazi bianchi iniziali e finali dalla stringa inserita
                 */
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query.trim(), 0, 3));
            }
        });

        /* Ascoltatore degli eventi sul click del bottone per la generazione del grafico sui record filtrati */
        grafico_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grafico datiStatistici = new Grafico(applicazione, mainFrame);
            }
        });

        /* Creazione del bottone per aggiungere un nuovo record alla tabella */
        JButton aggiungi_btn = new JButton("Aggiungi");
        /* Creazione del bottone per salvare i record della tabella su file */
        JButton salva_btn = new JButton("Salva");

        /* Ascoltatore degli eventi sul click del bottone per il salvataggio degli esami su file */
        salva_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (applicazione.getTabella().getTable().getRowCount() != 0) {
                    /* Salvataggio degli esami su file */
                    SalvaEsami salvaEsami = new SalvaEsami(mainFrame, applicazione);
                    if(salvaEsami.getIsSaved()) // Se il salvataggio è avvenuto con successo
                        /* Imposta a false la variabile "modificheNonSalvate" */
                        modificheNonSalvate = false;
                } else {
                    int result = JOptionPane.showConfirmDialog(mainFrame, "La tabella è vuota. Vuoi comunque salvarla?", "Informazione", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        /* Salvataggio degli esami su file */
                        SalvaEsami salvaEsami = new SalvaEsami(mainFrame, applicazione);
                        if(salvaEsami.getIsSaved()) // Se il salvataggio è avvenuto con successo
                            /* Imposta a false la variabile "modificheNonSalvate" */
                            modificheNonSalvate = false;
                    }
                }
            }
        });

        controlPanel.add(filtro_l);
        controlPanel.add(filtro_tf);
        controlPanel.add(media_btn);
        controlPanel.add(grafico_btn);
        controlPanel.add(aggiungi_btn);
        controlPanel.add(salva_btn);

        mainPanel.add(jScrollPane);
        mainPanel.add(controlPanel);

        mainFrame.add(mainPanel);

        /* Imposta la generazione del frame principale al centro dello schermo */
        mainFrame.setLocationRelativeTo(null);

        /* Rende visibile il frame principale */
        mainFrame.setVisible(true);

        /* Ascoltatore degli eventi sul click di un campo della tabella */
        applicazione.getTabella().getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrameInfo = new JFrame("Informazioni");
                /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                jPanelInfo = new JPanel();
                jPanelInfo.setLayout(null);
                /* Recupera la riga del campo selezionato */
                int row = applicazione.getTabella().getTable().rowAtPoint(e.getPoint());
                /* Recupera la classe dell'esame selezionato (semplice o composto) */
                String tipologia_esame = String.valueOf(applicazione.getArchivioEsami().get(row).getClass());

                if (tipologia_esame.contains("Semplice")) { // Se la classe dell'esame selezionato è Semplice...
                    /* Recupera l'esame semplice selezionato */
                    EsameSemplice esameSemplice = (EsameSemplice) applicazione.getArchivioEsami().get(row);
                    /* Crea un nuovo frame per mostrare le informazioni relative all'esame selezionato */
                    jFrameInfo.setSize(700, 300);
                    /* Creazione del pannello per contenere gli oggetti necessari a mostrare le informazioni relative all'esame selezionato */
                    jPanelInfo.setPreferredSize(new Dimension(700, 300));
                    /* Richiama il metodo per la visualizzazione e/o modifica delle informazioni dell'esame passato */
                    getFormInfoExam(esameSemplice, applicazione);

                    modifica_btn = new JButton("Modifca");
                    modifica_btn.setBounds(100, 150, 140, 25);
                    elimina_btn = new JButton("Elimina");
                    elimina_btn.setBounds(250,150,140,25);
                    reset_btn = new JButton("Reset");
                    reset_btn.setBounds(400, 150, 140, 25);

                    /* Ascoltatore degli eventi sul click del bottone che permette di modificare i dati di un esame semplice */
                    modifica_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            /* Controlla che i campi siano stati effettivamente compilati prima di procedere */
                            if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || voto_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(jFrameSemplice, "Attenzione: per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.WARNING_MESSAGE);
                            } else {
                                /* Richiama il metodo per la modifica del record della tabella */
                                if(editEntry(applicazione, row)) {
                                    modificheNonSalvate = true;
                                    /* Chiude il frame */
                                    jFrameInfo.dispose();
                                }
                            }
                        }
                    });

                    /* Ascoltatore degli eventi sul click del bottone che permette di resettare i dati di un esame selezionato */
                    reset_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            resetTextField(esameSemplice);
                        }
                    });

                    jFrameInfo.add(jPanelInfo);
                }

                if (tipologia_esame.contains("Composto")) { // Se la classe dell'esame selezionato è Composto
                    /* Recupera l'esame composto selezionato */
                    EsameComposto esameComposto = (EsameComposto) applicazione.getArchivioEsami().get(row);
                    /* Crea un nuovo frame per mostrare le informazioni relative all'esame selezionato */
                    jFrameInfo.setSize(700, 450);
                    jPanelInfo.setPreferredSize(new Dimension(700, 450));

                    /* Richiama il metodo per la visualizzazione e/o modifica delle informazioni dell'esame passato */
                    getFormInfoExam(esameComposto, applicazione);

                    /* Recupero il numero di prove parziali dell'esame selezionato */
                    int n_prove = esameComposto.getEsamiParziali().size();
                    /* Creo l'array di campi di testo per l'inserimento della tipologia delle n_prove parziali relative all'esame composto selezionato */
                    tipologia_prova_cb = new JComboBox[n_prove];
                    /* Creo l'array di campi di testo per l'inserimento dei pesi delle n_prove parziali relative all'esame composto selezionato*/
                    peso_prova_tf = new JTextField[n_prove];
                    /* Creo l'array di campi di testo per l'inserimento dei voti delle n_prove parziali relative all'esame composto selezionato */
                    voto_prova_tf = new JTextField[n_prove];

                    /* Itero le n_prove per la visualizzazione e/o modifica delle informazioni relative agli esami parziali dell'esame composto selezionato */
                    for (int i = 0, y = 170; i < n_prove; i++, y = y + 50) {

                        JLabel tipologia_prova_l = new JLabel("Tipologia prova:");
                        tipologia_prova_l.setBounds(20, y, 150, 20);
                        /* Creazione dell'i-esimo campo di testo per la visualizzazione della tipologia della i-esima prova intermedia */
                        String nome_tipologia = esameComposto.getEsamiParziali().get(i).getNome();
                        /* Mette come prima opzione quella scelta durante la registrazione dell'esame */
                        tipologia_prova_cb[i] = new JComboBox<>(generaOpzioniComboBox(nome_tipologia));
                        tipologia_prova_cb[i].setBounds(150, y, 100, 20);

                        JLabel peso_l = new JLabel("Peso:");
                        peso_l.setBounds(280, y, 100, 20);
                        /* Creazione dell'i-esimo campo di testo per la visualizzazione del peso della i-esima prova intermedia */
                        peso_prova_tf[i] = new JTextField();
                        peso_prova_tf[i].setBounds(330, y, 100, 20);
                        /* Imposta il peso dell'i-esima prova parziale dell'esame composto selezioanto */
                        peso_prova_tf[i].setText(Integer.toString(esameComposto.getEsamiParziali().get(i).getPeso()));

                        JLabel voto_l = new JLabel("Voto:");
                        voto_l.setBounds(450, y, 100, 20);
                        /* Creazione dell'i-esimo campo di testo per la visualizzazione del voto della i-esima prova intermedia */
                        voto_prova_tf[i] = new JTextField();
                        voto_prova_tf[i].setBounds(500, y, 100, 20);
                        /* Imposta il voto dell'i-esima prova parziale dell'esame composto selezioanto */
                        voto_prova_tf[i].setText(Integer.toString(esameComposto.getEsamiParziali().get(i).getVoto()));

                        jPanelInfo.add(tipologia_prova_l);
                        jPanelInfo.add(tipologia_prova_cb[i]);
                        jPanelInfo.add(peso_l);
                        jPanelInfo.add(peso_prova_tf[i]);
                        jPanelInfo.add(voto_l);
                        jPanelInfo.add(voto_prova_tf[i]);
                    }

                    modifica_btn = new JButton("Modifca");
                    elimina_btn = new JButton("Elimina");
                    reset_btn = new JButton("Reset");

                    /* Per gestire la posizione dei bottoni a seconda del numero di prove parziali dell'esame composto */
                    if (n_prove == 2) {
                        modifica_btn.setBounds(100, 280, 140, 25);
                        elimina_btn.setBounds(250,280,140,25);
                        reset_btn.setBounds(400, 280, 140, 25);
                    } else {
                        modifica_btn.setBounds(100, 310, 140, 25);
                        elimina_btn.setBounds(250,310,140,25);
                        reset_btn.setBounds(400, 310, 140, 25);
                    }

                    /* Ascoltatore degli eventi sul click del bottone che permette di modificare le informazioni dell'esame composto selezionato */
                    modifica_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            /* Controlla che i campi siano stati effettivamente compilati prima di procedere */
                            if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || cfu_tf.getText().isEmpty() || !checkCampiProveParziali(n_prove)) {
                                JOptionPane.showMessageDialog(jFrameComposto, "Attenzione: per procedere è necessario compilare tutti i campi o reinserirli correttamente", "Compilazione errata", JOptionPane.WARNING_MESSAGE);
                            } else {
                                /* Richiama il metodo per la modifica del record della tabella */
                                if(editEntry(applicazione, row)){
                                    modificheNonSalvate = true;
                                    /* Chiude il frame */
                                    jFrameInfo.dispose();
                                }
                            }
                        }
                    });

                    /* Ascoltatore degli eventi sul click del bottone che permette di resettare le informazioni dell'esame composto selezionato */
                    reset_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            resetTextField(esameComposto);
                        }
                    });
                }

                /* Ascoltatore degli eventi sul click del bottone che permette di eliminare un esame */
                elimina_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        /* Richiama il metodo per l'eliminazione di un esame */
                        deleteEntry(applicazione);
                        modificheNonSalvate = true;
                        /* Chiude il frame */
                        jFrameInfo.dispose();
                    }
                });

                jPanelInfo.add(modifica_btn);
                jPanelInfo.add(elimina_btn);
                jPanelInfo.add(reset_btn);
                jFrameInfo.add(jPanelInfo);
            }
        });

        /* Ascoltatore degli eventi sul click del bottone che permette di aggiungere un record nella tabella */
        aggiungi_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Richiama il metodo per l'inserimento delle informazioni di un esame semplice */
                getFormSimpleExam(applicazione);
            }
        });
    }

    /**
     * <strong>checkLodeAddEntry</strong>
     * <br>
     * Metodo che permette di controllare la corretta assegnazione della lode durante la creazione di un esame composto <br>
     * Più precisamente controlla se l'assegnazione della lode è coerente con i voti delle prove parziali
     * @param n_prove Numero di prove parziali
     * @return True se l'assegnazione della lode è legittima, False altrimenti
     */
    public boolean checkLodeAddEntry(int n_prove){
        for(int i=0; i<n_prove; i++)
            if(datiProve[i].getVoto() < 30)
                return false;
        return true;
    }

    /**
     * <strong>checkLodeEditEntry</strong>
     * <br>
     * Metodo che permette di controllare la corretta assegnazione della lode durante la modifica di un esame composto <br>
     * Più precisamente controlla se l'assegnazione della lode è coerente con i voti delle prove parziali
     * @param esame Esame da controllare
     * @return True se l'assegnazione della lode è legittima, False altrimenti
     */
    public boolean checkLodeEditEntry(Esame esame){
        for(int i=0; i<esame.getEsamiParziali().size(); i++)
            if(esame.getEsamiParziali().get(i).getVoto() < 30)
                return false;
        return true;
    }

    /**
     * <strong>riempimentoAutomatico</strong>
     * <br>
     * Metodo che una volta inserita la matricola, se già presente nell'archivio,
     * riempie automaticamente il campo nome e cognome dello studente
     * @param applicazione Verifica l'esistenza della matricola inserita
     * @param matricola Matricola da cui effettuare la ricerca
     */
    public void riempimentoAutomatico(Applicazione applicazione, int matricola){
        Studente studente = applicazione.ricercaStudente(matricola);
        if(studente != null){
            nome_tf.setText(studente.getNome());
            cognome_tf.setText(studente.getCognome());
        } else{
            nome_tf.setText("");
            cognome_tf.setText("");
        }
    }

    /**
     * <strong>generaOpzioniComboBox</strong>
     * <br>
     * Metodo che si occupa di generare le opzioni delle ComboBox per la tipologia della prova parziale
     * in modo tale da mettere come prima quella scelta durante la registrazione della prova parziale
     * @param nomeTipologia Nome della tipologia scelta durante la registrazione della prova parziale
     * @return Array di stringhe contenente le opzioni della ComboBox nell'ordine corretto
     */
    public String[] generaOpzioniComboBox(String nomeTipologia){
        if(nomeTipologia.equals("Scritta"))
            return new String[]{"Scritta", "Orale", "Pratica"};
        else if (nomeTipologia.equals("Orale"))
            return new String[]{"Orale", "Scritta", "Pratica"};
        else
            return new String[]{"Pratica", "Scritta", "Orale"};
    }

    /**
     * <strong>calcolaMedia</strong>
     * <br>
     * Metodo che restituisce la media pesata dei voti in relazione ai record filtrati nella tabella
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * @return Media pesata dei voti
     */
    public double calcolaMedia(Applicazione applicazione){

        int somma = 0;
        int voto;
        /* Recupero il numero di record filtrati nella tabella */
        int rowCount = applicazione.getTabella().getTable().getRowCount();

        /* Se non ci sono righe nella tabella, mostra una media di */
        if (rowCount == 0) {
            return 0.0;
        }

        /* Somma tutti i voti presenti nella colonna "Voti" */
        for (int i = 0; i < rowCount; i++) {
            Object value = applicazione.getTabella().getTable().getValueAt(i, 4); // La colonna "Voti" è la colonna 4
            if(value != null && !value.toString().isEmpty()) {
                voto = Integer.parseInt(value.toString());
                somma += voto;
            }
        }

        /* Restituisce la media arrotondando il numero */
        return  (double) somma / rowCount;
    }

    /**
     * <strong>checkCampiProveParziali</strong>
     * <br>
     * Metodo che controlla il corretto inserimento dei dati delle prove parziali
     * @param n_prove Numero di prove parziali
     * @return True se i campi sono compilati correttamente, False altrimenti
     */
    public boolean checkCampiProveParziali(int n_prove){
        for(int i=0; i<n_prove; i++){
            if(peso_prova_tf[i].getText().isEmpty() || voto_prova_tf[i].getText().isEmpty())
                return false;
            if(Integer.parseInt(peso_prova_tf[i].getText()) < 1 || Integer.parseInt(peso_prova_tf[i].getText()) > 99)
                return false;
            if(Integer.parseInt(voto_prova_tf[i].getText()) < 18 || Integer.parseInt(voto_prova_tf[i].getText()) > 30)
                return false;
        }
        return true;
    }

    /**
     * <strong>checkInserimentiNumerici</strong>
     * <br>
     * Metodo che verifica il corretto inserimento dei campi di testo numerici
     * @return True se i campi sono validi, False altrimenti
     */
    public boolean checkInserimentiNumerici(){
        return Integer.parseInt(cfu_tf.getText()) >= 1 && (Integer.parseInt(voto_tf.getText()) >= 18 && Integer.parseInt(voto_tf.getText()) <= 30);
    }

    /**
     * <strong>checkTextField2</strong>
     * <br>
     * Metodo che controlla il corretto inserimento dei campi di testo letterali
     * @param jTextField Campo di testo da controllare
     */
    public void checkTextField2(JTextField jTextField){
        String text = jTextField.getText();
        /* Crea un pattern per cercare un singolo numero nel testo */
        Pattern pattern = Pattern.compile("\\d"); // regex: qualsiasi cifra decimale (0 - 9)
        /* Crea un matcher per il pattern e il testo */
        Matcher matcher = pattern.matcher(text);
        /* Controlla se il matcher trova almeno una corrispondenza nel testo */
        if (matcher.find()) {
            /* Se il testo contiene un numero, genera un errore */
            JOptionPane.showMessageDialog(null, "Errore: il campo accetta solo caratteri letterali", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
            /* Permettte di eseguire un'azione nell'Event Dispatch Thread (EDT) che è il thread principale che gestisce gli eventi dell'interfaccia utente
             * In questo modo il codice esegue in modo sicuro e thread-safe (per prevenire problemi di concorrenza)
             * Prima rimuove l'ultimo carattere dalla stringa (ossia quello che ha generato l'errore) poi imposta il testo nel textField */
            SwingUtilities.invokeLater(() -> {
                if(!text.isEmpty())
                    jTextField.setText(text.substring(0, text.length() - 1));
            });
        }
    }

    /**
     * <strong>checkTextField</strong>
     * <br>
     * Metodo che controlla il corretto inserimento dei campi di testo numerici <br>
     * Elimina il carattere che ha generato l'errore
     * @param jTextField Campo di testo da controllare
     */
    public void checkTextField(JTextField jTextField){
        String text = jTextField.getText();
        if (!text.isEmpty()) { // Controlla se il campo non è vuoto
            try {
                Integer.parseInt(text); // Tentativo di conversione in intero
            } catch (NumberFormatException ex) {
                /* Il testo inserito non è un numero, mostra il messaggio di errore */
                JOptionPane.showMessageDialog(null, "Errore: il campo accetta solo caratteri numerici", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                /* Permette di eseguire un'azione nell'Event Dispatch Thread (EDT) che è il thread principale che gestisce gli eventi dell'interfaccia utente
                 * In questo modo il codice esegue in modo sicuro e thread-safe (per prevenire problemi di concorrenza)
                 * Prima rimuove l'ultimo carattere dalla stringa (ossia quello che ha generato l'errore) poi imposta il testo nel textField */
                SwingUtilities.invokeLater(() -> {
                    jTextField.setText(text.substring(0, text.length() - 1));
                });
            }
        }
    }

    /**
     * <strong>getFormInfoExam</strong>
     * <br>
     * Metodo che crea la parte grafica per la visualizzazione e/o modifica dei dati relativi ad un esame
     * @param esame Esame da registrare, necessario per identificare la sua tipologia (semplice o composto)
     */
    public void getFormInfoExam(Esame esame, Applicazione applicazione){

        String tipologia_esame = String.valueOf(esame.getClass());

        JLabel matricola_l = new JLabel("Matricola:");
        matricola_l.setBounds(200, 20, 100,20);
        matricola_tf = new JTextField();
        matricola_tf.setBounds(280,20,100,20);
        matricola_tf.setText(Integer.toString(esame.getStudente().getMatricola()));

        /* Verifica se il campo di testo della matricola è composto solo ed esclusivamente da numeri */
        matricola_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(matricola_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel nome_l = new JLabel("Nome:");
        nome_l.setBounds(20, 60, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(70, 60, 100, 20);
        nome_tf.setText(esame.getStudente().getNome());

        /* Verifica che il nome dello studente non contenga numeri */
        nome_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField2(nome_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        /* Gestisce ed effettua, se possibile, il riempimento automatico del nome dello studente  */
        nome_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!Objects.equals(matricola_tf.getText(), "")) {
                    int matricola = Integer.parseInt(matricola_tf.getText());
                    riempimentoAutomatico(applicazione, matricola);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        JLabel cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 60, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(280, 60, 100, 20);
        cognome_tf.setText(esame.getStudente().getCognome());

        /* Verifica che il cognome dello studente non contenga numeri */
        cognome_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField2(nome_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel corso_l = new JLabel("Corso:");
        corso_l.setBounds(410, 60, 100, 20);
        corso_tf = new JTextField();
        corso_tf.setBounds(490, 60, 100, 20);
        corso_tf.setText(esame.getNome());

        JLabel voto_l = new JLabel("Voto:");
        voto_l.setBounds(20, 100, 100, 20);
        voto_tf = new JTextField();
        voto_tf.setBounds(70, 100, 100, 20);
        voto_tf.setText(Integer.toString(esame.getVoto()));

        /* Verifica che il campo di testo del voto sia composto solo ed esclusivamente da numeri */
        voto_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(voto_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        /* Se l'esame è composto nego la possibilità di modificare il voto
         * in quanto esso viene calcolato automaticamente con l'inserimento delle prove parziali */
        if(tipologia_esame.contains("Composto")){
            voto_tf.setEditable(false);
            voto_tf.setEnabled(false);
        }

        JLabel lode_l = new JLabel("Lode:");
        lode_l.setBounds(200, 100, 100, 20);
        lode_cb = new JCheckBox();
        lode_cb.setBounds(280, 100, 100, 20);
        lode_cb.setSelected(esame.getLode());

        /* Verifica se l'assegnazione della lode è legittima o meno */
        lode_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lode_cb.isSelected() && Integer.parseInt(voto_tf.getText()) < 30 && tipologia_esame.contains("Semplice")){
                    JOptionPane.showMessageDialog(jFrameInfo, "Errore: non è possibile assegnare la lode con un voto inferiore a 30", "Errore", JOptionPane.ERROR_MESSAGE);
                    lode_cb.setSelected(false);
                }
            }
        });

        JLabel cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(410, 100, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(490, 100, 100, 20);
        cfu_tf.setText(Integer.toString(esame.getCfu()));

        /* Verifica se il campo di testo dei cfu è composto solo ed esclusivamente da numeri */
        cfu_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(cfu_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

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

        /* Imposta la generazione del frame principale al centro dello schermo */
        jFrameInfo.setLocationRelativeTo(null);
        /* Rende visibile il frame per la visualizzazione e/o modifica dei dati di un esame */
        jFrameInfo.setVisible(true);
    }

    /**
     * <strong>getFormSimpleExam</strong>
     * <br>
     * Metodo che crea la parte grafica per la registrazione di un esame semplice
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public void getFormSimpleExam(Applicazione applicazione){

        jFrameSemplice = new JFrame("Registrazione Esame Semplice");
        jFrameSemplice.setSize(700, 350);
        JPanel jPanelSemplice = new JPanel();
        /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
        jFrameSemplice.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
        jPanelSemplice.setPreferredSize(new Dimension(700, 350));
        jPanelSemplice.setLayout(null);

        JLabel tipologia_l = new JLabel("Tipologia esame:");
        tipologia_l.setBounds(150,20, 150,20);
        semplice_cb = new JCheckBox("Semplice");
        semplice_cb.setBounds(280,20, 100, 20);
        composto_cb = new JCheckBox("Composto");
        composto_cb.setBounds(380,20, 100, 20);
        /* Imposto la checkbox a true poichè il frame si apre direttamente con la form per l'inserimento di un esame semplice */
        semplice_cb.setSelected(true);

        /* Gestisce lo scambio di form (da semplice a composto) */
        composto_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameSemplice.dispose();
                getFormComposedExam(applicazione);
            }
        });

        JLabel matricola_l = new JLabel("Matricola:");
        matricola_l.setBounds(230, 60, 100,20);
        matricola_tf = new JTextField();
        matricola_tf.setBounds(310,60,100,20);

        /* Verifica se il campo di testo della matricola è composto solo ed esclusivamente da numeri */
        matricola_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(matricola_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel nome_l = new JLabel("Nome:");
        nome_l.setBounds(60, 100, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(110, 100, 100, 20);

        /* Verifica che il nome dello studente non contenga numeri */
        nome_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField2(nome_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        /* Gestisce ed effettua, se possibile, il riempimento automatico del nome dello studente  */
        nome_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!Objects.equals(matricola_tf.getText(), "")) {
                    // Il testo inserito non è un numero, mostra il messaggio di errore
                    int matricola = Integer.parseInt(matricola_tf.getText());
                    riempimentoAutomatico(applicazione, matricola);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        JLabel cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(230, 100, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(310, 100, 100, 20);

        /* Verifica che il cognome dello studente non contenga numeri */
        cognome_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField2(cognome_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel corso_l = new JLabel("Corso:");
        corso_l.setBounds(420, 100, 100, 20);
        corso_tf = new JTextField();
        corso_tf.setBounds(470, 100, 100, 20);

        JLabel voto_l = new JLabel("Voto:");
        voto_l.setBounds(60, 140, 100, 20);
        voto_tf = new JTextField();
        voto_tf.setBounds(110, 140, 100, 20);

        /* Verifica che il campo di testo del voto contenga solo ed esclusivamente numeri  */
        voto_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(voto_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Non necessario per campi di testo non formattati come JTextField
            }
        });

        JLabel lode_l = new JLabel("Lode:");
        lode_l.setBounds(230, 140, 100, 20);
        lode_cb = new JCheckBox();
        lode_cb.setBounds(310, 140, 100, 20);

        /* Verifica se l'assegnazione della lode è legittima o meno */
        lode_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lode_cb.isSelected() && Integer.parseInt(voto_tf.getText()) < 30){
                    JOptionPane.showMessageDialog(jFrameSemplice, "Errore: non è possibile assegnare la lode con un voto inferiore a 30", "Errore", JOptionPane.ERROR_MESSAGE);
                    lode_cb.setSelected(false);
                }
            }
        });

        JLabel cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(420, 140, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(470, 140, 100, 20);

        /* Verifica che il campo di testo dei cfu sia composto solo ed esclusivamente da numeri */
        cfu_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(cfu_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Non necessario per campi di testo non formattati come JTextField
            }
        });

        registra_esame_btn = new JButton("Registra Esame");
        registra_esame_btn.setBounds(250,200,150,25);

        registra_esame_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || voto_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jFrameSemplice, "Attenzione: per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.WARNING_MESSAGE);
                } else {
                    /* Richiama il metodo per l'aggiunta di un nuovo record nella tabella */
                    if(addEntry(applicazione))
                        modificheNonSalvate = true;
                    else
                        jFrameSemplice.dispose();
                }
            }
        });

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

        /* Imposta la generazione del frame principale al centro dello schermo */
        jFrameSemplice.setLocationRelativeTo(null);
        /* Rende visibile il frame per la registrazione di un esame semplice */
        jFrameSemplice.setVisible(true);
    }

    /**
     * <strong>getFormComposedExam</strong>
     * <br>
     * Metodo che crea la parte grafica per la registrazione di un esame composto
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public void getFormComposedExam(Applicazione applicazione){
        /* Creazione del frame per la registrazione di un esame composto */
        jFrameComposto = new JFrame("Registrazione Esame Composto");
        jFrameComposto.setSize(700, 350);
        /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
        jFrameComposto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
        JPanel jPanelComposto = new JPanel();
        jPanelComposto.setLayout(null);
        jPanelComposto.setPreferredSize(new Dimension(700, 350));

        JLabel tipologia_l = new JLabel("Tipologia esame:");
        tipologia_l.setBounds(150, 20, 150, 20);
        semplice_cb = new JCheckBox("Semplice");
        semplice_cb.setBounds(280, 20, 100, 20);
        composto_cb = new JCheckBox("Composto");
        composto_cb.setBounds(380, 20, 100, 20);
        composto_cb.setSelected(true);

        /* Gestisce lo scambio di form (da composto a semplice) */
        semplice_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameComposto.dispose();
                getFormSimpleExam(applicazione);
            }
        });

        JLabel matricola_l = new JLabel("Matricola:");
        matricola_l.setBounds(200, 60, 100, 20);
        matricola_tf = new JTextField();
        matricola_tf.setBounds(280, 60, 100, 20);

        /* Verifico se il campo di testo della matricola è composto solo ed esclusivamente da numeri */
        matricola_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(matricola_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel nome_l = new JLabel("Nome:");
        nome_l.setBounds(30, 100, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(80, 100, 100, 20);

        /* Verifica che il nome dello studente non contenga numeri */
        nome_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField2(nome_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        /* Gestisce ed effettua, se possibile, il riempimento automatico del nome dello studente  */
        nome_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!Objects.equals(matricola_tf.getText(), "")) {
                    int matricola = Integer.parseInt(matricola_tf.getText());
                    riempimentoAutomatico(applicazione, matricola);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        JLabel cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 100, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(280, 100, 100, 20);

        /* Verifica che il cognome dello studente non contenga numeri */
        cognome_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField2(cognome_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel corso_l = new JLabel("Corso:");
        corso_l.setBounds(400, 100, 100, 20);
        corso_tf = new JTextField();
        corso_tf.setBounds(470, 100, 100, 20);

        JLabel lode_l = new JLabel("Lode:");
        lode_l.setBounds(30, 140, 100, 20);
        lode_cb = new JCheckBox();
        lode_cb.setBounds(80, 140, 100, 20);

        JLabel cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(200, 140, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(280, 140, 100, 20);

        /* Verifico se il campo di testo dei cfu è composto solo ed esclusivamente da numeri */
        cfu_tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTextField(cfu_tf);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JLabel n_prove_l = new JLabel("N. prove:");
        n_prove_l.setBounds(400, 140, 100, 20);

        /* Numero di prove parziali registrabili per un esame composto */
        Integer[] options = {2, 3};
        n_prove_cb = new JComboBox<>(options);
        n_prove_cb.setBounds(470, 140, 100, 20);

        /* Ascoltatore degli eventi per la combobox sul numero di prove parziali che si vogliono registrare */
        n_prove_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Verifica che non ci siano campi vuoti */
                if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jFrameComposto, "Attenzione: per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.WARNING_MESSAGE);
                } else{
                    /* Creazione del frame per la registrazione delle prove parziali */
                    jFrameProve = new JFrame("Registrazione Prove Parziali");
                    jFrameProve.setSize(700, 300);
                    /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                    jFrameProve.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                    jPanelProve = new JPanel();
                    jPanelProve.setLayout(null);
                    jPanelProve.setPreferredSize(new Dimension(700, 300));

                    /* Recupero il numero di prove parziali registrate */
                    Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();

                    /* Creazione di un array di classe TipologiaProva che andrà a contenere temporaneamente le prove parziali registrate */
                    datiProve = new EsameParziale[selectedValue];

                    /* Creazione degli oggetti per la form di inserimento dei dati relativi ad una prova parziale */
                    String[] options = {"Scritta", "Orale", "Pratica"};
                    tipologia_prova_cb = new JComboBox[3];
                    peso_prova_tf = new JTextField[selectedValue];
                    voto_prova_tf = new JTextField[selectedValue];

                    /* Itero gli oggetti per l'inserimento della prova parziale n_prove-volte */
                    for (int i = 0, y = 20; i < selectedValue; i++, y = y + 50) {
                        JLabel tipologia_prova_l = new JLabel("Tipologia prova:");
                        tipologia_prova_l.setBounds(20, y, 150, 20);
                        tipologia_prova_cb[i] = new JComboBox<>(options);
                        tipologia_prova_cb[i].setBounds(150, y, 100, 20);
                        JLabel peso_l = new JLabel("Peso:");
                        peso_l.setBounds(280, y, 100, 20);
                        peso_prova_tf[i] = new JTextField();
                        peso_prova_tf[i].setBounds(330, y, 100, 20);

                        JLabel voto_l = new JLabel("Voto:");
                        voto_l.setBounds(450, y, 100, 20);
                        voto_prova_tf[i] = new JTextField();
                        voto_prova_tf[i].setBounds(500, y, 100, 20);

                        jPanelProve.add(tipologia_prova_l);
                        jPanelProve.add(tipologia_prova_cb[i]);
                        jPanelProve.add(peso_l);
                        jPanelProve.add(peso_prova_tf[i]);
                        jPanelProve.add(voto_l);
                        jPanelProve.add(voto_prova_tf[i]);
                    }

                    registra_esame_btn = new JButton("Registra Esame");
                    /* Per gestire la posizione dei bottoni a seconda del numero di prove parziali dell'esame composto */
                    if(selectedValue == 2)
                        registra_esame_btn.setBounds(260, 130, 150, 25);
                    else
                        registra_esame_btn.setBounds(260,160,150,25);

                    /* Ascoltatore degli eventi sul click del bottone per la registrazione di un esame composto */
                    registra_esame_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            boolean campi_compilati = true;
                            int somma = 0;
                            /* Verifica se i campi relatvi alle prove parziali sono stati compilati o meno */
                            for(int i=0; i<selectedValue; i++){
                                somma += Integer.parseInt(peso_prova_tf[i].getText());
                                if(peso_prova_tf[i].getText().isEmpty() && voto_prova_tf[i].getText().isEmpty())
                                    campi_compilati = false;
                            }
                            if (!campi_compilati)
                                JOptionPane.showMessageDialog(jFrameProve, "Attenzione: per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.WARNING_MESSAGE);
                            else if (somma > 100)
                                JOptionPane.showMessageDialog(jFrameProve, "Errore: la somma dei pesi inseriti non è valida", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                            else{
                                /* Recupera i dati dagli oggetti grafici */
                                for(int i=0; i<selectedValue; i++){
                                    String nome = (String) tipologia_prova_cb[i].getSelectedItem();
                                    int peso = Integer.parseInt(peso_prova_tf[i].getText());
                                    int voto = Integer.parseInt(voto_prova_tf[i].getText());
                                    /* Salva i dati in questo array temporaneo */
                                    datiProve[i] = new EsameParziale(nome,peso,voto);
                                }
                                /* Richiama il metodo per l'aggiunta di un nuovo record nella tabella */
                                if(addEntry(applicazione))
                                    modificheNonSalvate = true;
                                else{
                                    jFrameComposto.dispose();
                                    jFrameProve.dispose();
                                }
                            }
                        }
                    });

                    jPanelProve.add(registra_esame_btn);
                    jFrameProve.add(jPanelProve);

                    /* Imposta la generazione del frame per la registrazione delle prove parziali al centro dello schermo */
                    jFrameProve.setLocationRelativeTo(null);
                    /* Rende visibile il frame per la registrazione delle prove parziali*/
                    jFrameProve.setVisible(true);
                }
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
        /* Imposta la generazione del frame per la registrazione di un esame composto al centro dello schermo */
        jFrameComposto.setLocationRelativeTo(null);
        /* Rende visibile il frame per la registrazione di un esame composto */
        jFrameComposto.setVisible(true);
    }

    /**
     * <strong>addEntry</strong>
     * <br>
     * Metodo che permette di: <br>
     *  - aggiungere un nuovo record alla tabella <br>
     *  - aggiungere un nuovo studente nell'archivio studenti <br>
     *  - aggiungere un nuovo esame nell'archivio esami
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * @return True se la registrazione è andata a buon fine, False altrimenti
     */
    public boolean addEntry(Applicazione applicazione){
        Studente studente;
        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        String corso = corso_tf.getText();
        boolean lode = lode_cb.isSelected();
        int cfu = Integer.parseInt(cfu_tf.getText());

        /* Verifica se lo studente è gia registrato */
        studente = applicazione.ricercaStudente(matricola);
        if(studente == null){ // Nel caso in cui non lo fosse
            studente = new Studente(matricola, nome, cognome);
            /* Aggiunge lo studente nell'archivio studenti */
            applicazione.getArchivioStudenti().add(studente);
        }
        if (semplice_cb.isSelected()) { // Se viene selezionata la checkbox dell'esame semplice
            int voto = Integer.parseInt(voto_tf.getText());
            /* Verifica il corretto inserimento dei campi numerici */
            if(checkInserimentiNumerici()){
                EsameSemplice esameSemplice = new EsameSemplice(studente, corso, voto, lode, cfu);
                /* Verifico se l'esame appena compilato è gia stato registrato o meno */
                if(applicazione.checkEsistenzaEsame(esameSemplice)){
                    JOptionPane.showMessageDialog(jFrameSemplice, "Errore: questo esame è già stato registrato", "Errore", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else{
                    /* Aggiunge l'esame semplice nell'archivio esami */
                    applicazione.getArchivioEsami().add(esameSemplice);
                    /* Aggiunge il nuovo record nella tabella */
                    applicazione.getTabella().getDefaultTableModel().addRow(new Object[]{matricola, nome, cognome, corso, voto, lode, cfu});
                    /* Chiude il frame */
                    jFrameSemplice.dispose();
                }
            } else{
                JOptionPane.showMessageDialog(jFrameSemplice, "Errore: i valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(composto_cb.isSelected()) { // Se viene selezionata la checkbox dell'esame composto
            /* Recupero il numero di prove parziali */
            Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();
            /* Controlla se l'assegnazione della lode è coerente con i voti delle prove parziali */
            if (lode && !checkLodeAddEntry(selectedValue)){
                JOptionPane.showMessageDialog(null, "Errore: non è possibile assegnare la lode con dei voti inferiori a 30", "Errore", JOptionPane.ERROR_MESSAGE);
                lode = false;
            }
            EsameComposto esameComposto = new EsameComposto(studente, corso, lode, cfu);

            /* Verifico se l'esame appena compilato è gia stato registrato o meno */
            if(applicazione.checkEsistenzaEsame(esameComposto)){
                JOptionPane.showMessageDialog(jFrameProve, "Errore: questo esame è già stato registrato", "Errore", JOptionPane.ERROR_MESSAGE);
                return false;
            } else{
                if (checkCampiProveParziali(selectedValue) && cfu > 1) {
                    /* Registro le prove parziali recuperandole dall'array temporaneo creato durante la loro registrazione */
                    for (int i = 0; i < selectedValue; i++) {
                        esameComposto.getEsamiParziali().add(datiProve[i]);
                    }
                    /* Calcolo il voto finale dell'esame composto da più prove parziali */
                    esameComposto.calcolaVoto();
                    /* Aggiunge lo studente nell'archivio studenti */
                    applicazione.getArchivioStudenti().add(studente);
                    /* Aggiunge l'esame composto nell'archivio esami */
                    applicazione.getArchivioEsami().add(esameComposto);
                    /* Aggiunge il nuovo record nella tabella */
                    applicazione.getTabella().getDefaultTableModel().addRow(new Object[]{matricola, nome, cognome, corso, esameComposto.getVoto(), lode, cfu});
                    /* Chiusura dei frame */
                    jFrameComposto.dispose();
                    jFrameProve.dispose();
                } else{
                    JOptionPane.showMessageDialog(jFrameComposto, "Errore: i valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return true;
    }

    /**
     * <strong>editEntry</strong>
     * <br>
     * Metodo che permette di: <br>
     *  - modificare i dati del record selezionato <br>
     *  - salvare le modifiche nei relativi archivi studenti e/o esame
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * @param row Riga selezionata
     * @return True se la modifica dei dati è amdata a buon fine, False altrimenti
     */
    public boolean editEntry(Applicazione applicazione, int row){
        int voto = 0;
        /* Recupera la tipologia dell'esame (record) selezionato */
        String tipologia_esame = String.valueOf(applicazione.getArchivioEsami().get(row).getClass());

        if (row != -1) { // Se la riga ha un indice valido
            int matricola = Integer.parseInt(matricola_tf.getText());
            String nome = nome_tf.getText();
            String cognome = cognome_tf.getText();
            String corso = corso_tf.getText();
            /* Se l'esame è semplice recupero il voto, nel caso fosse composto esso non può essere modificato
             * in quanto calcolato automaticamente con la registrazione delle prove parziali */
            if(tipologia_esame.contains("Semplice"))
                voto = Integer.parseInt(voto_tf.getText());
            boolean lode = lode_cb.isSelected();
            int cfu = Integer.parseInt(cfu_tf.getText());

            /* Controlla se il voto e i cfu hanno un valore valido */
            if(checkInserimentiNumerici()){
                /* Recupera lo studente dall'archivio attraverso il metodo ricercaStudente */
                Studente studente_da_modificare = applicazione.ricercaStudente(applicazione.getArchivioEsami().get(row).getStudente().getMatricola());

                /* Modifica i dati dello studente e del corrispettivo esame selezionato */
                studente_da_modificare.setMatricola(matricola);
                studente_da_modificare.setNome(nome);
                studente_da_modificare.setCognome(cognome);

                applicazione.getArchivioEsami().get(row).setNome(corso);
                applicazione.getArchivioEsami().get(row).setVoto(voto);
                applicazione.getArchivioEsami().get(row).setLode(lode);
                applicazione.getArchivioEsami().get(row).setCfu(cfu);
            } else{
                JOptionPane.showMessageDialog(jFrameSemplice, "Errore: i valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if(tipologia_esame.contains("Composto")) { // Se l'esame è composto
                /* Recupera il numero di prove parziali dell'esame (record) selezionato */
                int n_prove = applicazione.getArchivioEsami().get(row).getEsamiParziali().size();
                if (checkCampiProveParziali(n_prove) && cfu > 0) {
                    /* Recupera i dati delle prove parziali */
                    for (int i = 0; i < n_prove; i++) {
                        String tipologia_prova = (String) tipologia_prova_cb[i].getSelectedItem();
                        int peso = Integer.parseInt(peso_prova_tf[i].getText());
                        int voto_prova = Integer.parseInt(voto_prova_tf[i].getText());

                        /* Modifica i dati delle prove parziali */
                        applicazione.getArchivioEsami().get(row).getEsamiParziali().get(i).setNome(tipologia_prova);
                        applicazione.getArchivioEsami().get(row).getEsamiParziali().get(i).setPeso(peso);
                        applicazione.getArchivioEsami().get(row).getEsamiParziali().get(i).setVoto(voto_prova);
                    }
                    /* Vereifica, nel caso in cui la lode sia stata assegnata, se è legittima con i voti delle prove parziali */
                    if (lode && !checkLodeEditEntry(applicazione.getArchivioEsami().get(row))){
                        JOptionPane.showMessageDialog(null, "Errore: non è possibile assegnare la lode con dei voti inferiori a 30", "Errore", JOptionPane.ERROR_MESSAGE);
                        lode = false;
                    }
                    applicazione.getArchivioEsami().get(row).setLode(lode);

                    /* Ricalcola il voto finale dell'esame composto appena modificato */
                    applicazione.getArchivioEsami().get(row).calcolaVoto();
                } else {
                    JOptionPane.showMessageDialog(jFrameSemplice, "Errore: i valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        applicazione.caricaTabella();
        return true;
    }

    /**
     * <strong>deleteEntry</strong>
     * <br>
     * Metodo che permette di: <br>
     *  - eliminare un record della tabella <br>
     *  - eliminare un esame dall'archivio esami <br>
     *  - eliminare uno studente dall'archivio studenti
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public void deleteEntry(Applicazione applicazione) {
        /* Recupera la riga selezionata */
        int selectedRow = applicazione.getTabella().getTable().getSelectedRow();
        if (selectedRow != -1) { // Se la riga ha un valore valido
            /* Verifica se posso eliminare anche lo studente */
            if(applicazione.checkEliminaStudente(applicazione.getArchivioEsami().get(selectedRow).getStudente().getMatricola()))
                applicazione.getArchivioStudenti().delete(applicazione.getArchivioStudenti().get(selectedRow));
            /* Rimuove l'esame del record selezionato dall'archivio esami */
            applicazione.getArchivioEsami().delete(applicazione.getArchivioEsami().get(selectedRow));
            /* Rimuove il record selezionato dalla tabella */
            applicazione.getTabella().getDefaultTableModel().removeRow(selectedRow);
        }
    }

    /**
     * <strong>resetTextField</strong>
     * <br>
     * Metodo che permette di resettare i campi di testo di un esame
     * @param esame Esame di cui resettare i campi
     */
    public void resetTextField(Esame esame) {
        String tipologia_esame = String.valueOf(esame.getClass());
        matricola_tf.setText("");
        nome_tf.setText("");
        cognome_tf.setText("");
        corso_tf.setText("");
        voto_tf.setText("");
        lode_cb.setSelected(false);
        cfu_tf.setText("");
        if (tipologia_esame.contains("Composto")) {
            for (int i = 0; i < esame.getEsamiParziali().size(); i++) {
                tipologia_prova_cb[i].setSelectedItem("Scritta");
                peso_prova_tf[i].setText("");
                voto_prova_tf[i].setText("");
            }
        }
    }
}