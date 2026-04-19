# Bowling-Game

## Beschreibung
Bowling-Game ist eine Konsolenanwendung zur Berechnung
der Punktzahl eines Bowling-Spiels.

Der Spieler gibt nach jedem Wurf die Anzahl der
umgeworfenen Pins ein. Die Anwendung berechnet
automatisch:
- Welcher Wurf und Frame gerade gespielt wird
- Ob ein Spare oder Strike erzielt wurde
- Den aktuellen Gesamtscore nach jedem Wurf – live,
  also nach jedem Wurf und nicht erst am Ende des Spiels

## Spielregeln
- 10 Frames pro Spiel
- 2 Würfe pro Frame
- Strike: Alle 10 Pins im ersten Wurf – Bonus sind
  die nächsten 2 Würfe
- Spare: Alle 10 Pins in zwei Würfen – Bonus ist
  der nächste Wurf
- 10ter Frame: Bei Strike oder Spare gibt es
  einen zusätzlichen Wurf

## How to Run

### Voraussetzungen
- Java 21
- Maven

### Starten
```bash
mvn clean install
mvn spring-boot:run
```

## Projektstruktur
```
src/main/java/mirow/joshua/bowlingGame/
├── validation/          # Eingabe und Validierung
│   ├── InputValidator.java
│   ├── InputValidatorImpl.java
│   └── ValidiereErgebnis.java
├── spiel/           # Spielobjekte
│   ├── Spiel.java
│   ├── Frame.java
│   ├── ZehnterFrame.java
│   └── Wurf.java
├── berechnung/        # Spiellogik und Berechnung
│   ├── ScoringService.java
│   ├── ScoringServiceImpl.java
│   └── BonusStatus.java
└── anzeige/        # Konsolenausgabe
    ├── ConsoleDisplay.java
    └── ConsoleInput.java
```

## Technologie Stack
- Java 21
- Spring Boot 3.4.4
- JUnit 5
- AssertJ

## Testabdeckung
| Modul | Getestet |
|---|---|
| InputValidator | Gültige und ungültige Eingaben, Summenprüfung |
| ScoringService | Normaler Wurf, Spare, Strike, Perfect Game, Live Score, Zehnter Frame, Fehlerwurf |
| ConsoleDisplay | Leeres Raster, Strike Anzeige, Spare Anzeige |

Der ConsoleInput wurde bewusst nicht per Automation getestet
da dies durch die manuelle Ausführung der Anwendung abgedeckt
wurde. Mit einem Frontend würde man hier Selenium, TestCafe
oder Playwright nutzen. Da kein Frontend existiert war die
manuelle Ausführung der pragmatische Weg.

## Planung

### Architektur
Monolith – bewusst gewählt weil es sich um ein kleines
Projekt handelt. Eine gute Ordnerstruktur mit klarer
Trennung der Verantwortlichkeiten gibt ausreichend
Überblick ohne unnötigen Overhead.

## Priorisierung

### 1. Funktionsfähigkeit
Die Anwendung muss kompilieren, testbar und ausführbar
sein – auch wenn die Aufgabe sagt es muss nicht perfekt
sein. Egal ob Test oder echte Anwendung, diese Standards
sollten niemals unterschlagen werden.

Das Live Scoring war ausdrücklich gewünscht und macht
in diesem Use Case Sinn – der Spieler soll nach jedem
Wurf den aktuellen Stand sehen.

### 2. Tests
Wer nicht testet rät – und darf Fehler später ausbaden.

Tests sind besonders wichtig in der Teamarbeit. Wenn
man einen neuen Branch erstellt, sollte der erste Schritt
sein die Tests auszuführen. Das stellt sicher dass die
Anwendung funktioniert oder zeigt Probleme die
adressiert werden müssen.

### 3. Dokumentation
JavaDoc ist Standard. Ja, es gibt Entwickler die sagen
"was wenn sich was ändert" – dann passt man die Doku an.

Teams die zusammenarbeiten sollten nicht raten müssen
was eine Methode tut. Die JavaDoc Kommentierung macht
klar: Was macht die Methode, was ist der Parameter
und was liefert sie zurück.

### 4. Fehlermeldungen
Fehler müssen dem Nutzer sichtbar gemacht werden.
Das hilft nicht nur dem Nutzer – es hilft auch den
Entwicklern die Suche einzugrenzen. Im Betrieb sind LOGs sinnvoller als die Konsolenausgabe,
aber in dem Fall war es in meinen Augen die bessere Entscheidung.

Bei einer UI würden Fehler gesammelt und gemeinsam
zurückgegeben – je nach Größe der Eingabefelder.


### 5. Validierung
Validierung wird in der Praxis leider häufig vernachlässigt –
egal ob Frontend oder Backend. Das führt zu Problemen die
schwer zu verstehen sind, besonders bei Edge Cases. Der
Anwender versteht dann nicht warum es zu Fehlern kam und
Entwickler haben Schwierigkeiten die Ursache einzugrenzen.

Deshalb wurde die Validierung bewusst implementiert –
mit klaren Fehlermeldungen die dem Nutzer direkt sagen
was falsch war.

### 6. SonarQube
SonarQube Warnings wurden dort reduziert, wo es sinnvoll
war – nicht blind jede Warnung behoben, sondern bewusst
entschieden was relevant ist.

## Bewusst weggelassen

### Datenbank
Keine Persistenz implementiert. Diese kann bei Bedarf
durch einen neuen `persistence` Ordner ergänzt werden.
Der Einstiegspunkt wäre nach `aktualisiereGesamtScore()`
im ScoringService.

### REST-API
Da kein Frontend existiert macht eine REST-API aktuell
keinen Sinn. Bei Bedarf könnten Controller direkt im
`input` Ordner hinzugefügt werden.

### Gemeinsame Konstanten
Es wurde kein zentraler Ordner für gemeinsame Konstanten
angelegt. Zum aktuellen Stand der Anwendung sind Konstanten
kaum vorhanden und es ist noch nicht klar welche in mehreren
Bereichen genutzt werden könnten. Das sollte im Nachgang
durch gezieltes Hinschauen bestimmt werden – eine voreilige
Zentralisierung wäre over-engineering.

### Test Data Generatoren
Die Tests haben keine Generatoren um Testdaten schnell
zu erstellen. Zum aktuellen Zeitpunkt ist die Anzahl
der benötigten Objekte noch so gering dass Generatoren
keinen Mehrwert bringen. Bei wachsender Komplexität
der Anwendung sollte das ergänzt werden.

### Validierung gegen Angriffe
Eine konkrete Validierung gegen Datenmanipulation oder
Angriffe wurde aufgrund des Use Cases und Umfangs nicht
implementiert. In der Realität sollte man Eingaben
entgegennehmen, sanitizen, validieren und erst dann
verarbeiten.

## Nächste Schritte
- REST-API im Input Paket hinzufügen
- Angular Frontend anbinden
- Persistenz durch PostgreSQL oder MongoDB ergänzen
- Mehrere Spieler unterstützen
- Neue Spielmodi hinzufügen
- Gemeinsame Konstanten bei wachsender Codebasis zentral verwalten
- Test Data Generatoren bei steigender Objektkomplexität ergänzen

## Verwendete Tools
- IntelliJ IDEA
- Spring Boot
- JUnit 5 + AssertJ
- Claude AI – für Sparring und Code Review