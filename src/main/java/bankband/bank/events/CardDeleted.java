package bankband.bank.events;

import bankband.bank.models.Card;

public class CardDeleted {

    private Card card = new Card();

    public CardDeleted(Card card){
        this.card = card;
    }

    public Card getCard(){
        return card;
    }
}
