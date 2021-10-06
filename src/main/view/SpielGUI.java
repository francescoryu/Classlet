package main.view;

import main.data.DataHandler;
import main.model.Schueler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * GUI für das Quiz, inklusive anonyme Klassen als Controller
 *
 * @author Martin Düppenbecker
 * @author Stefan Thomsen
 * @version 2.0
 * @since 04.10.2021
 */

public class SpielGUI extends JFrame {
    private StopButton stopButtonListener;
    private NextButton nextButtonListener;
    private AuswahlButton auswahlButtonListener;
    private JFrame fr;
    private JButton b1, b2, b3, b4;
    private JButton stop, next;
    private JLabel lbl;
    public JLabel jp;
    public JLabel cnt;
    private JPanel contentPanel, optionPanel, btnPanel, imgPanel;

    public SpielGUI(Vector<Schueler> schuelerListe, int schuelerIndex) {
        //content
        fr = new JFrame("Classlet - Learn");
        fr.setLayout(null);
        fr.setSize(840,600);
        fr.setLocationRelativeTo(null);

        lbl = new JLabel("Classlet");
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setFont(new Font("Monospace", Font.BOLD, 20));

        jp = new JLabel();
        jp.setBackground(Color.white);
        jp.setSize(200, 200);


        cnt = new JLabel("1/24");
        cnt.setBackground(Color.white);
        cnt.setFont(new Font("Monospace", Font.BOLD, 20));
        cnt.setName(schuelerIndex + 1 + "/" + schuelerListe.size());
        cnt.setSize(100, 30);


        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        stop = new JButton("stop");
        next = new JButton("next");

        next.setForeground(Color.white);
        next.setBackground(new Color(60, 207, 207));
        next.setFont(new Font("Monospace", Font.BOLD, 20));
        next.setBorderPainted(false);

        stop.setForeground(Color.white);
        stop.setBackground(new Color(60, 207, 207));
        stop.setFont(new Font("Monospace", Font.BOLD, 20));
        stop.setBorderPainted(false);

        b1.setBackground(Color.white);
        b2.setBackground(Color.white);
        b3.setBackground(Color.white);
        b4.setBackground(Color.white);




        //Panel
        optionPanel = new JPanel(new GridLayout(2, 2, 100, 50));
        optionPanel.add(b1);
        optionPanel.add(b2);
        optionPanel.add(b3);
        optionPanel.add(b4);
        optionPanel.setBackground(Color.white);


        btnPanel = new JPanel(new FlowLayout(1, 50, 20));
        btnPanel.setBackground(Color.white);
        btnPanel.add(stop);
        btnPanel.add(next);

        imgPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        imgPanel.setBackground(Color.white);

        //Classlet name
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx= 1;
        gbc.insets = new Insets(0,40,0,0);
        imgPanel.add(lbl, gbc);

        //Counter
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx=0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        imgPanel.add(cnt);

        //BILD
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx=1;
        gbc.ipady = 100;
        gbc.anchor= GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        imgPanel.add(jp, gbc);

        contentPanel = new JPanel(new BorderLayout(5, 5));
        fr.setLayout(new BorderLayout());
        fr.add(contentPanel, BorderLayout.CENTER);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
        contentPanel.setBackground(Color.white);
        contentPanel.add(optionPanel, BorderLayout.CENTER);
        contentPanel.add(btnPanel, BorderLayout.SOUTH);
        contentPanel.add(imgPanel, BorderLayout.NORTH);

        addListeners(schuelerIndex, schuelerListe);
        nextButtonListener.actionPerformed(null);

        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setVisible(true);
    }


    private void addListeners(int schuelerIndex, Vector<Schueler> schuelerListe){
        auswahlButtonListener = new AuswahlButton();
        stopButtonListener = new StopButton();
        nextButtonListener = new NextButton(schuelerIndex, schuelerListe);


        b1.addActionListener(auswahlButtonListener);
        b2.addActionListener(auswahlButtonListener);
        b3.addActionListener(auswahlButtonListener);
        b4.addActionListener(auswahlButtonListener);

        stop.addActionListener(new StopButton());
        next.addActionListener(nextButtonListener);
    }

