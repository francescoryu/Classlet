package main.view;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.data.DataHandler;
import main.model.Schueler;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class SpielGUI extends JFrame {
    public StopButton stopButtonListener;
    public NextButton nextButtonListener;
    public AuswahlButton auswahlButtonListener;
    JFrame fr;
    public JButton b1, b2, b3, b4;
    JButton stop, next;
    JLabel lbl;
    public JLabel jp;
    public JLabel cnt;

    public SpielGUI(Vector<Schueler> schuelerListe, int schuelerIndex) {
        fr = new JFrame();
        fr.setLayout(null);
        fr.setSize(600, 600);

        jp = new JLabel();
        fr.add(jp);

        cnt = new JLabel();
        fr.add(cnt);

        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container c = fr.getContentPane();

        c.setBackground(Color.cyan);


        b1 = new JButton("1");
        b1.setBounds(130, 350, 100, 30);
        b2 = new JButton("2");
        b2.setBounds(360, 350, 100, 30);
        b3 = new JButton("3");
        b3.setBounds(130, 400, 100, 30);
        b4 = new JButton("4");
        b4.setBounds(360, 400, 100, 30);

        stop = new JButton("stop");
        stop.setBounds(130, 500, 100, 30);
        next = new JButton("next");
        next.setBounds(360, 500, 100, 30);

        jp.setBackground(Color.white);
        jp.setBounds(200, 100, 200, 200);

        cnt.setBackground(Color.white);
        cnt.setName(schuelerIndex + 1 + "/" + schuelerListe.size());
        cnt.setBounds(450, 20, 100, 30);
        fr.add(cnt);

        lbl = new JLabel("");
        lbl.setText("        Classlet");
        lbl.setBounds(250, 10, 100, 30);

        fr.add(b1);
        fr.add(b2);
        fr.add(b3);
        fr.add(b4);
        fr.add(stop);
        fr.add(next);

        fr.add(lbl);

        addListeners(schuelerIndex, schuelerListe);
        nextButtonListener.actionPerformed(null);

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

                DataHandler.writeHistory(klassenNamen, prozentRichtig);

            }
            //UebersichtGUI
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

                b1.setBackground(new JButton().getBackground());
                b2.setBackground(new JButton().getBackground());
                b3.setBackground(new JButton().getBackground());
                b4.setBackground(new JButton().getBackground());

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

}