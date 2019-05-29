package bankband.bank.controllers;

import bankband.bank.Database;
import bankband.bank.models.User;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    private UserRepository userRepository = new UserRepository();

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField email;

    @FXML
    private TextField password;


    /**
     * Používáme otazníky -> řeknou databázi že to budou pouze hodnoty, předejdeme problému,
     * že by uživatel např. dropnul tabulku -> sql injection
     */
    public void onRegister() throws IOException {


        String hash = Password.hashPassword(password.getText());

        User user = new User();
        user.setName(name.getText());
        user.setSurname(surname.getText());
        user.setEmail(email.getText());
        user.setPassword(hash);

        if(userRepository.create(user)!=null){

            Auth.get().setUser(user);
            SceneManager.get().activate("main");
        }


    }

}
