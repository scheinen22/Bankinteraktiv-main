package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import view.View;

/**
 * @author Said Halilovic, Patrick Ferrera
 * @version 1.0
 */
public class Bank {
	// Attribute
    private int blz;
    private String bankname;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Konto> konten;
    public static final View view = new View();
    private static final String GEBEN_KONTO = "Geben Sie Ihre Kontonummer ein: ";

    /**
     * Standard Konstruktor für das Bankobjekt, wird im Controller benötigt.
     */
    public Bank() {
        this.konten = new ArrayList<>();
    }

    /**
     * Konstruktor für das Bankobjekt.
     * @param blz # Bankleitzahl, Kennnummer der Bank
     * @param bankname # Name der Bank
     */
    public Bank(int blz, String bankname) {
        this.setBlz(blz);
        this.setBankname(bankname);
        this.konten = new ArrayList<>();
    }

    //Getter & Setter Methoden
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

    /**
     * Fügt die Konten zu einer Arraylist hinzu. Wird genutzt, um die Kontonummer in einer späteren Methode zu finden.
     * @param konto # Kontoobjekt
     * @throws NullPointerException # Falls ein Konto nicht vorhanden ist, wird eine NullpointerException geworfen und abgefangen.
     */
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

    /**
     * FindeKonto sucht aus der Arraylist der Konten das Konto mit der eingegebenen Kontonummer heraus.
     * @param kontonummer # Kontonummer
     * @return kontonummer
     */
    public Konto findeKonto(int kontonummer) {
        for (Konto konto : konten) {
            if (konto.getKontonummer() == kontonummer) {
                return konto;
            }
        }
        return null;
    }

    /**
     * Interaktiver Ablauf der Überweisung. Erstellt nach verschiedenen Checks ein Transaktionsobjekt und führt die Transaktion durch.
     * @see #findeKonto(int)
     * {@link Transaktion#Transaktion(Konto, Konto, double, String)}  Transaktion)}
     * {@link Transaktion#durchfuehren()}
     * @throws IllegalArgumentException # Wird bei falscher Eingabe geworfen.
     * @throws NullPointerException # Wird geworfen, wenn ein Objekt nicht existiert.
     */
    public void ueberweisungInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int kontonummersender = Integer.parseInt(scanner.nextLine());
            Konto sender = findeKonto(kontonummersender);
            if (sender == null) {
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
            Transaktion t = new Transaktion(sender, empfaenger, betragempfaenger, verwendungszweck);
            t.durchfuehren();
        } catch (IllegalArgumentException | NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }

    /**
     * Interaktiver Ablauf der Einzahlung. Ruft die Einzahlungsmethode in der Konto Klasse auf.
     * @see #findeKonto(int)
     * {@link Konto#einzahlen(double)}
     * @throws NullPointerException # Wird geworfen, wenn die Kontonummer nicht existiert.
     */
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

    /**
     * Interaktiver Ablauf der Abhebung. Ruft die Abhebungsmethode in der Konto Klasse auf.
     * @see #findeKonto(int)
     * {@link Konto#abheben(double)}
     * @throws NullPointerException # Wird geworfen, wenn die Kontonummer nicht existiert.
     */
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

    /**
     * Interaktiver Ablauf der Ausgabe der Kontoinformationen. Wird durch die Verkettung der überschriebenen toString() Methoden durchgeführt.
     * @see #findeKonto(int)
     * {@link Konto#toString()}
     * {@link Kunde#toString()}
     * {@link Bank#toString()}
     * @throws NullPointerException # Wird geworfen, wenn die Kontonummer nicht existiert.
     */
    public void kontoInfosInteraktiv() {
        try {
            view.ausgabe(GEBEN_KONTO);
            int kontonummereingabe = Integer.parseInt(scanner.nextLine());
            Konto eingabeKonto = findeKonto(kontonummereingabe);
            if (eingabeKonto == null) {
                throw new NullPointerException("Aktion fehlgeschlagen: Kontonummer existiert nicht.");
            }
            view.ausgabe(eingabeKonto.getBank().toString() + eingabeKonto + eingabeKonto.getKunde().toString());
        } catch (NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }

    /**
     * Interaktiver Ablauf der Transaktionslistenausgabe. Wird durch das Aufrufen der zeigeTransaktionen() Methode erreicht.
     * @see #findeKonto(int)
     * {@link Konto#zeigeTransaktionen()}
     * @throws NullPointerException # Wird geworfen, wenn die Kontonummer nicht existiert.
     */
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

