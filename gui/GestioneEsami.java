package gui;

import classi.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

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
    /** Oggetto necessario per il salvataggio degli esami su file */
    private SalvaEsami salvaEsami;

    /**
     * <strong>GestioneEsami</strong>
     * <br>
     * Costruttore che gestisce completamente l'intera sezione per la gestione degli esami
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public GestioneEsami(Applicazione applicazione) {

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

        /* Aggiungo un listener per tracciare le modifiche sulla tabella
         *  Il listener è definito tramite una lambda expression che imposta la variabile booleana modificheNonSalvate
         * su true ogni volta che viene rilevata una modifica al modello di tabella
         */
        applicazione.getTabella().getDefaultTableModel().addTableModelListener(e -> modificheNonSalvate = true);

        /* Ascoltatore degli eventi di finestra per il frame principale */
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (modificheNonSalvate) { // Se non sono state salvate le modifiche già effettuate...
                    /* Mostra un messaggio di conferma per avvisare l'utente che ci sono modifiche non salvate prima di chiudere il frame principale */
                    int result = JOptionPane.showConfirmDialog(mainFrame, "Ci sono modifiche non salvate. Vuoi salvarle prima di chiudere?", "Modifiche non salvate", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) { // Se decide di salvare le modifiche...
                        /* Salvataggio delle modifiche */
                        salvaEsami = new SalvaEsami(mainFrame, applicazione);
                        /* Imposta a false la variabile "modificheNonSalvate" */
                        modificheNonSalvate = false;
                        /* Chiude il frame */
                        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } else if (result == JOptionPane.NO_OPTION) { // Se decide di non salvare le modifiche...
                        /* Chiude il frame principale senza salvare le modifiche */
                        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } else {
                        /* Annulla la chiusura del frame */
                        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    /* Chiude il frame se non ci sono modifiche non salvate */
                    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                }
            }
        });

        /* Ascoltatore degli eventi sul click del bottone per il calcolo della media dei voti sui record filtrati */
        media_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Chiamata della funzione per il calcolo della media dei voti sui record filtrati */
                if (applicazione.getTabella().getTable().getRowCount() != 0) // Se il campo di testo del filtro non è vuoto...
                    /* Mostra la media attraverso un messaggio informativo */
                    JOptionPane.showMessageDialog(mainFrame, "Media pesata dei voti: " + calcolaMedia(applicazione), "Informazione", JOptionPane.INFORMATION_MESSAGE);
                else
                    /* Mostra un messaggio d'errore */
                    JOptionPane.showMessageDialog(mainFrame, "Per ottenere la media è necessario registare almeno un esame", "Tabella vuota", JOptionPane.ERROR_MESSAGE);
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
             * @param query        Stringa da filtrare
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
                    salvaEsami = new SalvaEsami(mainFrame, applicazione);
                    /* Imposta a false la variabile "modificheNonSalvate" */
                    modificheNonSalvate = false;
                } else {
                    int result = JOptionPane.showConfirmDialog(mainFrame, "La tabella è vuota. Vuoi comunque salvarla?", "Informazione", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        /* Salvataggio degli esami su file */
                        salvaEsami = new SalvaEsami(mainFrame, applicazione);
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
                /* Recupera la riga del campo selezionato */
                int row = applicazione.getTabella().getTable().rowAtPoint(e.getPoint());
                /* Recupera la colonna del campo selezionato */
                int col = applicazione.getTabella().getTable().columnAtPoint(e.getPoint());
                /* Recupera la classe dell'esame selezionato (semplice o composto) */
                String tipologia_esame = String.valueOf(applicazione.getArchivioEsami().get(row).getClass());

                if (tipologia_esame.contains("Semplice")) { // Se la classe dell'esame selezionato è Semplice...
                    /* Recupera l'esame semplice selezionato */
                    EsameSemplice esameSemplice = (EsameSemplice) applicazione.getArchivioEsami().get(row);
                    /* Crea un nuovo frame per mostrare le informazioni relative all'esame selezionato */
                    jFrameInfo = new JFrame("Informazioni");
                    jFrameInfo.setSize(700, 300);
                    /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                    jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                    /* Creazione del pannello per contenere gli oggetti necessari a mostrare le informazioni relative all'esame selezionato */
                    jPanelInfo = new JPanel();
                    jPanelInfo.setLayout(null);
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
                                JOptionPane.showMessageDialog(jFrameSemplice, "Per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                            } else {
                                /* Richiama il metodo per la modifica del record della tabella */
                                editEntry(applicazione, row);
                                /* Chiude il frame */
                                jFrameInfo.dispose();
                            }
                        }
                    });

                    /* Ascoltatore degli eventi sul click del bottone che permette di resettare i dati di un esame selezionato */
                    reset_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            resetEntries(esameSemplice);
                        }
                    });

                    jFrameInfo.add(jPanelInfo);
                }

                if (tipologia_esame.contains("Composto")) { // Se la classe dell'esame selezionato è Composto...
                    /* Recupera l'esame semplice selezionato */
                    EsameComposto esameComposto = (EsameComposto) applicazione.getArchivioEsami().get(row);
                    /* Crea un nuovo frame per mostrare le informazioni relative all'esame selezionato */
                    jFrameInfo = new JFrame("Informazioni");
                    jFrameInfo.setSize(700, 450);
                    /* Imposta il comportamento che deve assumere il frame quando viene chiuso dall'utente */
                    jFrameInfo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Viene chiuso solo questo frame
                    jPanelInfo = new JPanel();
                    jPanelInfo.setLayout(null);
                    jPanelInfo.setPreferredSize(new Dimension(700, 400));

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
                                JOptionPane.showMessageDialog(jFrameComposto, "Per procedere è necessario compilare tutti i campi o reinserirli correttamente", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                            } else {
                                /* Richiama il metodo per la modifica del record della tabella */
                                editEntry(applicazione, row);
                                /* Chiude il frame */
                                jFrameInfo.dispose();
                            }
                        }
                    });

                    /* Ascoltatore degli eventi sul click del bottone che permette di resettare le informazioni dell'esame composto selezionato */
                    reset_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            resetEntries(esameComposto);
                        }
                    });
                }

                /* Ascoltatore degli eventi sul click del bottone che permette di eliminare un esame */
                elimina_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        /* Richiama il metodo per l'eliminazione di un esame */
                        deleteEntry(applicazione);
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
                formSimpleExam(applicazione);
            }
        });
    }

    /**
     * <strong>controlloLode</strong>
     * <br>
     * Metodo che permette di controllare la corretta assegnazione della lode in un esame composto <br>
     * Più precisamente controlla se l'assegnazione della lode è coerente con i voti delle prove parziali
     * @param n_prove Numero di prove parziali
     * @return True se l'assegnazione della lode è ottenibile, false altrimenti
     */
    public boolean checkLode(int n_prove){
        int somma = 0;
        for(int i=0; i<n_prove; i++)
            somma += datiProve[i].getVoto();
        return somma == 59 || somma == 89; // somma dei voti che permette di ottenere la lode rispettivamente con 2 o 3 prove
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
     * <strong>checkTextField</strong>
     * <br>
     * Metodo che controlla il corretto inserimento dei campi di testo
     */
    public void checkTextField(JTextField jTextField){
        String input = jTextField.getText();
        if (!input.isEmpty()) { // Controlla se il campo non è vuoto
            try {
                Integer.parseInt(input); // Tentativo di conversione in intero
            } catch (NumberFormatException ex) {
                // Il testo inserito non è un numero, mostra il messaggio di errore
                JOptionPane.showMessageDialog(null, "Il valore inserito non è valido", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
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

        JLabel nome_l = new JLabel("Nome:");
        nome_l.setBounds(20, 60, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(70, 60, 100, 20);
        nome_tf.setText(esame.getStudente().getNome());

        nome_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                try {
                    int matricola = Integer.parseInt(matricola_tf.getText());
                    riempimentoAutomatico(applicazione, matricola);
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "I valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Non fa nulla quando il focus viene perso
            }
        });

        JLabel cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 60, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(280, 60, 100, 20);
        cognome_tf.setText(esame.getStudente().getCognome());

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

        JLabel cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(410, 100, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(490, 100, 100, 20);
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

        /* Imposta la generazione del frame principale al centro dello schermo */
        jFrameInfo.setLocationRelativeTo(null);
        /* Rende visibile il frame per la visualizzazione e/o modifica dei dati di un esame */
        jFrameInfo.setVisible(true);
    }

    /**
     * <strong>formSimpleExam</strong>
     * <br>
     * Metodo che crea la parte grafica per la registrazione di un esame semplice
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public void formSimpleExam(Applicazione applicazione){

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

        composto_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameSemplice.dispose();
                formComposedExam(applicazione);
            }
        });

        JLabel matricola_l = new JLabel("Matricola:");
        matricola_l.setBounds(200, 60, 100,20);
        matricola_tf = new JTextField();
        matricola_tf.setBounds(280,60,100,20);

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
                // Non necessario per campi di testo non formattati come JTextField
            }
        });

        JLabel nome_l = new JLabel("Nome:");
        nome_l.setBounds(30, 100, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(80, 100, 100, 20);

        nome_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                int matricola = Integer.parseInt(matricola_tf.getText());
                riempimentoAutomatico(applicazione, matricola);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Non fa nulla quando il focus viene perso
            }
        });

        JLabel cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 100, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(280, 100, 100, 20);

        JLabel corso_l = new JLabel("Corso:");
        corso_l.setBounds(390, 100, 100, 20);
        corso_tf = new JTextField();
        corso_tf.setBounds(440, 100, 100, 20);

        JLabel voto_l = new JLabel("Voto:");
        voto_l.setBounds(30, 140, 100, 20);
        voto_tf = new JTextField();
        voto_tf.setBounds(80, 140, 100, 20);

        /* Verifico se il campo di testo del voto è composto solo ed esclusivamente da numeri */
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
        lode_l.setBounds(200, 140, 100, 20);
        lode_cb = new JCheckBox();
        lode_cb.setBounds(280, 140, 100, 20);

        lode_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lode_cb.isSelected() && Integer.parseInt(voto_tf.getText()) < 30){
                    JOptionPane.showMessageDialog(jFrameSemplice, "Non si può assegnare la lode ad un voto inferiore a 30", "Errore", JOptionPane.ERROR_MESSAGE);
                    lode_cb.setSelected(false);
                }
            }
        });

        JLabel cfu_l = new JLabel("CFU:");
        cfu_l.setBounds(390, 140, 100, 20);
        cfu_tf = new JTextField();
        cfu_tf.setBounds(440, 140, 100, 20);

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
                // Non necessario per campi di testo non formattati come JTextField
            }
        });

        registra_esame_btn = new JButton("Registra Esame");
        registra_esame_btn.setBounds(250,200,150,25);

        registra_esame_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || voto_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jFrameSemplice, "Per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                } else {
                    /* Richiama il metodo per l'aggiunta di un nuovo record nella tabella */
                    addEntry(applicazione);
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
     * <strong>formComposedExam</strong>
     * <br>
     * Metodo che crea la parte grafica per la registrazione di un esame composto
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public void formComposedExam(Applicazione applicazione){
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

        semplice_cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameComposto.dispose();
                formSimpleExam(applicazione);
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
                // Non necessario per campi di testo non formattati come JTextField
            }
        });

        JLabel nome_l = new JLabel("Nome:");
        nome_l.setBounds(30, 100, 100, 20);
        nome_tf = new JTextField();
        nome_tf.setBounds(80, 100, 100, 20);

        nome_tf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                riempimentoAutomatico(applicazione, Integer.parseInt(matricola_tf.getText()));
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Non fa nulla quando il focus viene perso
            }
        });

        JLabel cognome_l = new JLabel("Cognome:");
        cognome_l.setBounds(200, 100, 100, 20);
        cognome_tf = new JTextField();
        cognome_tf.setBounds(280, 100, 100, 20);

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
                // Non necessario per campi di testo non formattati come JTextField
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
                if (matricola_tf.getText().isEmpty() || nome_tf.getText().isEmpty() || cognome_tf.getText().isEmpty() || corso_tf.getText().isEmpty() || cfu_tf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(jFrameComposto, "Per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
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
                    if(selectedValue == 2)
                        registra_esame_btn.setBounds(260, 130, 150, 25);
                    else
                        registra_esame_btn.setBounds(260,160,150,25);

                    /* Ascoltatore degli eventi sul click del bottone per la registrazione di un esame composto */
                    registra_esame_btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            boolean campi_compilati = true;
                            /* Verifica se i campi relatvi alle prove parziali sono stati compilati o meno */
                            for(int i=0; i<selectedValue; i++){
                                if(peso_prova_tf[i].getText().isEmpty() && voto_prova_tf[i].getText().isEmpty())
                                    campi_compilati = false;
                            }
                            if (!campi_compilati) {
                                JOptionPane.showMessageDialog(jFrameProve, "Per procedere è necessario compilare tutti i campi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                            } else{
                                /* Chiude il frame per la registrazione dell'esame composto */
                                jFrameComposto.dispose();
                                /* Recupera i dati dagli oggetti grafici */
                                for(int i=0; i<selectedValue; i++){
                                    String nome = (String) tipologia_prova_cb[i].getSelectedItem();
                                    int peso = Integer.parseInt(peso_prova_tf[i].getText());
                                    int voto = Integer.parseInt(voto_prova_tf[i].getText());
                                    /* Salva i dati in questo array temporaneo */
                                    datiProve[i] = new EsameParziale(nome,peso,voto);
                                }
                                /* Chiude il frame per la registrazione delle prove parziali */
                                jFrameProve.dispose();
                                /* Richiama il metodo per l'aggiunta di un nuovo record nella tabella */
                                addEntry(applicazione);
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
     */
    public void addEntry(Applicazione applicazione){
        Studente studente;

        int matricola = Integer.parseInt(matricola_tf.getText());
        String nome = nome_tf.getText();
        String cognome = cognome_tf.getText();
        String corso = corso_tf.getText();
        boolean lode = lode_cb.isSelected();
        int cfu = Integer.parseInt(cfu_tf.getText());

        studente = applicazione.ricercaStudente(matricola);
        if(studente == null){
            studente = new Studente(matricola, nome, cognome);
            applicazione.getArchivioStudenti().add(studente);
        }
        if (semplice_cb.isSelected()) { // Se viene selezionata la checkbox dell'esame semplice
            int voto = Integer.parseInt(voto_tf.getText());
            if(checkInserimentiNumerici()){
                EsameSemplice esameSemplice = new EsameSemplice(studente, corso, voto, lode, cfu);
                /* Verifico se l'esame appena compilato è gia stato registrato o meno */
                if(applicazione.verificaEsistenzaEsame(esameSemplice)){
                    JOptionPane.showMessageDialog(jFrameSemplice, "Questo esame è già stato registrato", "Errore", JOptionPane.ERROR_MESSAGE);
                } else{
                    /* Aggiunge l'esame semplice nell'archivio esami */
                    applicazione.getArchivioEsami().add(esameSemplice);
                    /* Aggiunge il nuovo record nella tabella */
                    applicazione.getTabella().getDefaultTableModel().addRow(new Object[]{matricola, nome, cognome, corso, voto, lode, cfu});
                    /* Chiude il frame */
                    jFrameSemplice.dispose();
                }
            } else{
                JOptionPane.showMessageDialog(jFrameSemplice, "I valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(composto_cb.isSelected()) { // Se viene selezionata la checkbox dell'esame composto
            /* Recupero il numero di prove parziali */
            Integer selectedValue = (Integer) n_prove_cb.getSelectedItem();
            /* Controlla se l'assegnazione della lode è coerente con i voti delle prove parziali */
            if (!checkLode(selectedValue)){
                lode = !lode;
            }
            EsameComposto esameComposto = new EsameComposto(studente, corso, lode, cfu);

            /* Verifico se l'esame appena compilato è gia stato registrato o meno */
            if(applicazione.verificaEsistenzaEsame(esameComposto)){
                JOptionPane.showMessageDialog(jFrameComposto, "Questo esame è già stato registrato", "Errore", JOptionPane.ERROR_MESSAGE);
            } else{
                if (checkCampiProveParziali(selectedValue) && cfu > 1) {
                    /* Registro le prove parziali recuperandole dall'array temporaneo creato durante la loro registrazione */
                    for (int i = 0; i < selectedValue; i++) {
                        esameComposto.getEsamiParziali().add(datiProve[i]);
                    }
                    /* Calcolo il voto finale dell'esame composto da più prove parziali */
                    esameComposto.voto();
                    /* Aggiunge l'esame composto nell'archivio esami */
                    applicazione.getArchivioEsami().add(esameComposto);
                    /* Aggiunge il nuovo record nella tabella */
                    applicazione.getTabella().getDefaultTableModel().addRow(new Object[]{matricola, nome, cognome, corso, esameComposto.getVoto(), lode, cfu});
                    /* Chiude il frame */
                    jFrameComposto.dispose();
                } else{
                    JOptionPane.showMessageDialog(jFrameComposto, "I valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * <strong>editEntry</strong>
     * <br>
     * Metodo che permette di: <br>
     *  - modificare i dati del record selezionato <br>
     *  - salvare le modifiche nei relativi archivi studenti e/o esame
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * @param row Riga selezionata
     */
    public void editEntry(Applicazione applicazione, int row){
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
                /* Recupera lo studente dall'archivio studenti attraverso il metodo ricercaStudente */
                Studente studente = applicazione.ricercaStudente(matricola);
                /* Modifica i dati dello studente e del corrispettivo esame selezionato */
                studente.setMatricola(matricola);
                studente.setNome(nome);
                studente.setCognome(cognome);
                applicazione.getArchivioEsami().get(row).setNome(corso);
                applicazione.getArchivioEsami().get(row).setVoto(voto);
                applicazione.getArchivioEsami().get(row).setLode(lode);
                applicazione.getArchivioEsami().get(row).setCfu(cfu);
            } else{
                JOptionPane.showMessageDialog(jFrameSemplice, "I valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
            }

            if(tipologia_esame.contains("Composto")) { // Se l'esame è composto
                /* Recupera il numero di prove parziali dell'esame (record) selezionato */
                int n_prove = applicazione.getArchivioEsami().get(row).getEsamiParziali().size();
                if (checkCampiProveParziali(n_prove) && cfu > 0) {
                    if (!checkLode(n_prove)){
                        lode = !lode;
                    }
                    /* Recupero i dati delle prove parziali */
                    for (int i = 0; i < n_prove; i++) {
                        String tipologia_prova = (String) tipologia_prova_cb[i].getSelectedItem();
                        int peso = Integer.parseInt(peso_prova_tf[i].getText());
                        int voto_prova = Integer.parseInt(voto_prova_tf[i].getText());

                        /* Modifico i dati delle prove parziali */
                        applicazione.getArchivioEsami().get(row).getEsamiParziali().get(i).setNome(tipologia_prova);
                        applicazione.getArchivioEsami().get(row).getEsamiParziali().get(i).setPeso(peso);
                        applicazione.getArchivioEsami().get(row).getEsamiParziali().get(i).setVoto(voto_prova);
                    }
                    /* Ricalcolo il voto finale dell'esame composto appena modificato */
                    applicazione.getArchivioEsami().get(row).voto();
                    /* Salvo in una variabile il voto in modo tale da aggiornare il record della tabella a seconda del tipo
                     *  di esame che è stato modificato */
                    voto = applicazione.getArchivioEsami().get(row).getVoto();

                    /* Aggiorno i dati in tabella del record modificato */
                    applicazione.getTabella().getDefaultTableModel().setValueAt(matricola, row, 0);
                    applicazione.getTabella().getDefaultTableModel().setValueAt(nome, row, 1);
                    applicazione.getTabella().getDefaultTableModel().setValueAt(cognome, row, 2);
                    applicazione.getTabella().getDefaultTableModel().setValueAt(corso, row, 3);
                    applicazione.getTabella().getDefaultTableModel().setValueAt(voto, row, 4);
                    applicazione.getTabella().getDefaultTableModel().setValueAt(lode, row, 5);
                    applicazione.getTabella().getDefaultTableModel().setValueAt(cfu, row, 6);
                } else {
                    JOptionPane.showMessageDialog(jFrameSemplice, "I valori inseriti non sono validi", "Compilazione errata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
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
        String tipologia_esame = String.valueOf(applicazione.getArchivioEsami().get(selectedRow).getClass());
        if (selectedRow != -1) { // Se la riga ha un valore valido
            /* Rimuove il record selezionato dalla tabella */
            applicazione.getTabella().getDefaultTableModel().removeRow(selectedRow);
            /* Rimuove l'esame del record selezionato dall'archivio esami */
            applicazione.getArchivioEsami().delete(applicazione.getArchivioEsami().get(selectedRow));
            /* Se l'esame è composto vengono eliminate anche tutte le sue prove parziali */
            if(tipologia_esame.contains("Composto"))
                applicazione.getArchivioEsami().get(selectedRow).getEsamiParziali().clear();
        }
    }

    /**
     * <strong>resetEntries</strong>
     * <br>
     * Metodo che permette di resettare i campi di testo di un esame
     * @param esame Esame di cui resettare i campi
     */
    public void resetEntries(Esame esame) {
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