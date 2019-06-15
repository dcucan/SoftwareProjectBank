package bankband.bank.models;

import bankband.bank.repositories.TransactionTypeRepository;

import java.util.Date;

public class Transaction {

    private int id;

    private int amount;

    private Date dateTime;

    private Account fromAccount;

    private Account toAccount;



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

    public TransactionType getType() {
        return (new TransactionTypeRepository()).findByTransaction(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Transaction)) return false;

        return ((Transaction) o).getId() == this.getId();
    }


}
