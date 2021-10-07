package main.view;

import main.model.History;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class HistoryGUI extends JFrame {

    private JScrollPane jScrollPane;
    private JPanel panel;
    private JLabel title;

    HistoryGUI(Vector<History> histories) {
        int anzahl = histories.size();

        panel = new JPanel(new GridLayout(anzahl + 1, 3,20,10));
        title = new JLabel();
        title.setText("Classlet");
        title.setFont(new Font("Classlet", Font.PLAIN, 60));

        JLabel datum = new JLabel("DATUM");
        JLabel klassen = new JLabel("KLASSEN");
        JLabel quote = new JLabel("RICHTIG-QUOTE");

        datum.setFont(datum.getFont().deriveFont(Font.BOLD,20f));
        klassen.setFont(datum.getFont().deriveFont(Font.BOLD, 20f));
        quote.setFont(datum.getFont().deriveFont(Font.BOLD, 20f));

        panel.add(datum);
        panel.add(klassen);
        panel.add(quote);


        for (int i = 0; i < anzahl; i++) {
            String klassenListe = "";
            for (String klasse : histories.get(i).getKlassenNamen()){
                if (klassenListe == "") {
                    klassenListe += klasse;
                }
                else {
                    klassenListe += "," + klasse;
                }

            }
            panel.add(new JLabel(histories.get(i).getDatum()));
            panel.add(new JLabel(klassenListe));
            panel.add(new JLabel(histories.get(i).getProzent() + "%"));
        }

        jScrollPane = new JScrollPane(panel);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setSize(840, 470);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(title, BorderLayout.NORTH);
        this.setVisible(true);
    }

}