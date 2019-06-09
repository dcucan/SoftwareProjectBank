package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.models.User;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class MainController implements Controller {

    @FXML
    private Label name;

    @FXML
    private ListView<Pane> accounts;

    private UserRepository userRepo = new UserRepository();
    private AccountRepository accountRepo = new AccountRepository();

    public void initialize() {
        setName();
        updateAccounts();

    }

    public void updateAccounts() {
        List<Account> list = accountRepo.findAllForUser(Auth.get().getUser());
        accounts.getItems().clear();

        for (Account account : list) {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(new AccountController(account));
            loader.setLocation(getClass().getClassLoader().getResource("account.fxml"));
            try {
                Pane pane = loader.load();
                accounts.getItems().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setName() {
        name.setText(Auth.get().getUser().getName());
    }

    public void onLogout() throws IOException {
        User user = null;
        System.out.println(Auth.get().getUser().getName());
        Auth.get().setUser(null);
        if (Auth.get().getUser() == null) {
            System.out.println("null");
        }

        SceneManager.get().activate("login");
    }


    public void onNewAccount() throws IOException {
        SceneManager.get().activate("newAccount");
    }


    public void onNewCard(){

    }

}
