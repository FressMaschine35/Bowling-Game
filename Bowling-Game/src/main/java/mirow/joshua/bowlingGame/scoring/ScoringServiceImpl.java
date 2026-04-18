package mirow.joshua.bowlingGame.scoring;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import mirow.joshua.bowlingGame.game.Wurf;
import mirow.joshua.bowlingGame.game.ZehnterFrame;
import org.springframework.stereotype.Component;

/**
 * Implementierung des {@link ScoringService}.
 * Verarbeitet jeden Wurf, berechnet Strike- und Spare-Boni
 * und aktualisiert den Gesamtscore nach jedem Wurf.
 */
@Component
public class ScoringServiceImpl implements ScoringService {

    /**
     * Verarbeitet einen Wurf und aktualisiert den Spielstand.
     *
     * @param spiel das aktuelle Spiel
     * @param pins  die Anzahl der umgeworfenen Pins
     * @return das aktualisierte Spiel
     * @throws IllegalArgumentException wenn das Spiel null ist
     */
    @Override
    public Spiel verarbeiteWurf(Spiel spiel, int pins) {
        if (spiel == null) {
            throw new IllegalArgumentException("Spiel darf nicht null sein");
        }

        if (spiel.getFrames().isEmpty()) {
            spiel.getFrames().add(new Frame(1));
        }

        Frame aktuellerFrame = spiel.getFrames()
                .get(spiel.getAktuellerFrame() - 1);

        Wurf wurf = new Wurf(pins);
        aktuellerFrame.getWuerfe().add(wurf);

        aktualisiereOffeneBoni(spiel, wurf);
        berechneScore(aktuellerFrame);

        if (spiel.getAktuellerFrame() >= 10) {
            verarbeiteZehntenFrame(aktuellerFrame);
        } else {
            verarbeiteNormalenFrame(spiel, aktuellerFrame);
        }

        aktualisiereGesamtScore(spiel);

        return spiel;
    }

    /**
     * Verarbeitet die Sonderlogik des zehnten Frames.
     * Im zehnten Frame sind bis zu drei Würfe möglich.
     *
     * @param frame der zehnte Frame
     */
    private void verarbeiteZehntenFrame(Frame frame) {
        int anzahlWuerfe = frame.getWuerfe().size();

        if (anzahlWuerfe == 1 && frame.getWuerfe().get(0).pins() == 10) {
            frame.setStrike(true);
        } else if (anzahlWuerfe == 2) {
            int summe = frame.getWuerfe().get(0).pins()
                    + frame.getWuerfe().get(1).pins();
            if (summe == 10 && frame.getWuerfe().get(1).pins() != 10) {
                frame.setSpare(true);
            } else if (summe < 10) {
                frame.setComplete(true);
            }
        } else if (anzahlWuerfe == 3) {
            frame.setComplete(true);
            frame.setScore(frame.getWuerfe().stream()
                    .mapToInt(Wurf::pins)
                    .sum());
        }
    }

    /**
     * Verarbeitet die Logik eines normalen Frames (1-9).
     * Setzt Strike, Spare oder normalen Abschluss und wechselt zum nächsten Frame.
     *
     * @param spiel        das aktuelle Spiel
     * @param aktuellerFrame der aktuelle Frame
     */
    private void verarbeiteNormalenFrame(Spiel spiel, Frame aktuellerFrame) {
        if (pruefeStrike(aktuellerFrame)) {
            aktuellerFrame.setStrike(true);
            aktuellerFrame.setComplete(true);
            aktuellerFrame.setBonusStatus(BonusStatus.STRIKE_BONUS_1);
            berechneScore(aktuellerFrame);
            naechsterFrame(spiel);
        } else if (pruefeSpare(aktuellerFrame)) {
            aktuellerFrame.setSpare(true);
            aktuellerFrame.setComplete(true);
            aktuellerFrame.setBonusStatus(BonusStatus.SPARE_BONUS);
            berechneScore(aktuellerFrame);
            naechsterFrame(spiel);
        } else if (aktuellerFrame.getWuerfe().size() == 2) {
            aktuellerFrame.setComplete(true);
            berechneScore(aktuellerFrame);
            naechsterFrame(spiel);
        }
    }

    /**
     * Berechnet den Score eines Frames.
     * Strike und Spare starten mit 10 Punkten – Boni werden separat addiert.
     *
     * @param frame der zu berechnende Frame
     */
    private void berechneScore(Frame frame) {
        if (frame.isStrike() || frame.isSpare()) {
            frame.setScore(10);
        } else {
            frame.setScore(frame.getWuerfe().stream()
                    .mapToInt(Wurf::pins)
                    .sum());
        }
    }

    /**
     * Aktualisiert offene Boni vorheriger Frames mit dem neuen Wurf.
     *
     * @param spiel    das aktuelle Spiel
     * @param neuerWurf der neue Wurf
     */
    private void aktualisiereOffeneBoni(Spiel spiel, Wurf neuerWurf) {
        for (Frame frame : spiel.getFrames()) {
            switch (frame.getBonusStatus()) {
                case STRIKE_BONUS_1 -> {
                    frame.setScore(frame.getScore() + neuerWurf.pins());
                    frame.setBonusStatus(BonusStatus.STRIKE_BONUS_2);
                }
                case STRIKE_BONUS_2, SPARE_BONUS -> {
                    frame.setScore(frame.getScore() + neuerWurf.pins());
                    frame.setBonusStatus(BonusStatus.KEIN_BONUS);
                }
                default -> { /* Kein Bonus - nichts zu tun */ }
            }
        }
    }

    /**
     * Wechselt zum nächsten Frame.
     * Im zehnten Frame wird ein {@link ZehnterFrame} erstellt.
     *
     * @param spiel das aktuelle Spiel
     */
    private void naechsterFrame(Spiel spiel) {
        int naechsteFrameNummer = spiel.getAktuellerFrame() + 1;

        if (naechsteFrameNummer > 10) {
            return;
        }

        if (naechsteFrameNummer == 10) {
            spiel.getFrames().add(new ZehnterFrame());
        } else {
            spiel.getFrames().add(new Frame(naechsteFrameNummer));
        }

        spiel.setAktuellerFrame(naechsteFrameNummer);
    }

    /**
     * Berechnet den Gesamtscore aus allen Frames.
     *
     * @param spiel das aktuelle Spiel
     */
    private void aktualisiereGesamtScore(Spiel spiel) {
        spiel.setGesamtScore(spiel.getFrames().stream()
                .mapToInt(Frame::getScore)
                .sum());
    }

    /**
     * Prüft ob der Frame ein Strike ist.
     *
     * @param frame der zu prüfende Frame
     * @return true wenn Strike
     */
    private boolean pruefeStrike(Frame frame) {
        return frame.getWuerfe().size() == 1
                && frame.getWuerfe().get(0).pins() == 10;
    }

    /**
     * Prüft ob der Frame ein Spare ist.
     *
     * @param frame der zu prüfende Frame
     * @return true wenn Spare
     */
    private boolean pruefeSpare(Frame frame) {
        return frame.getWuerfe().size() == 2
                && frame.getWuerfe().get(0).pins()
                + frame.getWuerfe().get(1).pins() == 10;
    }
}
