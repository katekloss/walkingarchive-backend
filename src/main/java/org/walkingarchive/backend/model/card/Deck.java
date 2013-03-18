package org.walkingarchive.backend.model.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.Type;
import org.walkingarchive.backend.model.security.User;

public class Deck {
    private UUID id;
    private List<Card> deck = new ArrayList<Card>();
    private User owner;
    private String name;
    
    public Deck() {}
    
    public Deck(String name, User owner) {
        this.owner = owner;
        this.name = name;
    }

    public Deck(String name, User owner, List<Card> collection) {
        this.name = name;
        this.owner = owner;
        this.deck = collection;
    }

    public UUID getId() {
        return id;
    }
    
    public String getIdString() {
        return id.toString();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public List<Card> getDeck() {
        return deck;
    }
    
    public void addCardToDeck(Card card) {
        deck.add(card);
    }
    
    public void removeCardFromDeck(Card card) {
        deck.remove(card);
    }
    
    public boolean contains(Card card) {
        return deck.contains(card);
    }
    
    public List<Card> getCardsWithType(String type) {
        List<Card> result = new ArrayList<Card>();
        
        for(Card card : deck) {
            if(card.getType() == type) {
                result.add(card);
            }
        }
        
        return result;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", getIdString());
        result.put("name", getName());
        result.put("owner", getOwner().toJson());

        JSONArray collection = new JSONArray();
        for(Card card : getDeck()) {
            collection.put(card.toJson());
        }
        result.put("deck", collection);
        
        return result;
    }
}
