package mirow.joshua.bowlingGame.input;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Wurf;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidatorImpl();
    }

    @Test
    @DisplayName("Gueltige Eingabe - 5 Pins")
    void gueltige_eingabe_fuenf_pins() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("5", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isTrue();
        softly.assertThat(ergebnis.getFehlerMeldung()).isNull();
        softly.assertAll();
    }

    @Test
    @DisplayName("Gueltige Eingabe - 0 Pins")
    void gueltige_eingabe_null_pins() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("0", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isTrue();
        softly.assertThat(ergebnis.getFehlerMeldung()).isNull();
        softly.assertAll();
    }

    @Test
    @DisplayName("Gueltige Eingabe - 10 Pins")
    void gueltige_eingabe_zehn_pins() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("10", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isTrue();
        softly.assertThat(ergebnis.getFehlerMeldung()).isNull();
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Negative Zahl")
    void ungueltige_eingabe_negative_zahl() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("-1", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Pins dürfen nicht negativ sein");
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Mehr als 10 Pins")
    void ungueltige_eingabe_mehr_als_zehn_pins() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("11", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Maximale Pinanzahl ist 10");
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Buchstaben")
    void ungueltige_eingabe_buchstaben() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("abc", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Eingabe muss eine Zahl sein");
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Emoji")
    void ungueltige_eingabe_emoji() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("🎳", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Eingabe muss eine Zahl sein");
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Leer")
    void ungueltige_eingabe_leer() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("", null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Eingabe darf nicht leer sein");
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Null")
    void ungueltige_eingabe_null() {
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe(null, null);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Eingabe darf nicht leer sein");
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Summe ueber 10 im zweiten Wurf")
    void ungueltige_eingabe_summe_ueber_zehn() {
        // Given - Erster Wurf war 5
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(5));

        // When - Zweiter Wurf ist 6 - Summe 11
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("6", frame);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
            .isEqualTo("Summe der Pins darf nicht über 10 sein. Noch 5 Pins möglich");
        softly.assertAll();
    }

    @Test
    @DisplayName("Gueltige Eingabe - Summe genau 10 im zweiten Wurf")
    void gueltige_eingabe_summe_genau_zehn() {
        // Given - Erster Wurf war 5
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(5));

        // When - Zweiter Wurf ist 5 - Spare
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("5", frame);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isTrue();
        softly.assertThat(ergebnis.getFehlerMeldung()).isNull();
        softly.assertAll();
    }
}
