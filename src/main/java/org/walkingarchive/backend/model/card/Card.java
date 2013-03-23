package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.UUID;

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
    
    public String getIdString() {
        return id.toString();
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
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
