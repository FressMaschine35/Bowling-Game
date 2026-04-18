package mirow.joshua.bowlingGame.display;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import mirow.joshua.bowlingGame.input.InputValidator;
import mirow.joshua.bowlingGame.input.ValidiereErgebnis;
import mirow.joshua.bowlingGame.scoring.ScoringService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput {

    private final InputValidator inputValidator;
    private final ScoringService scoringService;
    private final ConsoleDisplay consoleDisplay;

    public ConsoleInput(InputValidator inputValidator,
                        ScoringService scoringService,
                        ConsoleDisplay consoleDisplay) {
        this.inputValidator = inputValidator;
        this.scoringService = scoringService;
        this.consoleDisplay = consoleDisplay;
    }

    public void starteSpiel() {
        Scanner scanner = new Scanner(System.in);
        Spiel spiel = new Spiel();

        System.out.println("Willkommen beim Bowling Spiel!");
        consoleDisplay.zeigeRaster(spiel);

        while (!istSpielBeendet(spiel)) {
            System.out.print("Bitte Pins eingeben: ");
            String eingabe = scanner.nextLine();

            // Aktuellen Frame holen
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

        System.out.println("Spiel beendet! Gesamtscore: "
            + spiel.getGesamtScore());
        scanner.close();
    }

    private boolean istSpielBeendet(Spiel spiel) {
        if (spiel.getFrames().size() < 10) {
            return false;
        }
        return spiel.getFrames().get(9).isComplete();
    }
}
