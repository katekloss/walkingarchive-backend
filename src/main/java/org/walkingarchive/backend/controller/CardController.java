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

/** CardController is responsible for serving information about Magic Cards via rest services. 
 * Each method is tagged with the url path to visit (appended to the main url dev.mtgwalkingarchive.com:8080).
 * 
 * @author Alison Orlando
 *
 */

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
    
    
    /** Retrieves a Magic card by its id and returns the card as a JSON object.
     * If no card is found, a 404 status is returned.
     * 
     * @param card - an integer representing the card id
     * @return Magic card represented as a JSON object, or Status 404 if not found.
     */
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
    
    /** Retrieves Magic cards by name and returns the cards as a JSON array.
     * If no card is found, an empty array is returned. This method defaults to
     * returning the first 20 results found when searching for the name prefix.
     * 
     * @param card - a string representing the card name
     * @return Magic cards represented as a JSON array, or Status 404 if not found.
     */
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
    
    /** Retrieves Magic cards by name and returns the cards as a JSON array.
     * If no card is found, an empty array is returned. Similar to the getCardsByName
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param card - a string representing the card name
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1,
     * which returns the first 20 results.
     * @return Magic cards represented as a JSON array, or Status 404 if not found.
     */
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
    
    /** Retrieves Magic cards by type and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. This method defaults to
     * returning the first 20 results found when searching for the name prefix.
     * 
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @return Magic cards  of the given type represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("type/{type}")
    public Response getCardsByType(@PathParam("type") String type) {
        //TODO - validate input
        return getCardsByTypeOffset(type, 0);
    }
    
    /** Retrieves Magic cards by type and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. Similar to the getCardsByType
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1, 
     * which returns the first 20 results
     * @return Magic cards  of the given type represented as a JSON array, or Status 404 if not found.
     */
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
    
    /** Retrieves Magic cards by set and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned.
     * 
     * @param setId - an integer representing the set id of a Magic card
     * @return Magic cards  of the given set represented as a JSON array, or Status 404 if not found.
     */
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
    
    /** Retrieves Magic cards by set name and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned.
     * 
     * @param setname - a string representing the name of a set of a Magic card
     * @return Magic cards of the given set represented as a JSON array, or Status 404 if not found.
     */
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
    
    /** Retrieves Magic cards by mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. This method defaults to
     * returning the first 20 results found when searching for the name prefix.
     * 
     * @param mana - a string representing the mana color of a Magic card (i.e. 'red')
     * @return Magic cards of the given mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("mana/{mana}")
    public Response getCardsByMana(@PathParam("mana") String mana) {
        //TODO - validate input
        return getCardsByManaOffset(mana, 1);
    }
    
    /** Retrieves Magic cards by mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. Similar to the getCardsByMana
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param mana - a string representing the mana color of a Magic card (i.e. 'red')
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1,
     * which will return the first 20 results.
     * @return Magic cards of the given mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("mana/{mana}/{offset}")
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
    
    /** Retrieves Magic cards by a combination of name, type, and mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. This method defaults to
     * returning the first 20 results found when searching for the name prefix.
     * 
     * @param name - a string representing the name of a Magic card, accepts prefix form
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @param mana - a srting representing the mana of a Magic card (i.e. 'red')
     * @return Magic cards of the given name, type, and mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}/type/{type}/mana/{mana}")
    public Response getCardsByNameManaType(@PathParam("name") String name,
            @PathParam("type") String type,
            @PathParam("mana") String mana) {
        //TODO - validate input
        return getCardsByNameManaTypeOffset(name, type, mana, 1);
    }
    
    /** Retrieves Magic cards by a combination of name, type, and mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. Similar to the getCardsByNameManaType
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param name - a string representing the name of a Magic card, accepts prefix form
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @param mana - a srting representing the mana of a Magic card (i.e. 'red')
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1,
     * which will return the first 20 results.
     * @return Magic cards of the given name, type, and mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}/type/{type}/mana/{mana}/{offset}")
    public Response getCardsByNameManaTypeOffset(@PathParam("name") String name,
            @PathParam("type") String type,
            @PathParam("mana") String mana,
            @PathParam("offset") int offset) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByNameManaType(name, type, mana, (offset-1)*20);
        if(c != null) {
            result = Response.ok(c, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        return result;
    }
    

    /** Retrieves Magic cards by a combination of type and mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. By default, this method returns a maximum of 20 results.
     * 
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @param mana - a srting representing the mana of a Magic card (i.e. 'red')
     * @return Magic cards of the given type and mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/type/{type}/mana/{mana}")
    public Response getCardsByManaType(@PathParam("type") String type,
            @PathParam("mana") String mana) {
        //TODO - validate input
        return getCardsByManaTypeOffset(type, mana, 1);
    }
    
    /** Retrieves Magic cards by a combination of type and mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. Similar to the getCardsByeManaType
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @param mana - a srting representing the mana of a Magic card (i.e. 'red')
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1,
     * which will return the first 20 results.
     * @return Magic cards of the given type and mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("type/{type}/mana/{mana}/{offset}")
    public Response getCardsByManaTypeOffset(@PathParam("type") String type,
            @PathParam("mana") String mana,
            @PathParam("offset") int offset) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByManaType(type, mana, (offset-1)*20);
        if(c != null) {
            result = Response.ok(c, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        return result;
    }
    

    /** Retrieves Magic cards by a combination of name and mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. By default, this method returns the first 20 results.
     * 
     * @param name - a string representing the name of a Magic card, accepts prefix form
     * @param mana - a srting representing the mana of a Magic card (i.e. 'red')
     * @return Magic cards of the given name and mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}/mana/{mana}")
    public Response getCardsByNameMana(@PathParam("name") String name,
            @PathParam("mana") String mana) {
        //TODO - validate input
        return getCardsByNameManaOffset(name, mana, 1);
    }
    
    /** Retrieves Magic cards by a combination of name and mana color and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. Similar to the getCardsByNameMana
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param name - a string representing the name of a Magic card, accepts prefix form
     * @param mana - a srting representing the mana of a Magic card (i.e. 'red')
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1,
     * which will return the first 20 results.
     * @return Magic cards of the given name and mana color represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}/mana/{mana}/{offset}")
    public Response getCardsByNameManaOffset(@PathParam("name") String name,
            @PathParam("mana") String mana,
            @PathParam("offset") int offset) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByNameMana(name, mana, (offset-1)*20);
        if(c != null) {
            result = Response.ok(c, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        return result;
    }
    
    /** Retrieves Magic cards by a combination of name and type and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. By default, this method returns the first 20 results.
     * 
     * @param name - a string representing the name of a Magic card, accepts prefix form
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @return Magic cards of the given name and type represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}/type/{type}")
    public Response getCardsByNameType(@PathParam("name") String name,
            @PathParam("type") String type) {
        //TODO - validate input
        return getCardsByNameTypeOffset(name, type, 1);
    }
    
    /** Retrieves Magic cards by a combination of name and type and returns the cards as a JSON array.
     * If no cards are found, an empty array is returned. Similar to the getCardsByNameType
     * method, this function allows the caller to specify the offset of results to return.
     * 
     * @param name - a string representing the name of a Magic card, accepts prefix form
     * @param type - a string representing the type of a Magic card (i.e. 'enchantment')
     * @param offset - an integer representing the 'page' of results to retrieve. The default is 1,
     * which will return the first 20 results.
     * @return Magic cards of the given name and type represented as a JSON array, or Status 404 if not found.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}/type/{type}/{offset}")
    public Response getCardsByNameTypeOffset(@PathParam("name") String name,
            @PathParam("type") String type,
            @PathParam("offset") int offset) {
        //TODO - validate input
        Response result;
        List<Card> c = CardDAO.getInstance().getCardsByNameType(name, type, (offset-1)*20);
        if(c != null) {
            result = Response.ok(c, MediaType.APPLICATION_JSON).build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        return result;
    }

    /** Retrieves Magic cards by running the search engine query on the passed in string.
     * This method retrieves the first 20 results.
     * 
     * @param query - a String representing the words on a Magic card
     * @return Magic cards that match terms in the query, weighted by relevance, or null if none are found
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{query}")
    public List<Card> getCardsByTextSearch(@PathParam("query") String query) {
        return CardDAO.getInstance().getCardsBySearch(query, 20);
    }
}
