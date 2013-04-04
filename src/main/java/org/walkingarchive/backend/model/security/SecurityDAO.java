package org.walkingarchive.backend.model.security;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.walkingarchive.backend.DbHelper;

public class SecurityDAO {
    private static SecurityDAO instance = new SecurityDAO();

    //Singleton
    public static SecurityDAO getInstance() {
        return instance;
    }
    
    private SecurityDAO() {}
    
    //----------------------------------------------------------------------------------------------
    // USER
    //----------------------------------------------------------------------------------------------
    
    public User getUserById(Integer id) {
        Session session = DbHelper.getSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }
    
    public User getUserByName(String name) {
        Session session = DbHelper.getSession();
        User user = (User) session.createQuery("from User where name = :name")
            .setParameter("name", name)
            .uniqueResult();
        session.close();
        return user;
    }
    
    public User getUserByEmail(String email) {
        Session session = DbHelper.getSession();
        User user = (User) session.createQuery("from User where lower(email) = lower(:email)")
            .setParameter("email", email)
            .uniqueResult();
        session.close();
        return user;
    }
    
    public User createUser(User user) throws Exception {
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
        finally {
            session.close();
        }

        return user;
    }
}
