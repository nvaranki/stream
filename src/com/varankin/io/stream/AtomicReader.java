package com.varankin.io.stream;

import java.io.*;
import java.util.*;

/**
 * {@link java.lang.Iterable} wrapper over character 
 * {@link java.io.Reader}.
 *
 * @param Atom elementary output object.
 *
 * @author &copy; 2011 Nikolai Varankine
 */
public class AtomicReader<Atom> implements Iterable<Atom>
{
    private final Iterator<Atom> factory; // single run only

    public AtomicReader( final Reader aInput, final Factory<Atom> aPacker )
    {
        factory = new AtomicIterator<Atom>( aInput, aPacker );
    }

    @Override
    public Iterator<Atom> iterator()
    {
        return factory;
    }
    
    public interface Factory<Atom>
    {
        Atom newInstance( char aAtom );
    }

    private static class  AtomicIterator<Atom> implements Iterator<Atom>
    {
        private static final int EOF = -1;
        
        private final Reader input;
        private final Factory<Atom> packer;
        private int cached = EOF, requested = EOF;
        private boolean ended;;

        AtomicIterator( final Reader aInput, final Factory<Atom> aPacker )
        {
            input = aInput;
            packer = aPacker;
            ended = aInput == null;
        }

        @Override
        public boolean hasNext()
        {
            if( cached == EOF && !ended )
                try
                {
                    cached = input.read();
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
                return packer.newInstance( (char)requested );
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

    }

}
