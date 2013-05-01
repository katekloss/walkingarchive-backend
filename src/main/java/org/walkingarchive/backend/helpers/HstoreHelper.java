// This file originally created in 2011 by Jakub Gluszecki
// Source: http://backtothefront.net/2011/storing-sets-keyvalue-pairs-single-db-column-hibernate-postgresql-hstore-type/

package org.walkingarchive.backend.helpers;

import java.util.HashMap;
import java.util.Map;

/** A helper class for Hibernate to restore the PostgreSQL hstore type to a Java HashMap
 *
 */
public class HstoreHelper {

    private static final String K_V_SEPARATOR = "=>";

    /** Converts a Map to a String representation for the hstore
     * 
     * @param m The Java Map
     * @return String representing the Map in the hstore format
     */
    public static String toString(Map<String, String> m) {
        if (m.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        int n = m.size();
        for (String key : m.keySet())
        {
            sb.append("\"" + key + "\"" + K_V_SEPARATOR + "\"" + m.get(key) + "\"");
            if (n > 1)
            {
                sb.append(", ");
                n--;
            }
        }
        return sb.toString();
    }

    /** Converts a String from an hstore to a Java Map
     * 
     * @param s String from hstore
     * @return Map
     */
    public static Map<String, String> toMap(String s) {
        Map<String, String> m = new HashMap<String, String>();
        if (!(s != null && s.length() > 0 && s.trim().length() > 0))
        {
            return m;
        }

        String[] tokens = s.split(", ");
        for (String token : tokens)
        {
            String[] kv = token.split(K_V_SEPARATOR);
            String k = kv[0];
            k = k.trim().substring(1, k.length() - 1);
            String v = kv[1];
            v = v.trim().substring(1, v.length() - 1);
            m.put(k, v);
        }
        return m;
    }
}