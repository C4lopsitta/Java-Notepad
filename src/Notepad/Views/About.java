package Notepad.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JDialog implements ActionListener {
    public About(Component parent) {
        setModal(true);
        setLocationRelativeTo(parent);
        setTitle("About");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("about"));

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
