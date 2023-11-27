package gui.my_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MyLabel extends JLabel {

    public MyLabel(String text){

        super(text);

        setPreferredSize(new Dimension(100, 200));
        //setFont(new Font("Arial", Font.BOLD, 12));
    }
}
