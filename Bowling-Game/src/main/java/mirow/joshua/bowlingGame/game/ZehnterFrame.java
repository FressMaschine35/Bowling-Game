package mirow.joshua.bowlingGame.game;


public class ZehnterFrame extends Frame{

    private Wurf bonusWurf;

    public ZehnterFrame() {
        super(10);
    }

    public Wurf getBonusWurf() {
        return bonusWurf;
    }

    public void setBonusWurf(Wurf bonusWurf) {
        this.bonusWurf = bonusWurf;
    }


}
