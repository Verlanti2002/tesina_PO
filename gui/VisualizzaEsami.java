package gui;

import gui.my_components.DataPanel;
import gui.my_components.MainFrame;
import gui.my_components.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VisualizzaEsami {

    public VisualizzaEsami(Applicazione applicazione){
        MainFrame mainFrame = new MainFrame("Visualizza Esami");
        DataPanel dataPanel = new DataPanel();

        String[] columnNames = {"Matricola", "Esame", "Voto", "Lode", "CFU"};
        Object[][] data = new Object[applicazione.getEsami().size()][];

        for(int i=0; i<applicazione.getEsami().size(); i++){
            for(int j=0; j<columnNames.length; j++){
                data[i][j] = applicazione.getEsami().get(i).getStudente().getMatricola();
                data[i][j] = applicazione.getEsami().get(i).getNome();
                data[i][j] = applicazione.getEsami().get(i).getVoto();
                data[i][j] = applicazione.getEsami().get(i).getLode();
                data[i][j] = applicazione.getEsami().get(i).getCfu();
            }
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable jTable = new JTable(defaultTableModel);

        dataPanel.add(jTable);
        mainFrame.add(dataPanel);
        mainFrame.pack();
    }
}
