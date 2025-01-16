package modell;

import view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Said Halilovic
 * @version 1.0
 */
public class Transaktion {
    // Attribute
    private final Konto sender;
    private final Konto empfaenger;
    private final String verwendungszweck;
    private final double betrag;
    private final int transaktionsnummer;
    private final String zeitpunkt;
    private final Random rand = new Random();
    private static final Set<Integer> verwendeteTransaktionsnummern = new HashSet<>();
    public static final View view = new View();

    /**
     * Konstruktor für das Transaktionsobjekt.
     * @param sender # Sender Konto
     * @param empfaenger # Empfänger Konto
     * @param betrag # Betrag, welcher überwiesen werden soll
     * @param verwendungszweck # Verwendungszweck
     */
    public Transaktion(Konto sender, Konto empfaenger, double betrag, String verwendungszweck) {
        this.sender = sender;
        this.empfaenger = empfaenger;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
        this.zeitpunkt = formatiereZeitpunkt();
        this.transaktionsnummer = generiereTransaktionsnummer();
    }
    // Getter Methode
    public String getZeitpunkt() {
        return zeitpunkt;
    }

    /**
     * Führt Transaktionen durch.
     * {@link Konto#ueberweisungAbzug(double, String)}
     * {@link Konto#ueberweisungEingang(double, String)}
     */
    public void durchfuehren() {
        sender.ueberweisungAbzug(betrag, verwendungszweck);
        empfaenger.ueberweisungEingang(betrag, verwendungszweck);
        System.out.print("Haben Sie einen Moment Geduld");
        printPunkte();
        view.ausgabe("\nTransaktion erfolgreich: " + betrag + "€ wurden an das Konto " + empfaenger.getKontonummer() + " überwiesen.");
    }

    @Override
    public String toString() {
        return "Transaktion " + transaktionsnummer + ":" + " \nZeitpunkt: " + zeitpunkt + "\nBetrag: " + betrag + "€" + "\nVerwendungszweck: " + verwendungszweck + "\n----------------------";
    }

    /**
     * Generiert eine zufällige Transaktionsnummer bis 10000 und stellt sicher, dass diese nicht doppelt vorhanden ist.
     * @return int
     */
    public int generiereTransaktionsnummer() {
        int transaktionsnummerGeneriert;
        do {
            transaktionsnummerGeneriert = rand.nextInt(10000);
        }
        while (verwendeteTransaktionsnummern.contains(transaktionsnummerGeneriert));
        verwendeteTransaktionsnummern.add(transaktionsnummerGeneriert);
        return transaktionsnummerGeneriert;
    }

    /**
     * Formatiert den Zeitpunkt in eine lesbare Form.
     * @return String
     */
    public String formatiereZeitpunkt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. LLLL yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    /**
     * Gibt drei Punkte in einem Abstand von drei Sekunden aus.
     */
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
