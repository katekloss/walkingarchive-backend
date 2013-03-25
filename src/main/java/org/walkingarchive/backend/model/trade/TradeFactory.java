package org.walkingarchive.backend.model.trade;

import java.util.Date;
import java.util.List;

import org.walkingarchive.backend.DbHelper;
import org.walkingarchive.backend.model.security.User;
import org.hibernate.Session;

public class TradeFactory {
    private static TradeFactory instance = new TradeFactory();

    //Singleton
    public static TradeFactory getInstance() {
        return instance;
    }
    
    private TradeFactory() {}

    public List<Trade> getAllTrades() {
        return null;
    }
    
    public Trade getTradeForId(Integer id) {
        Session session = DbHelper.getSession();
        Trade trade = (Trade) session.get(Trade.class, id);
        return trade;
    }

    public Trade getTradeForId(String id) {
        return null;
    }
    
    public List<Trade> getTradesForUser(User user) {
        return null;
    }

    public List<Trade> getTradesForDate(Date date) {
        return null;
    }
    
    public Trade addTrade(Trade trade) {
        Session session = DbHelper.getSession();
        Integer id = (Integer) session.save(trade);
        trade.setId(id);
        session.getTransaction().commit();
     
        return trade;
    }
}
