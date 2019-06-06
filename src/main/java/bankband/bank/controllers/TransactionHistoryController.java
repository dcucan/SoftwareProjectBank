package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.models.Transaction;
import bankband.bank.repositories.TransactionRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class TransactionHistoryController implements Controller {


    private Account account;
    private TransactionRepository repo = new TransactionRepository();

    @FXML
    private ListView<Pane> transactions;


    public TransactionHistoryController(Account account){
        this.account = account;
    }

    @Override
    public void initialize(){

        updateTransactions();
    }

    public void updateTransactions(){

        List<Transaction> list = repo.findAllForAccount(account);
        transactions.getItems().clear();


        for (Transaction transaction : list){

            FXMLLoader loader = new FXMLLoader();
            loader.setController(new  TransactionController(transaction, account));
            loader.setLocation(getClass().getClassLoader().getResource("singleTransaction.fxml"));

            try {
                Pane pane = loader.load();
                transactions.getItems().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }









}
