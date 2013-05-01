// This file originally created in 2011 by Jakub Gluszecki
// Source: http://backtothefront.net/2011/storing-sets-keyvalue-pairs-single-db-column-hibernate-postgresql-hstore-type/

package org.walkingarchive.backend.model.types;


import org.walkingarchive.backend.helpers.HstoreHelper;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.HibernateException;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/** A helper class for Hibernate to restore the PostgreSQL hstore type as a Java HashMap
 * 
 */
public class HstoreUserType implements UserType {
 
    public int[] sqlTypes()
    {
        return new int[]
            {
                StringType.INSTANCE.sqlType(),
            };
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    public Serializable disassemble(Object o) throws HibernateException
    {
        return (Serializable) o;
    }

    public boolean isMutable()
    {
        return true;
    }

    public Object deepCopy(Object o) throws HibernateException
    {
        // It's not a true deep copy, but we store only String instances, and they
        // are immutable, so it should be OK
        Map m = (Map) o;
        return new HashMap(m);
    }
 
    public boolean equals(Object o1, Object o2) throws HibernateException
    {
        Map m1 = (Map) o1;
        Map m2 = (Map) o2;
        return m1.equals(m2);
    }
 
    public int hashCode(Object o) throws HibernateException
    {
        return o.hashCode();
    }
 
    // TODO: Go over these methods more carefully, since the SessionImplementor parameter
    // seems to be new in Hibernate 4.1 and wasn't around in 2011

    @Override
    public Object nullSafeGet(ResultSet rs, String[] arg1, SessionImplementor session, Object arg2) throws SQLException
    {
        String col = arg1[0];
        String val = rs.getString(col);
        return HstoreHelper.toMap(val);
    }
 
    @Override
    public void nullSafeSet(PreparedStatement ps, Object obj, int i, SessionImplementor session) throws SQLException
    {
        String s = HstoreHelper.toString((Map) obj);
        StringType.INSTANCE.set(ps, s, i, session);
    }
 
    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }
 
    public Class returnedClass()
    {
        return Map.class;
    }
}