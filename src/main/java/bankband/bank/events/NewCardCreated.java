package bankband.bank.events;

import bankband.bank.models.Card;

public class NewCardCreated {

    private Card card = new Card();

    public NewCardCreated(Card card){
        this.card = card;
    }

    public Card getCard(){
        return card;
    }
}
