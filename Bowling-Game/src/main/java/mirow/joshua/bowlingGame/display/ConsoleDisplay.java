package mirow.joshua.bowlingGame.display;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import mirow.joshua.bowlingGame.game.Wurf;
import mirow.joshua.bowlingGame.scoring.BonusStatus;
import org.springframework.stereotype.Component;

/**
 * Verantwortlich für die Konsolenausgabe des Bowling-Rasterns.
 * Zeigt Frame-Nummern, Würfe und den aktuellen Score nach jedem Wurf an.
 */
@Component
public class ConsoleDisplay {

    private static final String LEERER_FRAME = "          ";
    private static final String LEERER_ZEHNTER_FRAME = "           ";

    /**
     * Zeigt das vollständige Bowling-Raster in der Konsole an.
     * Die Konsole wird vor jeder Ausgabe geleert.
     *
     * @param spiel das aktuelle Spiel mit allen Frames und Würfen
     */
    public void zeigeRaster(Spiel spiel) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        zeigeFrameNummern();
        zeigeWuerfe(spiel);
        zeigeScores(spiel);
        System.out.println();
    }

    /**
     * Gibt die Frame-Nummern als Kopfzeile des Rasters aus.
     */
    private void zeigeFrameNummern() {
        System.out.print("|");
        for (int i = 1; i <= 9; i++) {
            System.out.printf(" Frame %-2d |", i);
        }
        System.out.println(" Frame 10  |");
    }

    /**
     * Gibt die Würfe aller Frames aus.
     * Strike wird als X, Spare als / dargestellt.
     *
     * @param spiel das aktuelle Spiel
     */
    private void zeigeWuerfe(Spiel spiel) {
        System.out.print("|");
        for (int i = 0; i < 9; i++) {
            if (i < spiel.getFrames().size()) {
                System.out.print(formattiereFrame(spiel.getFrames().get(i)) + "|");
            } else {
                System.out.print(LEERER_FRAME + "|");
            }
        }
        if (spiel.getFrames().size() >= 10) {
            System.out.print(formattiereZehnterFrame(spiel.getFrames().get(9)) + "|");
        } else {
            System.out.print(LEERER_ZEHNTER_FRAME + "|");
        }
        System.out.println();
    }

    /**
     * Gibt den kumulierten Score pro Frame aus.
     * Frames mit offenem Bonus werden mit '-' dargestellt.
     *
     * @param spiel das aktuelle Spiel
     */
    private void zeigeScores(Spiel spiel) {
        System.out.print("|");
        int gesamt = 0;
        for (int i = 0; i < 9; i++) {
            if (i < spiel.getFrames().size()) {
                Frame frame = spiel.getFrames().get(i);
                if (frame.getBonusStatus() == BonusStatus.KEIN_BONUS) {
                    gesamt += frame.getScore();
                    System.out.printf("  %-7d |", gesamt);
                } else {
                    System.out.print("    -     |");
                }
            } else {
                System.out.print(LEERER_FRAME + "|");
            }
        }
        if (spiel.getFrames().size() >= 10) {
            Frame frame = spiel.getFrames().get(9);
            if (frame.isComplete()) {
                gesamt += frame.getScore();
                System.out.printf("  %-8d |", gesamt);
            } else {
                System.out.print("     -     |");
            }
        } else {
            System.out.print(LEERER_ZEHNTER_FRAME + "|");
        }
        System.out.println();
    }

    /**
     * Formatiert einen normalen Frame für die Ausgabe.
     * Strike wird als X, Spare als / dargestellt.
     *
     * @param frame der zu formatierende Frame
     * @return formatierter String für die Konsolenausgabe
     */
    private String formattiereFrame(Frame frame) {
        if (frame.getWuerfe().isEmpty()) {
            return LEERER_FRAME;
        }

        StringBuilder sb = new StringBuilder("  ");

        if (frame.isStrike()) {
            sb.append("X      ");
        } else {
            Wurf ersterWurf = frame.getWuerfe().get(0);
            sb.append(ersterWurf.pins()).append("  ");

            if (frame.getWuerfe().size() > 1) {
                sb.append(frame.isSpare() ? "/" : frame.getWuerfe().get(1).pins())
                  .append("  ");
            } else {
                sb.append("   ");
            }
        }
        sb.append(" ");
        return sb.toString();
    }

    /**
     * Formatiert den zehnten Frame für die Ausgabe.
     * Berücksichtigt bis zu drei Würfe inkl. Bonuswurf.
     *
     * @param frame der zehnte Frame
     * @return formatierter String für die Konsolenausgabe
     */
    private String formattiereZehnterFrame(Frame frame) {
        if (frame.getWuerfe().isEmpty()) {
            return LEERER_ZEHNTER_FRAME;
        }

        StringBuilder sb = new StringBuilder("  ");

        for (int i = 0; i < frame.getWuerfe().size(); i++) {
            Wurf wurf = frame.getWuerfe().get(i);
            if (wurf.pins() == 10) {
                sb.append("X  ");
            } else if (i > 0 && frame.getWuerfe().get(i - 1).pins() + wurf.pins() == 10) {
                sb.append("/  ");
            } else {
                sb.append(wurf.pins()).append("  ");
            }
        }

        for (int i = frame.getWuerfe().size(); i < 3; i++) {
            sb.append("   ");
        }

        return sb.toString();
    }
}
