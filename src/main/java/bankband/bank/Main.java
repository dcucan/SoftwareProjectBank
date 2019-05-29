package bankband.bank;

import bankband.bank.controllers.LoginController;
import bankband.bank.controllers.MainController;
import bankband.bank.controllers.RegisterController;
import bankband.bank.services.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        SceneManager.get().setScene(scene);
        SceneManager.get().newScreen("login", "login.fxml", new LoginController());
        SceneManager.get().newScreen("register", "register.fxml", new RegisterController());
        SceneManager.get().newScreen("main","mainScene.fxml",new MainController());

        SceneManager.get().activate("login");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
