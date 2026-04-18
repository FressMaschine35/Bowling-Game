package mirow.joshua.bowlingGame.game;

import java.util.ArrayList;
import java.util.List;

public class Spiel {

    private List<Frame> frames;
    private int aktuellerFrame;
    private int gesamtScore;


    public Spiel() {
        this.frames = new ArrayList<Frame>();
        this.aktuellerFrame = 1;
        this.gesamtScore = 0;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public int getAktuellerFrame() {
        return aktuellerFrame;
    }

    public void setAktuellerFrame(int aktuellerFrame) {
        this.aktuellerFrame = aktuellerFrame;
    }

    public int getGesamtScore() {
        return gesamtScore;
    }

    public void setGesamtScore(int gesamtScore) {
        this.gesamtScore = gesamtScore;
    }
}
