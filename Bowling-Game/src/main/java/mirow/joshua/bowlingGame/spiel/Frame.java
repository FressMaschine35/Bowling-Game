package mirow.joshua.bowlingGame.spiel;

import mirow.joshua.bowlingGame.berechnung.BonusStatus;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private int frameNummer;
    private List<Wurf> wuerfe;
    private int score;
    private boolean isSpare;
    private boolean isStrike;
    private boolean isComplete;
    private BonusStatus bonusStatus;

    public Frame(int frameNummer) {
        this.frameNummer = frameNummer;
        this.wuerfe = new ArrayList<>();
        this.bonusStatus = BonusStatus.KEIN_BONUS;
    }

    public int getFrameNummer() {
        return frameNummer;
    }

    public void setFrameNummer(int frameNummer) {
        this.frameNummer = frameNummer;
    }

    public List<Wurf> getWuerfe() {
        return wuerfe;
    }

    public void setWuerfe(List<Wurf> wuerfe) {
        this.wuerfe = wuerfe;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }

    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(boolean strike) {
        isStrike = strike;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public BonusStatus getBonusStatus() {
        return bonusStatus;
    }

    public void setBonusStatus(BonusStatus bonusStatus) {
        this.bonusStatus = bonusStatus;
    }
}
