package mirow.joshua.bowlingGame.input;

public class ValidiereErgebnis {
    private boolean gueltig;
    private String fehlerMeldung;

    public ValidiereErgebnis(boolean gueltig, String fehlerMeldung) {
        this.gueltig = gueltig;
        this.fehlerMeldung = fehlerMeldung;
    }

    public static ValidiereErgebnis gueltig() {
        return new ValidiereErgebnis(true, null);
    }

    public static ValidiereErgebnis ungueltig(String fehlerMeldung) {
        return new ValidiereErgebnis(false, fehlerMeldung);
    }

    public boolean isGueltig() { return gueltig; }
    public String getFehlerMeldung() { return fehlerMeldung; }
}
