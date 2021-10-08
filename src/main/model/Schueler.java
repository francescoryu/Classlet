package main.model;

/**
 * Modelklassen der Schüler
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class Schueler {
    private String path;
    private String klassenName;
    private String vorname;
    private String nachname;
    private int notizID;


    //Konstruktoren

    public Schueler(){
        this.notizID = 0;
    }

    public Schueler(String path, String klassenName, String vorname, String nachname){
        this();
        this.path = path;
        this.klassenName = klassenName;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Schueler(String path, String klassenName, String vorname, String nachname, int notizID){
        this(path, klassenName, vorname, nachname);
        this.notizID = notizID;
    }


    //Getter & Setter

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKlassenName() {
        return klassenName;
    }

    public void setKlassenName(String klasse) {
        this.klassenName = klasse;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getNotizID() {
        return notizID;
    }

    public void setNotizID(int notizID) {
        this.notizID = notizID;
    }
}