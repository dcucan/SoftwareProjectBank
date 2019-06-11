package bankband.bank.controllers;

import bankband.bank.models.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;


public class AccountController implements Controller {

    @Override
    public void initialize() {
        number.setText(account.getNumber() + "/" + account.getPostNumber());
        balance.setText("" + account.getBalance());
    }

    @FXML
    private Label number;

    @FXML
    private Label balance;

    private Account account;

    private Random random = new Random();

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




    public void onNewCard() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new NewCardController(account));
        loader.setLocation(getClass().getClassLoader().getResource("newCard.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("New card");
        stage.show();





        /**
        String hash = Password.hashPassword(random.nextInt(8999) + 1000 + "" );

        CardRepository repository = new CardRepository();

        Card card = new Card();
        card.setAccountId(account);
        card.setNumber(random.nextInt(8999) + 1000 + random.nextInt(8999) + 1000
        + random.nextInt(8999) + 1000 + random.nextInt(8999) + 1000);
        card.setCcv(random.nextInt(899)+100);
        card.setExpirationDate(new Date(2022));
        card.setImage("image");
        card.setLimit(10000);
        card.setPin(hash);

        repository.create(card);

         **/


    }
}
