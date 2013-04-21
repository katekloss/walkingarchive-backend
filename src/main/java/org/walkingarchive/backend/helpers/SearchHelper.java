package org.walkingarchive.backend.helpers;

import java.lang.String;
import java.lang.StringBuilder;
import java.util.LinkedList;
import java.util.List;

public class SearchHelper
{
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
