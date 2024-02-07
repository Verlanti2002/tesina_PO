package gui;

import gui.my_components.DataPanel;
import gui.my_components.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VisualizzaStudenti {

    public VisualizzaStudenti(Applicazione applicazione){

        MainFrame mainFrame = new MainFrame("Visualizza Studenti");
        DataPanel dataPanel = new DataPanel();
        dataPanel.setPreferredSize(new Dimension(600,400));

        JLabel title_l = new JLabel("Studenti registrati");
        title_l.setBounds(250,10,150,20);

        String[] columnNames = {"Matricola", "Nome", "Cognome"};
        Object[][] data = new Object[applicazione.getStudenti().size()][columnNames.length];

        for(int i=0; i<applicazione.getStudenti().size(); i++){
            data[i][0] = applicazione.getStudenti().get(i).getMatricola();
            data[i][1] = applicazione.getStudenti().get(i).getNome();
            data[i][2] = applicazione.getStudenti().get(i).getCognome();
        }

        for(int i=0; i<applicazione.getStudenti().size(); i++){
            System.out.println(data[i][0]);
            System.out.println(data[i][1]);
            System.out.println(data[i][2]);
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        JTable jTable = new JTable(defaultTableModel);
        jTable.setBounds(100, 40, 400, 300);
        JScrollPane jScrollPane = new JScrollPane(jTable);

        dataPanel.add(title_l);
        dataPanel.add(jTable);
        mainFrame.add(jScrollPane);
        mainFrame.add(dataPanel);
        mainFrame.pack();
    }
}
