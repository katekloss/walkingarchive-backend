package org.walkingarchive.backend.controller;

import java.math.BigDecimal;
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
import org.codehaus.jettison.json.JSONObject;
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
    public JSONArray getAll() throws JSONException {
        //validate card input
        List<Card> cards = CardFactory.getInstance().getAllCards();
        JSONArray cardList = new JSONArray();
        for (Card c : cards) {
            cardList.put(c.toJson());
        }
        return cardList;
    }
    
    @GET
    @Path("id/{card}")
    public JSONObject getCardById(@PathParam("card") String card) throws JSONException {
        UUID cardId = UUID.fromString(card);
        Card c = CardFactory.getInstance().getCard(cardId);
        return c.toJson();
    }
    
    @GET
    @Path("name/{card}")
    public JSONArray getCardsByName(@PathParam("card") String card) throws JSONException {
        //TODO - validate card input
        List<Card> cards = CardFactory.getInstance().getCardsByName(card);
        JSONArray cardList = new JSONArray();
        for (Card c : cards) {
            cardList.put(c.toJson());
        }
        return cardList;
    }
    
    @GET
    @Path("name/{card}/version/{version}")
    public JSONObject getCardByNameAndVersion(@PathParam("card") String card, 
            @PathParam("version") String version) throws JSONException {
        //TODO - validate input
        Card c = CardFactory.getInstance().getCardByNameAndVersion(card, version);
        return c.toJson();
    }
    
    @GET
    @Path("type/{type}")
    public JSONArray getCardsByType(@PathParam("type") String type) throws JSONException {
        //TODO - validate input
        List<Card> cards = CardFactory.getInstance().getCardsByType(type);
        JSONArray cardList = new JSONArray();
        for (Card c : cards) {
            cardList.put(c.toJson());
        }
        return cardList;
    }
    
    @GET
    @Path("value/{low}/{high}")
    public JSONArray getCardsByValue(@PathParam("low") BigDecimal low,
            @PathParam("high") BigDecimal high) throws JSONException {
        //TODO - validate input
        System.out.println(low + " - " + high);
        List<Card> cards = CardFactory.getInstance().getCardsInValueRange(low, high);
        JSONArray cardList = new JSONArray();
        for (Card c : cards) {
            cardList.put(c.toJson());
        }
        return cardList;
    }
}
