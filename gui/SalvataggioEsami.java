package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class SalvataggioEsami {

    public SalvataggioEsami(JFrame mainFrame, Applicazione applicazione){
        /** Crea un nuovo oggetto JFileChooser, che permette all'utente di navigare attraverso il filesystem */
        JFileChooser fileChooser = new JFileChooser();
        /** Imposta il filtro per i tipi di file */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tabella salvata (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        /** Mostra il dialogo di salvataggio e memorizza la risposta dell'utente */
        int returnVal = fileChooser.showSaveDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** Ottiene il file selezionato dall'utente */
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                /** Scrive i nomi delle colonne */
                for (int i = 0; i < applicazione.getDefaultTableModel().getColumnCount(); i++) {
                    writer.write(applicazione.getDefaultTableModel().getColumnName(i));
                    if (i < applicazione.getDefaultTableModel().getColumnCount() - 1) {
                        writer.write("\t");
                    } else {
                        writer.write("\n");
                    }
                }

                /** Scrive i dati della tabella */
                for (int row = 0; row < applicazione.getDefaultTableModel().getRowCount(); row++) {
                    for (int col = 0; col < applicazione.getDefaultTableModel().getColumnCount(); col++) {
                        writer.write(applicazione.getDefaultTableModel().getValueAt(row, col).toString());
                        if (col < applicazione.getDefaultTableModel().getColumnCount() - 1) {
                            writer.write("\t");
                        } else {
                            writer.write("\n");
                        }
                    }
                }

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
