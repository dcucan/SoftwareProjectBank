package bankband.bank.models;

import bankband.bank.repositories.TransactionTypeRepository;

import java.util.Date;

public class Transaction {

    private int id;

    private int amount;

    private Date dateTime;

    private Account fromAccount;

    private Account toAccount;

    private int transactionTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public boolean isIncommingFor(Account account) {
        return account.getId() == this.getToAccount().getId();
    }

    public TransactionType getTransactionType() {
        return new TransactionTypeRepository().findBy("id", this.transactionTypeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Transaction)) return false;

        return ((Transaction) o).getId() == this.getId();
    }


}
