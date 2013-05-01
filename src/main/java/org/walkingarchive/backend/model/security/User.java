package org.walkingarchive.backend.model.security;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/** A User of the Walking Archive application
 *
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    
    /** User constructor
     * 
     * @param name String username
     * @param password String password
     * @param email String email
     */
    public User(String name, String password, String email) {
        //TODO - check if this user already exists, encrypt password
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    /** Empty constructor for Hibernate
     * 
     */
    public User() {}
    
    /** Get the name of the User
     * 
     * @return String name
     */
    public String getName() {
        return name;
    }
    
    /** Get the email of the User
     * 
     * @return String email
     */
    public String getEmail() {
        return email;
    }
    
    /** Get the id of the User
     * 
     * @return int id
     */
    public Integer getId() {
        return id;
    }

    /** Set the id of the User
     * 
     * @param id int id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /** Get the password for the User. This is tagged with JsonIgnore
     * so that Jersey will not return the password for viewing via REST services
     * 
     * @return String password
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /** Set the password
     * 
     * @param password String password
     */
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    /** Set the user name
     * 
     * @param name String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** set the User email
     * 
     * @param email String email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Check that a given password string is equivalent to this User's password
     * For authentication
     * 
     * @param password String password
     * @return boolean true if the password is correct, false otherwise
     */
    public boolean isPassword(String password) {
        boolean result = false;
        
        if (password == this.password) {
            result = true;
        }
        
        return result;
    }
}
