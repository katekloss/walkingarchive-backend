package org.walkingarchive.backend.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.walkingarchive.backend.model.card.Card;
import org.walkingarchive.backend.model.card.CardDAO;
import org.walkingarchive.backend.model.card.Set;

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
        Card c = CardDAO.getInstance().getCard(cardId);
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
        List<Card> c = CardDAO.getInstance().getCardsByName(card, 0);
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
    @Path("name/{card}/{offset}")
    public Response getCardsByNameOffset(@PathParam("card") String card,
            @PathParam("offset") int offset) {
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByName(card, (offset-1)*20);
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
        Card c = CardDAO.getInstance().getCardByNameAndVersion(card, version);
        return c;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("type/{type}")
    public Response getCardsByType(@PathParam("type") String type) {
        //TODO - validate input
        return getCardsByTypeOffset(type, 0);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("type/{type}/{offset}")
    public Response getCardsByTypeOffset(@PathParam("type") String type,
            @PathParam("offset") int offset) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByType(type, (offset-1)*20);
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
    @Path("set/id/{set}")
    public Response getCardsBySet(@PathParam("set") int setId) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsBySet(setId);
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
    @Path("set/name/{set}")
    public Response getCardsBySet(@PathParam("set") String setname) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsBySetName(setname);
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
    @Path("mana/{mana}")
    public Response getCardsByMana(@PathParam("mana") String mana) {
        //TODO - validate input
        return getCardsByManaOffset(mana, 1);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("mana/{mana}/{offest}")
    public Response getCardsByManaOffset(@PathParam("mana") String mana,
            @PathParam("offset") int offset) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByMana(mana, (offset-1)*20);
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
    @Path("value/{low}/{high}")
    public List<Card> getCardsByValue(@PathParam("low") BigDecimal low,
            @PathParam("high") BigDecimal high) {
        //TODO - validate input
//        System.out.println(low + " - " + high);
//        return CardFactory.getInstance().getCardsInValueRange(low, high);
        throw new RuntimeException("getCardsByValue is unimplemented");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{query}")
    public List<Card> getCardsByTextSearch(@PathParam("query") String query) {
        return CardDAO.getInstance().getCardsBySearch(query, 0);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{query}/{offset}")
    public List<Card> getCardsByTextSearchOffset(@PathParam("query") String query,
            @PathParam("offset") int offset) {
        return CardDAO.getInstance().getCardsBySearch(query, (offset-1)*20);
    }
}
