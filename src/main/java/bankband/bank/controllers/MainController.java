package bankband.bank.controllers;

import bankband.bank.services.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private Label name;

    public void initialize(){
        name.setText(Auth.get().getUser().getName());
    }

}
