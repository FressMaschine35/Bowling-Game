package mirow.joshua.bowlingGame.display;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import mirow.joshua.bowlingGame.game.Wurf;
import org.springframework.stereotype.Component;

@Component
public class ConsoleDisplay {

    public void zeigeRaster(Spiel spiel) {
        // Konsole leeren
        System.out.print("\033[H\033[2J");
        System.out.flush();

        zeigeFrameNummern();
        zeigeWuerfe(spiel);
        zeigeScores(spiel);
        System.out.println();
    }

    private void zeigeFrameNummern() {
        System.out.print("|");
        for (int i = 1; i <= 9; i++) {
            System.out.printf(" Frame %-2d |", i);
        }
        System.out.println(" Frame 10  |");
    }

    private void zeigeWuerfe(Spiel spiel) {
        System.out.print("|");
        for (int i = 0; i < 9; i++) {
            if (i < spiel.getFrames().size()) {
                Frame frame = spiel.getFrames().get(i);
                System.out.print(formattiereFrame(frame) + "|");
            } else {
                System.out.print("          |");
            }
        }
        // 10ter Frame
        if (spiel.getFrames().size() >= 10) {
            Frame frame = spiel.getFrames().get(9);
            System.out.print(formattiereZehnterFrame(frame) + "|");
        } else {
            System.out.print("           |");
        }
        System.out.println();
    }

    private void zeigeScores(Spiel spiel) {
        System.out.print("|");
        int gesamt = 0;
        for (int i = 0; i < 9; i++) {
            if (i < spiel.getFrames().size()) {
                Frame frame = spiel.getFrames().get(i);
                if (frame.getBonusStatus() ==
                        mirow.joshua.bowlingGame.scoring.BonusStatus.KEIN_BONUS) {
                    gesamt += frame.getScore();
                    System.out.printf("  %-7d |", gesamt);
                } else {
                    System.out.print("    -     |");
                }
            } else {
                System.out.print("          |");
            }
        }
        // 10ter Frame Score
        if (spiel.getFrames().size() >= 10) {
            Frame frame = spiel.getFrames().get(9);
            if (frame.isComplete()) {
                gesamt += frame.getScore();
                System.out.printf("  %-8d |", gesamt);
            } else {
                System.out.print("     -     |");
            }
        } else {
            System.out.print("           |");
        }
        System.out.println();
    }

    private String formattiereFrame(Frame frame) {
        if (frame.getWuerfe().isEmpty()) {
            return "          ";
        }

        StringBuilder sb = new StringBuilder("  ");

        if (frame.isStrike()) {
            sb.append("X      ");
        } else {
            Wurf ersterWurf = frame.getWuerfe().get(0);
            sb.append(ersterWurf.pins()).append("  ");

            if (frame.getWuerfe().size() > 1) {
                if (frame.isSpare()) {
                    sb.append("/  ");
                } else {
                    sb.append(frame.getWuerfe().get(1).pins()).append("  ");
                }
            } else {
                sb.append("   ");
            }
        }
        sb.append(" ");
        return sb.toString();
    }

    private String formattiereZehnterFrame(Frame frame) {
        if (frame.getWuerfe().isEmpty()) {
            return "           ";
        }

        StringBuilder sb = new StringBuilder("  ");

        for (int i = 0; i < frame.getWuerfe().size(); i++) {
            Wurf wurf = frame.getWuerfe().get(i);
            if (wurf.pins() == 10) {
                sb.append("X  ");
            } else if (i > 0 && frame.getWuerfe().get(i - 1).pins()
                    + wurf.pins() == 10) {
                sb.append("/  ");
            } else {
                sb.append(wurf.pins()).append("  ");
            }
        }

        // Fehlende Würfe auffüllen
        for (int i = frame.getWuerfe().size(); i < 3; i++) {
            sb.append("   ");
        }

        return sb.toString();
    }
}