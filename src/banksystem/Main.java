package banksystem;

import controller.Controller;

/**
 * @author Patrick Ferrera
 * @version 1.0
 */
public class Main {
    /**
     * Ausführbarer Code, Ablaufsteuerung wird ausgeführt.
     * @param args # Main Methode
     * {@link Controller#run()}
     */
    public static void main(String[] args) {
    	Controller controller = new Controller();
        controller.run();
    }
}
