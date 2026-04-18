package mirow.joshua.bowlingGame.input;

import mirow.joshua.bowlingGame.game.Frame;
import org.springframework.stereotype.Component;

@Component
public class InputValidatorImpl implements InputValidator {

    @Override
    public ValidiereErgebnis validiereEingabe(String eingabe, Frame aktuellerFrame) {
        // Null oder leer prüfen
        if (eingabe == null || eingabe.isBlank()) {
            return ValidiereErgebnis.ungueltig("Eingabe darf nicht leer sein");
        }

        // Ist es überhaupt eine Zahl?
        try {
            int pins = Integer.parseInt(eingabe.trim());

            // Negative Zahl
            if (pins < 0) {
                return ValidiereErgebnis.ungueltig("Pins dürfen nicht negativ sein");
            }

            // Mehr als 10 Pins
            if (pins > 10) {
                return ValidiereErgebnis.ungueltig("Maximale Pinanzahl ist 10");
            }

            // Zweiter Wurf - Summe darf nicht über 10
            if (aktuellerFrame != null
                && aktuellerFrame.getWuerfe().size() == 1
                && !aktuellerFrame.isStrike()) {

                int ersterWurf = aktuellerFrame.getWuerfe().get(0).pins();
                if (ersterWurf + pins > 10) {
                    return ValidiereErgebnis.ungueltig(
                        "Summe der Pins darf nicht über 10 sein. " +
                        "Noch " + (10 - ersterWurf) + " Pins möglich");
                }
            }

            return ValidiereErgebnis.gueltig();

        } catch (NumberFormatException e) {
            return ValidiereErgebnis.ungueltig("Eingabe muss eine Zahl sein");
        }
    }
}
