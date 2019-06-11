package bankband.bank.controllers;

import bankband.bank.models.Account;
import bankband.bank.models.Card;
import bankband.bank.repositories.CardRepository;
import bankband.bank.util.Password;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.midi.Soundbank;
import java.sql.Date;
import java.util.Random;


public class NewCardController implements Controller {


    Account account = new Account();

    Random random = new Random();

    NewCardController(Account account){
        this.account = account;
    }

    @FXML
    ImageView image;

    @FXML
    ComboBox<String> design;

    @FXML
    TextField limit;

    @Override
    public void initialize(){
        design.getItems().clear();
        design.getItems().setAll("Card", "Nature");
    }

    public void onDesign(){
        image.setImage(nameToImage(design.getSelectionModel().getSelectedItem()));
    }

    public void onConfirm(){

        String hash = Password.hashPassword(random.nextInt(8999) + 1000 + "" );
        CardRepository repository = new CardRepository();

        Card card = new Card();
        card.setAccountId(account);
        card.setNumber(random.nextInt(8999) + 1000 + random.nextInt(8999) + 1000
                + random.nextInt(8999) + 1000 + random.nextInt(8999) + 1000);
        card.setCcv(random.nextInt(899)+100);
        card.setExpirationDate(new Date(2022));
        card.setImage(design.getSelectionModel().getSelectedItem());
        card.setLimit(Integer.parseInt(limit.getText()));
        card.setPin(hash);

        if(repository.create(card)==null){
            System.out.println("Cant create card");
        }


    }

    public Image nameToImage(String name) {
        switch (name) {
            case "Card": return new Image("images/card.png");
            case "Nature": return new Image("images/nature.png");
        }
        return null;
    }
}
