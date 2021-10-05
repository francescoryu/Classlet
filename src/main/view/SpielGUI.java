package main.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class SpielGUI implements ActionListener {
    JFrame fr;
    JButton b1, b2, b3, b4;
    JButton stop, next;
    JLabel lbl;
    JPanel jp;
    JPanel cnt;

    SpielGUI() {
        fr = new JFrame();
        fr.setLayout(null);
        fr.setSize(600, 600);

        jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fr.add(jp);

        cnt = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
        cnt.setBounds(450, 20, 100, 30);
        fr.add(cnt);

        lbl = new JLabel("");
        lbl.setText("        Classlet");
        lbl.setBounds(250, 10, 100, 30);

        fr.add(b1);
        b1.addActionListener(this);
        fr.add(b2);
        b2.addActionListener(this);
        fr.add(b3);
        b3.addActionListener(this);
        fr.add(b4);
        b4.addActionListener(this);
        fr.add(stop);
        stop.addActionListener(this);
        fr.add(next);
        next.addActionListener(this);

        fr.add(lbl);

        fr.setVisible(true);
    }

    public static void main(String s[]) {
        new SpielGUI();
    }

    public void actionPerformed(ActionEvent e) {

    }
}