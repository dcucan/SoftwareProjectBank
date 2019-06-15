package bankband.bank.models;

import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.TransactionRepository;
import bankband.bank.services.Auth;

import java.util.List;
import java.util.stream.Collectors;

public class Account {

    private int id;

    private int userId;

    private int number;

    private String type;

    private int balance;

    private int postNumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public List<Transaction> getTransactions() {
        return (new TransactionRepository()).findAllForAccount(this);
    }


    public List<Transaction> getOutgoingTransactions() {
        return getTransactions().stream()
                .filter(transaction -> transaction.getFromAccount().equals(this))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Account)) return false;

        return ((Account) o).getId() == this.getId();
    }




}
