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
    private static final String UNGUELTIG_KONTO = "Ungültige Kontonummer";
    private static final String UNGUELTIG_BETRAG = "Ungültiger Betrag";

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
        return "\nBLZ: " + this.getBlz() + "\nBankname: " + this.getBankname();
    }

    public void ueberweisung(Konto sender, Konto empfaenger, int blz, double betrag, String verwendungszweck) {
        try {
            if (empfaenger == null || empfaenger.getBank() == null || empfaenger.getBank().getBlz() <= 0) {
                throw new NullPointerException("Empfänger nicht vorhanden");
            }
            if (empfaenger.getBank().getBlz() != blz) {
                System.out.println("Überweisung fehlgeschlagen.\nBankleitzahl stimmt nicht überein.\n");
                return;
            }
            Transaktion t = new Transaktion(sender, empfaenger, betrag, verwendungszweck);
            t.durchfuehren();
        } catch (IllegalArgumentException e) {
            view.ausgabe("Fehler bei der Durchführung: " + e.getMessage());
        }
    }
    
    public void addKonto(Konto konto) {
        if (konto == null) {
        	view.ausgabe("Kontoliste ist null.");
        } else {
            this.konten.add(konto);
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
        if (senderkonto == null) {
        	view.ausgabe(UNGUELTIG_KONTO);
            return;
        }
        view.ausgabe("Geben Sie die Empfängerkontonummer ein: ");
        int ibanempfaenger = Integer.parseInt(scanner.nextLine());
        Konto empfaenger = findeKonto(ibanempfaenger);
        if (empfaenger == null) {
        	view.ausgabe("Ungültiger Empfänger");
            return;
        }
        view.ausgabe("Geben Sie die Bankleitzahl des Empfängers ein: ");
        int blzempfaenger = Integer.parseInt(scanner.nextLine());
        if (blzempfaenger <= 0) {
            view.ausgabe("Ungültige Bankleitzahl");
            return;
        }
        view.ausgabe("Geben die den gewünschten Betrag an: ");
        double betragempfaenger = Double.parseDouble(scanner.nextLine());
        if (betragempfaenger <= 0) {
        	view.ausgabe(UNGUELTIG_BETRAG);
            return;
        }
        view.ausgabe("Geben die den Verwendungszweck an: ");
        String verwendungszweck = scanner.nextLine();
        ueberweisung(senderkonto, empfaenger, blzempfaenger, betragempfaenger, verwendungszweck);
    }
    
    public void einzahlungInteraktiv() {
    	view.ausgabe(GEBEN_KONTO);
        int ibaneingabe = Integer.parseInt(scanner.nextLine());
        Konto eingabeKonto = findeKonto(ibaneingabe);
        view.ausgabe("Bargeld: " + eingabeKonto.getKunde().getBargeld());

        view.ausgabe("Gebe den gewünschten Betrag ein: ");
        double betragempfaenger = Double.parseDouble(scanner.nextLine());
        if (betragempfaenger <= 0) {
        	view.ausgabe(UNGUELTIG_BETRAG);
            return;
        }
        eingabeKonto.einzahlungBargeld(betragempfaenger);
    }
    
    public void abhebenInteraktiv() {
    	view.ausgabe(GEBEN_KONTO);
        int ibaneingabe = Integer.parseInt(scanner.nextLine());
        Konto eingabeKonto = findeKonto(ibaneingabe);
        if (eingabeKonto == null) {
        	view.ausgabe(UNGUELTIG_KONTO);
            return;
        }
        view.ausgabe("Gebe den gewünschten Betrag ein: ");
        double betragempfaenger = Double.parseDouble(scanner.nextLine());
        if (betragempfaenger <= 0) {
        	view.ausgabe(UNGUELTIG_BETRAG);
            return;
        }
        if (eingabeKonto.getKontostand() - betragempfaenger < eingabeKonto.getDispolimit()) {
        	view.ausgabe("Abhebung fehlgeschlagen: Konto überzogen. Dispolimit erreicht.");
            return;
        }
        eingabeKonto.abhebenBargeld(betragempfaenger);
    }
    
    public void kontoInfosInteraktiv() {
        view.ausgabe(GEBEN_KONTO);
        int ibaneingabe = Integer.parseInt(scanner.nextLine());
        Konto eingabeKonto  = findeKonto(ibaneingabe);
        if (eingabeKonto == null) {
            view.ausgabe(UNGUELTIG_KONTO);
            return;
        }
        view.ausgabe(eingabeKonto.getBank().toString() + eingabeKonto.toString() + eingabeKonto.getKunde().toString());
    }
    
    public void transaktionenAnzeigenInteraktiv() {
        view.ausgabe(GEBEN_KONTO);
        int ibaneingabe = Integer.parseInt(scanner.nextLine());
        Konto eingabeKonto  = findeKonto(ibaneingabe);
        if (eingabeKonto == null) {
            view.ausgabe(UNGUELTIG_KONTO);
            return;
        }
        eingabeKonto.zeigeTransaktionen();
    }
}

