package main.controller;

import main.data.DataHandler;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class Classlet {
    private DataHandler dataHandler;

    public Classlet(){
        dataHandler = DataHandler.getInstance();
    }





    public static void main(String[] args) {
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
