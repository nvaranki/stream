package com.varankin.io.stream;

import java.util.*;

/**
 * Iterable passthrough object that wraps incoming objects into 
 * container marked with structured location. Indices of location
 * are limited by value of {@link java.lang.Long#MAX_VALUE}.
 * 
 * @author &copy; 2010 Nikolai Varankine
 */
public final class Marker<T> implements Iterable<Marker.Box<T>>
{
    private final Iterator<T> source;
    private final Iterator<Marker.Box<T>> factory;
    private final T[] breaker;
    private final long[] counter;

    /**
     * @param aSource   provider of incoming objects.
     * @param aBreakers patterns of incoming objects to form ranks of location counters;
     *          when incoming object equals some pattern, it increases correspondent
     *          counter and resets all lowest counters to zero.
     */
    public Marker( Iterable<T> aSource, T... aBreakers )
    {
        source = aSource != null ? aSource.iterator() : Collections.<T>emptyList().iterator();
        breaker = aBreakers;
        counter = new long[1 + aBreakers.length];
        factory = new Iterator<Marker.Box<T>>() {

            @Override public boolean hasNext()
            {
                return source.hasNext();
            }

            @Override public Marker.Box<T> next()
            {
                T unit = source.next();
                // wrap object using current location
                long[] cc = new long[counter.length];
                System.arraycopy( counter, 0, cc, 0, counter.length );
                Marker.Box<T> box = new Box<T>( unit, cc );
                // increment lowest counter
                if( ++counter[0] == Long.MAX_VALUE ) counter[0] = 0L;
                // check for break
                for( int i = 0; i < breaker.length; i++ )
                    if( unit.equals( breaker[i] ))
                    {
                        final int nc = 1 + i;
                        ++counter[nc];
                        for( int j = 0; j < nc; j++ )
                            counter[j] = 0L;
                        break;
                    }
                return box;
            }

            @Override public void remove()
            {
                source.remove();
            }
        };
    }

    @Override
    public Iterator<Marker.Box<T>> iterator()
    {
        return factory;
    }

    /**
     * Container to wrap incoming object.
     * 
     * @param Unit class of incoming object.
     */
    public static final class Box<Unit>
    {
        /** Original object. */
        public final Unit unit;
        /** Structured location comprized of zero based indices. */
        public final long[] location;
        
        private Box( Unit aUnit, long[] aCounters )
        {
            unit = aUnit;
            location = aCounters;
        }

        public Box( Unit aUnit )
        {
            unit = aUnit;
            location = new long[0];
        }

        @Override public int hashCode()
        {
            return unit.hashCode();
        }
        
        @Override public boolean equals( Object aBox )
        {
            return aBox instanceof Box && unit.equals(((Box)aBox).unit);
        }
    }

}
