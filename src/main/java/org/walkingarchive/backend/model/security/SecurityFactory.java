package org.walkingarchive.backend.model.security;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.walkingarchive.backend.DbHelper;

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
        Session session = DbHelper.getSession();
        User user = (User) session.createQuery("from Card where name = :name")
            .setParameter("name", name)
            .uniqueResult();
        return user;
    }
    
    public User getUserByEmail(String email) {
        return null;
    }
    
    public User addUser(User user) throws Exception {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Integer id = (Integer) session.save(user);
            user.setId(id);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw e;
        }

        return user;
    }
}
