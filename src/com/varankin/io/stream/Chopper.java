package com.varankin.io.stream;

import com.varankin.io.container.Receiver;
import java.util.*;

/**
 * General purpose parser, trying to construct Component's from Part's.
 * Component is considered completed, once it rejects next Part object.
 * Parser starts consuming part(s) on getting Component over iterator.
 *
 * @param Component object being constructed.
 * @param Part elementary object used to build component.
 *
 * @author &copy; 2010 Nikolai Varankine
 */
public final class Chopper<Component extends Receiver<Part>, Part> implements Iterable<Component>
{
    private final Iterator<Part> source;
    private final Iterator<Component> factory;
    private final Iterator<Component> iterator;

    /**
     * @param aSource  source of Part(s) to construct Component(s).
     * @param aFactory producer of stub Component to fill in with Part(s).
     */
    public Chopper( Iterable<Part> aSource, Iterator<Component> aFactory )
    {
        source = aSource.iterator();
        factory = aFactory;
        iterator = new Iterator<Component>()
        {
            private Component candidate = null;
            private Part rejected = null;

            @Override public boolean hasNext()
            {
                return candidate != null ||
                    // there's nonempty source of part(s):
                    ( rejected != null || source.hasNext() ) &&
                    // there's available receiver of parts:
                    factory.hasNext() && ( candidate = factory.next() ) != null;
            }

            @Override public Component next()
            {
                if( candidate == null )
                    throw new NoSuchElementException();

                boolean cycling = true;
                if( rejected != null )
                    if( candidate.add( rejected ))
                        rejected = null;
                    else
                        cycling = false;
                        // despite it might cause infinite cycle, factory could
                        // provide another sort of Component to fix the problem
                if( cycling )
                    while( source.hasNext() )
                    {
                        Part part = source.next();
                        if( !candidate.add( part ))
                        {
                            rejected = part;
                            break;
                        }
                    }
                Component rv = candidate;
                candidate = null;
                return rv;
            }

            @Override public void remove()
            {
                throw new UnsupportedOperationException();
            }

        };
    }

    /**
     * Returns singleton iterator that produces Component's from 
     * input stream. Next invocation of the method returns the
     * same object.
     * 
     * @return iterator over Component's.
     */
    @Override
    public Iterator<Component> iterator()
    {
        return iterator;
    }
}
