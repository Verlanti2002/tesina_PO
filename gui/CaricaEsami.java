package gui;

import classi.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * <strong>CaricaEsami</strong>
 * <br>
 * Classe che permette il caricamento di una tabella di esami da file
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class CaricaEsami {

    /**
     * <strong>CaricaEsami</strong>
     * <br>
     * Costruttore che permette il caricamento dei dati da un file
     * @param mainFrame Frame su cui aprire il gestore dei file
     * @param applicazione Permette di aggiornare gli archivi dati e la tabella
     */
    public CaricaEsami(JFrame mainFrame, Applicazione applicazione){
        int columnSize = applicazione.getTabella().getColumnNames().length;
        JFileChooser fileChooser = new JFileChooser();
        /* Imposta il filtro per i tipi di file */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tabella salvata (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        /* Mostra il dialogo di selezione del file e aspetta che l'utente selezioni un file o annulli l'operazione */
        int returnVal = fileChooser.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /* Ottiene il file selezionato dall'utente */
            File file = fileChooser.getSelectedFile();
            /* Una volta selezionato il file, prima di caricare i dati dal file, creo una nuova istanza di applicazione
             * in modo tale da inizializzare nuovamente la tabella e gli archivi dati */
            applicazione.getArchivioStudenti().getStudenti().clear();
            applicazione.getArchivioEsami().getEsami().clear();
            applicazione.getTabella().getDefaultTableModel().setRowCount(0);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                /* Lettura del file */
                while ((line = reader.readLine()) != null) {
                    /* Lettura della riga con split del carattere di tabulazione */
                    String[] rowData = line.split("\t"); // Assume che i dati delle righe siano separati da una tabulazione
                    /* Memorizza in rowDataTable solo i campi necessari per riempire la tabella */
                    String[] rowDataTable = new String[columnSize]; // Record da inserire nella tabella
                    /* Copia solo i primi 7 valori (ossia i campi da inserire nella tabella) */
                    System.arraycopy(rowData, 0, rowDataTable, 0, columnSize);
                    /* Carica la nuova tabella con i dati prelevati dal file */
                    applicazione.getTabella().getDefaultTableModel().addRow(rowDataTable); // Aggiunge una nuova riga alla tabella con i dati letti dal file
                    Studente studente;
                    /* Verifica se lo studente è gia registrato */
                    studente = applicazione.ricercaStudente(Integer.parseInt(rowDataTable[0]));
                    if(studente == null){ // Nel caso in cui non lo fosse
                        /* Crea il nuovo studente */
                        studente = new Studente(Integer.parseInt(rowDataTable[0]), rowDataTable[1], rowDataTable[2]);
                        /* Aggiunge lo studente all'archivio studenti */
                        applicazione.getArchivioStudenti().add(studente);
                    }
                    if(rowData.length == columnSize){ // allora è un esame semplice
                        /* Registrazione di un nuovo esame semplice */
                        applicazione.getArchivioEsami().add(new EsameSemplice(studente, rowDataTable[3], Integer.parseInt(rowDataTable[4]), Boolean.parseBoolean(rowDataTable[5]), Integer.parseInt(rowDataTable[6])));
                    } else{ // altrimenti è un esame composto
                        /* Registrazione di un nuovo esame composto */
                        EsameComposto esameComposto = new EsameComposto(studente, rowDataTable[3], Boolean.parseBoolean(rowDataTable[5]), Integer.parseInt(rowDataTable[6]));
                        if(rowData.length == columnSize + 9){ // allora ha 3 prove parziali (3 prove da 3 campi ciascuna = 9 campi)
                            /* Registrazione prove parziali */
                            for(int i=0; i<3*3; i=i+3){ // 3*3 perchè 3 prove parziali con 3 attributi ciascuna
                                esameComposto.getEsamiParziali().add(new EsameParziale(rowData[i+7], Integer.parseInt(rowData[i+8]), Integer.parseInt(rowData[i+9])));
                            }
                        } else{ // altrimenti 2 prove parziali
                            /* Registrazione prove parziali*/
                            for(int i=0; i<2*3; i=i+3){ // 2*3 perchè 2 prove parziali con 3 attributi ciascuna
                                esameComposto.getEsamiParziali().add(new EsameParziale(rowData[i+7], Integer.parseInt(rowData[i+8]), Integer.parseInt(rowData[i+9])));
                            }
                        }
                        /* Calcola il voto finale dell'esame composto registrato */
                        esameComposto.voto();
                        /* Aggiunge l'esame all'archivio esami */
                        applicazione.getArchivioEsami().add(esameComposto);
                    }
                }
                /* Mostra un messaggio di conferma del caricamento */
                JOptionPane.showMessageDialog(mainFrame, "Tabella caricata con successo!");
            } catch (IOException e) {
                /* Gestione di eventuali eccezioni di I*/
                e.printStackTrace();
                /* Mostra un messaggio di errore se si verifica un problema durante il caricamento */
                JOptionPane.showMessageDialog(mainFrame, "Errore: si è verificato un problema durante il caricamento della tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
