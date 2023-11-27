package gui.my_components;

import javax.swing.*;
import java.awt.*;

public class DataButton extends JButton {

    public DataButton(String titolo){
        super(titolo);
        setFocusable(false);
        setContentAreaFilled(false);
        setFont(new Font("Arial", Font.PLAIN, 12));
    }
}
