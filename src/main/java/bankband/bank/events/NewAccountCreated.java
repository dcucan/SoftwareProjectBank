package bankband.bank.events;

import bankband.bank.models.Account;

public class NewAccountCreated {

    Account account = new Account();

    public NewAccountCreated(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return account;
    }
}
