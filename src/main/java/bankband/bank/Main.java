package bankband.bank;

import bankband.bank.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("login.fxml"));
        loader.setController(new LoginController());

        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
