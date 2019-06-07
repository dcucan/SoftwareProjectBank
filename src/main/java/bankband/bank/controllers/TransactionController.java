package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.models.TransactionType;
import bankband.bank.repositories.TransactionRepository;
import bankband.bank.repositories.TransactionTypeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class TransactionController implements Controller {



    @FXML
    private Label date;

    @FXML
    private Label in;

    @FXML
    private Label out;

    @FXML
    private Label number;

    @FXML
    private Label postCode;

    @FXML
    private Label amount;

    @FXML
    private Label type;

    @FXML
    private Pane colorPane;

    @FXML
    private Label fromTo;

    private Transaction transaction = new Transaction();
    private Account account = new Account();
    private TransactionRepository repo = new TransactionRepository();
    private TransactionType transactionType = new TransactionType();
    private TransactionTypeRepository typeRepo = new TransactionTypeRepository();

    public TransactionController(Transaction transaction, Account account){
        this.transaction = transaction;
        this.account = account;


    }



    public void setUp(Account account,Transaction transaction){

        if(repo.isIncoming(transaction, account)){
            colorPane.setStyle("-fx-background-color: #23db1a;");
            date.setText("06-06-2019");
            number.setText(""+transaction.getFromAccount().getNumber());
            postCode.setText(""+transaction.getFromAccount().getPostNumber());
            amount.setText(""+transaction.getAmount());
            type.setText(typeRepo.findByTransaction(transaction).getType());
            fromTo.setText("From account:");

        } else {
            colorPane.setStyle("-fx-background-color: #ff0000;");
            date.setText("06-06-2019");
            number.setText(""+transaction.getToAccount().getNumber());
            postCode.setText(""+transaction.getToAccount().getPostNumber());
            amount.setText(""+transaction.getAmount());
            type.setText(typeRepo.findByTransaction(transaction).getType());
            fromTo.setText("To account:");
        }





    }


    @Override
    public void initialize() {
        setUp(account, transaction);
    }
}
