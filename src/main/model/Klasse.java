package main.model;

import java.util.HashMap;

/**
 * Modelklasse der Klasse, die mit Schülern gefüllt ist
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class Klasse {
    private String name;
    private HashMap<String, Schueler> schuelers;

    public Klasse(String name){
        this.name = name;
        schuelers = new HashMap<>();
    }

    public void addSchueler(Schueler schueler){
        schuelers.put(schueler.getVorname() + "_" +schueler.getNachname(),schueler);
    }


    //Getter & Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Schueler> getSchuelers() {
        return schuelers;
    }
}