package com.varankin.io.stream;

import java.util.*;

/**
 * Passthrough iteratable object that ends iteration with extra null.
 * 
 * @param T elementary object to transit.
 *
 * @author &copy; 2009 Nikolai Varankine
 */
public final class NullTerminator<T> implements Iterable<T>
{
    private final Iterator<T> source, factory;

    public NullTerminator( Iterable<T> aSource )
    {
        source = aSource != null ? aSource.iterator() : Collections.<T>emptyList().iterator();
        factory = new Iterator<T>()
        {
            private boolean send_null = true;

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

        };
    }

    @Override public Iterator<T> iterator()
    {
        return factory;
    }

}
