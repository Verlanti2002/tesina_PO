package gui.my_components;

import gui.Terminator;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame(String titolo){
        super(titolo);
        setBounds(700,200,600,600);
        addWindowListener(new Terminator());
        setVisible(true);
    }
}
