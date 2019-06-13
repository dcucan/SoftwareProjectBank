package bankband.bank.controllers;

import bankband.bank.EventBus;
import bankband.bank.events.NewTransactionCreated;
import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.TransactionRepository;
import bankband.bank.repositories.TransactionTypeRepository;
import bankband.bank.services.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class NewTransactionController implements Controller {

    private Account fromAccount;

    @FXML
    private TextField number;

    @FXML
    private TextField postCode;

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<String> transactionType;

    @FXML
    private Label fal;

    @FXML
    private Label tru;


    public NewTransactionController(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    @Override
    public void initialize() {
        transactionType.getItems().clear();
        transactionType.getItems().setAll("Alcohol", "Food", "Gas");
    }


    public void onTransactionType() {
        String selected = transactionType.getSelectionModel().getSelectedItem();
        System.out.println(selected);
    }


    public void onConfirm() throws IOException {

        if(this.transactionType.getSelectionModel().getSelectedItem() == null) {
            fal.setText("Missing type of transaction");
            return;
        }

        fal.setText("");
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionTypeRepository transactionTypeRepository = new TransactionTypeRepository();

        int cash = Integer.parseInt(amount.getText());

        Account toAccount = accountRepository.findByNumber(Integer.parseInt(number.getText()));

        Transaction transaction = new Transaction();
        transaction.setAmount(cash);
        transaction.setDateTime(Date.valueOf(LocalDate.now()));
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);


        if (transactionRepository.create(transaction) != null) {

            TransactionType transactionType = new TransactionType();
            transactionType.setType(this.transactionType.getSelectionModel().getSelectedItem());
            transactionType.setTransactionId(transaction);
            transactionTypeRepository.create(transactionType);

            fromAccount.setBalance(fromAccount.getBalance() - cash);
            accountRepository.update(fromAccount);

            toAccount.setBalance(toAccount.getBalance() + cash);
            accountRepository.update(toAccount);
            tru.setText("Successful");
            number.clear();
            amount.clear();
            postCode.clear();




            EventBus.get().send(new NewTransactionCreated(transaction));
        } else {
            fal.setText("Something went wrong");
        }
    }


}
