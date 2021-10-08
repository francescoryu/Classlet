# Classlet
Classlet ist Schülernamen-Lernsystem

## Neue Klassen importieren:
Im Repository ist eine Demo-Klasse hinterlegt.
Um neue Klassen hinzuzufügen, muss eine bestimmte Ordnerstruktur beibehalten werden:

Der Pfad zum Ordner "resources" kann über die Datei "config.properties" geändert werden.

**"resources" muss folgende Ordnerstrukturen beibehalten:**

* resources/
   * Klassenname/
      * Bilder/
          * Vorname_Nachname.Dateityp
          * ...
      * Notizen/ (automatisch genertiert)
      * history.json (automatisch generiert)
      * allfällige HTML-Listen (wenn von Benutzer exportiert)
          
**oder:**

* resources/
   * Klassenname/
      * Bilder/
          * xyz/
              * Vorname_Nachname.Dateityp
              * ...
      * ...


Die zweite Struktur ist dafür gedacht, dass man direkt Moode-Abgaben von Bildern hineinziehen kann.
Beim ersten Ausführen wird dann diese Struktur zur anderen Struktur automatisch geändert.


## Installation:
Classlet benötigt eine externe Library, die im Repository enthalten ist. Der Anwender muss diese lediglich noch importieren.
Im Nachfolgenden ist dieser Import in IntelliJ IDEA erläutert:



Project Structure öffnen, kann alternativ auch mit dem Shortcut CTRL + ALT + SHIFT + S gemacht werden

![grafik](https://user-images.githubusercontent.com/69806451/136517696-c1bad434-8add-4a51-83a8-b3f420d18270.png)


Links im Tab "Modules", auf "Dependencies" und dann auf das Plus-Zeichen klicken

![grafik](https://user-images.githubusercontent.com/69806451/136518148-26f6fdc3-112a-405a-9151-4796071627bc.png)


Die Option "JARs or Directories" auswählen

![grafik](https://user-images.githubusercontent.com/69806451/136518374-85e93f1c-7e2b-41e1-9e92-2e403a81c9d5.png)


Zum Ordner "Jackson_externeLibrary" navigieren und diesen selektieren

![grafik](https://user-images.githubusercontent.com/69806451/136518626-73cc96d1-b288-4810-a69b-093e1ce81447.png)


Und zuletzt "Apply" drücken

![grafik](https://user-images.githubusercontent.com/69806451/136518814-2f8d8ded-c43b-443e-9541-59c8e1d69c7a.png)



### Falls alles rot wird

Auf "Invalidate Caches" drücken

![grafik](https://user-images.githubusercontent.com/69806451/136519039-687a9013-bbda-4b93-9902-5896af5b8ea1.png)

Bestätigen

![grafik](https://user-images.githubusercontent.com/69806451/136519446-7c2837ae-76ba-4ade-84df-f0fda376d8f4.png)
