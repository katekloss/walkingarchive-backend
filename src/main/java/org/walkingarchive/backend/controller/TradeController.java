package org.walkingarchive.backend.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.walkingarchive.backend.model.security.SecurityDAO;
import org.walkingarchive.backend.model.security.User;
import org.walkingarchive.backend.model.trade.Trade;
import org.walkingarchive.backend.model.trade.TradeDAO;

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
    
    /** Retrieves Trades for a user with the given user id
     * 
     * @param userId - int specifying the id of the owner of the Trades
     * @return List of Trades belonging to the user with the given id in JSON array
     */
    @GET
    @Path("user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Trade> getTradeByUser(@PathParam("userId") String userId) {
        //TODO - validate input
        Integer userIdInt = Integer.parseInt(userId);
        return TradeDAO.getInstance().getTradesForUser(userIdInt);
    }
    
    /** Retrieves the Trade with the given id.
     * 
     * @param id - int specifying the id of the Trade to retrieve
     * @return Trade with the given id as a JSON Object
     */
    @GET
    @Path("id/{tradeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTradeById(@PathParam("tradeId") Integer id)
    {
        Trade trade = TradeDAO.getInstance().getTradeForId(id);
        if(trade != null)
            return Response.ok(trade, MediaType.APPLICATION_JSON).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

    /** Creates a new trade given the new Trade information in JSON form.
     * 
     * @param json - a String representing the JSON for a new Trade. This must include
     *  the user id of the Trade owner.
     * @return HTTP Response ok with the newly created Trade data as a JSON object
     * @throws Exception
     */
    @PUT
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrade(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        User user = SecurityDAO.getInstance().getUserById(jsonObject.getInt("user"));
        
        Trade trade = new Trade(user, new ArrayList<Card>(), new ArrayList<Card>(), true);
        trade = TradeDAO.getInstance().createTrade(trade);
        
        return Response.ok(trade, MediaType.APPLICATION_JSON).build();
    }
    
    /** Updates a previously created trade with the given information in the JSON parameter.
     * 
     * @param json - a String of JSON spcifying the id of the Trade to modify, a list of integer ids for 'givingCards',
     * and a list of integer ids for 'receivingCards'
     * @return HTTP Response ok with the newly modified Tade information as a JSON object
     * @throws Exception
     */
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        Trade trade = TradeDAO.getInstance().getTradeForId(jsonObject.getInt("id"));
        trade.setActive(true);
        JSONArray cardsGiving = jsonObject.getJSONArray("givingCards");
        JSONArray cardsReceiving = jsonObject.getJSONArray("receivingCards");
        
        trade.removeAllCardsFromGiving();
        trade.removeAllCardsFromReceiving();
        
        for(int i = 0; i < cardsGiving.length(); i++) {
            Card card = CardDAO.getInstance().getCard(cardsGiving.getInt(i));
            trade.addCardToGiving(card);
        }
        for(int i = 0; i < cardsReceiving.length(); i++) {
            Card card = CardDAO.getInstance().getCard(cardsReceiving.getInt(i));
            trade.addCardToReceiving(card);
        }
        
        trade = TradeDAO.getInstance().updateTrade(trade);
        
        return Response.ok(trade, MediaType.APPLICATION_JSON).build();
    }
    
    /** Deletes a Trade with the given id
     * 
     * @param id - int representing the id of the Trade to delete
     * @return HTTP Response ok if the deletion is successful, or 404 if the Trade to delete cannot be found
     */
    @DELETE
    @Path("delete/{id}")
    public Response delete(@PathParam("id") int id) {
        Response result;
        Trade trade = TradeDAO.getInstance().getTradeForId(id);
        if (trade != null) {
            TradeDAO.getInstance().delete(trade);
            result = Response.ok().build();
        }
        else {
            result = Response.status(Status.NOT_FOUND).build();
        }
        
        return result;
    }
}
