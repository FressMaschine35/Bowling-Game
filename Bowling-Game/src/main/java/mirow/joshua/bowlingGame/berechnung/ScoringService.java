package mirow.joshua.bowlingGame.berechnung;

import mirow.joshua.bowlingGame.spiel.Spiel;
import org.springframework.stereotype.Service;

@Service
public interface ScoringService {

    /**
     * Verarbeitet einen Wurf und aktualisiert den Spielstand.
     *
     * @param spiel das aktuelle Spiel
     * @param pins  die Anzahl der umgeworfenen Pins
     * @return das aktualisierte Spiel
     * @throws IllegalArgumentException wenn das Spiel null ist
     */
    Spiel verarbeiteWurf(Spiel spiel, int pins);

}
