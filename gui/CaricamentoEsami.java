package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * CaricamentoEsami
 * Classe che permette il caricamento di una tabella di esami da file
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class CaricamentoEsami {
    /**
     * Costruttore della classe CaricamentoEsami
     * Permette di caricare una tabella di esami da file
     * @param applicazione contiene tutti gli oggetti necessari per il funzionamento dell'intero applicativo
     */
    public CaricamentoEsami(JFrame mainFrame, Applicazione applicazione){
        /** Mostra un dialogo per selezionare il file da cui caricare la tabella */
        JFileChooser fileChooser = new JFileChooser();
        /** Imposta il filtro per i tipi di file */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tabella salvata (*.tbl)", "tbl");
        fileChooser.setFileFilter(filter);

        /** Mostra il dialogo di apertura e memorizza la risposta dell'utente */
        int returnVal = fileChooser.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** Permette di ottenere il file selezionato dall'utente */
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                /** Legge i dati dal file utilizzando un ObjectInputStream */
                Object data = inputStream.readObject();
                if (data instanceof Object[][]) {
                    /** Rimuovi tutte le righe esistenti dal modello di tabella */
                    applicazione.getDefaultTableModel().setRowCount(0);
                    /** Rimuovi tutte le colonne esistenti dal modello di tabella */
                    applicazione.getDefaultTableModel().setColumnCount(0);
                    /** Ottieni il numero di colonne */
                    int columnCount = ((Object[][]) data)[0].length;
                    /** Aggiungi le intestazioni delle colonne al modello di tabella */
                    for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                        String columnName = "Colonna " + (columnIndex + 1); // Puoi sostituire questo con nomi personalizzati se necessario
                        applicazione.getDefaultTableModel().addColumn(columnName);
                    }
                    /** Imposta i dati del modello di tabella */
                    for (Object[] row : (Object[][]) data) {
                        applicazione.getDefaultTableModel().addRow(row);
                    }
                    /** Mostra un messaggio di conferma del caricamento */
                    JOptionPane.showMessageDialog(mainFrame, "Tabella caricata con successo!");
                } else {
                    /** Mostra un messaggio di errore se il file non contiene dati della tabella */
                    JOptionPane.showMessageDialog(mainFrame, "Il file selezionato non contiene dati della tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                /** Gestione di eventuali eccezioni di IO o di class not found */
                e.printStackTrace();
                /** Mostra un messaggio di errore se si verifica un problema durante il caricamento */
                JOptionPane.showMessageDialog(mainFrame, "Si è verificato un errore durante il caricamento della tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        /** Rende il frame principale visibile */
        mainFrame.setVisible(true);
    }
}
