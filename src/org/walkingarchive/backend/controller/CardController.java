package org.walkingarchive.backend.controller;

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
    @Path("{card}")
    public JSONObject getCard(@PathParam("card") String card) throws JSONException {
        UUID cardId = UUID.fromString(card);
        Card c = CardFactory.getInstance().getCard(cardId);
        return toJson(c);
    }
    
    public static JSONObject toJson(Card card) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", card.getIdString());
        result.put("name", card.getName());
        result.put("type", card.getType());
        result.put("manacolor", card.getManaColor());
        
        return result;
    }
}
