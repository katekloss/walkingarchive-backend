package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.List;

import org.walkingarchive.backend.model.security.User;
import org.walkingarchive.backend.DbHelper;

import org.hibernate.Session;

public class CardFactory {
    private static CardFactory instance = new CardFactory();

    //Singleton
    public static CardFactory getInstance() {
        return instance;
    }
    
    private CardFactory() {}
    

    //----------------------------------------------------------------------------------------------
    // CARD
    //----------------------------------------------------------------------------------------------
    
    public List<Card> getCardsByType(String type) {
        return null;
    }
    
    public List<Card> getCardsByName(String name) {
        return null;
    }
    
    public Card getCard(int cardId) {
        Session session = DbHelper.getSession();
        return (Card) session.get(Card.class, cardId);
    }
    
    public Card getCardByNameAndVersion(String name, String Version) {
        return null;
    }
    
    public List<Card> getCardsInValueRange(BigDecimal low, BigDecimal high) {
        return null;
    }
    
    public List<Card> getAllCards() {
        return null;
    }

    //----------------------------------------------------------------------------------------------
    // COLLECTION
    //----------------------------------------------------------------------------------------------
    
    public List<Deck> getAllDecks() {
        return null;
    }
    
    public Deck getDeck(User user) {
        return null;
    }
    
    public Deck getDeckByType(User user, String type) {
        return null;
    }
}
