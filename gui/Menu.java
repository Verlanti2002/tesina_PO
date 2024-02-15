package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.*;

/**
 * Menu
 * Classe che rappresenta il menù principale dell'intera applicazione
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Menu {

    /**
     * Menu
     * Costruttore che realizza l'intera finestra per la visualizzazione del menù
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * */
    public Menu(Applicazione applicazione){

        JFrame mainFrame = new JFrame("Gestione Esami");
        mainFrame.setSize(600,300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title_l = new JLabel("<html><h1><strong><i>Benvenuto nell'applicazione Gestione Esami!</i></strong></h1><hr></html>");
        title_l.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title_l, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton gestione_esami_btn = new JButton("<html><h3>GESTIONE ESAMI</h3></html>");
        gestione_esami_btn.setFocusPainted(false);

        JButton carica_esami_btn = new JButton("<html><h3>CARICAMENTO ESAMI</h3></html>");
        carica_esami_btn.setFocusPainted(false);

        JButton stampa_tabella_btn = new JButton("<html><h3>STAMPA TABELLA DEGLI ESAMI</h3></html>");
        stampa_tabella_btn.setFocusPainted(false);

        gestione_esami_btn.addActionListener(e -> new GestioneEsami(applicazione));
        carica_esami_btn.addActionListener(e -> new CaricaEsami(mainFrame, applicazione));

        stampa_tabella_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(applicazione.getTabella().getTable().getRowCount() == 0){
                    int result = JOptionPane.showConfirmDialog(mainFrame, "La tabella è vuota. Vuoi comunque stamparla?", "Informazione", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        // Ottengo un'istanza di PrinterJob, che rappresenta il lavoro di stampa
                        PrinterJob printerJob = PrinterJob.getPrinterJob();

                        // Imposta il lavoro di stampa utilizzando un oggetto Printable personalizzato
                        printerJob.setPrintable(new Printable() {
                            @Override
                            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                                // Controlla se pageIndex è maggiore di 0 (per gestire le pagine successive)
                                if(pageIndex > 0)
                                    return Printable.NO_SUCH_PAGE;

                                // Ottieni un'istanza di Graphics2D dalla grafica fornita, per poter disegnare
                                Graphics2D graphics2D = (Graphics2D) graphics;
                                // Trasla il contesto grafico per adattarlo all'area stampabile della pagina
                                graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                                // Stampa la tabella dell'applicazione utilizzando il metodo printAll() della tabella
                                applicazione.getTabella().getTable().printAll(graphics2D);

                                // Indica che la pagina è stata stampata con successo
                                return Printable.PAGE_EXISTS;
                            }
                        });

                        // Mostra il dialogo di stampa del sistema operativo e attendi che l'utente interagisca con esso
                        if (printerJob.printDialog()) {
                            try {
                                // Se l'utente conferma la stampa, stampa il documento
                                printerJob.print();
                            } catch (PrinterException ex) {
                                // Gestisci eventuali eccezioni di stampa
                                ex.printStackTrace();
                                // Mostra un messaggio di errore all'utente in caso di errore di stampa
                                JOptionPane.showMessageDialog(mainFrame, "Errore durante la stampa.", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        buttonPanel.add(gestione_esami_btn);
        buttonPanel.add(carica_esami_btn);
        buttonPanel.add(stampa_tabella_btn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        /*JFrame mainFrame = new JFrame("Gestione Esami");
        mainFrame.setSize(600,300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(600,300));

        JLabel title_l = new JLabel("<html><h1><strong><i>Benvenuto nell'applicazione Gestione Esami!</i></strong></h1><hr></html>");
        title_l.setBounds(35,10, 600,60);

        JButton gestione_esami_btn = new JButton("Gestione degli esami");
        gestione_esami_btn.setBounds(200,115, 200,25);
        JButton carica_esami_btn = new JButton("Caricamento esami");
        carica_esami_btn.setBounds(200,140, 200,25);
        JButton stampa_tabella_btn = new JButton("Stampa tabella degli esami");
        stampa_tabella_btn.setBounds(200,165, 200,25);

        gestione_esami_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneEsami gestioneEsami = new GestioneEsami(applicazione);
            }
        });

        carica_esami_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaricaEsami caricaEsami = new CaricaEsami(mainFrame, applicazione);
            }
        });



        mainPanel.add(title_l);
        mainPanel.add(gestione_esami_btn);
        mainPanel.add(carica_esami_btn);
        mainPanel.add(stampa_tabella_btn);

        // Aggiunta del pannello principale al frame
        mainFrame.add(mainPanel);

        // Imposta la finestra al centro dello schermo
        mainFrame.setLocationRelativeTo(null);

        // Rendi la finestra visibile
        mainFrame.setVisible(true);*/
    }
}
