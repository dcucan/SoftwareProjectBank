package bankband.bank.services;

import bankband.bank.models.User;

/**
 * stará se o to, aby aplikace držela informace o přihlášeném uživateli
 */
public class Auth {

    private static Auth instance;
    private User user;

    private Auth(){

    }

    public static Auth get(){
        if (instance==null){
            instance = new Auth();
        }
        return instance;
    }

    //Zjistit kdo je přihlášen


    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }


}
