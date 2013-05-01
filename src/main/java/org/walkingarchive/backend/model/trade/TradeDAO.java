package org.walkingarchive.backend.model.trade;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.walkingarchive.backend.DbHelper;

/** A singleton responsible for all database interactions for Trade objects:
 * Restores Trades from the database, saves new Trades, updates existing Trades,
 * and deletes Trades from the database.
 *
 */
public class TradeDAO {
    private static TradeDAO instance = new TradeDAO();

    /** Retrieves the singleton instance of the TradeDAO
     * 
     * @return TradeDAO
     */
    public static TradeDAO getInstance() {
        return instance;
    }
    
    /** Private constructor
     * 
     */
    private TradeDAO() {}
    
    /** Retrieve a Trade by id
     * 
     * @param id int id of Trade to restore
     * @return Trade with the given id, or null if it does not exist
     */
    public Trade getTradeForId(Integer id) {
        Session session = DbHelper.getSession();
        Trade trade = null;
        try {
            trade = (Trade) session.get(Trade.class, id);
        }
        finally {
            session.close();
        }
        
        return trade;
    }
    
    /** Retrieve a list of Trades by user id
     * 
     * @param userId int id of the user that owns the Trades
     * @return List of Trades belonging to the user
     */
    public List<Trade> getTradesForUser(Integer userId) {
        Session session = DbHelper.getSession();
        List trades = null;
        try {
            trades = session.createQuery("from Trade where userid = :user")
                .setParameter("user", userId)
                .list();
        }
        finally {
            session.close();
        }
        return trades;
    }
    
    /** Persists a new Trade in the database
     * 
     * @param trade newly created Trade object to persist
     * @return Trade after save, including the id assigned by the database
     * @throws Exception
     */
    public Trade createTrade(Trade trade) throws Exception {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Integer id = (Integer) session.save(trade);
            trade.setId(id);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw new RuntimeException("Error creating new Trade", e);
        }
        finally {
            session.close();
        }

        return trade;
    }
    
    /** Update an existing Trade in the database
     * 
     * @param trade Trade object to update
     * @return Trade with the updated information
     * @throws Exception
     */
    public Trade updateTrade(Trade trade) throws Exception {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(trade);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error in updating Trade", e);
        }
        finally {
            session.close();
        }
        
        return trade;
    }
    
    /** Remove a persisted Trade from the database
     * 
     * @param trade Trade object to delete
     */
    public void delete(Trade trade) {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(trade);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error in deleting trade", e);
        }
        finally {
            session.close();
        }
    }
}
