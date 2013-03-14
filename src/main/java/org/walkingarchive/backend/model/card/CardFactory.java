package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.walkingarchive.backend.model.security.User;


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
    
    public Card getCard(UUID cardId) {
        return null;
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
    
    public List<UserCardCollection> getAllUserCollections() {
        return null;
    }
    
    public UserCardCollection getUserCollection(User user) {
        return null;
    }
    
    public UserCardCollection getUserCollectionByType(User user, String type) {
        return null;
    }
}
