package modell;

import view.View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Konto {
    private int iban;
    private int ueberweisungslimit;
    private double kontostand;
    private Kunde kunde;
    private Bank bank;
    private double dispolimit;
    private List<Transaktion> transaktionsliste;
    public static final View view = new View();
    public static final String REALISTISCHER_BEREICH = "liegt im nicht realistischen Bereich.";

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

    @Override
    public String toString() {
        return "\nIBAN: " + getIban() + "\nKontostand: " + getKontostand() + "€";
    }

    public void zeigeTransaktionen() {
        transaktionsliste.sort(Comparator.comparing(Transaktion::getZeitpunkt));
        for (Transaktion t : transaktionsliste) {
            System.out.println(t);
        }
    }

    public void einzahlen(double betrag) {
        try {
            if (betrag <= 0) {
                throw new IllegalArgumentException("Einzahlung fehlgeschlagen: " + betrag + REALISTISCHER_BEREICH);
            }
            if (kunde.getBargeld() <= 0) {
                throw new IllegalArgumentException("Einzahlung fehlgeschlagen: Kein Bargeld vorhanden.");
            }
            double test = kunde.getBargeld();
            if (test - betrag < 0) {
                throw new IllegalArgumentException("Einzahlung fehlgeschlagen: Nicht genügend Bargeld vorhanden.");
            }
            this.kontostand += betrag;
            double test2 = kunde.getBargeld();
            test2 -= betrag;
            kunde.setBargeld(test2);
            view.ausgabe("Einzahlung erfolgreich: " + betrag + "€ wurde auf dein Konto eingezahlt.");
            this.transaktionsliste.add(new Transaktion(null, this, betrag, null));
        } catch (IllegalArgumentException e) {
            view.ausgabe(e.getMessage());
        }
    }

    public void ueberweisungEingang(double betrag, String verwendungszweck) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: " + betrag + REALISTISCHER_BEREICH);
        }
        if (betrag > this.ueberweisungslimit) {
            view.ausgabe("Überweisung fehlgeschlagen: Überweisungslimit überschritten.");
        }
        this.kontostand += betrag;
        this.transaktionsliste.add(new Transaktion(null, this, betrag, verwendungszweck));
    }

    public void ueberweisungAbzug(double betrag, String verwendungszweck) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: " + betrag + REALISTISCHER_BEREICH);
        }
        if (betrag > this.ueberweisungslimit) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: Überweisungslimit überschritten.");
        }
        if (this.kontostand - betrag < this.dispolimit) {
            throw new IllegalArgumentException("Dispolimit erreicht.");
        }
        this.kontostand -= betrag;
        transaktionsliste.add(new Transaktion(this, null, betrag, verwendungszweck));
    }

    public void abheben(double betrag) {
        try {
            if (betrag <= 0) {
                throw new IllegalArgumentException("Abhebung fehlgeschlagen: " + betrag + REALISTISCHER_BEREICH);
            }
            if (this.kontostand - betrag < this.dispolimit) {
                throw new IllegalArgumentException("Abhebung fehlgeschlagen: Das Dispolimit wurde erreicht.");
            }
            double kundeBargeldTemporaer = kunde.getBargeld();
            kundeBargeldTemporaer += betrag;
            kunde.setBargeld(kundeBargeldTemporaer);
            this.kontostand -= betrag;
            view.ausgabe("Abhebung erfolgreich: " + betrag + "€ wurde von deinem Konto abgehoben.");
            transaktionsliste.add(new Transaktion(this, null, betrag, null));
        } catch (IllegalArgumentException e) {
            view.ausgabe(e.getMessage());
        }
    }

}

