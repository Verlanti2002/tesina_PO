package gui;

import classi.EsameComposto;
import classi.EsameSemplice;
import classi.Studente;
import classi.TipologiaProva;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * CaricamentoEsami
 * Classe che permette il caricamento di una tabella di esami da file
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class CaricaEsami {
    /**
     * Costruttore della classe CaricamentoEsami
     * Permette di caricare una tabella di esami da file
     * @param mainFrame finestra per il caricamento degli esami da file
     * @param applicazione oggetto che permette di aggiornare gli archivi dati
     */
    public CaricaEsami(JFrame mainFrame, Applicazione applicazione){
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
                String line;
                // Legge il resto del file che contiene i dati della tabella
                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split("\t"); // Assume che i dati delle righe siano separati da una tabulazione
                    String[] rowDataTable = new String[7]; // Array che conterrà solo i dati per riempire la tabella
                    System.arraycopy(rowData, 0, rowDataTable, 0, 7);
                    applicazione.getTabella().getDefaultTableModel().addRow(rowDataTable); // Aggiunge una nuova riga alla tabella con i dati letti dal file
                    Studente studente = new Studente(Integer.parseInt(rowDataTable[0]), rowDataTable[1], rowDataTable[2]);
                    applicazione.getStudenti().add(studente);
                    if(rowData.length == 7){ // allora è un esame semplice
                        applicazione.getEsami().add(new EsameSemplice(studente, rowDataTable[3], Integer.parseInt(rowDataTable[4]), Boolean.parseBoolean(rowDataTable[5]), Integer.parseInt(rowDataTable[6])));
                    } else{ // altrimenti è un esame composto
                        EsameComposto esameComposto = new EsameComposto(studente, rowDataTable[3], Boolean.parseBoolean(rowDataTable[5]), Integer.parseInt(rowDataTable[6]));
                        if(rowData.length == 16){ // allora ha 3 prove parziali
                            for(int i=0; i<3*3; i=i+3){ // 3*3 perchè 3 prove parziali con 3 attributi ciascuna
                                esameComposto.getEsami_parziali().add(new TipologiaProva(rowData[i+7], Integer.parseInt(rowData[i+8]), Integer.parseInt(rowData[i+9])));
                            }
                        } else{ // altrimenti 2 prove intermedie
                            for(int i=0; i<2*3; i=i+3){ // 2*3 perchè 2 prove parziali con 3 attributi ciascuna
                                esameComposto.getEsami_parziali().add(new TipologiaProva(rowData[i+7], Integer.parseInt(rowData[i+8]), Integer.parseInt(rowData[i+9])));
                            }
                        }
                        esameComposto.voto();
                        applicazione.getEsami().add(esameComposto);
                    }
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
