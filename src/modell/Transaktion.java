package modell;

import view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Transaktion {

    private final Konto sender;
    private final Konto empfaenger;
    private final String verwendungszweck;
    private final double betrag;
    private final int transaktionsnummer;
    private final String zeitpunkt;
    private final Random rand = new Random();
    private static final Set<Integer> verwendeteTransaktionsnummern = new HashSet<>();
    public static final View view = new View();

    public Transaktion(Konto sender, Konto empfaenger, double betrag, String verwendungszweck) {
        this.sender = sender;
        this.empfaenger = empfaenger;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
        this.zeitpunkt = formatiereZeitpunkt();
        this.transaktionsnummer = generiereTransaktionsnummer();
    }

    public String getZeitpunkt() {
        return zeitpunkt;
    }

    public void durchfuehren() {
        try {
            sender.ueberweisungAbzug(betrag, verwendungszweck);
            empfaenger.ueberweisungEingang(betrag, verwendungszweck);
            System.out.print("Haben Sie einen Moment Geduld");
            printPunkte();
            view.ausgabe("\nTransaktion erfolgreich: " + betrag + "€ wurden an das Konto " + empfaenger.getKontonummer() + " überwiesen.");
        } catch (IllegalArgumentException | NullPointerException e) {
            view.ausgabe(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Transaktion " + transaktionsnummer + ":" + " \nZeitpunkt: " + zeitpunkt + "\nBetrag: " + betrag + "€" + "\nVerwendungszweck: " + verwendungszweck + "\n----------------------";
    }

    public int generiereTransaktionsnummer() {
        int transaktionsnummerGeneriert;
        do {
            transaktionsnummerGeneriert = rand.nextInt(10000);
        }
        while (verwendeteTransaktionsnummern.contains(transaktionsnummerGeneriert));
        verwendeteTransaktionsnummern.add(transaktionsnummerGeneriert);
        return transaktionsnummerGeneriert;
    }

    public String formatiereZeitpunkt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. LLLL yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public void printPunkte() {
        for(int i = 0; i <= 2; i++) {
            System.out.print(".");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
