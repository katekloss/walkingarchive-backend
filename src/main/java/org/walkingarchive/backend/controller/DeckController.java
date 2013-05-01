package org.walkingarchive.backend.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.walkingarchive.backend.model.card.Card;
import org.walkingarchive.backend.model.card.CardDAO;
import org.walkingarchive.backend.model.card.Deck;
import org.walkingarchive.backend.model.security.SecurityDAO;
import org.walkingarchive.backend.model.security.User;

/** DeckController is responsible for serving information about user's Decks via rest services. 
 * Each method is tagged with the url path to visit (appended to the main url dev.mtgwalkingarchive.com:8080).
 * 
 * @author Alison Orlando
 *
 */
@Path("/deck/")
public class DeckController {
    @Context
    protected ServletContext context;
    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;
    @Context
    protected UriInfo uriInfo;

    /** Retrieves a deck by the deck owner's id
     * 
     * @param userId - an Integer representing the id of the user who owns the deck
     * @return List of Decks belonging to the given user as a JSON array
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/{userId}")
    public List<Deck> getDecksByUser(@PathParam("userId") String userId) {
        //TODO - validate input
        User user = SecurityDAO.getInstance().getUserById(Integer.parseInt(userId));
        return CardDAO.getInstance().getDecks(user);
    }
    
    /** Retrieves a deck by id
     * 
     * @param id - an Integer representing the id of the Deck
     * @return Deck with the given id as a JSON object
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("id/{id}")
    public Deck getDeck(@PathParam("id") int id) {
        //TODO - validate input
        Deck deck = CardDAO.getInstance().getDeck(id);
        return deck;
    }
    
    /** A method to add a Deck, using HTTP Put. This method consumes JSON data attached to the request.
     * The JSON must specify the deck name and the id of the user who will own the Deck.
     * By default, there are no cards in a newly created Deck.
     * 
     * @param json - a String of JSON specifying the Deck name and user id of the Deck owner
     * @return HTTP OK response with newly created Deck information attached as a JSON object
     * @throws Exception
     */
    @PUT
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDeck(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        User user = SecurityDAO.getInstance().getUserById(jsonObject.getInt("user"));
        
        Deck deck = new Deck(user, jsonObject.getString("name"));
        deck = CardDAO.getInstance().createDeck(deck);
        
        return Response.ok(deck, MediaType.APPLICATION_JSON).build();
    }
    
    /** Updates a previously created Deck, as specified in the JSON data attached to teh request.
     * The JSON must specify the id of the Deck to be modified, the name (new or the same) of the Deck,
     * and a list of card ids to be held on the deck. These ids will replace any previous list on the Deck.
     * 
     * @param json - a String of JSON specifying the Deck id, name, and list of cards
     * @return HTTP OK response with newly modified Deck information attached as a JSON object, 
     * or Status 404 i the Deck is not found
     * @throws Exception
     */
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String json) throws Exception {
        Response result;
        JSONObject jsonObject = new JSONObject(json);
        Deck deck = CardDAO.getInstance().getDeck(jsonObject.getInt("id"));

        JSONArray collection = jsonObject.getJSONArray("collection");
        
        deck.removeAllCardsFromCollection();
        
        for(int i = 0; i < collection.length(); i++) {
            Card card = CardDAO.getInstance().getCard(collection.getInt(i));
            deck.addCardToCollection(card);
        }
        if (jsonObject.get("name") != null) {
            deck.setName(jsonObject.getString("name"));
        }
        
        deck = CardDAO.getInstance().updateDeck(deck);
        
        if (deck != null) {
            result = Response.ok(deck, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        
        return result;
    }

    /** Deletes a Deck with the specified id
     * 
     * @param id - int reperesenting the id of the Deck
     * @return HTTP Response ok if the deletion in successful, or 404 if the Deck to remove is not found
     */
    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") int id) {
        Response result;
        Deck deck = CardDAO.getInstance().getDeck(id);
        if (deck != null) {
            CardDAO.getInstance().deleteDeck(deck);
            result = Response.ok().build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        
        return result;
    }
}
