package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.Random;

public class NewAccountController implements Controller {

    private AccountRepository accountRepository = new AccountRepository();
    Random random = new Random();

    @FXML
    private TextField type;

    /**
     * Vytvoření nové karty
     */
    public void onCreate() {
        Account account = new Account();
        account.setType(type.getText());
        account.setBalance(0);
        account.setNumber(random.nextInt(9000000) + 1000000);
        account.setPostNumber(6400);
        accountRepository.create(account);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("New account successfully created!");
        alert.showAndWait();
        try {
            onGoBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vrátí se na hlavní obrazovku
     * @throws IOException
     */
    public void onGoBack() throws IOException {
        SceneManager.get().activate("main");
    }

    @Override
    public void initialize() {
        type.clear();

    }


}
