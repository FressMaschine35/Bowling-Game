package mirow.joshua.bowlingGame.game;

import java.util.List;

public class Frame {

    private int frameNummer;
    private List<Wurf> wuerfe;
    private int score;
    private boolean isSpare;
    private boolean isStrike;
    private boolean isComplete;

    public Frame(int frameNummer, List<Wurf> wuerfe, int score, boolean isSpare, boolean isStrike, boolean isComplete) {
        this.frameNummer = frameNummer;
        this.wuerfe = wuerfe;
        this.score = score;
        this.isSpare = isSpare;
        this.isStrike = isStrike;
        this.isComplete = isComplete;
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
}
