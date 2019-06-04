package bankband.bank.services;

import bankband.bank.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;
import java.util.HashMap;

/**
 * Singleton starajici se o jednotlive sceny v aplikaci.
 * Scena je obrazovka (napr prihlaseni, registrace, ...) mezi kterymi uzivatel naviguje.
 * SceneManager jednotlive sceny nacita az kdyz sjou potreba pomoci lazy loadingu.
 */
public class SceneManager {


    /**
     * Instance singletonu
     */
    private static SceneManager instance;

    /**
     * Reference na hlavni scenu. SceneManager doopravdy neprepina jednotlive sceny,
     * ale teto jedne hlavni scene meni root element. Root element je JavaFX prvek,
     * ktery je v scene builderu v hiearchii nejvyse a ostatni elementy jsou jeho deti.
     */
    private Scene scene;

    /**
     * Mapa obrazovek mezi kterymi SceneManager muze prepinat.
     * Jako klice jsou pouzity stringy identifikujici danou obrazovku (register, login, ...)
     */
    private HashMap<String, LazyScrene> screnes;

    /**
     * Nastavi hlavni scenu. Je nutne zavolat pred aktivaci prvni obrazovky.
     *
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Prida novou obrazovku do seznamu obrazovek. Jeji nacteni a zobrazeni oddali pomoci lazy loadingu.
     *
     * @param name
     * @param fxml
     * @param controller
     */
    public void newScreen(String name, String fxml, Controller controller) {
        screnes.put(name, new LazyScrene(fxml, controller));
    }

    /**
     * Aktivuje a zobrazi danou obrazovku.
     *
     * @param name
     * @throws IOException
     */
    public void activate(String name) throws IOException {


        LazyScrene screne = screnes.get(name);

        scene.setRoot(screne.get());
        screne.controller.initialize();


    }

    private SceneManager() {
        screnes = new HashMap<>();
    }

    public static SceneManager get() {
        if (instance == null) {
            instance = new SceneManager();
        }

        return instance;
    }

    /**
     * Trida starajici se o lazy loading obrazovek.
     */
    private class LazyScrene {

        /**
         * Cesta k fxml souboru ze ktereho se dana obrazovka nacte.
         */
        private String fxml;

        /**
         * Controller fxml souboru
         */
        private Controller controller;

        /**
         * Instance rootu fxml. Pokud je null, dana obrazovka jeste nebyla nactena.
         */
        private Parent root;

        public LazyScrene(String fxml, Controller controller) {
            this.fxml = fxml;
            this.controller = controller;
            this.root = null;


        }

        /**
         * Vrati root element dane obrazovky. Podle potreby ho nacte, ci vrati jiz drive nacteny.
         *
         * @return
         * @throws IOException
         */
        public Parent get() throws IOException {
            // Pokud je root null, obrazovka jeste nebyla nactena a je treba ji nacist.
            if (root == null) {
                // Vytvorime novy FXMLLoader
                FXMLLoader loader = new FXMLLoader();
                // Nastavime controller obrazovky
                loader.setController(controller);

                // Nastavime FXML soubor obsahujici danou obrazovku
                loader.setLocation(getClass().getClassLoader().getResource(fxml));
                // Nacteme root element
                root = loader.load();
            }

            return root;
        }

    }

}
