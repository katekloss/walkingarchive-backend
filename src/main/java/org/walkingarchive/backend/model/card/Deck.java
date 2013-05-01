package org.walkingarchive.backend.model.card;

import java.util.ArrayList;
import java.util.List;

import org.walkingarchive.backend.model.security.User;

/** A Deck object represents a Magic: The Gathering trading card deck
 * A Deck is made up of a collection of Cards, with a User as its owner,
 * and a name given by the user.
 */
public class Deck {
    private Integer id;
    private List<Card> collection = new ArrayList<Card>();
    private User owner;
    private String name;
    
    /** Deck constructor for Hibernate
     * 
     */
    public Deck() {}
    
    /** Constructor for specifying a new Deck with a User and a name
     * 
     * @param owner User that owns the Deck
     * @param name String name of the deck
     */
    public Deck(User owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    /** Constructor for specifying a new Deck with a User 
     * and a List of Cards in the collection
     * 
     * @param owner User that owns the deck
     * @param collection List of Cards in the Deck collection
     */
    public Deck(User owner, List<Card> collection) {
        this.owner = owner;
        this.collection = collection;
    }

    /** Get the id
     * 
     * @return Integer id
     */
    public Integer getId() {
        return id;
    }

    /** Set id
     * 
     * @param id int id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /** Get the owner
     * 
     * @return User that owns the Deck
     */
    public User getOwner() {
        return owner;
    }
    
    /** Get the List of Cards in the deck
     * 
     * @return List of Cards in the deck
     */
    public List<Card> getCollection() {
        return collection;
    }
    
    /** Add a card to the Deck
     * 
     * @param card Card to add to the Deck
     */
    public void addCardToCollection(Card card) {
        collection.add(card);
    }
    
    /** Remove a card from the Deck
     * 
     * @param card Card to remove from the Deck, if it is in the Deck
     */
    public void removeCardFromCollection(Card card) {
        collection.remove(card);
    }
    
    /** Empty the Cards in the collection
     * 
     */
    public void removeAllCardsFromCollection() {
        for (int i = collection.size() - 1; i >= 0; i--) {
            collection.remove(i);
        }
    }
    
    /** Check if the collection contains the Card
     * 
     * @param card Card
     * @return boolean true if the Card exists in the Deck, else false
     */
    public boolean contains(Card card) {
        return collection.contains(card);
    }
    
    /** Get the name of the Deck
     * 
     * @return String name
     */
    public String getName() {
        return name;
    }

    /** Set the name of the Deck
     * 
     * @param name String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Set the collection of the deck
     * 
     * @param collection List of cards to become the collection
     */
    public void setCollection(List<Card> collection) {
        this.collection = collection;
    }

    /** Set the owner of the deck
     * 
     * @param owner User to own the Deck
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /** Get the Cards with the given type in the Deck
     * 
     * @param type String representing the card type (i.e. enchantment)
     * @return List of Cards of the given type. If no Cards of the type exist, 
     * an empty List is returned.
     */
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
