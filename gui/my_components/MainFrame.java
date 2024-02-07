package gui.my_components;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(String titolo){
        super(titolo);
        setBounds(700,200,800,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
