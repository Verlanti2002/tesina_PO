package gui;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame(String titolo){
        super(titolo);
        setBounds(200,100,600,600);
        addWindowListener(new Terminator());
        setVisible(true);
    }
}
