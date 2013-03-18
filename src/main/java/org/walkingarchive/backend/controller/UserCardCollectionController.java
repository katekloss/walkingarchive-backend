package org.walkingarchive.backend.controller;

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
import org.walkingarchive.backend.model.card.CardFactory;
import org.walkingarchive.backend.model.card.Deck;
import org.walkingarchive.backend.model.card.UserCardCollection;
import org.walkingarchive.backend.model.security.SecurityFactory;
import org.walkingarchive.backend.model.security.User;

@Path("/collection/")
public class UserCardCollectionController {
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
        List<UserCardCollection> collections = CardFactory.getInstance().getAllUserCollections();
        JSONArray collectionList = new JSONArray();
        for (UserCardCollection ucc : collections) {
            collectionList.put(ucc.toJson());
        }
        return collectionList;
    }

    @GET
    @Path("user/{userId}")
    public JSONObject getCollectionByUser(@PathParam("userId") String userId) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(UUID.fromString(userId));
        UserCardCollection collection = CardFactory.getInstance().getUserCollection(user);
        return collection.toJson();
    }

    @GET
    @Path("deck/user/{userId}")
    public JSONArray getAllDecksByUser(@PathParam("userId") String userId) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(UUID.fromString(userId));
        List<Deck> decks = CardFactory.getInstance().getAllDecks(user);
        JSONArray deckList = new JSONArray();
        for (Deck d : decks) {
            deckList.put(d.toJson());
        }
        
        return deckList;
    }

    @GET
    @Path("deck/user/{userId}/name/{name}")
    public JSONObject getDeckByNameAndUser(@PathParam("userId") String userId,
            @PathParam("name") String name) throws JSONException {
        //TODO - validate input
        User user = SecurityFactory.getInstance().getUserById(UUID.fromString(userId));
        Deck deck = CardFactory.getInstance().getDeckByUserAndName(user, name);
        return deck.toJson();
    }
}
