package org.walkingarchive.backend.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

import org.walkingarchive.backend.model.card.Card;
import org.walkingarchive.backend.model.card.CardFactory;

@Path("/card/")
public class CardController {
    @Context
    protected ServletContext context;
    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;
    @Context
    protected UriInfo uriInfo;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("id/{card}")
    public Card getCardById(@PathParam("card") String card) {
        Integer cardId = Integer.parseInt(card);
        return CardFactory.getInstance().getCard(cardId);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{card}")
    public List<Card> getCardsByName(@PathParam("card") String card) {
        //TODO - validate card input
        return CardFactory.getInstance().getCardsByName(card);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{card}/version/{version}")
    public Card getCardByNameAndVersion(@PathParam("card") String card, 
            @PathParam("version") String version) {
        //TODO - validate input
        Card c = CardFactory.getInstance().getCardByNameAndVersion(card, version);
        return c;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("type/{type}")
    public List<Card> getCardsByType(@PathParam("type") String type) {
        //TODO - validate input
//        return CardFactory.getInstance().getCardsByType(type);
        throw new RuntimeException("getCardsByType is unimplemented.");
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("value/{low}/{high}")
    public List<Card> getCardsByValue(@PathParam("low") BigDecimal low,
            @PathParam("high") BigDecimal high) {
        //TODO - validate input
//        System.out.println(low + " - " + high);
//        return CardFactory.getInstance().getCardsInValueRange(low, high);
        throw new RuntimeException("getCardsByValue is unimplemented");
    }
}
