package model;

import view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(Transaktion.class.getName());
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
        try {
            if (sender != null) {
                sender.ueberweisungAbzug(betrag, verwendungszweck);
            } else {
                throw new NullPointerException("Sender existiert nicht.");
            }
            if (empfaenger != null) {
                empfaenger.ueberweisungEingang(betrag, verwendungszweck);
            } else {
                throw new NullPointerException("Empfänger existiert nicht.");
            }
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

    /**
     * Generiert eine zufällige Transaktionsnummer bis 10000 und stellt sicher, dass diese nicht doppelt vorhanden ist.
     * @return int
     */
    private int generiereTransaktionsnummer() {
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
    private String formatiereZeitpunkt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. LLLL yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    /**
     * Gibt drei Punkte in einem Abstand von drei Sekunden aus.
     */
    private void printPunkte() {
        for(int i = 0; i <= 2; i++) {
            System.out.print(".");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Thread wurde unterbrochen", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}