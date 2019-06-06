package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.TransactionRepository;
import bankband.bank.services.SceneManager;
import javafx.fxml.FXML;
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
    private TextField type;

    public NewTransactionController(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    @Override
    public void initialize(){

    }

    public void onConfirm(){
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        int cash = Integer.parseInt(amount.getText());

        Account toAccount = accountRepository.findByNumber(Integer.parseInt(number.getText()));

        Transaction transaction = new Transaction();
        transaction.setAmount(cash);
        transaction.setDateTime(Date.valueOf(LocalDate.now()));
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        if(transactionRepository.create(transaction) != null) {
            fromAccount.setBalance(fromAccount.getBalance() - cash);
            accountRepository.update(fromAccount);

            toAccount.setBalance(toAccount.getBalance() + cash);
            accountRepository.update(toAccount);
        }
    }

    public void onGoBack() throws IOException {

        SceneManager.get().activate("main");
    }
}
