package banksystem;

import controller.Controller;

public class Main {
    public static void main(String[] args) {
        
    	Controller controller = new Controller(); 
        controller.run();
    	
    	
    	/*Bank bank = new Bank();
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 0));
        Konto konto2 = new Konto(new Bank(123456, "Sparkasse"), 2131, 31331.32, -2000, 2000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 0));
        Konto konto3 = new Konto(new Bank(123,"Deutsche Bank"), 9876, 0, -2000, 2000, new Kunde("Manuel", "Hammer", "16.04.1987", "Grafstraße", 0));
        bank.addKonto(konto);
        bank.addKonto(konto2);
        bank.addKonto(konto3);*/
    }
}
