package gui.my_components;

import javax.swing.*;
import java.awt.*;

public class MainButton extends JButton {

    public MainButton(String titolo){
        super(titolo);
        setFocusable(false);
        setContentAreaFilled(false);
        //setBackground(Color.red);
        //setForeground(Color.white);
        setFont(new Font("Arial", Font.PLAIN, 15));
    }
}
