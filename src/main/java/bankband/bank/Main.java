package bankband.bank;

import bankband.bank.controllers.*;
import bankband.bank.services.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        if(args.length == 1 && args[0].equals("install")) {
            Database.getInstance().install();

            System.out.println("Installation successful.");
        } else {
            launch(args);
        }

        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Pane());
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/bootstrap3.css").toExternalForm());

        SceneManager.get().setScene(scene);
        SceneManager.get().newScreen("login", "login.fxml", new LoginController());
        SceneManager.get().newScreen("register", "register.fxml", new RegisterController());
        SceneManager.get().newScreen("main","mainScene.fxml",new MainController());
        SceneManager.get().newScreen("newAccount","newAccount.fxml", new NewAccountController());
        // SceneManager.get().newScreen("newTransaction","newTransaction.fxml", new NewTransactionController());

        SceneManager.get().activate("login");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
