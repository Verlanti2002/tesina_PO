package GUI;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame(){
        super("Gestione esami");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);

        MyPanel myPanel = new MyPanel();
        this.add(myPanel);
        this.pack();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
