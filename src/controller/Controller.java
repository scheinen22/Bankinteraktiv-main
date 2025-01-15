package controller;

import java.util.Scanner;

import modell.Bank;
import modell.Konto;
import modell.Kunde;
import view.View;

public class Controller {
	//Eigenschaften
	View view = new View(); 
	Scanner scanner = new Scanner(System.in);
	Bank bank = new Bank();
	
	//Start Variablen anlegen
	public void startVariablen() {
		Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 1000));
        Konto konto2 = new Konto(new Bank(123456, "Sparkasse"), 2131, 31331.32, -2000, 2000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 200));
        Konto konto3 = new Konto(new Bank(123,"Deutsche Bank"), 9876, 0, -2000, 2000, new Kunde("Manuel", "Hammer", "16.04.1987", "Grafstraße", 0));
        bank.addKonto(konto);
        bank.addKonto(konto2);
        bank.addKonto(konto3);
	}
	
	//Ablauf des Programmes
	public void run() {
			startVariablen();
	        boolean running = true;
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
	                    bank.ueberweisungInteraktiv();
	                    break;
	                case 2:
	                    bank.einzahlungInteraktiv();
	                    break;
	                case 3:
	                    bank.abhebenInteraktiv();
	                    break;
	                    case 4:
	                        bank.transaktionenAnzeigenInteraktiv();
	                        break;
	                        case 5:
	                            bank.kontoInfosInteraktiv();
	                            break;
	                            case 6:
	                                running = false;
	                                break;
	                                default:
	                                    break;
	            }
			}
	}
}
