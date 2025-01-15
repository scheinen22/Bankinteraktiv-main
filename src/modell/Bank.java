package modell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import view.View;

public class Bank {

	//Eigenschaften
    private int blz;
    private String bankname;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Konto> konten;
    public static final View view = new View();
    private static final String GEBEN_KONTO = "Geben Sie Ihre Kontonummer ein: ";

    //Konstruktor
    //Standard Konstruktor
    public Bank() {
        this.konten = new ArrayList<>();
    }

    //Spezial Konstruktor
    public Bank(int blz, String bankname) {
        this.setBlz(blz);
        this.setBankname(bankname);
        this.konten = new ArrayList<>();
    }

    //Getter & Setter
    public String getBankname() {
        return bankname;
    }
    public int getBlz() {
        return blz;
    }
    public void setBlz(int blz) {
        this.blz = blz;
    }
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    
    //Methoden
    @Override
    public String toString() {
        return "\n----------------------" + "\nBLZ: " + this.getBlz() + "\nBankname: " + this.getBankname() + "\n----------------------";
    }

    public void ueberweisung(Konto sender, Konto empfaenger, int blz, double betrag, String verwendungszweck) {
        try {
            if (sender == null || sender.getBank() == null || sender.getBank().getBlz() <= 0) {
                throw new NullPointerException("Überweisung fehlgeschlagen: Sender existiert nicht.");
            }
            if (empfaenger == null || empfaenger.getBank() == null || empfaenger.getBank().getBlz() <= 0) {
                throw new NullPointerException("Überweisung fehlgeschlagen: Empfänger existiert nicht.");
            }
            if (empfaenger.getBank().getBlz() != blz) {
                throw new IllegalArgumentException("Überweisung fehlgeschlagen.\nBankleitzahl stimmt nicht überein.\n");
            }
            Transaktion t = new Transaktion(sender, empfaenger, betrag, verwendungszweck);
            t.durchfuehren();
        } catch (IllegalArgumentException | NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
    
    public void addKonto(Konto konto) {
        try {
            if (konto == null) {
                throw new NullPointerException("Das angegebene Konto existiert nicht.");
            } else {
                this.konten.add(konto);
            }
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
    
    public Konto findeKonto(int iban) {
        for (Konto konto : konten) {
            if (konto.getIban() == iban) {
                return konto;
            }
        }
        return null;
    }

    public void ueberweisungInteraktiv() {
    	view.ausgabe(GEBEN_KONTO);
        int ibansender = Integer.parseInt(scanner.nextLine());
        Konto senderkonto = findeKonto(ibansender);
        view.ausgabe("Geben Sie die Kontonummer des Empfängers ein: ");
        int ibanempfaenger = Integer.parseInt(scanner.nextLine());
        Konto empfaenger = findeKonto(ibanempfaenger);
        view.ausgabe("Geben Sie die Bankleitzahl des Empfängers ein: ");
        int blzempfaenger = Integer.parseInt(scanner.nextLine());
        view.ausgabe("Geben Sie den gewünschten Betrag ein: ");
        double betragempfaenger = Double.parseDouble(scanner.nextLine());
        view.ausgabe("Geben Sie den Verwendungszweck ein: ");
        String verwendungszweck = scanner.nextLine();
        ueberweisung(senderkonto, empfaenger, blzempfaenger, betragempfaenger, verwendungszweck);
    }
    
    public void einzahlungInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int ibaneingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(ibaneingabe);
            if (eingabeKonto == null) {
                throw new NullPointerException("Einzahlung fehlgeschlagen: Kontonummer existiert nicht.");
            }
            view.ausgabe("Vorhandenes Bargeld: " + eingabeKonto.getKunde().getBargeld());
            view.ausgabe("Geben Sie den gewünschten Betrag ein: ");
            double betragempfaenger = Double.parseDouble(scanner.nextLine());
            eingabeKonto.einzahlen(betragempfaenger);
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
    
    public void abhebenInteraktiv() {
    	try {
            view.ausgabe(GEBEN_KONTO);
            int ibaneingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(ibaneingabe);
            if (eingabeKonto == null) {
                throw new NullPointerException("Abhebung fehlgeschlagen: Kontonummer existiert nicht.");
            }
            view.ausgabe("Gebe den gewünschten Betrag ein: ");
            double betragempfaenger = Double.parseDouble(scanner.nextLine());
            eingabeKonto.abheben(betragempfaenger);
            view.ausgabe("Aktuelles Bargeld: " + eingabeKonto.getKunde().getBargeld());
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
    
    public void kontoInfosInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int ibaneingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(ibaneingabe);
            if (eingabeKonto == null) {
                throw new NullPointerException("Aktion fehlgeschlagen: Kontonummer existiert nicht.");
            }
            view.ausgabe(eingabeKonto.getBank().toString() + eingabeKonto.toString() + eingabeKonto.getKunde().toString());
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
    
    public void transaktionenAnzeigenInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int ibaneingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(ibaneingabe);
            if (eingabeKonto == null) {
                throw new NullPointerException("Aktion fehlgeschlagen: Kontonummer existiert nicht.");
            }
            eingabeKonto.zeigeTransaktionen();
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
}

