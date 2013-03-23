package org.walkingarchive.backend.model.trade;

import java.util.Date;
import java.util.List;

import org.walkingarchive.backend.model.security.User;

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
        return null;
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
}