    class AuswahlButton implements ActionListener{
        int laenge = 0;
        int richtige = 0;

        public int getLaenge() {
            return laenge;
        }

        public int getRichtige() {
            return richtige;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String schuelerName = ((JButton)e.getSource()).getText();
            Schueler richtigSchueler = nextButtonListener.getSchuelerListe().get(nextButtonListener.getSchuelerIndex() - 1);
            String richtiSchuelerName = richtigSchueler.getVorname() + " " + richtigSchueler.getNachname();

            if (b1.getText().equals(richtiSchuelerName)) b1.setBackground(Color.GREEN);
            else if (b2.getText().equals(richtiSchuelerName)) b2.setBackground(Color.GREEN);
            else if (b3.getText().equals(richtiSchuelerName)) b3.setBackground(Color.GREEN);
            else if (b4.getText().equals(richtiSchuelerName)) b4.setBackground(Color.GREEN);

            if (!schuelerName.equals(richtiSchuelerName)){
                ((JButton)e.getSource()).setBackground(Color.RED);
            }
            else {
                richtige++;
            }
            laenge++;

            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(false);

            nextButtonListener.setSelected(true);
        }


    }

    class StopButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (nextButtonListener.schuelerIndex >= 1){
                DataHandler dataHandler = DataHandler.getInstance();

                int prozentRichtig = (int)Math.round((100 / auswahlButtonListener.laenge * auswahlButtonListener.richtige));
                String[] klassenNamen = klassenNamen(nextButtonListener.getSchuelerListe());

                dataHandler.writeHistory(klassenNamen, prozentRichtig);

            }
            new HistoryGUI(DataHandler.getHistories());
            fr.dispose();
        }


        private String[] klassenNamen(Vector<Schueler> schuelers){
            Vector<String> klassenNamen = new Vector<>();

            for (Schueler schueler : schuelers){
                if (!klassenNamen.contains(schueler.getKlassenName())){
                    klassenNamen.add(schueler.getKlassenName());
                }
            }
            Object[] tmpArr = klassenNamen.toArray();

            return Arrays.copyOf(tmpArr, tmpArr.length, String[].class);
        }
    }

    class NextButton implements ActionListener{
        private DataHandler dataHandler = DataHandler.getInstance();
        private boolean selected = false;
        private int schuelerIndex;
        private Vector<Schueler> schuelerListe;

        public NextButton(int schuelerIndex,Vector<Schueler> schuelerListe){
            this.schuelerIndex = schuelerIndex;
            this.schuelerListe = schuelerListe;
        }

        public int getSchuelerIndex() {
            return schuelerIndex;
        }

        public Vector<Schueler> getSchuelerListe() {
            return schuelerListe;
        }

        public void setSelected(Boolean selected){
            this.selected = selected;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selected || e == null){
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
                b4.setEnabled(true);

                b1.setBackground(Color.white);
                b2.setBackground(Color.white);
                b3.setBackground(Color.white);
                b4.setBackground(Color.white);

                b1.setBorder(new RoundedBorder(20));
                b2.setBorder(new RoundedBorder(20));
                b3.setBorder(new RoundedBorder(20));
                b4.setBorder(new RoundedBorder(20));

                if (schuelerListe.size() > schuelerIndex){
                    String[] namen = dataHandler.auswahlDerNamen(schuelerListe,schuelerListe.get(schuelerIndex),4);
                    b1.setText(namen[0]);
                    b2.setText(namen[1]);
                    b3.setText(namen[2]);
                    b4.setText(namen[3]);

                    jp.setIcon(DataHandler.imageFromSchueler(schuelerListe.get(schuelerIndex)));
                    schuelerIndex++;
                    cnt.setText(schuelerIndex + "/" + schuelerListe.size());
                }
                else {
                    stopButtonListener.actionPerformed(null);
                }

                setSelected(false);
            }
        }
    }

    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

}