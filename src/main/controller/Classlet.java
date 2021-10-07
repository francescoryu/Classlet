package main.controller;

import main.data.DataHandler;
import main.model.Schueler;
import main.view.*;

import java.util.Vector;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class Classlet {
    private DataHandler dataHandler;
    private HauptseiteGUI haupseiteGUI;
    private String[] klassenliste;

    public Classlet(){
        this.dataHandler = DataHandler.getInstance();
        this.klassenliste = dataHandler.alleKlassenNamen();

        for (String klasse : klassenliste){
            dataHandler.readBilder(klasse);
        }

        haupseiteGUI = new HauptseiteGUI(0, klassenliste);
    }

    public static Vector<Schueler> neueRandomSchuelerListe(String[] klassenNamen){
        return DataHandler.getInstance().randomSchuelerListe(klassenNamen);
    }


    public static void main(String[] args) {
        new Classlet();
    }
}
