package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.walkingarchive.backend.DbHelper;
import org.walkingarchive.backend.model.security.User;

public class CardDAO {
    private static CardDAO instance = new CardDAO();

    //Singleton
    public static CardDAO getInstance() {
        return instance;
    }
    
    private CardDAO() {}
    

    //----------------------------------------------------------------------------------------------
    // CARD
    //----------------------------------------------------------------------------------------------
    
    public List<Card> getCardsByType(String type, int offset) {
        Session session = DbHelper.getSession();
        List cards = session.createQuery("from Card where type = :type order by name asc")
            .setParameter("type", type)
            .setFirstResult(offset)
            .setMaxResults(20)
            .list();
        session.close();
        return cards;
    }
    
    public List<Card> getCardsBySet(int setId) {
        Session session = DbHelper.getSession();
        Criteria criteria = session.createCriteria(Card.class);
        criteria.createAlias("sets", "set");
        criteria.add(Restrictions.eq("set.id", setId));
        List cards = criteria.list();
        session.close();
        return cards;
    }
    
    public List<Card> getCardsByMana(String mana, int offset) {
        Session session = DbHelper.getSession();
        List cards = session.createSQLQuery("SELECT {cards.*} FROM cards {cards} WHERE exist(mana, :mana)")
                .addEntity("cards", Card.class)
                .setParameter("mana", mana)
                .setFirstResult(offset)
                .setMaxResults(20)
                .list();
        session.close();
        return cards;
    }
    
    public List<Card> getCardsByName(String name) {
        Session session = DbHelper.getSession();
        List cards = session.createQuery("from Card where lower(name) like concat(lower(:name),'%')")
            .setParameter("name", name)
            .list();
        session.close();
        return cards;
    }
    
    public Card getCard(int cardId) {
        Session session = DbHelper.getSession();
        Card card = (Card) session.get(Card.class, cardId);
        session.close();
        return card;
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
    // DECK
    //----------------------------------------------------------------------------------------------
    
    public List<Deck> getAllDecks() {
        return null;
    }
    
    public List<Deck> getDecks(User user) {
        Session session = DbHelper.getSession();
        List decks = session.createQuery("from Deck where userid = :userid")
            .setParameter("userid", user.getId())
            .list();
        session.close();
        return decks;
    }
    
    public Deck getDeckByType(User user, String type) {
        return null;
    }

    //----------------------------------------------------------------------------------------------
    // SET
    //----------------------------------------------------------------------------------------------
    public Set getSet(int id) {
        Session session = DbHelper.getSession();
        Set set = (Set) session.get(Set.class, id);
        session.close();
        return set;
    }
}
