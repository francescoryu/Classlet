package main.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @author Stefan Thomsen
 * @version 1.0
 * @since 04.10.2021
 */

public class UebersichtGUI extends JFrame{
    JLabel title, name, img;
    JTextArea desc;
    JPanel textPanel, descPanel, titlePanel, contentPanel, allStudentPanel;

    public UebersichtGUI(){
        setSize(840,900);
        setLocationRelativeTo(null);

        title = new JLabel("Classlet");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Monospace", Font.BOLD, 20));

        name = new JLabel("Name von Schüler");
        name.setFont(new Font("Monospace", Font.BOLD, 20));

        img = new JLabel("BILD");

        desc = new JTextArea("Schülerbeschreibung");

        //Panels von der Mitte nach Aussen
        textPanel = new JPanel(new BorderLayout( 2, 1));
        textPanel.add(name, BorderLayout.CENTER);
        textPanel.add(desc, BorderLayout.SOUTH);


        descPanel = new JPanel();
        descPanel.add(textPanel, BorderLayout.WEST);
        descPanel.add(img, BorderLayout.EAST);

        // @Martin musch vlt BorderLayout ändere. Weiss nöd wie du es ahzeige wötsch
        allStudentPanel = new JPanel(new BorderLayout());
        allStudentPanel.add(descPanel, BorderLayout.CENTER);

        titlePanel = new JPanel(new BorderLayout(150,0));
        titlePanel.add(title, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(allStudentPanel);



        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        UebersichtGUI gui = new UebersichtGUI();
    }
}
