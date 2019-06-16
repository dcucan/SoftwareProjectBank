package bankband.bank.controllers;

import bankband.bank.EventBus;
import bankband.bank.events.*;
import bankband.bank.models.Account;
import bankband.bank.models.Card;
import bankband.bank.models.User;
import bankband.bank.repositories.AccountRepository;
import bankband.bank.repositories.CardRepository;
import bankband.bank.repositories.UserRepository;
import bankband.bank.services.Auth;
import bankband.bank.util.Password;
import bankband.bank.util.SwitchImage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.List;

public class EditController implements Controller {


    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField email;

    @FXML
    private TextField type;

    @FXML
    private TextField limit;

    @FXML
    private PasswordField oldpassword;

    @FXML
    private PasswordField newpassword;

    @FXML
    private ComboBox<String> account;

    @FXML
    private ComboBox<String> card;

    @FXML
    private ComboBox<String> design;

    @FXML
    private ImageView image;

    UserRepository userRepo = new UserRepository();
    AccountRepository accountRepo = new AccountRepository();
    CardRepository cardRepo = new CardRepository();
    SwitchImage switchImage = new SwitchImage();

    /**
     * Při každé změně se znovu zavolá metoda setUp()
     */
    @Override
    public void initialize() {
        setUp();

        EventBus.get().subscribe("cardDeletedE", CardDeleted.class, event -> {
            setUp();
        });

        EventBus.get().subscribe("cardUpdatedE", CardUpdated.class, event -> {
            setUp();
        });

        EventBus.get().subscribe("accountUpdatedE", AccountUpdated.class, event -> {
            setUp();
        });

        EventBus.get().subscribe("accountDeletedE", AccountDeleted.class, event -> {
            setUp();

        });

        EventBus.get().subscribe("userUpdatedE", UserUpdated.class, event -> {
            setUp();
        });


    }

    /**
     * Změní nastavení profilu uživatele jak v aplikaci, tak v databázi
     */
    public void onEditProfile() {
        User user = Auth.get().getUser();


        if (name.getText().length() >= 2) {
            user.setName(name.getText());

        }

        if (surname.getText().length() > 2) {
            user.setSurname(surname.getText());
        }

        if (email.getText().length() > 2) {

            if (!email.getText().matches("^(.*@.*\\..+)$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Please enter a valid email!");
                alert.showAndWait();
            } else {
                user.setEmail(email.getText());
            }
        }

        if (Password.checkPassword(oldpassword.getText(), user.getPassword())) {
            if (newpassword.getText().length() >= 5) {
                user.setPassword(Password.hashPassword(newpassword.getText()));
            }
        }

        if (userRepo.update(user)) {
            EventBus.get().send(new UserUpdated(user));
            name.clear();
            surname.clear();
            email.clear();
            newpassword.clear();
            oldpassword.clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succsessful");
            alert.setHeaderText(null);
            alert.setContentText("Profile successfully edited");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something went wrong");
            alert.showAndWait();
        }
    }

    /**
     * Změní informace o účtu jak v databázi, tak v aplikaci
     */
    public void onEditAccount() {
        int number = Integer.parseInt(account.getSelectionModel().getSelectedItem());

        List<Account> accounts = Auth.get().getUser().getAccounts();

        for (Account account : accounts) {

            if (number == account.getNumber()) {
                if (type.getText().length() >= 2) {
                    account.setType(type.getText());
                    if (accountRepo.update(account)) {
                        EventBus.get().send(new AccountUpdated(account));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Account successfully edited");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }
                }
            }
        }


    }

    /**
     * Po vybrání karty se zobrazí její vzhled
     */
    public void onChooseCard() {
        int number = Integer.parseInt(card.getSelectionModel().getSelectedItem());

        List<Account> accounts = Auth.get().getUser().getAccounts();
        List<Card> cards;

        for (Account account : accounts) {
            cards = cardRepo.findAllForAccount(account);

            for (Card card : cards) {
                if (number == card.getNumber()) {
                    image.setImage(switchImage.nameToImage(card.getImage()));
                }
            }
        }

    }

    /**
     * Po vybrání designu se zobrazí jeho vzhled
     */
    public void onDesign() {
        image.setImage(switchImage.nameToImage(design.getSelectionModel().getSelectedItem()));
    }

    public void onEditCard() {
        int cardNumber = Integer.parseInt(card.getSelectionModel().getSelectedItem());
        List<Account> accounts = Auth.get().getUser().getAccounts();
        List<Card> cards;

        for (Account account : accounts) {
            cards = cardRepo.findAllForAccount(account);

            for (Card card : cards) {
                if (cardNumber == card.getNumber()) {

                    if (limit.getText().length() >= 2) {
                        System.out.println("Suc");
                        card.setLimit(Integer.parseInt(limit.getText()));

                    }
                    if (design.getSelectionModel().getSelectedItem() != null) {
                        card.setImage(design.getSelectionModel().getSelectedItem());
                    }

                    if (cardRepo.update(card)) {
                        EventBus.get().send(new CardUpdated(card));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Card successfully edited");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();
                    }
                }
            }
        }


    }

    /**
     * Účet bude smazán
     */
    public void onDeleteAccount() {
        int number = Integer.parseInt(account.getSelectionModel().getSelectedItem());

        List<Account> accounts = Auth.get().getUser().getAccounts();

        for (Account account : accounts) {

            if (number == account.getNumber()) {
                if (accountRepo.delete(account)) {
                    EventBus.get().send(new AccountDeleted(account));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Account successfully deleted");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();

                }
            }
        }
    }

    /**
     * Karta bude smazána
     */
    public void onDeleteCard() {

        int cardNumber = Integer.parseInt(card.getSelectionModel().getSelectedItem());
        List<Account> accounts = Auth.get().getUser().getAccounts();
        List<Card> cards;

        for (Account account : accounts) {
            cards = cardRepo.findAllForAccount(account);

            for (Card card : cards) {
                if (cardNumber == card.getNumber()) {

                    if (cardRepo.delete(card)) {
                        EventBus.get().send(new CardDeleted(card));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Card successfully deleted");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Something went wrong");
                        alert.showAndWait();

                    }
                }
            }
        }
    }

    /**
     * Základní nastavení comboboxů - nabídky účtů a karet
     */
    public void setUp() {
        List<Account> accounts = Auth.get().getUser().getAccounts();
        List<String> accountNumbers = new ArrayList<>();
        List<String> cardNumbers = new ArrayList<>();
        List<Card> cards;

        for (Account account : accounts) {
            accountNumbers.add(account.getNumber() + "");
            cards = cardRepo.findAllForAccount(account);

            for (Card card : cards) {
                cardNumbers.add(card.getNumber() + "");
            }
        }

        account.getItems().setAll(accountNumbers);
        card.getItems().setAll(cardNumbers);
        design.getItems().setAll("Card", "Nature", "Gold");


    }


}
