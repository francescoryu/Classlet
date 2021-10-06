package main.view;

/**
 * Beschreibung der Klasse
 *
 * @author Francesco Ryu
 * @version 1.0
 * @since 05.10.2021
 */

import main.model.Schueler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class HauptseiteGUI extends JFrame {

    private StartButtonListener startButtonListener;
    private JButton start;
    private JButton klassenliste;
    private JButton history;
    private JCheckBox cb;
    private JTextField txtpn;
    private JPanel buttonPanel;
    private JPanel cbPanel;
    private JPanel eastPanel;
    private JLabel titel;


    public HauptseiteGUI(Vector<Schueler> schuelerListe, int schuelerIndex) {

        buttonPanel = new JPanel();
        cbPanel = new JPanel();
        titel = new JLabel("", SwingConstants.CENTER);
        start = new JButton("Start");
        klassenliste = new JButton("Klassenliste");
        history = new JButton("History");
        cb = new JCheckBox();
        txtpn = new JTextField();

        titel.setText("Classlet");
        titel.setFont(new Font("", Font.PLAIN, 60));

        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        klassenliste.setAlignmentX(Component.CENTER_ALIGNMENT);
        history.setAlignmentX(Component.CENTER_ALIGNMENT);
        titel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(titel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        buttonPanel.add(start);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(klassenliste);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(history);


        this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
        this.getContentPane().add(cbPanel, BorderLayout.WEST);
        this.getContentPane().add(titel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cb.setText("Test");

        cbPanel.setLayout(new BoxLayout(cbPanel, BoxLayout.Y_AXIS));
        cbPanel.setLayout(new BorderLayout());
        cbPanel.add(cb, BorderLayout.EAST);

        addListeners(schuelerListe, schuelerIndex);

        setResizable(false);
        setSize(840, 470);
        setVisible(true);

    }

    private void addListeners(Vector<Schueler> schuelerListe, int schuelerIndex){
        startButtonListener = new StartButtonListener(schuelerListe, schuelerIndex);
        start.addActionListener(startButtonListener);
    }


    class StartButtonListener implements ActionListener{
        private Vector<Schueler> schuelerListe;
        private int schuelerIndex;

        public StartButtonListener(Vector<Schueler> schuelerListe, int schuelerIndex){
            this.schuelerListe = schuelerListe;
            this.schuelerIndex = schuelerIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new SpielGUI(schuelerListe, schuelerIndex);
            //new HauptseiteGUI(schuelerListe, schuelerIndex);
        }
    }

}