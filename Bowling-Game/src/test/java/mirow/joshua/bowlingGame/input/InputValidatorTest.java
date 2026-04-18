package mirow.joshua.bowlingGame.input;

import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Wurf;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    private static final int ERSTER_WURF_PINS = 5;

    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidatorImpl();
    }

    @ParameterizedTest(name = "Gueltige Eingabe - {0} Pins")
    @DisplayName("Gueltige Eingaben - 0, 5 und 10 Pins")
    @ValueSource(strings = {"0", "5", "10"})
    void gueltige_eingaben(String eingabe) {
        // When
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe(eingabe, null);

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isTrue();
        softly.assertThat(ergebnis.getFehlerMeldung()).isNull();
        softly.assertAll();
    }

    @ParameterizedTest(name = "Ungueltige Eingabe - {0} ist keine Zahl")
    @DisplayName("Ungueltige Eingaben - Buchstaben und Emoji")
    @ValueSource(strings = {"abc", "🎳"})
    void ungueltige_eingabe_keine_zahl(String eingabe) {
        // When
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe(eingabe, null);

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
                .isEqualTo("Eingabe muss eine Zahl sein");
        softly.assertAll();
    }

    @ParameterizedTest(name = "Ungueltige Eingabe - leer oder null")
    @DisplayName("Ungueltige Eingaben - Leer und Null")
    @NullAndEmptySource
    void ungueltige_eingabe_leer_oder_null(String eingabe) {
        // When
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe(eingabe, null);

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
                .isEqualTo("Eingabe darf nicht leer sein");
        softly.assertAll();
    }

    @ParameterizedTest(name = "Ungueltige Eingabe - {0} Pins, Fehlermeldung: {1}")
    @DisplayName("Ungueltige Eingaben - Negative Zahl und mehr als 10 Pins")
    @CsvSource({
        "-1, Pins dürfen nicht negativ sein",
        "11, Maximale Pinanzahl ist 10"
    })
    void ungueltige_eingabe_ungueltige_pinanzahl(String eingabe, String erwarteteFehlerMeldung) {
        // When
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe(eingabe, null);

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung()).isEqualTo(erwarteteFehlerMeldung);
        softly.assertAll();
    }

    @Test
    @DisplayName("Ungueltige Eingabe - Summe ueber 10 im zweiten Wurf")
    void ungueltige_eingabe_summe_ueber_zehn() {
        // Given
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(ERSTER_WURF_PINS));

        // When
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("6", frame);

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isFalse();
        softly.assertThat(ergebnis.getFehlerMeldung())
                .isEqualTo("Summe der Pins darf nicht über 10 sein. Noch 5 Pins möglich");
        softly.assertAll();
    }

    @Test
    @DisplayName("Gueltige Eingabe - Summe genau 10 im zweiten Wurf")
    void gueltige_eingabe_summe_genau_zehn() {
        // Given
        Frame frame = new Frame(1);
        frame.getWuerfe().add(new Wurf(ERSTER_WURF_PINS));

        // When
        ValidiereErgebnis ergebnis = inputValidator.validiereEingabe("5", frame);

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(ergebnis.isGueltig()).isTrue();
        softly.assertThat(ergebnis.getFehlerMeldung()).isNull();
        softly.assertAll();
    }
}
