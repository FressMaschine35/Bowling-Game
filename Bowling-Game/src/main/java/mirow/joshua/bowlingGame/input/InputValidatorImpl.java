package mirow.joshua.bowlingGame.input;

import mirow.joshua.bowlingGame.game.Frame;
import org.springframework.stereotype.Component;

/**
 * Implementierung des {@link InputValidator}.
 * Validiert die Benutzereingabe auf Korrektheit und Plausibilität.
 */
@Component
public class InputValidatorImpl implements InputValidator {

    private static final int MAX_PINS = 10;

    /**
     * Validiert die Eingabe des Benutzers.
     *
     * @param eingabe        die Roheingabe des Benutzers
     * @param aktuellerFrame der aktuelle Frame – wird für die Summenprüfung benötigt
     * @return {@link ValidiereErgebnis} mit Status und optionaler Fehlermeldung
     */
    @Override
    public ValidiereErgebnis validiereEingabe(String eingabe, Frame aktuellerFrame) {
        if (eingabe == null || eingabe.isBlank()) {
            return ValidiereErgebnis.ungueltig("Eingabe darf nicht leer sein");
        }

        try {
            int pins = Integer.parseInt(eingabe.trim());

            if (pins < 0) {
                return ValidiereErgebnis.ungueltig("Pins dürfen nicht negativ sein");
            }

            if (pins > MAX_PINS) {
                return ValidiereErgebnis.ungueltig("Maximale Pinanzahl ist 10");
            }

            return pruefeZweiterWurf(pins, aktuellerFrame);

        } catch (NumberFormatException e) {
            return ValidiereErgebnis.ungueltig("Eingabe muss eine Zahl sein");
        }
    }

    /**
     * Prüft ob die Summe der Pins im zweiten Wurf die maximale Pinanzahl überschreitet.
     *
     * @param pins           die Anzahl der Pins im zweiten Wurf
     * @param aktuellerFrame der aktuelle Frame
     * @return {@link ValidiereErgebnis} mit Status und optionaler Fehlermeldung
     */
    private ValidiereErgebnis pruefeZweiterWurf(int pins, Frame aktuellerFrame) {
        if (aktuellerFrame != null
                && aktuellerFrame.getWuerfe().size() == 1
                && !aktuellerFrame.isStrike()) {

            int ersterWurf = aktuellerFrame.getWuerfe().get(0).pins();
            if (ersterWurf + pins > MAX_PINS) {
                return ValidiereErgebnis.ungueltig(
                        "Summe der Pins darf nicht über 10 sein. "
                        + "Noch " + (MAX_PINS - ersterWurf) + " Pins möglich");
            }
        }
        return ValidiereErgebnis.gueltig();
    }
}
