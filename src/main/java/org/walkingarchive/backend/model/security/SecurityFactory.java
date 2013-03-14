package org.walkingarchive.backend.model.security;

import java.util.UUID;

public class SecurityFactory {
    private static SecurityFactory instance = new SecurityFactory();

    //Singleton
    public static SecurityFactory getInstance() {
        return instance;
    }
    
    private SecurityFactory() {}
    
    //----------------------------------------------------------------------------------------------
    // USER
    //----------------------------------------------------------------------------------------------
    
    public User getUserById(UUID id) {
        return null;
    }
    
    public User getUserByName(String name) {
        return null;
    }
    
    public User getUserByEmail(String email) {
        return null;
    }
}
