package org.walkingarchive.backend.model.security;

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
    
    public User getUserById(Integer id) {
        return null;
    }
    
    public User getUserByName(String name) {
        return null;
    }
    
    public User getUserByEmail(String email) {
        return null;
    }
}
