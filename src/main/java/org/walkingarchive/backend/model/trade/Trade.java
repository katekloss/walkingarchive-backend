package org.walkingarchive.backend.model.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.walkingarchive.backend.model.card.Card;
import org.walkingarchive.backend.model.security.User;

/** Represents a two sided trade of Cards between two entities
 *
 */
public class Trade {
    private Integer id;
    private Date created;
    private User owner;
    private List<Card> receivingCards = new ArrayList<Card>();
    private List<Card> givingCards = new ArrayList<Card>();
    private boolean active;
    
    /** Empty Trade constructor for Hibernate
     * 
     */
    public Trade() {}
    
    /** Trade constructor
     * 
     * @param user User that owns this Trade
     * @param a List of cards that the user is receiving in the Trade
     * @param b List of cards that the user is giving to the tradee in the Trade
     * @param active whether or not this Trade is active
     */
    public Trade(User user, List<Card> a, List<Card> b, boolean active) {
        created = new Date();
        owner = user;
        for (Card ca : a) {
            receivingCards.add(ca);
        }
        for (Card cb : b) {
            givingCards.add(cb);
        }
        this.active = active;
    }
    
    /** Get the id
     * 
     * @return int id
     */
    public Integer getId() {
        return id;
    }
    
    /** Get the date the Trade was created - this is the identifier in the ui
     * 
     * @return Date the Trade was created
     */
    public Date getCreated() {
        return created;
    }

    /** Set the date the Trade was created
     * 
     * @param created Date
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /** Get the list of cards the User is receiving in the Trade
     * 
     * @return List of cards receiving
     */
    public List<Card> getReceivingCards() {
        return receivingCards;
    }

    /** Set the List of Cards the User is receiving in the Trade
     * 
     * @param receivingCards List of Cards receiving
     */
    public void setReceivingCards(List<Card> receivingCards) {
        this.receivingCards = receivingCards;
    }

    /** Get the List of cards the User is giving in the Trade
     * 
     * @return List of Cards giving
     */
    public List<Card> getGivingCards() {
        return givingCards;
    }

    /** Set the List of cards the User is giving in the Trade
     * 
     * @param givingCards List of Cards giving
     */
    public void setGivingCards(List<Card> givingCards) {
        this.givingCards = givingCards;
    }

    /** Set the id of the Trade
     * 
     * @param id int id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /** set the owner of the Trade
     * 
     * @param owner User the Trade belongs to
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /** Get the id as a String
     * 
     * @return String id
     */
    public String getIdString() {
        return id.toString();
    }
    
    /** Add a card to the receiving list
     * 
     * @param card Card
     */
    public void addCardToReceiving(Card card) {
        receivingCards.add(card);
    }
    
    /** Add a Card to the giving list
     * 
     * @param card Card
     */
    public void addCardToGiving(Card card) {
        givingCards.add(card);
    }
    
    /** Remove a card from the receiving List
     * 
     * @param card Card to remove from the receiving list
     */
    public void removeCardFromReceiving(Card card) {
        receivingCards.remove(card);
    }
    
    /** Remove a Card from the giving list
     * 
     * @param card Card to remove from the giving list
     */
    public void removeCardFromGiving(Card card) {
        givingCards.remove(card);
    }
    
    /** Remove all the Cards from the receiving list
     * 
     */
    public void removeAllCardsFromReceiving() {
        for (int i = receivingCards.size() - 1; i >= 0; i--) {
            receivingCards.remove(i);
        }
    }
    
    /** Remove all the Cards from the giving list
     * 
     */
    public void removeAllCardsFromGiving() {
        for (int i = givingCards.size() - 1; i >= 0; i--) {
            givingCards.remove(i);
        }
    }
    
    /** Get the owner of the Trade
     * 
     * @return User the Trade belongs to
     */
    public User getOwner() {
        return owner;
    }
    
    /** Get the date the Trade was created
     * 
     * @return Date the Trade was created
     */
    public Date getDate() {
        return created;
    }
    
    /** Set this Trade as active
     * 
     * @param a boolean true if active, false if not
     */
    public void setActive(boolean a) {
        active = a;
    }
    
    /** Get the active status for the Trade
     * 
     * @return boolean true if active, false if not
     */
    public boolean getActive() {
        return active;
    }
}
