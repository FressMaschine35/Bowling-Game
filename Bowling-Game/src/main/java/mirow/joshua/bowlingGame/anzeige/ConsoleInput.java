package mirow.joshua.bowlingGame.anzeige;

import mirow.joshua.bowlingGame.spiel.Frame;
import mirow.joshua.bowlingGame.spiel.Spiel;
import mirow.joshua.bowlingGame.validation.InputValidator;
import mirow.joshua.bowlingGame.validation.ValidiereErgebnis;
import mirow.joshua.bowlingGame.berechnung.ScoringService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Verantwortlich für die Konsoleneingabe des Bowling-Spiels.
 * Liest Benutzereingaben, validiert diese und leitet sie an den {@link ScoringService} weiter.
 */
@Component
public class ConsoleInput {

    private final InputValidator inputValidator;
    private final ScoringService scoringService;
    private final ConsoleDisplay consoleDisplay;

    /**
     * Erstellt eine neue Instanz von ConsoleInput.
     *
     * @param inputValidator  der Validator für Benutzereingaben
     * @param scoringService  der Service für die Spiellogik
     * @param consoleDisplay  die Anzeige für das Bowling-Raster
     */
    public ConsoleInput(InputValidator inputValidator,
                        ScoringService scoringService,
                        ConsoleDisplay consoleDisplay) {
        this.inputValidator = inputValidator;
        this.scoringService = scoringService;
        this.consoleDisplay = consoleDisplay;
    }

    /**
     * Startet das Bowling-Spiel in der Konsole.
     * Läuft bis alle 10 Frames gespielt wurden.
     */
    public void starteSpiel() {
        Scanner scanner = new Scanner(System.in);
        Spiel spiel = new Spiel();

        System.out.println("Willkommen beim Bowling Spiel!");
        consoleDisplay.zeigeRaster(spiel);

        while (!istSpielBeendet(spiel)) {
            System.out.print("Bitte Pins eingeben: ");
            String eingabe = scanner.nextLine();

            Frame aktuellerFrame = spiel.getFrames().isEmpty()
                    ? null
                    : spiel.getFrames().get(spiel.getAktuellerFrame() - 1);

            ValidiereErgebnis ergebnis =
                    inputValidator.validiereEingabe(eingabe, aktuellerFrame);

            if (!ergebnis.isGueltig()) {
                System.out.println("Fehler: " + ergebnis.getFehlerMeldung());
                continue;
            }

            int pins = Integer.parseInt(eingabe.trim());
            scoringService.verarbeiteWurf(spiel, pins);
            consoleDisplay.zeigeRaster(spiel);
        }

        System.out.println("Spiel beendet! Gesamtscore: " + spiel.getGesamtScore());
        scanner.close();
    }

    /**
     * Prüft ob das Spiel beendet ist.
     * Das Spiel ist beendet wenn der zehnte Frame abgeschlossen ist.
     *
     * @param spiel das aktuelle Spiel
     * @return true wenn das Spiel beendet ist
     */
    private boolean istSpielBeendet(Spiel spiel) {
        if (spiel.getFrames().size() < 10) {
            return false;
        }
        return spiel.getFrames().get(9).isComplete();
    }
}
