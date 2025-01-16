package modell;

import view.View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Konto {
    private int kontonummer;
    private int ueberweisungslimit;
    private double kontostand;
    private Kunde kunde;
    private Bank bank;
    private double dispolimit;
    private final List<Transaktion> transaktionsliste;
    public static final View view = new View();
    public static final String IST_EIN_UNGUELTIGER_BETRAG = " ist ein ungültiger Betrag.";

    public Konto(Bank bank, int kontonummer, double kontostand, double dispolimit, int ueberweisungslimit, Kunde kunde) {
        this.setKunde(kunde);
        this.setKontonummer(kontonummer);
        this.setKontostand(kontostand);
        this.setBank(bank);
        this.transaktionsliste = new ArrayList<>();
        this.setDispolimit(dispolimit);
        this.setUeberweisungslimit(ueberweisungslimit);
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
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
        return "\nKontonummer: " + getKontonummer() + "\nKontostand: " + getKontostand() + "€" +"\nÜberweisungslimit: " + getUeberweisungslimit() + "€" + "\nDispolimit: " + getDispolimit() + "€" + "\n----------------------";
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
                throw new IllegalArgumentException("Einzahlung fehlgeschlagen: " + betrag + IST_EIN_UNGUELTIGER_BETRAG);
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
            this.transaktionsliste.add(new Transaktion(null, this, betrag, "Einzahlung"));
        } catch (IllegalArgumentException e) {
            view.ausgabe(e.getMessage());
        }
    }

    public void ueberweisungEingang(double betrag, String verwendungszweck) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: " + betrag + IST_EIN_UNGUELTIGER_BETRAG);
        }
        if (betrag > this.ueberweisungslimit) {
            view.ausgabe("Überweisung fehlgeschlagen: Überweisungslimit überschritten.");
        }
        this.kontostand += betrag;
        this.transaktionsliste.add(new Transaktion(null, this, betrag, verwendungszweck));
    }

    public void ueberweisungAbzug(double betrag, String verwendungszweck) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: " + betrag + IST_EIN_UNGUELTIGER_BETRAG);
        }
        if (betrag > this.ueberweisungslimit) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: Überweisungslimit überschritten.");
        }
        if (this.kontostand - betrag < this.dispolimit) {
            throw new IllegalArgumentException("Überweisung fehlgeschlagen: Das Dispolimit wurde erreicht.");
        }
        this.kontostand -= betrag;
        transaktionsliste.add(new Transaktion(this, null, -betrag, verwendungszweck));
    }

    public void abheben(double betrag) {
        try {
            if (betrag <= 0) {
                throw new IllegalArgumentException("Abhebung fehlgeschlagen: " + betrag + IST_EIN_UNGUELTIGER_BETRAG);
            }
            if (this.kontostand - betrag < this.dispolimit) {
                throw new IllegalArgumentException("Abhebung fehlgeschlagen: Das Dispolimit wurde erreicht.");
            }
            double kundeBargeldTemporaer = kunde.getBargeld();
            kundeBargeldTemporaer += betrag;
            kunde.setBargeld(kundeBargeldTemporaer);
            this.kontostand -= betrag;
            view.ausgabe("Abhebung erfolgreich: " + betrag + "€ wurde von deinem Konto abgehoben.");
            transaktionsliste.add(new Transaktion(this, null, -betrag, "Abhebung"));
        } catch (IllegalArgumentException e) {
            view.ausgabe(e.getMessage());
        }
    }
}

