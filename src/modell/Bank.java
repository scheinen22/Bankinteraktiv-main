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
    public static View view; 

    //Konstruktor
    //Standard Konstruktor
    public Bank() {
        this.konten = new ArrayList<>();
        this.view = new View();
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
    public String bankinformationenAnzeigenString() {
        return "\nBLZ: " + this.getBlz() + "\nBankname: " + this.getBankname();
    }
    
    public static void transfer(Konto sender, Konto empfaenger, double betrag, int blz, int iban) {
        if (empfaenger == null || empfaenger.getBank() == null || empfaenger.getBank().getBlz() <= 0) {
        	view.ausgabe("Empfänger nicht vorhanden");
            return;
        }
        if (empfaenger.getBank().getBlz() != blz && empfaenger.getIban() != iban) {
        	view.ausgabe("Überweisung fehlgeschlagen.\nIBAN und Bankleitzahl stimmen nicht überein.\n");
            return;
        }
        if (empfaenger.getBank().getBlz() != blz) {
        	view.ausgabe("Überweisung fehlgeschlagen.\nBankleitzahl stimmt nicht überein.\n");
            return;
        }
        if (empfaenger.getIban() != iban) {
        	view.ausgabe("Überweisung fehlgeschlagen.\nIBAN stimmt nicht überein.\n");
            return;
        }
        if (sender.transaktion(betrag, empfaenger, blz, iban)) {
        	view.ausgabe("Überweisung von Konto " + sender.getIban() + " zu Konto " + empfaenger.getIban() + " abgeschlossen.\n");
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
    
    /*public void interaktivesMenu() {
        boolean running = true;
        String gebenKonto = "Geben Sie Ihre Kontonummer ein: ";
        String ungueltigKonto = "Ungültige Kontonummer";
        String ungueltigBetrag = "Ungültiger Betrag";

        while (running) {
        	view.ausgabe("\n--- Bank Menü ---");
        	view.ausgabe("1. Überweisung");
        	view.ausgabe("2. Einzahlung");
        	view.ausgabe("3. Abheben");
        	view.ausgabe("4. Transaktionen anzeigen");
        	view.ausgabe("5. Kontoinformationen anzeigen");
        	view.ausgabe("6. Beenden");
        	view.ausgabe("Wählen Sie eine Option: ");

            int wahl = scanner.nextInt();
            scanner.nextLine();
            switch (wahl) {
                case 1:
                    ueberweisungInter(gebenKonto, ungueltigKonto, ungueltigBetrag);
                    break;
                case 2:
                    einzahlungInter(gebenKonto, ungueltigKonto, ungueltigBetrag);
                    break;
                case 3:
                    abhebenInter(gebenKonto, ungueltigKonto, ungueltigBetrag);
                    break;
                    case 4:
                        transaktionenAnzeigen(gebenKonto, ungueltigKonto);
                        break;
                        case 5:
                            kontoInfos(gebenKonto, ungueltigKonto);
                            break;
                            case 6:
                                running = false;
                                break;
                                default:
                                    break;
            }
        }
    }*/
    
    public void ueberweisungInter(String gebenKonto, String ungueltigKonto, String ungueltigBetrag) {
    	view.ausgabe(gebenKonto);
        int ibansender = Integer.parseInt(scanner.nextLine());
        Konto senderkonto = findeKonto(ibansender);
        if (senderkonto == null) {
        	view.ausgabe(ungueltigKonto);
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
        int blzemp = Integer.parseInt(scanner.nextLine());
        if (blzemp <= 0) {
            view.ausgabe("Ungültige Bankleitzahl");
            return;
        }
        view.ausgabe("Geben die den gewünschten Betrag an: ");
        double betragemp = Double.parseDouble(scanner.nextLine());
        if (betragemp <= 0) {
        	view.ausgabe(ungueltigBetrag);
            return;
        }
        transfer(senderkonto, empfaenger, betragemp, blzemp, ibanempfaenger);
    }
    
    public void einzahlungInter(String gebenKonto, String ungueltigKonto, String ungueltigBetrag) {
    	view.ausgabe(gebenKonto);
        int ibaneing = Integer.parseInt(scanner.nextLine());
        Konto iban = findeKonto(ibaneing);
        view.ausgabe("Bargeld: " + iban.getKunde().getBargeld());
        if (iban == null) {
        	view.ausgabe(ungueltigKonto);
            return;
        }
        view.ausgabe("Gebe den gewünschten Betrag ein: ");
        double betragemp2 = Double.parseDouble(scanner.nextLine());
        if (betragemp2 <= 0) {
        	view.ausgabe(ungueltigBetrag);
            return;
        }
        iban.einzahlung(betragemp2);
    }
    
    public void abhebenInter(String gebenKonto, String ungueltigKonto, String ungueltigBetrag) {
    	view.ausgabe(gebenKonto);
        int ibaneing2 = Integer.parseInt(scanner.nextLine());
        Konto iban2  = findeKonto(ibaneing2);
        if (iban2 == null) {
        	view.ausgabe(ungueltigKonto);
            return;
        }
        view.ausgabe("Gebe den gewünschten Betrag ein: ");
        double betragemp3 = Double.parseDouble(scanner.nextLine());
        if (betragemp3 <= 0) {
        	view.ausgabe(ungueltigBetrag);
            return;
        }
        if (iban2.getKontostand() - betragemp3 < iban2.getDispolimit()) {
        	view.ausgabe("Abhebung fehlgeschlagen: Konto überzogen. Dispolimit erreicht.");
            return;
        }
        iban2.abheben(betragemp3);
        view.ausgabe("Abhebung erfolgreich: " + betragemp3 + "€ wurde von deinem Konto abgehoben.");
    }
    
    public void kontoInfos(String gebenKonto, String ungueltigKonto) {
        view.ausgabe(gebenKonto);
        int ibaneing5 = Integer.parseInt(scanner.nextLine());
        Konto iban5  = findeKonto(ibaneing5);
        if (iban5 == null) {
            view.ausgabe(ungueltigKonto);
            return;
        }
        view.ausgabe(iban5.getBank().bankinformationenAnzeigenString() + iban5.kontoinformationenAnzeigenString() + iban5.getKunde().kundeninformationenAnzeigenString());
    }
    
    public void transaktionenAnzeigen(String gebenKonto, String ungueltigKonto) {
        view.ausgabe(gebenKonto);
        int ibaneing4 = Integer.parseInt(scanner.nextLine());
        Konto iban3  = findeKonto(ibaneing4);
        if (iban3 == null) {
            view.ausgabe(ungueltigKonto);
            return;
        }
        iban3.printTransaktionen();
    }
}

