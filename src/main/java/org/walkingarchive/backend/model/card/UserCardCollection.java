package org.walkingarchive.backend.model.card;

import java.util.ArrayList;
import java.util.List;

import org.walkingarchive.backend.model.security.User;

public class UserCardCollection {
    private Integer id;
    private List<Card> collection = new ArrayList<Card>();
    private User owner;
    
    public UserCardCollection(User owner) {
        this.owner = owner;
    }

    public UserCardCollection(User owner, List<Card> collection) {
        this.owner = owner;
        this.collection = collection;
    }

    public Integer getId() {
        return id;
    }
    
    public String getIdString() {
        return id.toString();
    }
    
    
    public User getOwner() {
        return owner;
    }
    
    public List<Card> getCollection() {
        return collection;
    }
    
    public void addCardToCollection(Card card) {
        collection.add(card);
    }
    
    public void removeCardFromCollection(Card card) {
        collection.remove(card);
    }
    
    public boolean contains(Card card) {
        return collection.contains(card);
    }
    
    public List<Card> getCardsWithType(String type) {
        List<Card> result = new ArrayList<Card>();
        
        for(Card card : collection) {
            if(card.getType() == type) {
                result.add(card);
            }
        }
        
        return result;
    }
}
