package bankband.bank.controllers;

import bankband.bank.EventBus;
import bankband.bank.events.NewCardCreated;
import bankband.bank.models.Account;
import bankband.bank.models.Card;
import bankband.bank.repositories.CardRepository;
import bankband.bank.util.Password;
import bankband.bank.util.SwitchImage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


import java.sql.Date;
import java.util.Random;
import java.lang.String;


public class NewCardController implements Controller {


    Account account = new Account();

    Random random = new Random();

    NewCardController(Account account){
        this.account = account;
    }
    private SwitchImage switchImage = new SwitchImage();

    @FXML
    private ImageView image;

    @FXML
    private ComboBox<String> design;

    @FXML
    private TextField limit;

    @FXML
    private Label fal;

    @FXML
    private Label tru;

    /**
     * Nastavení comboboxu
     */
    @Override
    public void initialize(){
        design.getItems().clear();
        design.getItems().setAll("Card", "Nature", "Gold");
    }

    /**
     * Zobrazí se styl karty
     */
    public void onDesign(){
        image.setImage(switchImage.nameToImage(design.getSelectionModel().getSelectedItem()));
    }

    /**
     * Vytvoří novou kartu
     */
    public void onConfirm(){

        String hash = Password.hashPassword(random.nextInt(8999) + 1000 + "" );
        CardRepository repository = new CardRepository();

        Card card = new Card();
        card.setAccount(account);
        card.setNumber(random.nextInt(8999) + 1000 + random.nextInt(8999) + 1000
                + random.nextInt(8999) + 1000 + random.nextInt(8999) + 1000);
        card.setCcv(random.nextInt(899)+100);
        card.setExpirationDate(new Date(2022));
        card.setImage(design.getSelectionModel().getSelectedItem());
        try {
            card.setLimit(Integer.parseInt(limit.getText()));
        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid number!");
            alert.showAndWait();
            return;
        }

        card.setPin(hash);

        if(repository.create(card)==null){
            fal.setText("Something went wrong");
        } else {
            tru.setText("Success.");
            image.setImage(null);
            EventBus.get().send(new NewCardCreated(card));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("New card successfully created!");
            alert.showAndWait();
        }
    }
}