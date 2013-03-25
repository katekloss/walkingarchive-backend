package org.walkingarchive.backend.model.security;

import org.walkingarchive.backend.DbHelper;
import org.walkingarchive.backend.model.trade.Trade;
import org.hibernate.Session;

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
        Session session = DbHelper.getSession();
        User user = (User) session.get(User.class, id);
        return user;
    }
    
    public User getUserByName(String name) {
        return null;
    }
    
    public User getUserByEmail(String email) {
        return null;
    }
    
    public User addUser(User user) {
        Session session = DbHelper.getSession();
        Integer id = (Integer) session.save(user);
        user.setId(id);
     
        return user;
    }
}
