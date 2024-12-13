package modell;

import view.View;

import java.util.ArrayList;
import java.util.List;

public class Konto {
    private int iban;
    private int ueberweisungslimit;
    private double kontostand;
    private Kunde kunde;
    private Bank bank;
    private double dispolimit;
    private List<String> transaktionsliste;
    public static final View view = new View();

    public Konto() {
        this.transaktionsliste = new ArrayList<>();
    }

    public Konto(Bank bank, int iban, double kontostand, double dispolimit, int ueberweisungslimit, Kunde kunde) {
        this.setKunde(kunde);
        this.setIban(iban);
        this.setKontostand(kontostand);
        this.setBank(bank);
        this.transaktionsliste = new ArrayList<>();
        this.setDispolimit(dispolimit);
        this.setUeberweisungslimit(ueberweisungslimit);
    }
    public int getIban() {
        return iban;
    }
    public void setIban(int iban) {
        this.iban = iban;
    }
    public double getKontostand() {
        return kontostand;
    }
    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }
    public Kunde getKunde() {
        return kunde;
    }
    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }
    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    public void setDispolimit(double dispolimit) {
        this.dispolimit = dispolimit;
    }
    public double getDispolimit() {
        return dispolimit;
    }
    public int getUeberweisungslimit() {
        return ueberweisungslimit;
    }
    public void setUeberweisungslimit(int ueberweisungslimit) {
        this.ueberweisungslimit = ueberweisungslimit;
    }
    public String kontoinformationenAnzeigenString() {
        return "\nIBAN: " + getIban() + "\nKontostand: " + getKontostand() + "€";
    }
    public void einzahlung(double betrag) {
        if (betrag <= 0) {
            view.ausgabe("Einzahlung fehlgeschlagen: " + betrag + "liegt im nicht realistischen Bereich.");
            return;
        }
        if (kunde.getBargeld() <= 0) {
            view.ausgabe("Einzahlung fehlgeschlagen: Kein Bargeld vorhanden.");
            return;
        }
        double test = kunde.getBargeld();
        if (test - betrag < 0) {
            view.ausgabe("Einzahlung fehlgeschlagen: Nicht genügend Bargeld vorhanden");
            return;
        }
        this.kontostand += betrag;
        double test2 = kunde.getBargeld();
        test2 -= betrag;
        kunde.setBargeld(test2);
        view.ausgabe("Einzahlung erfolgreich: " + betrag + "€ wurde auf dein Konto eingezahlt.");
        this.transaktionsliste.add("Einzahlung: +" + betrag + " €");
    }
    public void abheben(double betrag) {
        if (betrag <= 0) {
            return;
        }
        if (this.kontostand - betrag < this.dispolimit) {
            return;
        }
        double test2 = kunde.getBargeld();
        test2 += betrag;
        kunde.setBargeld(test2);
        this.kontostand -= betrag;
        this.transaktionsliste.add("Abhebung: -" + betrag + " €");
    }
}
