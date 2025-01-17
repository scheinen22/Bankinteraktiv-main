package model;

/**
 * @author Alvar Reggio, Joshua Gawenda
 * @version 1.0
 */
public class Kunde {
    // Attribute
    private String vorname;
    private String nachname;
    private String geburtsdatum;
    private String adresse;
    private double bargeld;

    /**
     * Konstruktor für das Kundenobjekt.
     * @param vorname # Vorname des Kunden
     * @param nachname # Nachname des Kunden
     * @param geburtsdatum # Geburtsdatum des Kunden
     * @param adresse # Adresse des Kunden
     * @param bargeld # Bargeld des Kunden
     */
    public Kunde(String vorname, String nachname, String geburtsdatum, String adresse, double bargeld) {
        this.setVorname(vorname);
        this.setNachname(nachname);
        this.setGeburtsdatum(geburtsdatum);
        this.setAdresse(adresse);
        this.setBargeld(bargeld);
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getBargeld() {
        return bargeld;
    }

    public void setBargeld(double bargeld) {
        this.bargeld = bargeld;
    }

    @Override
    public String toString() {
        return "\nVorname: " + this.getVorname() + "\nNachname: " + this.getNachname() + "\nGeburtsdatum: " + this.getGeburtsdatum() + "\nAdresse: " + this.getAdresse() + "\nBargeld: " + this.getBargeld() + "€" + "\n----------------------";
    }
}
