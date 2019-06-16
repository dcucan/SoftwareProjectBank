package bankband.bank.events;

import bankband.bank.models.Card;

public class CardUpdated {

    private Card card = new Card();

    public CardUpdated(Card card){
        this.card = card;
    }

    public Card getCard(){
        return card;
    }
}
