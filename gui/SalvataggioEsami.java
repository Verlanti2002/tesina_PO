package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SalvataggioEsami {

    public SalvataggioEsami(JFrame mainFrame, Applicazione applicazione){
        /** Mostra un dialogo per selezionare il file in cui salvare la tabella */
        JFileChooser fileChooser = new JFileChooser();
        /** Imposta il filtro per i tipi di file */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tabella salvata (*.tbl)", "tbl");
        fileChooser.setFileFilter(filter);

        /** Mostra il dialogo di salvataggio e memorizza la risposta dell'utente */
        int returnVal = fileChooser.showSaveDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** Ottiene il file selezionato dall'utente */
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                /** Scrive i dati del modello di tabella nel file usando un ObjectOutputStream */
                outputStream.writeObject(applicazione.getDefaultTableModel().getDataVector());
                /** Mostra un messaggio di conferma del salvataggio */
                JOptionPane.showMessageDialog(mainFrame, "Tabella salvata con successo!");
            } catch (IOException e) {
                /** Gestione di eventuali eccezioni di IO */
                e.printStackTrace();
                /** Mostra un messaggio di errore se si verifica un problema durante il salvataggio */
                JOptionPane.showMessageDialog(mainFrame, "Si è verificato un errore durante il salvataggio della tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        /** Rende visibile il frame */
        mainFrame.setVisible(true);
    }
}
