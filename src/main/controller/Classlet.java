package main.controller;

import main.data.DataHandler;
import main.view.*;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class Classlet {
    private DataHandler dataHandler;

    private SpielGUI spielGUI;

    public Classlet(){
        dataHandler = DataHandler.getInstance();
        String[] klassenliste = new String[1];
        klassenliste[0] = "IM21a";
        dataHandler.readBilder(klassenliste[0]);
        spielGUI = new SpielGUI(dataHandler.randomSchuelerListe(klassenliste),0);
    }





    public static void main(String[] args) {
        DataHandler handler = DataHandler.getInstance();
        new Classlet();
        /*
        System.out.println(DataHandler.getProperty("resourcePath"));
        DataHandler handler = DataHandler.getInstance();
        handler.readBilder("IM21a");

        System.out.println(DataHandler.getKlassen().get("IM21a").getName());
        DataHandler.changeNotizen(DataHandler.getKlassen().get("IM21a").getSchuelers().get("Lucas_Blom"), "Testtestetst");
        DataHandler.changeNotizen(DataHandler.getKlassen().get("IM21a").getSchuelers().get("Marco_Spina"), "Bitet");

         */
    }
}
