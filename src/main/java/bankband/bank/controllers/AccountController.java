package bankband.bank.controllers;

import bankband.bank.models.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class AccountController implements Controller {

    @Override
    public void initialize() {
        number.setText(account.getNumber() + "/" + account.getPostNumber());
        balance.setText(""+account.getBalance());
    }

    @FXML
    private Label number;

    @FXML
    private Label balance;

    private Account account;

    public AccountController(Account account) {
        this.account = account;
    }

    public void onNewTransaction(){

    }

    public void onTransactionHistory(){

    }
}
