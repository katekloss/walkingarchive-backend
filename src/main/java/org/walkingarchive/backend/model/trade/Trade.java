package org.walkingarchive.backend.model.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.walkingarchive.backend.model.card.Card;
import org.walkingarchive.backend.model.security.User;

public class Trade {
    private Integer id;
    private Date created;
    private User owner;
    private List<Card> receivingCards = new ArrayList<Card>();
    private List<Card> givingCards = new ArrayList<Card>();
    private boolean active;
    
    public Trade() {
    }
    
    public Trade(User user, List<Card> a, List<Card> b) {
        created = new Date();
        owner = user;
        for (Card ca : a) {
            receivingCards.add(ca);
        }
        for (Card cb : b) {
            givingCards.add(cb);
        }
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getIdString() {
        return id.toString();
    }
    
    public void addCardToReceiving(Card card) {
        receivingCards.add(card);
    }
    
    public void addCardToGiving(Card card) {
        givingCards.add(card);
    }
    
    public List<Card> getCardsReceiving() {
        return receivingCards;
    }
    
    public List<Card> getCardsGiving() {
        return givingCards;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public Date getDate() {
        return created;
    }
    
    public void setActive(boolean a) {
        active = a;
    }
    
    public boolean getActive() {
        return active;
    }
}
