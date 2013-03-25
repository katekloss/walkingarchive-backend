package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;

public class Card {
    private Integer id;
    private String name;
    private String type;
    private String subtype;
    private BigDecimal value;
    private String manacolor;

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

    public String getManaColor() {
        return manacolor;
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal v) {
        value = v;
    }
}
