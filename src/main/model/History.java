package main.model;

/**
 * Modelklasse der History, benötigt, damit einfach zu JSON geschrieben werden kann
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 06.10.2021
 */

public class History {
    private String datum;
    private String[] klassenNamen;
    private int prozent;

    /**
     * Default-Konstruktor, damit das JSON-Einlesen funktioniert
     */
    public History(){

    }

    public History(String datum, String[] klassenNamen, int prozent){
        this.datum = datum;
        this.klassenNamen = klassenNamen;
        this.prozent = prozent;
    }

    //Getter & Setter

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String[] getKlassenNamen() {
        return klassenNamen;
    }

    public void setKlassenNamen(String[] klassenNamen) {
        this.klassenNamen = klassenNamen;
    }

    public int getProzent() {
        return prozent;
    }

    public void setProzent(int prozent) {
        this.prozent = prozent;
    }
}
