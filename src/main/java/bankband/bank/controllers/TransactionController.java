package bankband.bank.controllers;

import bankband.bank.models.Transaction;

public class TransactionController implements Controller {


    private Transaction transaction = new Transaction();

    public TransactionController(Transaction transaction){
        this.transaction = transaction;
    }





    @Override
    public void initialize() {

    }
}
