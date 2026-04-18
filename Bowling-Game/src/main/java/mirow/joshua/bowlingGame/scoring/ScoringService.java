package mirow.joshua.bowlingGame.scoring;

import mirow.joshua.bowlingGame.game.Spiel;
import org.springframework.stereotype.Service;

@Service
public interface ScoringService {

    Spiel verarbeiteWurf(Spiel spiel, int pins);

}
