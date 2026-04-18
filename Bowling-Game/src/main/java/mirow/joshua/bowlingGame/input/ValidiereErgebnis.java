package mirow.joshua.bowlingGame.input;

/**
 * Ergebnis der Eingabevalidierung.
 * Enthält den Validierungsstatus und eine optionale Fehlermeldung.
 */
public class ValidiereErgebnis {

    private final boolean gueltig;
    private final String fehlerMeldung;

    /**
     * Erstellt ein neues Validierungsergebnis.
     *
     * @param gueltig       true wenn die Eingabe gültig ist
     * @param fehlerMeldung die Fehlermeldung bei ungültiger Eingabe
     */
    public ValidiereErgebnis(boolean gueltig, String fehlerMeldung) {
        this.gueltig = gueltig;
        this.fehlerMeldung = fehlerMeldung;
    }

    /**
     * Erstellt ein gültiges Validierungsergebnis.
     *
     * @return gültiges {@link ValidiereErgebnis}
     */
    public static ValidiereErgebnis gueltig() {
        return new ValidiereErgebnis(true, null);
    }

    /**
     * Erstellt ein ungültiges Validierungsergebnis mit Fehlermeldung.
     *
     * @param fehlerMeldung die Fehlermeldung
     * @return ungültiges {@link ValidiereErgebnis}
     */
    public static ValidiereErgebnis ungueltig(String fehlerMeldung) {
        return new ValidiereErgebnis(false, fehlerMeldung);
    }

    /**
     * @return true wenn die Eingabe gültig ist
     */
    public boolean isGueltig() {
        return gueltig;
    }

    /**
     * @return die Fehlermeldung oder null bei gültiger Eingabe
     */
    public String getFehlerMeldung() {
        return fehlerMeldung;
    }
}
