package bankband.bank.events;

import bankband.bank.models.Transaction;

public class NewTransactionCreated {

    private Transaction transaction;

    public NewTransactionCreated(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

}
