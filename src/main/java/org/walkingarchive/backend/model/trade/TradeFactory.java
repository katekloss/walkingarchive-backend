package org.walkingarchive.backend.model.trade;

import java.util.List;
import java.util.UUID;

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
    
    public Trade getTradeForId(UUID id) {
        return null;
    }

    public Trade getTradeForId(String id) {
        return null;
    }
}
