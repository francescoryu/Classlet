package main.view;

import main.data.DataHandler;
import main.model.Schueler;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

public class SpielGUI extends JFrame {
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

        fr.setVisible(true);
    }


    private void addListeners(int schuelerIndex, Vector<Schueler> schuelerListe){
        stop.addActionListener(new StopButton());
        next.addActionListener(new NextButton(schuelerIndex, schuelerListe));
    }


    class StopButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //UebersichtGUI
            fr.dispose();
        }
    }

    class NextButton implements ActionListener{
        DataHandler dataHandler = DataHandler.getInstance();
        int schuelerIndex;
        Vector<Schueler> schuelerListe;

        public NextButton(int schuelerIndex,Vector<Schueler> schuelerListe){
            this.schuelerIndex = schuelerIndex;
            this.schuelerListe = schuelerListe;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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
                //UebersichtGUI
                fr.dispose();
            }
        }
    }

}