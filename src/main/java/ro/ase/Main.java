package ro.ase;

import ro.ase.controller.App;

/**
 * Application entry point.
 */
public class Main {

    public static void main(String[] args) {
        App app = new App("data");
        app.run();
    }
}
