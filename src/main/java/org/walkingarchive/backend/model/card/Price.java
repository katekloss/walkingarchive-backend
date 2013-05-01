package org.walkingarchive.backend.model.card;

import java.math.BigDecimal;
import java.util.Date;

/** An object to hold the Price of a Cad from a particular Set
 * 
 */
public class Price
{
    private Integer id;
    private Set set;
    private BigDecimal price;
    private String source;
    private Date date;

    /** Price constructor
     * 
     */
    public Price() {}

    /** Get the id
     * 
     * @return int id
     */
    public Integer getId()
    {
        return id;
    }

    /** Set the id (for Hibernate)
     * 
     * @param value int id
     */
    public void setId(Integer value)
    {
        id = value;
    }

    /** Get the Set the price is associated with
     * 
     * @return Set
     */
    public Set getSet()
    {
        return set;
    }

    /** Set the Set
     * 
     * @param value Set
     */
    public void setSet(Set value)
    {
        set = value;
    }

    /** Get the monetary value of this price
     * 
     * @return BigDecimal value of the card
     */
    public BigDecimal getPrice()
    {
        return price;
    }

    /** Set the monetary value
     * 
     * @param value BigDecimal monetary value
     */
    public void setPrice(BigDecimal value)
    {
        price = value;
    }

    /** Get the source of information that provided the monetary values
     * 
     * @return String with the online destination
     */
    public String getSource()
    {
        return source;
    }

    /** Set the source of information for the monetary value
     * 
     * @param value String source
     */
    public void setSource(String value)
    {
        source = value;
    }

    /** Get the date that this value was checked
     * 
     * @return Date that this price was checked
     */
    public Date getDate()
    {
        return date;
    }

    /** Set the date that this value was checked
     * 
     * @param value Date that the price was checked
     */
    public void setDate(Date value)
    {
        date = value;
    }
}