package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.List;

import org.walkingarchive.backend.model.security.User;
import org.walkingarchive.backend.DbHelper;

import org.hibernate.Session;
import org.hibernate.type.CustomType;

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
        Session session = DbHelper.getSession();
        List cards = session.createQuery("from Card where type = :type")
            .setParameter("type", type)
            .list();
        return cards;
    }
    
    public List<Card> getCardsByName(String name) {
        Session session = DbHelper.getSession();
        List cards = session.createQuery("from Card where lower(name) like lower(:name)")
            .setParameter("name", name)
            .list();
        return cards;
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
    
    public List<Deck> getDecks(User user) {
        Session session = DbHelper.getSession();
        List decks = session.createQuery("from Deck where userid = :userid")
            .setParameter("userid", user.getId())
            .list();
        return decks;
    }
    
    public Deck getDeckByType(User user, String type) {
        return null;
    }
}
