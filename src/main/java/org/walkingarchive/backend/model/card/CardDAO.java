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

/** The Card Data Access Object is responsible for adding, retrieving, modifying, and deleting
 * Card, Deck, and Set information from the database.
 *
 */
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
    
    /** Retrieves the Cards by type from the database. The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param type String of the type of Card to retrieve
     * @param offset the page of results to retrieve
     * @return List of Cards of the given type
     */
    public List<Card> getCardsByType(String type, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createQuery("from Card where lower(type) = lower(:type) order by name asc")
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
    
    /** Retrieves the Cards by set id from the database.
     * 
     * @param setId int of the set id
     * @return List of Cards in the given set
     */
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
    
    /** Retrieves the Cards by set name from the database.
     * 
     * @param setname String name of the set
     * @return List of Cards in the specified set
     */
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
    
    /** Retrieves the Cards by mana color from the database. 
     * The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param mana String mana color of a Card
     * @param offset the 'page' of results to retrieve
     * @return List of Cards of given mana color
     */
    public List<Card> getCardsByMana(String mana, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createSQLQuery("SELECT {cards.*} FROM cards {cards} WHERE exist(mana, lower(:mana)) ORDER BY name ASC")
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
    
    /** Retrieves the Cards by name prefix from the database. 
     * The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param name String prefix of a name of a Card
     * @param offset the 'page' of results to retrieve
     * @return List of Cards with name that starts with the given name
     */
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
    
    /** Retrieves the Cards by a combination of name, type, and mana from the database. 
     * The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param name String prefix of name of a card
     * @param type String type
     * @param mana String mana color
     * @param offset the 'page' of results to return
     * @return List of Cards matching the specified requirements
     */
    public List<Card> getCardsByNameManaType(String name, String type, String mana, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createSQLQuery("select {cards.*} from cards {cards} where lower(name) like concat(lower(:name),'%') " +
                        "and lower(type) = lower(:type) and exist(mana, lower(:mana)) order by name asc")
                .addEntity("cards", Card.class)
                .setParameter("name", name)
                .setParameter("type", type)
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
    
    /** Retrieves the Cards by a combination of nameand type from the database. 
     * The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param name String prefix of name of a card
     * @param type String type
     * @param offset the 'page' of results to return
     * @return List of Cards matching the specified requirements
     */
    public List<Card> getCardsByNameType(String name, String type, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createSQLQuery("select {cards.*} from cards {cards} where lower(name) like concat(lower(:name),'%') " +
                        "and lower(type) = lower(:type) order by name asc")
                .addEntity("cards", Card.class)
                .setParameter("name", name)
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
    

    /** Retrieves the Cards by a combination of name and mana from the database. 
     * The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param name String prefix of name of a card
     * @param mana String mana color
     * @param offset the 'page' of results to return
     * @return List of Cards matching the specified requirements
     */
    public List<Card> getCardsByNameMana(String name, String mana, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createSQLQuery("select {cards.*} from cards {cards} where lower(name) like concat(lower(:name),'%') " +
                        "and exist(mana, lower(:mana)) order by name asc")
                .addEntity("cards", Card.class)
                .setParameter("name", name)
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
    

    /** Retrieves the Cards by a combination of type and mana from the database. 
     * The specified offset retrieves different 'pages'
     * of results, each page contains a maximum of 20 results
     * 
     * @param type String type
     * @param mana String mana color
     * @param offset the 'page' of results to return
     * @return List of Cards matching the specified requirements
     */
    public List<Card> getCardsByManaType(String type, String mana, int offset) {
        Session session = DbHelper.getSession();
        List cards = null;
        try {
            cards = session.createSQLQuery("select {cards.*} from cards {cards} where " + 
                    "lower(type) = lower(:type) and exist(mana, lower(:mana)) order by name asc")
                .addEntity("cards", Card.class)
                .setParameter("type", type)
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
    
    /** Retrieves a card with the given id from the database
     * 
     * @param cardId int of Card id
     * @return Card with given id
     */
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

    /** Perform a special weighted search of Cards based on the given text.
     * 
     * @param query String of text on a Card
     * @param limit 
     * @return List of Cards matching the query, ordered by relevance
     */
    public List<Card> getCardsBySearch(String query, int limit)
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

        String cleanedQuery = SearchHelper.join(cleanTokens, "|");
        System.out.println(cleanedQuery);
        try
        {
            String sql =
                "WITH SearchResults AS (SELECT (PerformSearch(:query, :count)).*) " +
                "SELECT C.* FROM SearchResults SR " +
                "JOIN Cards AS C ON C.cardid = SR.cardid " +
                "ORDER BY SR.rank DESC";
            cards = session.createSQLQuery(sql)
                .addEntity(Card.class)
                .setParameter("query", cleanedQuery)
                .setParameter("count", limit)
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
    
    /** Retrieve a Deck by its id
     * 
     * @param deckId int id of the Deck to retrieve
     * @return Deck with given id
     */
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
    
    /** Retrieve all the decks blonging to the given user
     * 
     * @param user int id of User
     * @return List of Decks belonging to the given user
     */
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
    
    /** Persist a new Deck in the database
     * 
     * @param deck newly created Deck object to persist
     * @return saved Deck information (now includes id)
     * @throws Exception
     */
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
    
    /** Update a Deck in the database
     * 
     * @param deck Deck object to update
     * @return Deck object with new information
     * @throws Exception
     */
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
    
    /** Remove a Deck from the database
     * 
     * @param deck Deck object to remove
     */
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
    /** Retrieve a Set from the database given the id
     * 
     * @param id int id of the Set
     * @return Set with given id
     */
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
    
    /** Retrieve a list of sets prefix matching the given name
     * 
     * @param name String as a prefix of some Set name to find
     * @return List of Sets that prefix match the given name, (null if not found)
     */
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
