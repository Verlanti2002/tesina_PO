package gui;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame(String titolo){
        super(titolo);
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new Terminator());
        this.setVisible(true);
    }
}
