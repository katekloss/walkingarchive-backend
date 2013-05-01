package org.walkingarchive.backend.model.security;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.walkingarchive.backend.DbHelper;

/** A singleton responsible for the restoration of User objects from the database, as well as
 * additions, modifications, and deletions of Users in the database.
 *
 */
public class SecurityDAO {
    private static SecurityDAO instance = new SecurityDAO();

    /** Retrieves the singleton SecurityDAO instance
     * 
     * @return SecurityDAO
     */
    public static SecurityDAO getInstance() {
        return instance;
    }
    
    /** Private contructor
     * 
     */
    private SecurityDAO() {}
    
    //----------------------------------------------------------------------------------------------
    // USER
    //----------------------------------------------------------------------------------------------
    
    /** Retireve the User by id from the database
     * 
     * @param id int id of the User
     * @return User with given id
     */
    public User getUserById(Integer id) {
        Session session = DbHelper.getSession();
        User user = null;
        try {
            user = (User) session.get(User.class, id);
        }
        finally {
            session.close();
        }
        return user;
    }
    
    /** Retrieve the User by name from the database
     * 
     * @param name String name of User
     * @return User with given name
     */
    public User getUserByName(String name) {
        Session session = DbHelper.getSession();
        User user = null;
        try {
            user = (User) session.createQuery("from User where name = :name")
                    .setParameter("name", name)
                    .uniqueResult();
        }
        finally {
            session.close();
        }
        return user;
    }
    
    /** Persist a new User in the database
     * 
     * @param user User object to persist
     * @return the newly created User including id in database
     * @throws Exception
     */
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
    
    /** Remove a persisted User from the database
     * 
     * @param user User object to remove
     */
    public void delete(User user) {
        Session session = DbHelper.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error in deleting user", e);
        }
        finally {
            session.close();
        }
    }
}
