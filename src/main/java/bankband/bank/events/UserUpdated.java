package bankband.bank.events;

import bankband.bank.models.User;


public class UserUpdated {

    User user = new User();

    public UserUpdated(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
