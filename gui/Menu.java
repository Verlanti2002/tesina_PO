package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu {

    public Menu(Applicazione applicazione){
        JFrame mainFrame = new JFrame("Gestione Esami");
        mainFrame.setSize(600,300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;

        mainPanel.add(new JLabel("<html><h1><strong><i>Benvenuto nella mia nuova applicazione!</i></strong></h1><hr></html>"), gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton gestione_esami_btn = new JButton("Gestione degli esami");
        JButton carica_esami_btn = new JButton("Caricamento esami");
        JButton stampa_tabella_btn = new JButton("Stampa tabella degli esami");

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

        stampa_tabella_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
;
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
        });

        buttonPanel.add(gestione_esami_btn, gridBagConstraints);
        buttonPanel.add(carica_esami_btn, gridBagConstraints);
        buttonPanel.add(stampa_tabella_btn, gridBagConstraints);

        gridBagConstraints.weighty = 1;
        mainPanel.add(buttonPanel, gridBagConstraints);

        // Aggiunta del pannello principale al frame
        mainFrame.add(mainPanel);

        // Imposta la finestra al centro dello schermo
        mainFrame.setLocationRelativeTo(null);

        // Rendi la finestra visibile
        mainFrame.setVisible(true);
    }
}
