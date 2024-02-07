package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame{

    public Menu(Applicazione applicazione){
        setTitle("Gestione Esami");
        setSize(600,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;

        mainPanel.add(new JLabel("<html><h1><strong><i>Benvenuto nella mia nuova applicazione!</i></strong></h1><hr></html>"), gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton jButton1 = new JButton("Gestione degli esami");
        JButton jButton2 = new JButton("Caricamento esami");
        JButton jButton3 = new JButton("Salvataggio esami");
        JButton jButton4 = new JButton("Dati statistici");
        JButton jButton5 = new JButton("Tabella degli esami");

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestioneEsami gestioneEsami = new GestioneEsami(applicazione);
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.add(jButton1, gridBagConstraints);
        buttonPanel.add(jButton2, gridBagConstraints);
        buttonPanel.add(jButton3, gridBagConstraints);
        buttonPanel.add(jButton4, gridBagConstraints);
        buttonPanel.add(jButton5, gridBagConstraints);

        gridBagConstraints.weighty = 1;
        mainPanel.add(buttonPanel, gridBagConstraints);

        // Aggiunta del pannello principale al frame
        add(mainPanel);

        // Imposta la finestra al centro dello schermo
        setLocationRelativeTo(null);

        // Rendi la finestra visibile
        setVisible(true);
    }
}
