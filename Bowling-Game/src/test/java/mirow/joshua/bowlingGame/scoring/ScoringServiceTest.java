package mirow.joshua.bowlingGame.scoring;

import org.assertj.core.api.SoftAssertions;
import mirow.joshua.bowlingGame.game.Frame;
import mirow.joshua.bowlingGame.game.Spiel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoringServiceTest {

    private ScoringService scoringService;

    @BeforeEach
    void setUp() {
        scoringService = new ScoringServiceImpl();
    }

    @Test
    @DisplayName("Normaler Wurf mit 5 Pins - Score wird korrekt gesetzt")
    void normaler_wurf_fuenf_pins() {
        // Given
        Spiel spiel = new Spiel();

        // When
        scoringService.verarbeiteWurf(spiel, 5);

        // Then
        Frame frame = spiel.getFrames().getFirst();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(frame.getWuerfe().size()).isEqualTo(1);
        softly.assertThat(frame.getWuerfe().getFirst().pins()).isEqualTo(5);
        softly.assertThat(frame.isStrike()).isFalse();
        softly.assertThat(frame.isSpare()).isFalse();
        softly.assertThat(frame.getScore()).isEqualTo(5);
        softly.assertAll();
    }

    @Test
    @DisplayName("Normaler Frame mit zwei Wuerfen - Score wird korrekt addiert")
    void normaler_frame_zwei_wuerfe() {
        // Given
        Spiel spiel = new Spiel();

        // When
        scoringService.verarbeiteWurf(spiel, 5);
        scoringService.verarbeiteWurf(spiel, 3);

        // Then
        Frame frame = spiel.getFrames().getFirst();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(frame.getWuerfe().size()).isEqualTo(2);
        softly.assertThat(frame.getScore()).isEqualTo(8);
        softly.assertThat(frame.isComplete()).isTrue();
        softly.assertThat(frame.isSpare()).isFalse();
        softly.assertThat(frame.isStrike()).isFalse();
        softly.assertAll();
    }

    @Test
    @DisplayName("Spare - Score wird mit Bonus des naechsten Wurfs berechnet")
    void spare_frame() {
        // Given
        Spiel spiel = new Spiel();

        // When
        scoringService.verarbeiteWurf(spiel, 7);
        scoringService.verarbeiteWurf(spiel, 3);
        scoringService.verarbeiteWurf(spiel, 5); // Bonus Wurf

        // Then
        Frame frame = spiel.getFrames().getFirst();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(frame.isSpare()).isTrue();
        softly.assertThat(frame.isComplete()).isTrue();
        softly.assertThat(frame.getScore()).isEqualTo(15);
        softly.assertThat(frame.getBonusStatus()).isEqualTo(BonusStatus.KEIN_BONUS);
        softly.assertAll();
    }

    @Test
    @DisplayName("Strike - Score wird mit Bonus der naechsten zwei Wuerfe berechnet")
    void strike_frame() {
        // Given
        Spiel spiel = new Spiel();

        // When
        scoringService.verarbeiteWurf(spiel, 10); // Strike
        scoringService.verarbeiteWurf(spiel, 5);  // Bonus Wurf 1
        scoringService.verarbeiteWurf(spiel, 3);  // Bonus Wurf 2

        // Then
        Frame frame = spiel.getFrames().getFirst();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(frame.isStrike()).isTrue();
        softly.assertThat(frame.isComplete()).isTrue();
        softly.assertThat(frame.getScore()).isEqualTo(18);
        softly.assertThat(frame.getBonusStatus()).isEqualTo(BonusStatus.KEIN_BONUS);
        softly.assertAll();
    }

    @Test
    @DisplayName("Zwei aufeinanderfolgende Strikes - Bonus wird korrekt berechnet")
    void zwei_aufeinanderfolgende_strikes() {
        // Given
        Spiel spiel = new Spiel();

        // When
        scoringService.verarbeiteWurf(spiel, 10); // Strike Frame 1
        scoringService.verarbeiteWurf(spiel, 10); // Strike Frame 2
        scoringService.verarbeiteWurf(spiel, 5);  // Bonus Wurf
        scoringService.verarbeiteWurf(spiel, 3);  // Normaler Wurf

        // Then
        Frame frame1 = spiel.getFrames().getFirst();
        Frame frame2 = spiel.getFrames().get(1);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(frame1.getScore()).isEqualTo(25); // 10 + 10 + 5
        softly.assertThat(frame2.getScore()).isEqualTo(18); // 10 + 5 + 3
        softly.assertThat(frame1.getBonusStatus()).isEqualTo(BonusStatus.KEIN_BONUS);
        softly.assertThat(frame2.getBonusStatus()).isEqualTo(BonusStatus.KEIN_BONUS);
        softly.assertAll();
    }

    @Test
    @DisplayName("Perfect Game - 12 Strikes ergeben 300 Punkte")
    void perfect_game() {
        // Given
        Spiel spiel = new Spiel();

        // When - 12 Strikes
        for (int i = 0; i < 12; i++) {
            scoringService.verarbeiteWurf(spiel, 10);
        }

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(spiel.getGesamtScore()).isEqualTo(300);
        softly.assertAll();
    }

    @Test
    @DisplayName("Gesamtscore wird nach jedem Wurf aktualisiert")
    void gesamtscore_wird_live_berechnet() {
        // Given
        Spiel spiel = new Spiel();

        // When
        scoringService.verarbeiteWurf(spiel, 5);
        int scoreNachErstemWurf = spiel.getGesamtScore();

        scoringService.verarbeiteWurf(spiel, 3);
        int scoreNachZweitemWurf = spiel.getGesamtScore();

        // Then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(scoreNachErstemWurf).isEqualTo(5);
        softly.assertThat(scoreNachZweitemWurf).isEqualTo(8);
        softly.assertAll();
    }

}
