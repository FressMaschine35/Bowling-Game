# Bowling-Game

## Beschreibung
Bowling-Game ist eine Konsolenanwendung zur Berechnung
der Punktzahl eines Bowling-Spiels.

Der Spieler gibt nach jedem Wurf die Anzahl der
umgeworfenen Pins ein. Die Anwendung berechnet
automatisch:
- Welcher Wurf und Frame gerade gespielt wird
- Wie viele Pins noch stehen
- Ob ein Spare oder Strike erzielt wurde
- Den aktuellen Gesamtscore nach jedem Wurf

Die Punkteberechnung erfolgt live – also nach jedem
Wurf und nicht erst am Ende des Spiels.

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
mvn spring-boot:run
```

## Projektstruktur
```
src/main/java/mirow/joshua/bowlingGame/
├── input/          # Validierung der Eingaben
│   ├── InputValidator.java
│   ├── InputValidatorImpl.java
│   └── ValidiereErgebnis.java
├── game/           # Spielobjekte
│   ├── Spiel.java
│   ├── Frame.java
│   ├── ZehnterFrame.java
│   └── Wurf.java
├── scoring/        # Spiellogik und Berechnung
│   ├── ScoringService.java
│   ├── ScoringServiceImpl.java
│   └── BonusStatus.java
└── display/        # Konsolenaus- & Eingabe
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
| ScoringService | Normaler Wurf, Spare, Strike, Perfect Game, Live Score |
| ConsoleDisplay | Leeres Raster, Strike Anzeige, Spare Anzeige |

## Planung

### Architektur
Monolith – bewusst gewählt weil:
- Kleines Projekt
- Guter Überblick durch saubere Ordnerstruktur und
  klare Trennung der Verantwortlichkeiten

### Zeitplanung (Soll)
- 1 Stunde Planung
- 2 Stunden Implementierung
- 1 Stunde Testing und Dokumentation

## Priorisierung

### Höher priorisiert
- Korrekte Scoring Logik inkl. Strike und Spare Bonus
- Live Berechnung nach jedem Wurf
- Eingabevalidierung mit klaren Fehlermeldungen
- Testabdeckung – Scoring, Validierung und Ausgabe
- Saubere Struktur für spätere Erweiterbarkeit
- SonarQube Warnungen gering wie möglich (Sinnvoll)

### Bewusst weggelassen
- Persistenz – Spielstand wird nur im Speicher gehalten.
  In Zukunft könnte PostgreSQL oder MongoDB genutzt werden
- Mehrere Spieler
- Frontend – Angular wäre der nächste Schritt
- REST API – Input Paket kann dafür umgebaut werden
- Keine einzelnen Branches für die einzelnen Implementierungen

## Nächste Schritte (Theoretisch)
- REST API hinzufügen
- Angular Frontend anbinden
- Mehrere Spieler unterstützen
- Neue Spielmodi hinzufügen

## Verwendete Tools
- IntelliJ IDEA
- Spring Boot
- JUnit 5 + AssertJ
- Claude AI – für Sparring 