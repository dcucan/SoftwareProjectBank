package bankband.bank.events;

import bankband.bank.models.Account;

public class AccountDeleted {

    Account account = new Account();

    public AccountDeleted(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return account;
    }

}
