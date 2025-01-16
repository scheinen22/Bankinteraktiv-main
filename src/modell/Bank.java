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
    
    public Konto findeKonto(int kontonummer) {
        for (Konto konto : konten) {
            if (konto.getKontonummer() == kontonummer) {
                return konto;
            }
        }
        return null;
    }

    public void ueberweisungInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int kontonummersender = Integer.parseInt(scanner.nextLine());
            Konto senderkonto = findeKonto(kontonummersender);
            if (senderkonto == null) {
                throw new NullPointerException("Überweisung fehlgeschlagen: Sender existiert nicht.");
            }
            view.ausgabe("Geben Sie die Kontonummer des Empfängers ein: ");
            int kontonummerempfaenger = Integer.parseInt(scanner.nextLine());
            Konto empfaenger = findeKonto(kontonummerempfaenger);
            if (empfaenger == null) {
                throw new NullPointerException("Überweisung fehlgeschlagen: Empfänger existiert nicht.");
            }
            view.ausgabe("Geben Sie die Bankleitzahl des Empfängers ein: ");
            int blzempfaenger = Integer.parseInt(scanner.nextLine());
            if (empfaenger.getBank() == null) {
                throw new NullPointerException("Überweisung fehlgeschlagen: Bank existiert nicht.");
            }
            if (empfaenger.getBank().getBlz() != blzempfaenger) {
                throw new IllegalArgumentException("Überweisung fehlgeschlagen: Bankleitzahl stimmt nicht überein.");
            }
            view.ausgabe("Geben Sie den gewünschten Betrag ein: ");
            double betragempfaenger = Double.parseDouble(scanner.nextLine());
            view.ausgabe("Geben Sie den Verwendungszweck ein: ");
            String verwendungszweck = scanner.nextLine();
            Transaktion t = new Transaktion(senderkonto, empfaenger, betragempfaenger, verwendungszweck);
            t.durchfuehren();
        } catch (IllegalArgumentException | NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
    
    public void einzahlungInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int kontonummereingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(kontonummereingabe);
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
            int kontonummereingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(kontonummereingabe);
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
            int kontonummereingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(kontonummereingabe);
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
            int kontonummereingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(kontonummereingabe);
            if (eingabeKonto == null) {
                throw new NullPointerException("Aktion fehlgeschlagen: Kontonummer existiert nicht.");
            }
            eingabeKonto.zeigeTransaktionen();
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }
}

