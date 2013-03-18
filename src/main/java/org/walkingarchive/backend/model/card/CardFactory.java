package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.walkingarchive.backend.HibernateUtil;
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
        List<Card> result = null;
        
        Transaction tx = null;
        Session session = HibernateUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          List cards = session.createQuery("select c from Cards as c")
                  .list();
          for (Iterator iter = cards.iterator(); iter.hasNext();) {
            Card element = (Card) iter.next();
            //logger.debug(“{}”, element);
            System.out.println(element);
            result.add(element);
          }
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              //logger.debug(“Error rolling back transaction”);
                System.out.println("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }
        }

        return result;
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

    //----------------------------------------------------------------------------------------------
    // DECK
    //----------------------------------------------------------------------------------------------
    
    public List<Deck> getAllDecks(User user) {
        return null;
    }
    
    public Deck getDeckByUserAndName(User user, String name) {
        return null;
    }
}
