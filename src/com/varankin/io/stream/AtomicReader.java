package com.varankin.io.stream;

import java.io.*;
import java.util.*;

/**
 * {@link java.lang.Iterable} wrapper over character 
 * {@link java.io.Reader}.
 *
 * @param Atom elementary output object.
 *
 * @author &copy; 2009 Nikolai Varankine
 */
public class AtomicReader<Atom> implements Iterable<Atom>
{
    public interface Factory<Atom>
    {
        Atom newInstance( char aAtom );
    }

    private final Iterator<Atom> factory;

    public AtomicReader( final Reader aInput, final Factory<Atom> aPacker )
    {
        factory = new Iterator<Atom>()
        {
            private static final int EOF = -1;
            private int cached = EOF, requested = EOF;
            private boolean ended = aInput == null;

            @Override
            public boolean hasNext()
            {
                if( cached == EOF && !ended )
                    try
                    {
                        cached = aInput.read();
                        ended = cached == EOF;
                    }
                    catch( IOException ex )
                    {
                        throw new RuntimeException( ex );
                    }
                return cached != EOF;
            }

            @Override
            public Atom next()
            {
                if( hasNext() )
                {
                    requested = cached;
                    cached = EOF;
                    return aPacker.newInstance( (char)requested );
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

    @Override
    public Iterator<Atom> iterator()
    {
        return factory;
    }

}
