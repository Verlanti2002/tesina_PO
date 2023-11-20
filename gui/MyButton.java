package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MyButton extends JButton {

    public MyButton(String titolo){
        super(titolo);
        this.setFocusable(false);
        /*ImageIcon = new ImageIcon("img.png");
        this.setIcon(imageIcon);
        this.setIconTextGap(5);*/
        setSize(new Dimension(10,10));
        setContentAreaFilled(false);
        setFont(new Font("Arial", Font.PLAIN, 12));
    }
}
