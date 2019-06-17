package bankband.bank.controllers;

import bankband.bank.models.Card;
import bankband.bank.services.Auth;
import bankband.bank.util.SwitchImage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CardController implements Controller {

    private Card card = new Card();

    CardController(Card card){
        this.card = card;
    }

    @Override
    public void initialize(){
        setUp(card);
    }

    @FXML
    private ImageView image;

    @FXML
    private Label limit;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private Label account;

    @FXML
    private Label postCode;

    SwitchImage switchImage = new SwitchImage();


    /**
     * Nastavení jednotlivých Pane pro main controller
     * Každá karta má svůj vlastní pane
     * @param card
     */
    public void setUp(Card card){
        String imageName = card.getImage();
        image.setImage(switchImage.nameToImage(imageName));
        limit.setText(card.getLimit()+"");
        name.setText(Auth.get().getUser().getName()+" "+Auth.get().getUser().getSurname());
        number.setText(card.getNumber()+"");
        account.setText(card.getAccount().getNumber()+"");
        postCode.setText(card.getAccount().getPostNumber()+"");

    }


}
