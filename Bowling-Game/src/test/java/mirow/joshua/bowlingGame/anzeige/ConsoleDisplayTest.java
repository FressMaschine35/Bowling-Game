package mirow.joshua.bowlingGame.anzeige;

import mirow.joshua.bowlingGame.spiel.Frame;
import mirow.joshua.bowlingGame.spiel.Spiel;
import mirow.joshua.bowlingGame.spiel.Wurf;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ConsoleDisplayTest {

    private static final String FRAME_1 = "Frame 1";
    private static final String FRAME_10 = "Frame 10";

    private ConsoleDisplay consoleDisplay;
    private ByteArrayOutputStream ausgabe;
    private PrintStream original;

    @BeforeEach
    void setUp() {
        consoleDisplay = new ConsoleDisplay();
        ausgabe = new ByteArrayOutputStream();
        original = System.out;
        System.setOut(new PrintStream(ausgabe));
    }

    @AfterEach
    void tearDown() {
        System.setOut(original);
    }

    @Test
    @DisplayName("Leeres Spiel - Raster wird korrekt angezeigt")
    void leeres_spiel_raster() {
        // Given
        Spiel spiel = new Spiel();

        // When
        consoleDisplay.zeigeRaster(spiel);
        String ausgabeText = ausgabe.toString();

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ausgabeText).contains(FRAME_1);
        softly.assertThat(ausgabeText).contains(FRAME_10);
        softly.assertAll();
    }

    @ParameterizedTest(name = "Wurf mit {0} Pins wird als {1} angezeigt")
    @DisplayName("Strike und Spare werden korrekt dargestellt")
    @CsvSource({
        "10, true, false, X",
        "7, false, true, /"
    })
    void wurf_wird_korrekt_dargestellt(int ersterWurf, boolean isStrike,
                                        boolean isSpare, String erwarteteAnzeige) {
        // Given
        Spiel spiel = new Spiel();
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(ersterWurf));
        if (isSpare) {
            frame.getWuerfe().add(new Wurf(3));
        }
        frame.setStrike(isStrike);
        frame.setSpare(isSpare);
        frame.setComplete(true);
        spiel.getFrames().add(frame);

        // When
        consoleDisplay.zeigeRaster(spiel);
        String ausgabeText = ausgabe.toString();

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ausgabeText).contains(erwarteteAnzeige);
        softly.assertAll();
    }
}
