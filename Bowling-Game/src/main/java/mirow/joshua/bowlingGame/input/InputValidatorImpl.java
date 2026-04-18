package mirow.joshua.bowlingGame.input;

import org.springframework.stereotype.Component;

@Component
public class InputValidatorImpl implements InputValidator {

    @Override
    public ValidiereErgebnis validiereEingabe(String eingabe) {
        if (eingabe == null || eingabe.isBlank()) {
            return ValidiereErgebnis.ungueltig("Eingabe darf nicht leer sein");
        }

        try {
            int pins = Integer.parseInt(eingabe.trim());

            if (pins < 0) {
                return ValidiereErgebnis.ungueltig("Pins dürfen nicht negativ sein");
            }

            if (pins > 10) {
                return ValidiereErgebnis.ungueltig("Maximale Pinanzahl ist 10");
            }

            return ValidiereErgebnis.gueltig();

        } catch (NumberFormatException e) {
            return ValidiereErgebnis.ungueltig("Eingabe muss eine Zahl sein");
        }
    }
}
