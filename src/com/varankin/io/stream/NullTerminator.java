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
    private final Iterable<T> source;

    public NullTerminator( Iterable<T> source )
    {
        this.source = source;
    }

    @Override public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {
            private final Iterator<T> iterator = source.iterator();
            private boolean send_null = true;

            @Override public boolean hasNext()
            {
                return iterator.hasNext() || send_null;
            }

            @Override public T next()
            {
                if( iterator.hasNext() )
                {
                    return iterator.next();
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
                iterator.remove();
            }

        };
    }

}
