package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

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
        JFileChooser fileChooser = new JFileChooser();
        // Imposta il filtro per i tipi di file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tabella salvata (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        // Mostra il dialogo di selezione del file e aspetta che l'utente selezioni un file o annulli l'operazione
        int returnVal = fileChooser.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Ottiene il file selezionato dall'utente
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Legge la prima riga del file che contiene i nomi delle colonne
                String line = reader.readLine();
                if (line != null) {
                    String[] columnNames = line.split("\t"); // Assume che le colonne siano separate da una tabulazione
                    applicazione.creaTabella(columnNames); // Crea una nuova tabella con i nomi delle colonne letti dal file
                }

                // Legge il resto del file che contiene i dati della tabella
                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split("\t"); // Assume che i dati delle righe siano separati da una tabulazione
                    applicazione.aggiungiRigaAllaTabella(rowData); // Aggiunge una nuova riga alla tabella con i dati letti dal file
                }

                // Mostra un messaggio di conferma del caricamento
                JOptionPane.showMessageDialog(mainFrame, "Tabella caricata con successo!");
            } catch (IOException e) {
                // Gestione di eventuali eccezioni di IO
                e.printStackTrace();
                // Mostra un messaggio di errore se si verifica un problema durante il caricamento
                JOptionPane.showMessageDialog(mainFrame, "Si è verificato un errore durante il caricamento della tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        /** Rende il frame principale visibile */
        mainFrame.setVisible(true);
    }
}
