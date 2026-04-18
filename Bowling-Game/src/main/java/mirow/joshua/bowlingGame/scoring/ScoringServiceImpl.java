package mirow.joshua.bowlingGame.scoring;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import org.springframework.stereotype.Component;

@Component
public class ScoringServiceImpl implements ScoringService{


    @Override
    public Spiel verarbeiteWurf(Spiel spiel, int pins) {
        return null;
    }

    private void berechneScore(Spiel spiel) {}

    private boolean pruefeStrike(Frame frame) {
        return false;
    }

    private boolean pruefeSpare(Frame frame) {
        return false;
    }

}
