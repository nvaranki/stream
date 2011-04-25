package com.varankin.io.stream;

import java.util.*;

/**
 * Passthrough iteratable object that appends iteration with extra null.
 * 
 * @param T elementary object to transit.
 *
 * @author &copy; 2011 Nikolai Varankine
 */
public final class NullTerminator<T> implements Iterable<T>
{
    private final Iterator<T> repeater;

    public NullTerminator( Iterable<T> aSource )
    {
        this( aSource != null ? aSource.iterator() : Collections.<T>emptyList().iterator() );
    }

    public NullTerminator( Iterator<T> aSource )
    {
        repeater = new Repeater<T>( aSource != null ? aSource : Collections.<T>emptyList().iterator() );
    }

    @Override public Iterator<T> iterator()
    {
        return repeater;
    }

    private static class Repeater<T> implements Iterator<T>
    {
        private final Iterator<T> source;
        private boolean send_null = true;

        public Repeater( Iterator<T> aSource )
        {
            source = aSource;
        }

        @Override public boolean hasNext()
        {
            return source.hasNext() || send_null;
        }

        @Override public T next()
        {
            if( source.hasNext() )
            {
                return source.next();
            }
            else if( send_null )
            {
                send_null = false;
                return null;
            }
            else
                throw new NoSuchElementException();
        }

        @Override public void remove()
        {
            source.remove();
        }

    }

}
