package classi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * <strong>Tabella</strong>
 * <br>
 * Permette di rappresentare la tabella che gestirà gli esami
 * @author Alessandro Verlanti
 * @version java 21.0.1 2023-10-17 LTS
 */
public class Tabella {
    /** Tabella grafica */
    private final JTable jTable;
    /** Modello di default della tabella */
    private final DefaultTableModel defaultTableModel;

    /**
     * <strong>Tabella</strong>
     * <br>
     * Costruttore che crea una nuova istanza della tabella con le colonne specificate
     * @param columnNames Un array di stringhe contenente i nomi delle colonne della tabella
     */
    public Tabella(String[] columnNames){
        defaultTableModel = new DefaultTableModel();;
        for(int i=0; i< columnNames.length; i++){
            defaultTableModel.addColumn(columnNames[i]);
        }
        jTable = new JTable(defaultTableModel);
    }

    /**
     * <strong>getTable</strong>
     * <br>
     * Metodo getter che restituisce l'oggetto JTable associato a questa Tabella
     * @return La tabella grafica JTable
     */
    public JTable getTable() {
        return jTable;
    }

    /**
     * <strong>getDefaultTableModel</strong>
     * <br>
     * Metodo getter che restituisce il modello di default della tabella
     * @return Il modello di default della tabella
     */
    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }
}
