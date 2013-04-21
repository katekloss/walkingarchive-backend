package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.walkingarchive.backend.DbHelper;
import org.walkingarchive.backend.helpers.SearchHelper;
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
        List cards = null;
        try {
            cards = session.createQuery("from Card where type = :type order by name asc")
                .setParameter("type", type)
                .setFirstResult(offset)
                .setMaxResults(20)
                .list();
        }
        finally {
            session.close();
        }
        return cards;
    }
    
    public List<Card> getCardsBySet(int setId) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            Criteria criteria = session.createCriteria(Card.class);
            criteria.createAlias("sets", "set");
            criteria.add(Restrictions.eq("set.id", setId));
            cards = criteria.list();
        }
        finally {
            session.close();
        }
        return cards;
    }
    
    public List<Card> getCardsBySetName(String setname) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            Criteria criteria = session.createCriteria(Card.class);
            criteria.createAlias("sets", "set");
            criteria.add(Restrictions.ilike("set.name", setname));
            cards = criteria.list();
        }
        finally {
            session.close();
        }
        return cards;
    }
    
    public List<Card> getCardsByMana(String mana, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createSQLQuery("SELECT {cards.*} FROM cards {cards} WHERE exist(mana, :mana) ORDER BY name ASC")
                .addEntity("cards", Card.class)
                .setParameter("mana", mana)
                .setFirstResult(offset)
                .setMaxResults(20)
                .list();
        }
        finally {
            session.close();
        }
        return cards;
    }
    
    public List<Card> getCardsByName(String name, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createQuery("from Card where lower(name) like concat(lower(:name),'%') order by name asc")
                .setParameter("name", name)
                .setFirstResult(offset)
                .setMaxResults(20)
                .list();
        }
        finally {
            session.close();
        }
        return cards;
    }
    
    public Card getCard(int cardId) {
        Session session = DbHelper.getSession();
        Card card = null;
        try {
            card = (Card) session.get(Card.class, cardId);
        }
        finally {
            session.close();
        }
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

    public List<Card> getCardsBySearch(String query, int offset)
    {
        Session session = DbHelper.getSession();

        List cards = null;
        List<String> tokens = SearchHelper.tokenize(query);
        LinkedList<String> cleanTokens = new LinkedList();
        for(String token : tokens)
        {
            String sql =
                "SELECT COUNT(*) " +
                "FROM RawDictionaryMaterialized " +
                "WHERE word LIKE :token";
            int count = ((BigInteger) session.createSQLQuery(sql)
                .setParameter("token", token)
                .list()
                .get(0))
                .intValue();

            // This word appears exactly in the dictionary
            if(count == 1)
                cleanTokens.add(token);

            // Find the word most similar to this one, or remove it
            // from the query entirely if nothing matches
            else
            {

                sql =
                    "SELECT word, similarity(word, :token) AS similarity " +
                    "FROM RawDictionaryMaterialized " +
                    "WHERE word % :token " +
                    "ORDER BY similarity DESC " +
                    "LIMIT 1";
                try
                {
                    Object[] row = (Object[]) session.createSQLQuery(sql)
                        .setParameter("token", token)
                        .list()
                        .get(0);
                    String correctedToken = (String) row[0];
                    // TODO: We might want to filter on this score?
                    Float similarity = (Float) row[1];
                    cleanTokens.add(correctedToken);
                }
                catch(IndexOutOfBoundsException e) { }
            }
        }

        String cleanedQuery = SearchHelper.join(cleanTokens, " ");
        System.out.println("Cleaned '" + query + "' to '" + cleanedQuery + "'");

        try
        {
            String sql =
                "WITH SearchResults AS (SELECT (PerformSearch(:query)).*) " +
                "SELECT C.* FROM SearchResults " +
                "JOIN Cards AS C ON C.cardid = SearchResults.cardid";
            cards = session.createSQLQuery(sql)
                .addEntity(Card.class)
                .setParameter("query", cleanedQuery)
                .setFirstResult(offset)
                .setMaxResults(20)
                .list();
        }
        finally {
            session.close();
        }
        return cards;
    }

    //----------------------------------------------------------------------------------------------
    // DECK
    //----------------------------------------------------------------------------------------------
    
    public List<Deck> getAllDecks() {
        return null;
    }
    
    public Deck getDeck(int deckId) {
        Session session = DbHelper.getSession();
        Deck deck = null;
        try {
            deck = (Deck) session.get(Deck.class, deckId);
        }
        catch(Exception e) {
            throw new RuntimeException("Error updating Deck with id " + deckId, e);
        }
        finally {
            session.close();
        }
        
        return deck;
    }
    
    public List<Deck> getDecks(User user) {
        Session session = DbHelper.getSession();
        List decks = null;
        try {
            decks = session.createQuery("from Deck where userid = :userid")
                .setParameter("userid", user.getId())
                .list();
        }
        finally {
            session.close();
        }
        return decks;
    }
    
    public Deck getDeckByType(User user, String type) {
        return null;
    }
    
    public Deck createDeck(Deck deck) throws Exception {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Integer id = (Integer) session.save(deck);
            deck.setId(id);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw new RuntimeException("Error creating new Deck", e);
        }
        finally {
            session.close();
        }

        return deck;
    }
    
    public Deck updateDeck(Deck deck) throws Exception {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(deck);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error in updating Deck", e);
        }
        finally {
            session.close();
        }
        
        return deck;
    }
    
    public void deleteDeck(Deck deck) {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(deck);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error in deleting deck", e);
        }
        finally {
            session.close();
        }
    }

    //----------------------------------------------------------------------------------------------
    // SET
    //----------------------------------------------------------------------------------------------
    public Set getSet(int id) {
        Session session = DbHelper.getSession();
        Set set = null;
        try {
            set = (Set) session.get(Set.class, id);
        }
        finally {
            session.close();
        }
        return set;
    }
    
    public List<Set> getSetsByName(String name) {
        Session session = DbHelper.getSession();
        List sets = null;
        try {
            sets = session.createQuery("from Set where lower(setname) like concat(lower(:name),'%')")
                .setParameter("name", name)
                .list();
        }
        finally {
            session.close();
        }
        return sets;
    }
}
