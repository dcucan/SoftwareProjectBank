package bankband.bank.controllers;

import bankband.bank.models.User;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController implements Controller {

    private UserRepository userRepo = new UserRepository();


    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label info;

    @Override
    public void initialize() {

    }


    /**
     * Přihlášení uživatele podle emailu
     * Rozhashování hesla z databáze, porovná se se vtupem od uživatele
     * @throws IOException
     */
    public void onLogin() throws IOException {
        User user = userRepo.findByEmail(email.getText());


        if (user == null) {

            info.setText("Invalid email or password.");
        } else if (Password.checkPassword(password.getText(), user.getPassword())) {


            Auth.get().setUser(user);

            SceneManager.get().activate("main");

        } else {

            info.setText("Invalid email or password.");
        }
    }

    /**
     * Zobrazí okno pro registraci nového uživatele
     * @throws IOException
     */
    public void onRegister() throws IOException {
        SceneManager.get().activate("register");
    }


}
