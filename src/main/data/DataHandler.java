package main.data;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import main.model.History;
import main.model.Klasse;
import main.model.Schueler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * DataHandler für das Einlesen der Bilder, Property & History-Datei.
 * Zudem exportiert es ins HTML und schreibt die History-Datei
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 04.10.2021
 */

public class DataHandler {
    private static DataHandler instance;
    private static Vector<Integer> notizenIds = null;
    private static HashMap<String, Klasse> klassen = null;
    private static Vector<History> histories = null;
    private static Properties properties = null;


    private DataHandler(){
        klassen = new HashMap<>();
        notizenIds = new Vector<>();
        histories = new Vector<>();

        readProperties();
    }


    //Methoden

    /**
     * Liest die Properties von config.properties ein und speichert sie in einer Variable
     * @return Properties
     */
    private static Properties readProperties() {
        String propertiesFile = "src/config.properties";
        FileInputStream fileInputStream = null;
        Properties properties = null;
        try {
            fileInputStream = new FileInputStream(propertiesFile);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataHandler.properties = properties;
        return properties;
    }

    /**
     * Liest alle Bilder ein und füllt die Klasse mit den jeweiligen Schülern
     * Zudem, wenn das Bild in einem zusätzlichen Ordner ist, setzt es die Hierarchie richtig um
     * @param klassenName - der Name des Ordners, entspricht dem Klassennamen.
     */
    public static void readBilder(String klassenName){
        File klassenOrdner;
        String pathBilder = getProperty("resourcePath") + klassenName + "/Bilder";

        try {
            klassenOrdner = new File(pathBilder);
            klassen.put(klassenName, new Klasse(klassenName));

            for (File schueler : klassenOrdner.listFiles()){

                //Moodle-Abgaben haben noch einen weiteren Ordner
                if (schueler.isDirectory()){
                    File tmpSchueler = new File(schueler.getPath() + "/" + schueler.list()[0]);
                    Files.move(Paths.get(tmpSchueler.getPath()), Paths.get(pathBilder + "/" + tmpSchueler.getName()));
                    Files.delete(schueler.toPath());
                    schueler = new File(schueler.getParent() + "/" + tmpSchueler.getName());
                }

                String[] attribute = schueler.getName().split("\\.")[0].split("_");

                Schueler schuelerEcht = new Schueler(schueler.getAbsolutePath(), klassenName, attribute[0], attribute[1]);
                if (attribute.length == 3){
                    schuelerEcht.setNotizID(Integer.parseInt(attribute[2]));
                    notizenIds.add(Integer.parseInt(attribute[2]));
                }

                klassen.get(klassenName).addSchueler(schuelerEcht);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * Sucht nach einer neuen NotizID und gibt sie zurück
     * @return notizID - eine neue NotizID
     */
    private static int newNotizID(){
        int notizID = 1;
        while (true){
            if (!notizenIds.contains(notizID)){
                return notizID;
            }
            notizID++;
        }
    }

    /**
     * Löscht, ändert, und erstellt Notizen für den Schüler
     * Kümmert sich auch um die Erstellung der jeweiligen Dateien und Pfäde
     * @param schueler - Das Schüler-Objekt, das geändert werden soll
     * @param notizen - Die neuen Notizen
     */
    public static void changeNotizen(Schueler schueler, String notizen) {
        String pathNotizen = getProperty("resourcePath") + schueler.getKlassenName() + "/Notizen/";
        try {
            File schuelerFile = new File(schueler.getPath());
            if (notizen == "" && schueler.getNotizID() != 0) {   //Notizen werden gelöscht und es gibt auch wirklich Notizen
                pathNotizen += schueler.getNotizID() + ".txt";
                String dateiEndung = schuelerFile.getName().split("\\.")[1];
                String[] attribute = schuelerFile.getName().split("\\.")[0].split("_");

                Files.move(Paths.get(schuelerFile.getPath()), Paths.get(schuelerFile.getParent() + "/" + attribute[0] + attribute[1] + dateiEndung));
                Files.delete(Paths.get(pathNotizen));

                notizenIds.remove(schueler.getNotizID());
                klassen.get(schueler.getKlassenName()).getSchuelers().get(schueler.getVorname() + "_" + schueler.getNachname()).setNotizID(0);
            }

            else if (notizen != ""){   //Neue werden erstellt oder bestehende werden überschrieben
                int newNotizID = schueler.getNotizID();
                if (newNotizID == 0){
                    newNotizID = newNotizID();
                    notizenIds.add(newNotizID);
                }
                pathNotizen += newNotizID + ".txt";
                String dateiEndung = schuelerFile.getName().split("\\.")[1];
                String[] attribute = schuelerFile.getName().split("\\.")[0].split("_");
                String neuerPath = schuelerFile.getParent() + "/" + attribute[0] + "_" + attribute[1] + "_" + newNotizID + "." + dateiEndung;
                neuerPath = Paths.get(neuerPath).toString();
                Files.move(Paths.get(schueler.getPath()), Paths.get(neuerPath));

                schueler.setPath(neuerPath);
                schueler.setNotizID(newNotizID);

                if (Files.notExists(Paths.get(pathNotizen).getParent())) Files.createDirectories(Paths.get(pathNotizen).getParent());
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathNotizen));
                String[] notizenWoerter = notizen.split(" ");
                for (int i = 0; i < notizenWoerter.length; i++) {
                    bufferedWriter.write(notizenWoerter[i]);
                    if (i + 1 < notizenWoerter.length){
                        bufferedWriter.write("_");
                    }
                }
                bufferedWriter.close();
            }

        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Gibt den Text der Notiz eines Schülers zurück.
     * Wenn keine Notiz vorhanden ist, dann wird ein leerer String zurückgegeben
     * @param schueler
     * @return Text der Notiz
     */
    public static String notizenText(Schueler schueler){
        if (schueler.getNotizID() != 0){
            Scanner scanner;
            try {
                File notizFile = new File(getProperty("resourcePath") + schueler.getKlassenName() + "/Notizen/" + schueler.getNotizID() + ".txt");
                scanner = new Scanner(notizFile);
            } catch (FileNotFoundException e){
                e.printStackTrace();
                throw new RuntimeException();
            }
            return scanner.next();
        }
        return "";
    }

    /**
     * Liefert alle im resourse-Folder enthaltenen Klassen
     * @return String[] - alle Klassennamen
     */
    public static String[] alleKlassenNamen(){
        Vector<String> tmpKlassenNamen = new Vector<>();

        for (File file : new File(getProperty("resourcePath")).listFiles()){
            if (file.isDirectory()){
                tmpKlassenNamen.add(file.getName());
            }
        }

        return Arrays.copyOf(tmpKlassenNamen.toArray(),tmpKlassenNamen.size(), String[].class);
    }

    /**
     * Listet alle Schüler der ausgewählten Klassen auf und gibt diese in einem Schueler-Vektor zurück
     * @param klassennamen - String-Array der Namen der Klassen
     * @return alle Schüler der Klassen
     */
    public static Vector<Schueler> schuelerListe(String[] klassennamen){
        for (String klasse : klassennamen){
            readBilder(klasse);
        }
        Vector<Schueler> schuelerListe = new Vector<>();
        for (String klassenname : klassennamen){
            for (Map.Entry<String,Schueler> schuelerSet: klassen.get(klassenname).getSchuelers().entrySet()){
                schuelerListe.add(schuelerSet.getValue());
            }
        }
        return schuelerListe;
    }

    /**
     * Gibt alle Schüler der ausgewählten Klassen in einem gemischten Schueler-Vektor zurück
     * @param klassennamen - String-Array der Namen der Klassen
     * @return alle Schüler der Klassen gemischt
     */
    public static Vector<Schueler> randomSchuelerListe(String[] klassennamen){
        Vector<Schueler> schuelerListe = schuelerListe(klassennamen);
        Collections.shuffle(schuelerListe);

        return schuelerListe;
    }

    /**
     * Gibt eine Liste der Namen, zufällig ausgewählter, und einem spezifizierten, Schüler zurück
     * @param schuelerAuswahl - Der Pool, aus dem die Schüler ausgewählt werden sollen
     * @param richtigSchueler - Der eine richtige Schüler
     * @param anzahl - Die Grösse der Liste, die zurückgegeben wird
     * @return String[] namen - Die Namen der Schüler
     */
    public static String[] auswahlDerNamen(Vector<Schueler> schuelerAuswahl, Schueler richtigSchueler, int anzahl){
        Vector<String> namen = new Vector<>(anzahl);
        Vector<Schueler> schuelerAuswahlShuffled = (Vector<Schueler>) schuelerAuswahl.clone();
        Collections.shuffle(schuelerAuswahlShuffled);
        for (int i = 0; i < anzahl - 1; i++) {
            if (schuelerAuswahlShuffled.get(i) != richtigSchueler){
                namen.add(schuelerAuswahlShuffled.get(i).getVorname() + " " + schuelerAuswahlShuffled.get(i).getNachname());
            }
            else {
                anzahl++;
            }

        }
        namen.add(richtigSchueler.getVorname() + " " + richtigSchueler.getNachname());
        Collections.shuffle(namen);

        return Arrays.copyOf(namen.toArray(), namen.toArray().length, String[].class);
    }

    /**
     * Wie auswahlDerNamen(...), nur anstatt von Namen, gibt es Bilder zurück
     * Zudem wird den einzelnen Bildern als Description, der Name der Schüler angegeben
     * @param schuelerAuswahl - Der Pool, aus dem die Schüler ausgewählt werden sollen
     * @param richtigSchueler - Der eine richtige Schüler
     * @param anzahl - Die Grösse der Liste, die zurückgegeben wird
     * @return ImageIcon[] bilder - Die Bilder der Schüler
     */
    public static ImageIcon[] auswahlDerBilder(Vector<Schueler> schuelerAuswahl, Schueler richtigSchueler, int anzahl){
        Vector<ImageIcon> bilder = new Vector<>(anzahl);
        Vector<Schueler> schuelerAuswahlShuffled = (Vector<Schueler>)schuelerAuswahl.clone();
        Collections.shuffle(schuelerAuswahlShuffled);
        for (int i = 0; i < anzahl - 1; i++) {
            if (schuelerAuswahlShuffled.get(i) != richtigSchueler){
                ImageIcon icon = imageFromSchueler(schuelerAuswahlShuffled.get(i));
                icon.setDescription(schuelerAuswahlShuffled.get(i).getVorname() + " " + schuelerAuswahlShuffled.get(i).getNachname());
                bilder.add(icon);
            }
            else {
                anzahl++;
            }
        }
        ImageIcon richtigSchuelerIcon = imageFromSchueler(richtigSchueler);
        richtigSchuelerIcon.setDescription(richtigSchueler.getVorname() + " " + richtigSchueler.getNachname());
        bilder.add(richtigSchuelerIcon);
        Collections.shuffle(bilder);

        return Arrays.copyOf(bilder.toArray(), bilder.toArray().length, ImageIcon[].class);
    }

    /**
     * Returnt das Bild des Schülers
     * @param schueler - Das Schueler-Objekt
     * @return ImageIcon - Das Bild
     */
    public static ImageIcon imageFromSchueler(Schueler schueler){
        ImageIcon icon = null;
        try{
            icon = new ImageIcon(ImageIO.read(new File(schueler.getPath())));
            icon.setImage(icon.getImage().getScaledInstance(150,150, Image.SCALE_FAST));
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return icon;
    }

    /**
     * Sucht einen Schüler nach dem Attribut path
     * @param path - Der Bilder-Path des Schülers, der gesucht ist
     * @return Schueler - Der gesuchte Schüler
     */
    public static Schueler schuelerFromImagepath(String path){
        for (Klasse klasse : klassen.values()){
            for (Schueler schueler : klasse.getSchuelers().values()){
                if (schueler.getPath().equals(path)){
                    return schueler;
                }
            }
        }
        return null;
    }

    /**
     * Schreibt die neue History ins history.json
     * @param klassen - Die Klassennamen, mit denen gespielt worden ist
     * @param prozent - Die Prozentzahl, der erspielten Richtig-Quote
     */
    public static void writeHistory(String[] klassen, int prozent){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String datum = dateTimeFormatter.format(LocalDateTime.now());

        histories.removeAllElements();
        getHistories().add(new History(datum, klassen, prozent));
        readHistory();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String historyPath = getProperty("resourcePath") + "history.json";

        try {
            fileOutputStream = new FileOutputStream(historyPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getHistories());
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * Liest history.json in den Vector histories ein
     */
    public static void readHistory(){
        String historyPath = getProperty("resourcePath") + "history.json";

        try {
            try {
                byte[] jsonData = Files.readAllBytes(Paths.get(historyPath));
                if (jsonData.length > 0){
                    ObjectMapper objectMapper = new ObjectMapper();
                    History[] historiesJSON = objectMapper.readValue(jsonData, History[].class);

                    for (History history : historiesJSON){
                        histories.add(history);
                    }
                }
            } catch (NoSuchFileException e){
                //nichts
            }

        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    /**
     * Exportiert die Klassenliste in ein HTML-Dokument, damit es ausgedruckt werden kann
     * @param klassennamen - Die Liste der Klassennamen, die exportiert werden sollen
     */
    public static void writeHTML(String[] klassennamen){
        String filename = "Klassenliste";
        for (int i = 0; i < klassennamen.length; i++) {
            filename += "_" + klassennamen[i];
        }
        File htmlFile;
        BufferedWriter bufferedWriter;

        try {
            htmlFile = new File(getProperty("resourcePath") + filename + ".html");
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), StandardCharsets.UTF_8));

            bufferedWriter.write("<html><head><meta charset=\"utf-8\"></head><body>");
            for (int i = 0; i < klassennamen.length; i++) {
                bufferedWriter.write("<h1>" + klassennamen[i] + "</h1>");


                for (Map.Entry<String,Schueler> schuelerSet: klassen.get(klassennamen[i]).getSchuelers().entrySet()){
                    String bilderPath = schuelerSet.getValue().getPath();
                    String[] tmpString = bilderPath.split("resources");
                    String relativeBilderPath = tmpString[1].substring(1);

                    bufferedWriter.write("<div>");
                    bufferedWriter.write("<img src=\"" + relativeBilderPath + "\", width=\"150\", height=\"150\">");
                    bufferedWriter.write("<h2>" + schuelerSet.getValue().getVorname() + " " + schuelerSet.getValue().getNachname() + "</h2>");
                    if (schuelerSet.getValue().getNotizID() != 0){
                        bufferedWriter.write("<p>" + DataHandler.notizenText(schuelerSet.getValue()) + "</p><br>");
                    }
                    bufferedWriter.write("</div>");
                }
                bufferedWriter.write("<br>");
            }
            bufferedWriter.write("</body></html>");
            bufferedWriter.close();

        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }


    }


    //Getter

    /**
     * Entnimmt der Variable properties eine Property
     * Wenn sie noch noch leer ist, wird readProperties() aufgerufen
     * @param key - Keywort, das in config.properties spezifiziert ist
     * @return value - dazugehoeriger Value, der in config.properties spezifiziert ist
     */
    public static String getProperty(String key){
        if (DataHandler.properties == null){
            return readProperties().getProperty(key);
        }
        return DataHandler.properties.getProperty(key);
    }

    public static DataHandler getInstance() {
        if(instance == null) instance = new DataHandler();
        return instance;
    }

    public static Vector<Integer> getNotizenIds() {
        return notizenIds;
    }

    public static HashMap<String, Klasse> getKlassen() {
        return klassen;
    }

    public static Vector<History> getHistories() {
        return histories;
    }
}