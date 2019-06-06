package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.repositories.TransactionRepository;
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

    public TransactionController(Transaction transaction, Account account){
        this.transaction = transaction;
        this.account = account;


    }



    public void setUp(Account account,Transaction transaction ){

        if(repo.isIncoming(transaction, account)){
            colorPane.setStyle("-fx-background-color: #23db1a;");
            date.setText(transaction.getDateTime().toString());
            number.setText(""+transaction.getFromAccount().getNumber());
            postCode.setText(""+transaction.getFromAccount().getPostNumber());
            amount.setText(""+transaction.getAmount());
            type.setText(transaction.getToAccount().getType());
            fromTo.setText("From account:");

        } else {
            colorPane.setStyle("-fx-background-color: #e03c0b;");
            date.setText(transaction.getDateTime().toString());
            number.setText(""+transaction.getToAccount().getNumber());
            postCode.setText(""+transaction.getToAccount().getPostNumber());
            amount.setText(""+transaction.getAmount());
            type.setText(transaction.getToAccount().getType());
            fromTo.setText("To account:");
        }





    }


    @Override
    public void initialize() {
        setUp(account, transaction);
    }
}
