package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;

/**
 * <strong>Menu</strong>
 * <br>
 * Classe che rappresenta il menù principale dell'intera applicazione
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Menu {

    /**
     * <strong>Menu</strong>
     * <br>
     * Costruttore che realizza l'intera finestra per la visualizzazione del menù
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     * */
    public Menu(Applicazione applicazione){

        JFrame mainFrame = new JFrame("Gestione Esami");
        mainFrame.setSize(700,350);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title_l = new JLabel("<html><h1><strong><i>Benvenuto nell'applicazione Gestione Esami!</i></strong></h1><hr></html>");
        title_l.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title_l, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton gestione_esami_btn = new JButton("<html><h3>GESTIONE ESAMI</h3></html>");
        gestione_esami_btn.setFocusPainted(false);
        gestione_esami_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Aggiungi bordo nero intorno al pulsante
        gestione_esami_btn.addActionListener(e -> new GestioneEsami(mainFrame, applicazione));

        // Aggiunge un MouseListener per gestire gli eventi del mouse
        gestione_esami_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambia il colore del pulsante quando il mouse entra
                gestione_esami_btn.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Ripristina il colore originale del pulsante quando il mouse esce
                gestione_esami_btn.setBackground(null);
            }
        });

        JButton carica_esami_btn = new JButton("<html><h3>CARICAMENTO ESAMI</h3></html>");
        carica_esami_btn.setFocusPainted(false);
        carica_esami_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        carica_esami_btn.addActionListener(e -> new CaricaEsami(mainFrame, applicazione));

        // Aggiunge un MouseListener per gestire gli eventi del mouse
        carica_esami_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambia il colore del pulsante quando il mouse entra
                carica_esami_btn.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Ripristina il colore originale del pulsante quando il mouse esce
                carica_esami_btn.setBackground(null);
            }
        });

        JButton stampa_tabella_btn = new JButton("<html><h3>STAMPA TABELLA DEGLI ESAMI</h3></html>");
        stampa_tabella_btn.setFocusPainted(false);
        stampa_tabella_btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Aggiunge un MouseListener per gestire gli eventi del mouse
        stampa_tabella_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambia il colore del pulsante quando il mouse entra
                stampa_tabella_btn.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Ripristina il colore originale del pulsante quando il mouse esce
                stampa_tabella_btn.setBackground(null);
            }
        });

        stampa_tabella_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(applicazione.getTabella().getTable().getRowCount() == 0){
                    int result = JOptionPane.showConfirmDialog(mainFrame, "La tabella è vuota. Vuoi comunque stamparla?", "Informazione", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        stampaTabella(applicazione);
                    }
                } else {
                    stampaTabella(applicazione);
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
    }

    /**
     * <strong>stampaTabella</strong>
     * <br>
     * Metodo per stampare la tabella utilizzando il servizio di stampa del sistema operativo Linux (WSL)
     * @param applicazione Permette l'accesso alla tabella da stampare
     */
    public void stampaTabella(Applicazione applicazione){
        /* Creazione dell'intestazione per il documento stampato */
        MessageFormat header = new MessageFormat("Tabella degli esami registrati");
        try {
            // Impostazione degli attributi di stampa, come l'orientamento della pagina
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            /* Stampa della tabella utilizzando la modalità FIT_WIDTH
                FIT_WIDTH fa in modo che la tabella venga adattata alla larghezza della pagina
                header: l'intestazione per ogni pagina
                null: il footer per ogni pagina (che non è specificato in questo caso)
                true: showPrintDialog - se true, mostra il dialogo di stampa del sistema
                set: gli attributi di stampa aggiuntivi, come l'orientamento
                true: interactive - se false, non mostra un dialogo di conferma dopo la stampa */
            if(applicazione.getTabella().getTable().print(JTable.PrintMode.FIT_WIDTH, header, null, true, set, false))
                JOptionPane.showMessageDialog(null, "\n" + "Printed Succefully");
        }catch (java.awt.print.PrinterException e){
            JOptionPane.showMessageDialog(null, "Errore: " + e);
        }
    }
}
