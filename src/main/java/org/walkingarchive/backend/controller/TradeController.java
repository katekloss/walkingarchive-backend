package org.walkingarchive.backend.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.walkingarchive.backend.model.security.SecurityFactory;
import org.walkingarchive.backend.model.security.User;
import org.walkingarchive.backend.model.trade.Trade;
import org.walkingarchive.backend.model.trade.TradeFactory;

@Path("/trade/")
public class TradeController {
    @Context
    protected ServletContext context;
    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;
    @Context
    protected UriInfo uriInfo;
    
    
    @GET
    public JSONArray getAll() throws JSONException {
        //TODO - validate input
        List<Trade> trades = TradeFactory.getInstance().getAllTrades();
        JSONArray tradeList = new JSONArray();
        for (Trade t : trades) {
            tradeList.put(t.toJson());
        }
        return tradeList;
    }
    
    @GET
    @Path("user/{userId}")
    public JSONArray getTradeByUser(@PathParam("userId") String userId) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(UUID.fromString(userId));
        List<Trade> trades = TradeFactory.getInstance().getTradesForUser(user);
        JSONArray tradeList = new JSONArray();
        for (Trade t : trades) {
            tradeList.put(t.toJson());
        }
        return tradeList;
    }

    @GET
    @Path("date/{date}")
    public JSONArray getTradeByUser(@PathParam("date") Long date) throws JSONException {
        //TODO - validate input
        Date tradeDate = new Date(date);
        List<Trade> trades = TradeFactory.getInstance().getTradesForDate(tradeDate);
        JSONArray tradeList = new JSONArray();
        for (Trade t : trades) {
            tradeList.put(t.toJson());
        }
        return tradeList;
    }
}
