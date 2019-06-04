package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class NewAccountController implements Controller {

    private AccountRepository accountRepository = new AccountRepository();
    Random random = new Random();

    @FXML
    private TextField type;

    public void onCreate() {
        Account account = new Account();
        account.setType(type.getText());
        account.setBalance(00);
        account.setNumber(random.nextInt(9000000) + 1000000);
        account.setPostNumber(0100);
        accountRepository.create(account);
        System.out.println("created");
    }

    public void onGoBack() throws IOException {
        SceneManager.get().activate("main");
    }

    @Override
    public void initialize() {
        type.clear();

    }


}
