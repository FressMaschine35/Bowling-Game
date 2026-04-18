package mirow.joshua.bowlingGame.input;

import mirow.joshua.bowlingGame.game.Frame;

public interface InputValidator {
    ValidiereErgebnis validiereEingabe(String eingabe, Frame aktuellerFrame);
}
