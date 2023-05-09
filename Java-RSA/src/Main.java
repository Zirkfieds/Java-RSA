import gui.RSAGUI;
import utils.KeyManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("RSA Algorithm Implementation in Java");

        KeyManager.initFnMap();

        // TODO: Add args to the app that changes the window title
        RSAGUI app = new RSAGUI();
        app.setVisible(true);


    }
}
