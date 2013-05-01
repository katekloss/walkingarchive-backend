package org.walkingarchive.backend.helpers;

import java.lang.String;
import java.lang.StringBuilder;
import java.util.LinkedList;
import java.util.List;

/**A Search helper for Hibernate to use PostgrSQL search
 *
 */
public class SearchHelper
{
    /** Converts a string of text words into a linked list
     * 
     * @param text String of text
     * @return LinkedList of words for search
     */
    public static LinkedList<String> tokenize(String text)
    {
        LinkedList tokens = new LinkedList();
        text = text.trim();
        String[] words = text.split("[^a-zA-z0-9]");
        for(String word : words)
        {
            word = word.trim();
            if(word.length() >= 3)
                tokens.add(word);
        }
        return tokens;
    }

    /** Converts from LinkedList of words to a text String
     * 
     * @param list List of words to create text from
     * @param joiner the delimiter to join the words
     * @return String of text
     */
    public static String join(List<String> list, String joiner)
    {
        StringBuilder builder = new StringBuilder();
        for(String token : list)
        {
            builder.append(token);
            builder.append(joiner);
        }

        return builder.substring(0, builder.length() - 1);
    }
}
