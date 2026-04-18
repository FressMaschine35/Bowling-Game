package mirow.joshua.bowlingGame.scoring;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import mirow.joshua.bowlingGame.game.Wurf;
import mirow.joshua.bowlingGame.game.ZehnterFrame;
import org.springframework.stereotype.Component;

@Component
public class ScoringServiceImpl implements ScoringService{


    @Override
    public Spiel verarbeiteWurf(Spiel spiel, int pins) {
        // Nullpointer Check
        if (spiel == null) {
            throw new IllegalArgumentException("Spiel darf nicht null sein");
        }

        // Ersten Frame initialisieren
        if (spiel.getFrames().isEmpty()) {
            spiel.getFrames().add(new Frame(1));
        }

        // Aktuellen Frame holen
        Frame aktuellerFrame = spiel.getFrames()
                .get(spiel.getAktuellerFrame() - 1);

        // Wurf erstellen und Frame hinzufügen
        Wurf wurf = new Wurf(pins);
        aktuellerFrame.getWuerfe().add(wurf);

        // Offene Boni aktualisieren
        aktualisiereOffeneBoni(spiel, wurf);
        berechneScore(aktuellerFrame);

        // 10ter Frame Sonderlogik
        if (spiel.getAktuellerFrame() >= 10) {
            if (aktuellerFrame.getWuerfe().size() == 1) {
                if (aktuellerFrame.getWuerfe().get(0).pins() == 10) {
                    aktuellerFrame.setStrike(true);
                }
            } else if (aktuellerFrame.getWuerfe().size() == 2) {
                if (aktuellerFrame.getWuerfe().get(1).pins() == 10) {
                    // Zweiter Strike
                } else if (aktuellerFrame.getWuerfe().get(0).pins()
                        + aktuellerFrame.getWuerfe().get(1).pins() == 10) {
                    aktuellerFrame.setSpare(true);
                } else {
                    aktuellerFrame.setComplete(true);
                }
            } else if (aktuellerFrame.getWuerfe().size() == 3) {
                aktuellerFrame.setComplete(true);
                aktuellerFrame.setScore(aktuellerFrame.getWuerfe().stream()
                        .mapToInt(Wurf::pins)
                        .sum());
            }
        } else{
            // Normale Frame Logik
            // Strike prüfen
            if (pruefeStrike(aktuellerFrame)) {
                aktuellerFrame.setStrike(true);
                aktuellerFrame.setComplete(true);
                aktuellerFrame.setBonusStatus(BonusStatus.STRIKE_BONUS_1);
                berechneScore( aktuellerFrame);
                naechsterFrame(spiel);
            }
            // Spare prüfen
            else if (pruefeSpare(aktuellerFrame)) {
                aktuellerFrame.setSpare(true);
                aktuellerFrame.setComplete(true);
                aktuellerFrame.setBonusStatus(BonusStatus.SPARE_BONUS);
                berechneScore(aktuellerFrame);
                naechsterFrame(spiel);
            }
            // Zweiter normaler Wurf
            else if (aktuellerFrame.getWuerfe().size() == 2) {
                aktuellerFrame.setComplete(true);
                berechneScore( aktuellerFrame);
                naechsterFrame(spiel);
            }

        }
        // Gesamtscore aktualisieren
        aktualisiereGesamtScore(spiel);

        return spiel;
    }

    private void berechneScore(Frame frame) {
        if (frame.isStrike()) {
            frame.setScore(10);
        } else if (frame.isSpare()) {
            frame.setScore(10);
        } else {
            frame.setScore(frame.getWuerfe().stream()
                    .mapToInt(Wurf::pins)
                    .sum());
        }
    }

    private void aktualisiereOffeneBoni(Spiel spiel, Wurf neuerWurf) {
        for (Frame frame : spiel.getFrames()) {
            if (frame.getBonusStatus() == BonusStatus.STRIKE_BONUS_1) {
                // Erster Bonuswurf eingetroffen
                frame.setScore(frame.getScore() + neuerWurf.pins());
                frame.setBonusStatus(BonusStatus.STRIKE_BONUS_2);

            } else if (frame.getBonusStatus() == BonusStatus.STRIKE_BONUS_2) {
                // Zweiter Bonuswurf eingetroffen
                frame.setScore(frame.getScore() + neuerWurf.pins());
                frame.setBonusStatus(BonusStatus.KEIN_BONUS);

            } else if (frame.getBonusStatus() == BonusStatus.SPARE_BONUS) {
                // Spare Bonus eingetroffen
                frame.setScore(frame.getScore() + neuerWurf.pins());
                frame.setBonusStatus(BonusStatus.KEIN_BONUS);
            }
        }
    }

    private void naechsterFrame(Spiel spiel) {
        int naechsteFrameNummer = spiel.getAktuellerFrame() + 1;

        // Spiel beendet nach Frame 10
        if (naechsteFrameNummer > 10) {
            return;
        }

        // 10ter Frame ist ein ZehnterFrame
        if (naechsteFrameNummer == 10) {
            spiel.getFrames().add(new ZehnterFrame());
        } else {
            spiel.getFrames().add(new Frame(naechsteFrameNummer));
        }

        spiel.setAktuellerFrame(naechsteFrameNummer);
    }

    private void aktualisiereGesamtScore(Spiel spiel) {
        int gesamt = spiel.getFrames().stream()
                .mapToInt(Frame::getScore)
                .sum();
        spiel.setGesamtScore(gesamt);
    }

    private boolean pruefeStrike(Frame frame) {
        return frame.getWuerfe().size() == 1
                && frame.getWuerfe().get(0).pins() == 10;
    }

    private boolean pruefeSpare(Frame frame) {
        return frame.getWuerfe().size() == 2
                && frame.getWuerfe().get(0).pins()
                + frame.getWuerfe().get(1).pins() == 10;
    }

}
