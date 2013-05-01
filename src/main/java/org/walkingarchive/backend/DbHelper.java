package org.walkingarchive.backend;

import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;

/** Database helper for establishing a connection
 */
public class DbHelper
{
    /** Establishes the database connection
     * 
     * @return Session
     */
    public static Session getSession()
    {
        Configuration config = new Configuration();
        config.configure();
        ServiceRegistry registry = new ServiceRegistryBuilder()
            .applySettings(config.getProperties())
            .buildServiceRegistry();
        return config.buildSessionFactory(registry).openSession();
    }
}