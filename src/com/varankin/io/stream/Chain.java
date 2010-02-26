package com.varankin.io.stream;

import java.util.*;

/**
 * This object creates a new iterable source of Atom's,
 * combined from Atom's received from every instance of
 * repository provided by external factory.

 * @param Atom elementary object of input and output streams.
 *
 * @author &copy; 2010 Nikolai Varankine
 */
public class Chain<Atom> implements Iterable<Atom>
{
    private final Iterator<Atom> iterator;

    /**
     * @param aFactory provide of Atom repositories.
     */
    public Chain( final Iterator<Iterable<Atom>> aFactory )
    {
        iterator = new Iterator<Atom>()
        {
            private Iterator<Atom> worker = null;

            @Override public boolean hasNext()
            {
                return worker != null && worker.hasNext() ||
                    aFactory != null && aFactory.hasNext() &&
                    (worker = aFactory.next().iterator()).hasNext();
            }

            @Override public Atom next()
            {
                if( worker != null && worker.hasNext() )
                    return worker.next();
                else
                    throw new NoSuchElementException();
            }

            @Override public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public final Iterator<Atom> iterator()
    {
        return iterator;
    }
}
