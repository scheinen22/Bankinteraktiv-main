package modell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Konto extends Bank {
    private int iban;
    private int ueberweisungslimit;
    private double kontostand;
    private Kunde kunde;
    private Bank bank;
    private double dispolimit;
    private List<String> transaktionsliste;

    public Konto() {
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
    public List<String> getTransaktionsliste() {
        return transaktionsliste;
    }

    public void printTransaktionen() {
        view.ausgabe("Transaktionen für Konto " + this.getIban() + ":");
        for (String transaktion : transaktionsliste) {
            view.ausgabe(transaktion);
        }
    }
    public void addTransaktion(String transaktion) {
        this.transaktionsliste.add(transaktion);
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
    public boolean transaktion(double betrag, Konto empfaenger, int blz, int iban) {
        if (betrag > this.ueberweisungslimit) {
            view.ausgabe("Abgelehnt: Der Betrag " + betrag + " liegt über dem Überweisungslimit.\n");
            return false;
        }
        if (this.kontostand - betrag < -this.dispolimit) {
            view.ausgabe("Abgelehnt: Dispolimit überschritten.\nKontostand: " + getKontostand() + "\n");
            return false;
        }
        this.kontostand -= betrag;
        empfaenger.kontostand += betrag;
        this.transaktionsliste.add("Überweisung an Konto " + empfaenger.getIban() + ": -" + betrag + " €");
        empfaenger.addTransaktion("Eingang von Konto " + this.getIban() + ": +" + betrag + " €");
        System.out.print("Haben Sie einen Moment Geduld");
        for(int i = 0; i <= 2; i++) {
            System.out.print(".");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        view.ausgabe("\n\nÜberweisung erfolgreich: " + betrag + " € an Konto " + empfaenger.getIban());
        return true;
    }
}
