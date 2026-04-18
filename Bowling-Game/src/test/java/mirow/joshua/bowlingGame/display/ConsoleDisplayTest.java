package mirow.joshua.bowlingGame.display;


import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import mirow.joshua.bowlingGame.game.Wurf;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class ConsoleDisplayTest {

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
        softly.assertThat(ausgabeText).contains("Frame 1");
        softly.assertThat(ausgabeText).contains("Frame 10");
        softly.assertAll();
    }

    @Test
    @DisplayName("Strike wird als X angezeigt")
    void strike_wird_als_x_angezeigt() {
        // Given
        Spiel spiel = new Spiel();
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(10));
        frame.setStrike(true);
        frame.setComplete(true);
        spiel.getFrames().add(frame);

        // When
        consoleDisplay.zeigeRaster(spiel);
        String ausgabeText = ausgabe.toString();

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ausgabeText).contains("X");
        softly.assertAll();
    }

    @Test
    @DisplayName("Spare wird als / angezeigt")
    void spare_wird_als_slash_angezeigt() {
        // Given
        Spiel spiel = new Spiel();
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(7));
        frame.getWuerfe().add(new Wurf(3));
        frame.setSpare(true);
        frame.setComplete(true);
        spiel.getFrames().add(frame);

        // When
        consoleDisplay.zeigeRaster(spiel);
        String ausgabeText = ausgabe.toString();

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ausgabeText).contains("7");
        softly.assertThat(ausgabeText).contains("/");
        softly.assertAll();
    }

}
