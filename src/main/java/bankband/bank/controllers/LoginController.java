package bankband.bank.controllers;

import bankband.bank.models.User;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    private UserRepository userRepo = new UserRepository();

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    public void onLogin() throws IOException {
        User user = userRepo.findByEmail(email.getText());

        if(user == null) {
            System.out.println("Invalid email or password.");
        } else if (Password.checkPassword(password.getText(), user.getPassword())) {
            System.out.println("Logged in.");

            Auth.get().setUser(user);
            SceneManager.get().activate("main");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    public void onRegister() throws IOException {
        SceneManager.get().activate("register");
    }

}
