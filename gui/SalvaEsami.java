package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * <strong>SalvaEsami</strong>
 * <br>
 * Classe per il salvataggio degli esami registrati su file
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class SalvaEsami {

    /**
     * <strong>SalvaEsami</strong>
     * <br>
     * Costruttore che permette il salvataggio degli esami, presenti nella tabella, su file
     * @param mainFrame Frame su cui visualizzare il gestore dei file
     * @param applicazione Permette di gestire gli archivi dati e la tabella
     */
    public SalvaEsami(JFrame mainFrame, Applicazione applicazione){
        /* Crea un nuovo oggetto JFileChooser, che permette all'utente di navigare attraverso il filesystem */
        JFileChooser fileChooser = new JFileChooser();
        /* Imposta il filtro per i tipi di file */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tabella salvata (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        /* Mostra il dialogo di salvataggio e memorizza la risposta dell'utente */
        int returnVal = fileChooser.showSaveDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /* Ottiene il file selezionato dall'utente */
            File file = fileChooser.getSelectedFile();
            // Verifica se il file esiste già
            if (file.exists()) {
                // Se il file esiste già, chiedo all'utente se desidera sovrascriverlo
                int result = JOptionPane.showConfirmDialog(mainFrame, "Il file esiste già. Vuoi sovrascriverlo?", "Conferma sovrascrittura", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    // Se l'utente non conferma la sovrascrittura, esco dal costruttore
                    return;
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                /* Scrive i dati della tabella su file */
                for(int i = 0; i<applicazione.getArchivioEsami().size(); i++){
                    String tipologia_esame = String.valueOf(applicazione.getArchivioEsami().get(i).getClass());
                    writer.write(applicazione.getArchivioEsami().get(i).getStudente().getMatricola() + "\t");
                    writer.write(applicazione.getArchivioEsami().get(i).getStudente().getNome() + "\t");
                    writer.write(applicazione.getArchivioEsami().get(i).getStudente().getCognome() + "\t");
                    writer.write(applicazione.getArchivioEsami().get(i).getNome() + "\t");
                    writer.write(applicazione.getArchivioEsami().get(i).getVoto() + "\t");
                    writer.write(applicazione.getArchivioEsami().get(i).getLode() + "\t");
                    writer.write(applicazione.getArchivioEsami().get(i).getCfu() + "\t");
                    if(tipologia_esame.contains("Composto")){
                        for(int j = 0; j<applicazione.getArchivioEsami().get(i).getEsamiParziali().size(); j++){
                            writer.write(applicazione.getArchivioEsami().get(i).getEsamiParziali().get(j).getNome() + "\t");
                            writer.write(applicazione.getArchivioEsami().get(i).getEsamiParziali().get(j).getPeso() + "\t");
                            writer.write(applicazione.getArchivioEsami().get(i).getEsamiParziali().get(j).getVoto() + "\t");
                        }
                    }
                    writer.write("\n");
                }
                /* Mostra un messaggio di conferma del salvataggio */
                JOptionPane.showMessageDialog(mainFrame, "Tabella salvata con successo!");
            } catch (IOException e) {
                /* Gestione di eventuali eccezioni di IO */
                e.printStackTrace();
                /* Mostra un messaggio di errore se si verifica un problema durante il salvataggio */
                JOptionPane.showMessageDialog(mainFrame, "Si è verificato un errore durante il salvataggio della tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
