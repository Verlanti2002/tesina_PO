package classi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tabella {

    private JTable jTable;
    private DefaultTableModel defaultTableModel;

    public Tabella(String[] columnNames){
        defaultTableModel = new DefaultTableModel();;
        for(int i=0; i< columnNames.length; i++){
            defaultTableModel.addColumn(columnNames[i]);
        }
        jTable = new JTable(defaultTableModel);
    }

    public JTable getTable() {
        return jTable;
    }

    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }
}
