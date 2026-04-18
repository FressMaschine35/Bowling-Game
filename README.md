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

## Planung
### Architektur
Modulith Ansatz – bewusst gewählt weil:
- Kleines Projekt, kein Overhead durch Microservices
- Module klar getrennt – Input, Game, Scoring, Display

#### Warum Modulith?
Bowling Center bieten oft verschiedene Spielvarianten an.
Der Modulith Ansatz ermöglicht es einzelne Module
unabhängig zu erweitern oder auszutauschen ohne
andere Module zu beeinflussen.

In Zukunft können einzelne Module bei Bedarf
als eigenständige Services ausgelagert werden.

### Bewusst weggelassen



### Zeitplanung
- 1 Stunde Planung
- 2 Stunden Implementierung
- 1 Stunde Testing und Dokumentation

### Datenstruktur
[folgt]

## Priorisierung
### Höher priorisiert
[folgt]

### Bewusst weggelassen
[folgt]

## Nächste Schritte
[folgt]

## Verwendete Tools
[folgt]

## How to Run
[folgt]