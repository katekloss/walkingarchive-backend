package org.walkingarchive.backend.model.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.walkingarchive.backend.model.card.Card;

public class Trade {
    private UUID id = UUID.randomUUID();
    private Date created;
    private List<Card> traderACards = new ArrayList<Card>();
    private List<Card> traderBCards = new ArrayList<Card>();
    
    public Trade() {
    }
    
    public Trade(List<Card> a, List<Card> b) {
        for (Card ca : a) {
            traderACards.add(ca);
        }
        for (Card cb : b) {
            traderBCards.add(cb);
        }
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getIdString() {
        return id.toString();
    }
    
    public void addCardToA(Card card) {
        traderACards.add(card);
    }
    
    public void addCardToB(Card card) {
        traderBCards.add(card);
    }
    
    public List<Card> getCardsForA() {
        return traderACards;
    }
    
    public List<Card> getCardsForB() {
        return traderBCards;
    }

}
