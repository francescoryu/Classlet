package main.view;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @author Francesco Ryu
 * @version 1.0
 * @since 05.10.2021
 */

import main.controller.Classlet;
import main.data.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

public class HauptseiteGUI extends JFrame {

    private StartButtonListener startButtonListener;
    private HistoryButtonListener historyButtonListener;
    private UebersichtButtonListener uebersichtButtonListener;
    private JButton start;
    private JButton klassenliste;
    private JButton history;
    Vector<JCheckBox> checkBoxes;
    JCheckBox gesicht;
    private JTextField txtpn;
    private JPanel buttonPanel;
    private JPanel cbPanel;
    private JLabel titel;


    public HauptseiteGUI(int schuelerIndex, String[] alleKlassenNamen) {
        buttonPanel = new JPanel();
        cbPanel = new JPanel(new GridLayout(alleKlassenNamen.length,1));
        titel = new JLabel("", SwingConstants.CENTER);
        start = new JButton("Start");
        klassenliste = new JButton("Klassenliste");
        history = new JButton("History");
        checkBoxes = new Vector<>(alleKlassenNamen.length);
        txtpn = new JTextField();
        gesicht = new JCheckBox("Gesicht");

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


        for (String klassenName : alleKlassenNamen){
            JCheckBox jCheckBox = new JCheckBox(klassenName);
            cbPanel.add(jCheckBox);
            checkBoxes.add(jCheckBox);
        }

        this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
        this.getContentPane().add(cbPanel, BorderLayout.WEST);
        this.getContentPane().add(titel, BorderLayout.NORTH);
        this.getContentPane().add(gesicht, BorderLayout.EAST);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addListeners(schuelerIndex);

        setResizable(false);
        setSize(840, 470);
        setVisible(true);

    }

    private void addListeners(int schuelerIndex){
        startButtonListener = new StartButtonListener(schuelerIndex);
        uebersichtButtonListener = new UebersichtButtonListener();
        historyButtonListener = new HistoryButtonListener();

        start.addActionListener(startButtonListener);
        klassenliste.addActionListener(uebersichtButtonListener);
        history.addActionListener(historyButtonListener);
    }

    private String[] klassenNamen(){
        boolean ausgewaehlt = false;
        Vector<String> tmpKlassenNamen = new Vector<>();
        for (int i = 0; i < checkBoxes.size(); i++){
            if (checkBoxes.get(i).isSelected()){
                tmpKlassenNamen.add(checkBoxes.get(i).getText());
                ausgewaehlt = true;
            }
        }

        if (ausgewaehlt){
            String[] klassenNamen = Arrays.copyOf(tmpKlassenNamen.toArray(), tmpKlassenNamen.size(), String[].class);
            for (String klasse : klassenNamen){
                DataHandler.readBilder(klasse);
            }
            return klassenNamen;
        }
        return new String[0];
    }

    class StartButtonListener implements ActionListener{
        private int schuelerIndex;

        public StartButtonListener(int schuelerIndex){
            this.schuelerIndex = schuelerIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int modus = 1;
            if (gesicht.isSelected()){
                modus = 2;
            }

            String[] klassenNamen = klassenNamen();
            if (klassenNamen.length > 0){
                new SpielGUI(Classlet.neueRandomSchuelerListe(klassenNamen), schuelerIndex, modus);
            }
        }
    }

    class HistoryButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            DataHandler.readHistory();
            new HistoryGUI(DataHandler.getHistories());
        }
    }

    class UebersichtButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] klassenNamen = klassenNamen();
            if (klassenNamen.length > 0){
                new UebersichtGUI(DataHandler.schuelerListe(klassenNamen), klassenNamen);
            }
        }
    }
}