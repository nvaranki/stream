package com.varankin.io.stream;

import com.varankin.io.container.*;
import java.util.*;

/**
 * General purpose parser, trying to construct Component's from Part's.
 * Component is considered completed, once it rejects next Part object.
 * Parser starts consuming part(s) on getting Component over iterator.
 * Parser retracts unused Part's from completed Component to reuse them
 * for next Component instance.
 *
 * @param Component object being constructed.
 * @param Part elementary object used to build component.
 *
 * @author &copy; 2010 Nikolai Varankine
 */
public final class AccuChopper<Component extends Accumulator<Part>, Part> implements Iterable<Component>
{
    private final Iterator<Part> source;
    private final Iterator<Component> factory;
    private final Iterator<Component> iterator;

    /**
     * @param aSource  source of Part(s) to construct Component(s).
     * @param aFactory producer of stub Component to fill in with Part(s).
     */
    public AccuChopper( Iterable<Part> aSource, Iterator<Component> aFactory )
    {
        source = aSource.iterator();
        factory = aFactory;
        iterator = new Iterator<Component>()
        {
            private Component candidate = null;
            private List<Part> rejected = new ArrayList<Part>();

            @Override public boolean hasNext()
            {
                return candidate != null ||
                    // there's nonempty source of part(s):
                    ( !rejected.isEmpty() || source.hasNext() ) &&
                    // there's available receiver of parts:
                    factory.hasNext() && ( candidate = factory.next() ) != null;
            }

            @Override public Component next()
            {
                if( candidate == null )
                    throw new NoSuchElementException();

                boolean cycling = true;
                for( Iterator<Part> ri = rejected.iterator(); ri.hasNext(); )
                    if( candidate.add( ri.next() ))
                    {
                        ri.remove();
                    }
                    else
                    {
                        cycling = false;
                        break;
                        // despite it might cause infinite cycle, factory could
                        // provide another sort of Component to fix the problem
                    }
                if( cycling )
                    while( source.hasNext() )
                    {
                        Part part = source.next();
                        if( !candidate.add( part ))
                        {
                            if( part != null ) rejected.add( part );
                            break;
                        }
                    }
                if( candidate.valid() )
                    rejected.addAll( 0, candidate.tail() );
                Component rv = candidate;
                candidate = null;
                return rv;
            }

            @Override public void remove()
            {
                throw new UnsupportedOperationException();
            }

            @Override
            protected void finalize() throws Throwable
            {
                rejected.clear();
                super.finalize();
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
