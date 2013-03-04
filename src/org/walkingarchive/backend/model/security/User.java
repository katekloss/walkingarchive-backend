package org.walkingarchive.backend.model.security;

public class User {
    private String name;
    private String password;
    
    public User(String name, String password) {
        //TODO - check if this user already exists, encrypt password
        this.name = name;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
}
