package bankband.bank.controllers;

import bankband.bank.models.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;



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

    public void onNewTransaction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new NewTransactionController(account));
        loader.setLocation(getClass().getClassLoader().getResource("newTransaction.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("New transaction");
        stage.show();

    }

    public void onTransactionHistory() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new TransactionHistoryController(account));
        loader.setLocation(getClass().getClassLoader().getResource("TransactionHistory.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Transaction history");
        stage.show();


    }
}
