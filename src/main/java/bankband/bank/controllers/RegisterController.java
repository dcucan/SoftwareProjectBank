package bankband.bank.controllers;


import bankband.bank.models.User;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;


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
        user.setEmail(email.getText());
        user.setPassword(hash);

        if (userRepository.create(user) != null) {

            Auth.get().setUser(user);
            SceneManager.get().activate("main");
        }


    }


    public void onBackTo() throws IOException {
        SceneManager.get().activate("login");
    }

}
