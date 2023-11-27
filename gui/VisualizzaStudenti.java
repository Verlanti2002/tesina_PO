package gui;

import gui.my_components.DataPanel;
import gui.my_components.MainFrame;
import gui.my_components.MainPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VisualizzaStudenti {

    public VisualizzaStudenti(Applicazione applicazione){

        MainFrame mainFrame = new MainFrame("Visualizza Studenti");
        DataPanel dataPanel = new DataPanel();
        dataPanel.setPreferredSize(new Dimension(300,200));

        String[] columnNames = {"Matricola", "Nome", "Cognome"};
        Object[][] data = new Object[applicazione.getStudenti().size()][];

        for(int i=0; i<applicazione.getStudenti().size(); i++){
            for(int j=0; j<columnNames.length; j++){
                data[i][j] = applicazione.getStudenti().get(i).getMatricola();
                data[i][j] = applicazione.getStudenti().get(i).getNome();
                data[i][j] = applicazione.getStudenti().get(i).getCognome();
            }
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable jTable = new JTable(defaultTableModel);

        dataPanel.add(jTable);
        mainFrame.add(dataPanel);
        mainFrame.pack();
    }
}
