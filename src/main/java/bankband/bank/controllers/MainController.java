package bankband.bank.controllers;

import bankband.bank.EventBus;
import bankband.bank.events.NewCardCreated;
import bankband.bank.events.NewTransactionCreated;
import bankband.bank.models.Account;
import bankband.bank.models.Card;
import bankband.bank.models.TransactionType;
import bankband.bank.models.User;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.CardRepository;
import bankband.bank.repositories.TransactionTypeRepository;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.services.SceneManager;
import bankband.bank.services.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController implements Controller {

    @FXML
    private Label name;

    @FXML
    private ListView<Pane> accounts;

    @FXML
    private ListView<Pane> cards;

    @FXML
    private PieChart pieChart;

    @FXML
    private PieChart pieChart1;

    private UserRepository userRepo = new UserRepository();
    private AccountRepository accountRepo = new AccountRepository();

    public void initialize() {
        setName();
        updateAccounts();
        updateCards();
        updateChart();

        EventBus.get().subscribe("transactionPrinter", NewTransactionCreated.class, e -> {
            updateAccounts();
            updateChart();
        });
        EventBus.get().subscribe("cardCreated", NewCardCreated.class, event -> {
            updateCards();
        });
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

    public void updateCards(){

        CardRepository cardRepository = new CardRepository();
        AccountRepository accountRepository = new AccountRepository();

        User user = Auth.get().getUser();
        List<Account> allAccounts = accountRepository.findAllForUser(user);

        List<Card> allCards = new ArrayList<>();

        for (Account acount : allAccounts){

            allCards = cardRepository.findAllForAccount(acount);

            for (Card card : allCards ){

                FXMLLoader loader = new FXMLLoader();
                loader.setController(new CardController(card));
                loader.setLocation(getClass().getClassLoader().getResource("card.fxml"));

                try {
                    Pane pane = loader.load();
                    cards.getItems().add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    public void updateChart(){

        Stats stats = new Stats();

        HashMap<String,Integer> map = stats.getSpendsAlcohol();
        ObservableList<PieChart.Data>list2 = FXCollections.observableArrayList(
                new PieChart.Data("Alcohol", map.get("Alcohol").intValue()),
                new PieChart.Data("Food", map.get("Food").intValue())
        );

        pieChart1.setData(list2);

        ObservableList<PieChart.Data>list = FXCollections.observableArrayList(
                new PieChart.Data("Alcohol", stats.getAlcohol()),
                new PieChart.Data("Food", stats.getFood())
        );

        pieChart.setData(list);







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
