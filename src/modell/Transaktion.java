package modell;

import view.View;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Transaktion {

    private double betrag;
    private Konto sender;
    private Konto empfaenger;
    private String verwendungszweck;
    private LocalDateTime zeitpunkt;
    public static final View view = new View();

    public Transaktion(Konto sender, Konto empfaenger, double betrag, String verwendungszweck) {
        this.sender = sender;
        this.empfaenger = empfaenger;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
        this.zeitpunkt = LocalDateTime.now();
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public void durchfuehren() {
        try {
            if (sender != null) {
                sender.abheben(betrag, verwendungszweck);
            }
            if (empfaenger != null) {
                empfaenger.einzahlung(betrag, verwendungszweck);
            }
            System.out.print("Haben Sie einen Moment Geduld");
            for(int i = 0; i <= 2; i++) {
                System.out.print(".");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            view.ausgabe("Transaktion ausgeführt: " + verwendungszweck);
        } catch (IllegalArgumentException e) {
            view.ausgabe("Fehler bei der Transaktion: " + e.getMessage());
        }
    }
    public String toString() {
        return "Transaktion [Zeitpunkt=" + zeitpunkt + ", Betrag=" + betrag + ", Beschreibung=" + verwendungszweck + "]";
    }
}
