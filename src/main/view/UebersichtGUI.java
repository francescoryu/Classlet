package main.view;

import main.data.DataHandler;
import main.model.Schueler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @author Stefan Thomsen
 * @version 1.0
 * @since 04.10.2021
 */

public class UebersichtGUI extends JFrame{
    private JLabel title;
    private JPanel titlePanel, contentPanel, allStudentPanel;
    private JScrollPane scrollPane;
    private JButton printToHTML;
    private DescriptionFocusListener descriptionFocusListener;
    private DescriptionKeyListener descriptionKeyListener;
    private HtmlButtonListener htmlButtonListener;
    Vector<JTextField> descriptionsVector;

    public UebersichtGUI(Vector<Schueler> schuelerListe, String[] klassenNamen){
        setSize(840,900);
        setLocationRelativeTo(null);

        descriptionsVector = new Vector<>();

        title = new JLabel("Classlet");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Monospace", Font.BOLD, 30));


        allStudentPanel = new JPanel(new GridLayout(schuelerListe.size() + klassenNamen.length,2));


        String jetztigeklasse = "";
        for (Schueler schueler : schuelerListe){
            if (!(jetztigeklasse.equals(schueler.getKlassenName()))){
                JLabel klassennamenLaben = new JLabel(schueler.getKlassenName());
                klassennamenLaben.setFont(new Font("Monospace", Font.BOLD, 20));
                allStudentPanel.add(klassennamenLaben);
                allStudentPanel.add(new JPanel());
                jetztigeklasse = schueler.getKlassenName();
            }
            JLabel name = new JLabel(schueler.getVorname() + " " + schueler.getNachname());
            name.setFont(new Font("Monospace", Font.BOLD, 20));

            JLabel img = new JLabel(DataHandler.imageFromSchueler(schueler));

            JTextField desc = new JTextField("Notiz hinzufügen");
            desc.setName(schueler.getPath());
            if (schueler.getNotizID() != 0){
                desc.setText(DataHandler.notizenText(schueler));
            }
            descriptionsVector.add(desc);

            //Panels von der Mitte nach Aussen
            JPanel textPanel = new JPanel(new BorderLayout( 2, 1));
            textPanel.add(name, BorderLayout.CENTER);
            textPanel.add(desc, BorderLayout.SOUTH);

            JPanel descPanel = new JPanel();
            descPanel.add(textPanel, BorderLayout.WEST);

            allStudentPanel.add(descPanel);
            allStudentPanel.add(img);
        }

        scrollPane = new JScrollPane(allStudentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        printToHTML = new JButton("Zu HTML-Datei konvertieren");

        titlePanel = new JPanel(new BorderLayout(150,0));
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(printToHTML, BorderLayout.EAST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane);

        addListeners(klassenNamen);

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addListeners(String[] klassenNamen){
        descriptionFocusListener = new DescriptionFocusListener();
        descriptionKeyListener = new DescriptionKeyListener();
        htmlButtonListener = new HtmlButtonListener(klassenNamen);
        for (JTextField desc : descriptionsVector){
            desc.addFocusListener(descriptionFocusListener);
            desc.addActionListener(descriptionKeyListener);
        }
        printToHTML.addActionListener(htmlButtonListener);
    }

    class HtmlButtonListener implements ActionListener{
        private String[] klassenNamen;

        public HtmlButtonListener(String[] klassenNamen){
            this.klassenNamen = klassenNamen;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DataHandler.writeHTML(klassenNamen);
        }
    }

    class DescriptionKeyListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           descriptionFocusListener.focusLostAction((JTextField) e.getSource());
        }
    }

    class DescriptionFocusListener implements FocusListener{

        @Override
        public void focusGained(FocusEvent e) {
            JTextField desc = (JTextField) e.getSource();
            if (desc.getText().equals("Notiz hinzufügen")){
                desc.setText("");
            }
        }

        public void focusLostAction(JTextField desc){
            desc.getText().trim();
            if (!desc.getText().equals("") && !desc.getText().isEmpty() && !desc.getText().equals("Notiz hinzufügen")){
                DataHandler.changeNotizen(DataHandler.schuelerFromImagepath(desc.getName()), desc.getText());
            }
            else {
                desc.setText("Notiz hinzufügen");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField desc = (JTextField)e.getSource();
            desc.getText().trim();
            if (!desc.getText().equals("") && !desc.getText().isEmpty() && desc.getText().equals("Notiz hinzufügen")){
                DataHandler.changeNotizen(DataHandler.schuelerFromImagepath(desc.getName()), desc.getText());
            }
            else {
                desc.setText("Notiz hinzufügen");
            }
        }
    }

}
