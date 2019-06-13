package bankband.bank.controllers;


import bankband.bank.models.User;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import bankband.bank.util.Password;
import com.sun.org.apache.xpath.internal.operations.Equals;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.*;


public class RegisterController implements Controller {

    private UserRepository userRepository = new UserRepository();

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField email;

    @FXML
    private TextField password;


    @Override
    public void initialize() {

        email.clear();
        password.clear();
        surname.clear();
        name.clear();
    }


    /**
     * Používáme otazníky -> řeknou databázi že to budou pouze hodnoty, předejdeme problému,
     * že by uživatel např. dropnul tabulku -> sql injection
     */
    public void onRegister() throws IOException {


        String hash = Password.hashPassword(password.getText());

        User user = new User();
        user.setName(name.getText());
        user.setSurname(surname.getText());
        if(!email.getText().matches("^(.*@.*\\..+)$")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid email!");
            alert.showAndWait();
            return;
        }

        if(password.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid password!");
            alert.showAndWait();
            return;
        }

        user.setEmail(email.getText());
        user.setPassword(hash);

        if (userRepository.create(user) != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("New user successfully created!");
            alert.showAndWait();
            Auth.get().setUser(user);
            SceneManager.get().activate("main");
        }


    }


    public void onBackTo() throws IOException {
        SceneManager.get().activate("login");
    }

}
