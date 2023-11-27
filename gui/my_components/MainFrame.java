package gui.my_components;

import gui.Terminator;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(String titolo){
        super(titolo);
        setBounds(700,200,600,600);
        addWindowListener(new Terminator());
        setVisible(true);
    }
}
