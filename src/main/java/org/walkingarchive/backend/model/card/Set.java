package org.walkingarchive.backend.model.card;

/** Object representing a released set of Magic cards
 *
 */
public class Set
{
    Integer id;
    String name;

    /** Set constructor
     * 
     */
    public Set() {}

    /** Get the id
     * 
     * @return int id
     */
    public Integer getId()
    {
        return id;
    }

    /** Set the id
     * 
     * @param value int id
     */
    public void setId(Integer value)
    {
        id = value;
    }

    /** Get the name of the Set
     * 
     * @return String name
     */
    public String getName()
    {
        return name;
    }

    /** Set the name of the Set
     * 
     * @param value String name
     */
    public void setName(String value)
    {
        name = value;
    }
}