package bankband.bank.controllers;

import bankband.bank.EventBus;
import bankband.bank.events.NewTransactionCreated;
import bankband.bank.filters.NaturalNumberFilter;
import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.TransactionRepository;
import bankband.bank.repositories.TransactionTypeRepository;
import bankband.bank.util.Convert;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class NewTransactionController implements Controller {

    private TransactionTypeRepository transactionTypeRepository = new TransactionTypeRepository();

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

        for (TransactionType type : transactionTypeRepository.findAll()) {
            transactionType.getItems().add(type.getName());
        }

        number.setTextFormatter(NaturalNumberFilter.getFormatter());
        postCode.setTextFormatter(NaturalNumberFilter.getFormatter());
        amount.setTextFormatter(NaturalNumberFilter.getFormatter());
    }


    public void onTransactionType() {
        String selected = transactionType.getSelectionModel().getSelectedItem();
        System.out.println(selected);
    }


    public void onConfirm() throws IOException {
        fal.setText("");

        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionTypeRepository transactionTypeRepository = new TransactionTypeRepository();

        Account toAccount = accountRepository.findByNumber(Convert.toInt(number.getText()));

        if (toAccount == null) {
            fal.setText("Invalid account number.");
            return;
        }

        if (toAccount.equals(fromAccount)) {
            fal.setText("Can not send money to yourself.");
            return;
        }

        if (toAccount.getPostNumber() != Convert.toInt(postCode.getText())) {
            fal.setText("Invalid bank code.");
            return;
        }

        if (this.transactionType.getSelectionModel().getSelectedItem() == null) {
            fal.setText("Missing type of transaction.");
            return;
        }

        int cash = Convert.toInt(amount.getText());

        if (cash <= 0) {
            fal.setText("Invalid amount.");
            return;
        }

        TransactionType type = transactionTypeRepository.findBy("name", transactionType.getValue());
        if (type == null) {
            fal.setText("Invalid transaction type.");
            return;
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(cash);
        transaction.setDateTime(Date.valueOf(LocalDate.now()));
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setTransactionTypeId(type.getId());


        if (transactionRepository.create(transaction) == null) {
            fal.setText("Something went wrong.");
            return;
        }

        fromAccount.setBalance(fromAccount.getBalance() - cash);
        accountRepository.update(fromAccount);

        toAccount.setBalance(toAccount.getBalance() + cash);
        accountRepository.update(toAccount);

        tru.setText("Successful");
        number.clear();
        amount.clear();
        postCode.clear();

        EventBus.get().send(new NewTransactionCreated(transaction));
    }
}
