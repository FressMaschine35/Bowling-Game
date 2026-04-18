package mirow.joshua.bowlingGame.input;

import mirow.joshua.bowlingGame.game.Frame;

public interface InputValidator {

    /**
     * Validiert die Eingabe des Benutzers.
     *
     * @param eingabe        die Roheingabe des Benutzers
     * @param aktuellerFrame der aktuelle Frame – wird für die Summenprüfung benötigt
     * @return {@link ValidiereErgebnis} mit Status und optionaler Fehlermeldung
     */
    ValidiereErgebnis validiereEingabe(String eingabe, Frame aktuellerFrame);
}
