package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.HashMap;

/**Card is an Object representing a Magic :The Gathering trading card.
 *
 */
public class Card {
    private Integer id;
    private String name;
    private String type;
    private String subtype;
    private String text;
    private String flavortext;
    private BigDecimal value;
    private java.util.Set<Set> sets;
    private java.util.Set<Price> prices;
    private HashMap<String, String> mana;

    /** Creates a new instance of a Card.
     * 
     */
    public Card() {}
    
    /** Get the id of the Card
     * 
     * @return Integer id of Card
     */
    public Integer getId() {
        return id;
    }
    
    /** Set the id of a Card, used by Hibernate
     * 
     * @param value int that sets the id of the Card to this value
     */
    public void setId(int value) {
        this.id = value;
    }

    /** Get the id of the Card as a String
     * 
     * @return String id of Card
     */
    public String getIdString() {
        return id.toString();
    }
    
    /** Get the name of the Card
     * 
     * @return String name
     */
    public String getName() {
        return name;
    }
    
    /** Set the name of the card
     * 
     * @param value - String name of card
     */
    public void setName(String value) {
        this.name = value;
    }

    /** Get the type of the Card
     * 
     * @return String type
     */
    public String getType() {
        return type;
    }
    
    /** Set the type of the Card
     * 
     * @param value - String type
     */
    public void setType(String value) {
        this.type = value;
    }

    /** Get the subtype of a Card
     * 
     * @return String subtype
     */
    public String getSubtype() {
        return subtype;
    }
    
    /** Set the subtype of a Card
     * 
     * @param value - String subtype
     */
    public void setSubtype(String value) {
        this.subtype = value;
    }

    /** Get the text on a Card
     * 
     * @return String text
     */
    public String getText() {
        return text;
    }

    /** Set the text on a Card
     * 
     * @param value - String text
     */
    public void setText(String value) {
        this.text = value;
    }

    /** Get the flavor text on a Card
     * 
     * @return String flavor text
     */
    public String getFlavortext() {
        return flavortext;
    }

    /** Set the flavor text of a card
     * 
     * @param value String flavor text
     */
    public void setFlavortext(String value) {
        this.flavortext = value;
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal v) {
        value = v;
    }

    /** Get the Sets a Card is a part of
     * 
     * @return Set of Sets
     */
    public java.util.Set<Set> getSets() {
        return sets;
    }

    /** Set the Sets a Card is a part of
     * 
     * @param value Set of Sets
     */
    public void setSets(java.util.Set<Set> value)
    {
        sets = value;
    }

    /** Get the Set of prices associated with a card
     * 
     * @return Set of Prices
     */
    public java.util.Set<Price> getPrices() {
        return prices;
    }

    /** Set the prices of the associated card
     * 
     * @param value Set of Prices
     */
    public void setPrices(java.util.Set<Price> value)
    {
        prices = value;
    }

    /** Get the mana cost associated with a Card
     * 
     * @return HashMap of mana color to cost mapping
     */
    public HashMap<String, String> getMana()
    {
        return mana;
    }

    /** Set the mana cost mappings for a Card
     * 
     * @param value HashMap of mana color to cost mappings
     */
    public void setMana(HashMap<String, String> value)
    {
        mana = value;
    }
}
