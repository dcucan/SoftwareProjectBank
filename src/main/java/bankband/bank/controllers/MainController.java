package bankband.bank.controllers;

import bankband.bank.models.User;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController implements Controller {

    @FXML
    private Label name;

    private UserRepository userRepo = new UserRepository();

    public void initialize() {
        setName();

    }

    public void setName(){
        name.setText(Auth.get().getUser().getName());
    }

    public void onLogout() throws IOException {
        User user = null;
        System.out.println(Auth.get().getUser().getName());
        Auth.get().setUser(null);
        if (Auth.get().getUser() == null) {
            System.out.println("null");
        }

        SceneManager.get().activate("login");
    }



    public void onNewAccount() throws IOException {
        SceneManager.get().activate("newAccount");
    }

}
