package mirow.joshua.bowlingGame.game;

import java.util.List;

public class ZehnterFrame extends Frame{

    private Wurf bonusWurf;

    public ZehnterFrame(int frameNummer, List<Wurf> wuerfe, int score, boolean isSpare, boolean isStrike, boolean isComplete, Wurf bonusWurf) {
        super(frameNummer, wuerfe, score, isSpare, isStrike, isComplete);
        this.bonusWurf = bonusWurf;
    }

    public Wurf getBonusWurf() {
        return bonusWurf;
    }

    public void setBonusWurf(Wurf bonusWurf) {
        this.bonusWurf = bonusWurf;
    }


}
