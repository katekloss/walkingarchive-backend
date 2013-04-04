package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.HashMap;

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

    public Card() {}
    
    public Integer getId() {
        return id;
    }
    
    public void setId(int value) {
        this.id = value;
    }

    public String getIdString() {
        return id.toString();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String value) {
        this.name = value;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String value) {
        this.type = value;
    }

    public String getSubtype() {
        return subtype;
    }
    
    public void setSubtype(String value) {
        this.subtype = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public String getFlavortext() {
        return flavortext;
    }

    public void setFlavortext(String value) {
        this.flavortext = value;
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal v) {
        value = v;
    }

    public java.util.Set<Set> getSets() {
        return sets;
    }

    public void setSets(java.util.Set<Set> value)
    {
        sets = value;
    }

    public java.util.Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(java.util.Set<Price> value)
    {
        prices = value;
    }

    public HashMap<String, String> getMana()
    {
        return mana;
    }

    public void setMana(HashMap<String, String> value)
    {
        mana = value;
    }
}
