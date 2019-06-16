package bankband.bank.events;

import bankband.bank.models.Account;

public class AccountUpdated {

    Account account = new Account();

    public AccountUpdated(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return account;
    }
}
