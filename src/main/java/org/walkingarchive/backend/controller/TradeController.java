package org.walkingarchive.backend.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
    @Path("user/{userId}")
    public List<Trade> getTradeByUser(@PathParam("userId") String userId) {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(Integer.parseInt(userId));
        return TradeFactory.getInstance().getTradesForUser(user);
    }

    @GET
    @Path("date/{date}")
    public List<Trade> getTradeByUser(@PathParam("date") Long date) {
        //TODO - validate input
        Date tradeDate = new Date(date);
        return TradeFactory.getInstance().getTradesForDate(tradeDate);
    }
}
