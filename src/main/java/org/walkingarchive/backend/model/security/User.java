package org.walkingarchive.backend.model.security;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class User {
    private String name;
    private String password;
    private String email;
    
    public User(String name, String password, String email) {
        //TODO - check if this user already exists, encrypt password
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean isPassword(String password) {
        boolean result = false;
        
        if (password == this.password) {
            result = true;
        }
        
        return result;
    }
    
    public JSONObject toJson() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("name", getName());
        result.put("email", getEmail());
        
        return result;
    }
}
