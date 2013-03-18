package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "Cards")
public class Card {

    @Id
    @Column(name = "cardid")
    @Type(type = "uuid-char")
    private UUID id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;
    
    @Column(name = "subtype")
    private String subtype;
    
    @Column(name = "cardtext")
    private String cardtext;
    
    @Column(name = "flavortext")
    private String flavortext;
    
    @Column(name = "extid")
    private String extid;
    
    @Column(name = "value")
    private BigDecimal value;
    
    @Column(name = "mana")
    private String manacolor;

    public Card() {}

    public Card(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCardtext() {
        return cardtext;
    }

    public void setCardtext(String cardtext) {
        this.cardtext = cardtext;
    }

    public String getFlavortext() {
        return flavortext;
    }

    public void setFlavortext(String flavortext) {
        this.flavortext = flavortext;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
    }

    public String getManacolor() {
        return manacolor;
    }

    public void setManacolor(String manacolor) {
        this.manacolor = manacolor;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
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
    
    public JSONObject toJson() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", getIdString());
        result.put("name", getName());
        result.put("type", getType());
        result.put("color", getManaColor());
        result.put("value", getValue());
        
        return result;
    }
}
