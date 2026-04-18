package mirow.joshua.bowlingGame.game;

import java.util.List;

public class ZehnterFrame extends Frame{

    private Wurf bonusWurf;

    public ZehnterFrame() {
        super(10);
        this.bonusWurf = bonusWurf;
    }

    public Wurf getBonusWurf() {
        return bonusWurf;
    }

    public void setBonusWurf(Wurf bonusWurf) {
        this.bonusWurf = bonusWurf;
    }


}
