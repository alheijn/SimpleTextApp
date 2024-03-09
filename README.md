# Einzelbeispiel 
Aufgabe: im Alleingang eine kleine Android App laut Angabe entwickeln & dabei VCS mit github betreiben.
## UE Software Engineering 2 - SS2024
Diese Aufgabe ist im Rahmen der Übung Software Engineering 2 im Sommersemester 2024 zu lösen.

### 2.1
Projektbeschreibung laut Angabe:
"Erstellen Sie eine einfache Android-Anwendung, die ein Eingabefeld, ein Textfeld und einen Button enthält. 
Das Eingabefeld soll nur numerische Eingaben erlauben. Durch Tap auf den Button soll die Eingabe via TCP an einen Server geschickt werden. 
Sobald eine Antwort vom Server eintrifft, soll diese am Bildschirm erscheinen.

Der Server nimmt eine Matrikelnummer als Bytestream über die TCP Verbindung entgegen, führt eine Berechnung aus und sendet das Ergebnis zurück.
Die Server-Domain lautet: se2-submission.aau.at, Port: 20080."

### 2.2
"Erweitern Sie die Applikation aus 2.1 entsprechend Ihrer Matrikelnummer. 
Berechnen Sie dazu den Wert Ihrer Matrikelnummer Modulo 7 und lösen sie die dazugehörige Aufgabe aus der Tabelle. 
Das Ergebnis soll wieder am Bildschirm ausgegeben werden.
Nutzen Sie dafür das existierende Nummernfeld und Textfeld für Ein- und Ausgabe. 
Fügen Sie außerdem noch einen weiteren Button ein, der die Berechnung auslöst."

Meine Matrikelnummer: 11702848
Matrikelnummer mod 7 = 3 (siehe Wolframalpha: https://www.wolframalpha.com/input?i=11702848+mod+7)
Daraus ergibt sich folgende Aufgabe:

"Matrikelnummer sortieren, wobei zuerst alle geraden, dann alle ungeraden Ziffern 
gereiht sind (erst die geraden, dann alle ungeraden Ziffern aufsteigend sortiert)"

### Persönliche Anmerkung:
Ich habe diese Aufgabe genutzt um mich zum ersten Mal ein bisschen mit Jetpack Compose und Kotlin auseinanderzusetzen.
Wahrscheinlich werde ich die Aufgabe nach dieser Fertigstellung noch einmal von Grund auf mit Java umsetzen,
da in der Gruppenarbeit während dem Semester wohl eher damit gearbeitet werden wird. 
Für wen auch immer diese Abgabe korrigieren muss, es tut mir leid :D
It's not pretty but it works...
![Screenshot 2024-03-09 at 23 09 32](https://github.com/alheijn/SimpleTextApp/assets/72282853/02bec8ed-dc56-4933-aa77-327cf7150e11
![Screenshot 2024-03-09 at 23 11 21](https://github.com/alheijn/SimpleTextApp/assets/72282853/9edd8deb-4f37-4f22-b3d5-8b3616a1dc6d)
)



