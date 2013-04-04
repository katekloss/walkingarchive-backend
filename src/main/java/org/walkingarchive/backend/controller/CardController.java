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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
    public Response getCardById(@PathParam("card") String card) {
        Response result;
        Integer cardId = Integer.parseInt(card);
        Card c = CardFactory.getInstance().getCard(cardId);
        if(c != null) {
            result = Response.ok(c, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        return result;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{card}")
    public Response getCardsByName(@PathParam("card") String card) {
        Response result;
        List<Card> c = CardFactory.getInstance().getCardsByName(card);
        if(c != null) {
            result = Response.ok(c, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        return result;
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
