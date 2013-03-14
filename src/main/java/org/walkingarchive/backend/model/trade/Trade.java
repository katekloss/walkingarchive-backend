package org.walkingarchive.backend.model.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.walkingarchive.backend.model.card.Card;
import org.walkingarchive.backend.model.security.User;

public class Trade {
    private UUID id = UUID.randomUUID();
    private Date created;
    private User owner;
    private List<Card> receivingCards = new ArrayList<Card>();
    private List<Card> givingCards = new ArrayList<Card>();
    private boolean active;
    
    public Trade() {
    }
    
    public Trade(User user, List<Card> a, List<Card> b) {
        created = new Date();
        owner = user;
        for (Card ca : a) {
            receivingCards.add(ca);
        }
        for (Card cb : b) {
            givingCards.add(cb);
        }
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getIdString() {
        return id.toString();
    }
    
    public void addCardToReceiving(Card card) {
        receivingCards.add(card);
    }
    
    public void addCardToGiving(Card card) {
        givingCards.add(card);
    }
    
    public List<Card> getCardsReceiving() {
        return receivingCards;
    }
    
    public List<Card> getCardsGiving() {
        return givingCards;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public Date getDate() {
        return created;
    }
    
    public void setActive(boolean a) {
        active = a;
    }
    
    public boolean getActive() {
        return active;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", getIdString());
        result.put("date", getDate());
        result.put("active", getActive());
        result.put("owner", getOwner().toJson());

        JSONArray giving = new JSONArray();
        for(Card card : getCardsGiving()) {
            giving.put(card.toJson());
        }
        result.put("giving", giving);

        JSONArray receiving = new JSONArray();
        for(Card card : getCardsGiving()) {
            receiving.put(card.toJson());
        }
        result.put("receiving", receiving);

        return result;
    }
}
