package com.varankin.io.stream;

import java.io.*;
import java.util.*;

/**
 * {@link java.lang.Iterable} wrapper over character 
 * {@link java.io.Reader}.
 *
 * @author &copy; 2009 Nikolai Varankine
 */
public class CharacterReader implements Iterable<Character>
{
    private static final int EOF = -1;

    private final Reader source;
    private int cached, requested;
    private boolean ended;

    public CharacterReader( Reader aInput )
    {
        source = aInput;
        cached = requested = EOF;
        ended = aInput == null;
    }

    @Override
    public Iterator<Character> iterator()
    {
        cached = requested;
        return new Iterator<Character>()
        {
            @Override
            public boolean hasNext()
            {
                if( cached == EOF && !ended )
                    try
                    {
                        cached = source.read();
                        ended = cached == EOF;
                    }
                    catch( IOException ex )
                    {
                        throw new RuntimeException( ex );
                    }
                return cached != EOF;
            }

            @Override
            public Character next()
            {
                if( hasNext() )
                {
                    requested = cached;
                    cached = EOF;
                    return new Character( (char)requested );
                }
                else
                    throw new NoSuchElementException();
            }

            @Override
            public void remove()
            {
                if( requested != EOF )
                    requested = EOF;
                else
                    throw new IllegalStateException();
            }

        };
    }

}
