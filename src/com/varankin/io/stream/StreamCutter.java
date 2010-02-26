package com.varankin.io.stream;

import com.varankin.io.container.*;
import com.varankin.io.stream.*;
import java.util.*;

/**
 * General purpose parser, trying to construct Component's from Part's.
 * Where multiple options found, parser chooses Component with high
 * priority, as indicated by provider of candidate parts. Parser
 * starts consuming part(s) on using its iterator.
 *
 * @author &copy; 2009 Nikolai Varankine
 *
 * @param Component object being constructed.
 * @param Part elementary object used to build component.
 */
public abstract class StreamCutter<Component extends Receiver<Part>, Part> implements Iterable<Component>
{
    private final Iterator<Part> source;
    private Component cached, requested;
    private boolean ended;

    /**
     * @param aSource source of parts to construct component(s).
     */
    public StreamCutter( Iterable<Part> aSource )
    {
        source = new NullTerminator<Part>( aSource ).iterator();
        cached = requested = null;
        ended = aSource == null;
    }

    /**
     * @return ordered sequence of new candidates to fill in, in descending priority.
     *          Null means no more candidates
     */
    protected abstract List<Component> next();
    
    protected List<Component> next( Component component )
    {
        return Collections.singletonList( component );
    }

    private Component read()
    {
        Component created = null;
        List<Component> candidates = null;
        List<Component> passed = new ArrayList<Component>();
        List<Component> failed = new ArrayList<Component>();

        while( source.hasNext() )
        {
            Part part = source.next();

            if( candidates == null )
                if( ( candidates = next() ) == null || candidates.isEmpty() )
                    break;
                else
                    candidates = new ArrayList<Component>( candidates ); // editable list
            passed.clear();
            failed.clear();

            for( Component candidate : candidates )
                if( candidate.add( part ) )
                    passed.add( candidate );
                else
                    failed.add( candidate );

            if( passed.isEmpty() )
            {
                // the word is wrong, candidate with high priority is lucky one to live
                candidates.clear();
                if( ! failed.isEmpty() ) candidates.add( failed.get( 0 ) );
                break;
            }
            else
            {
                // reply notification that at least one document accepted the word
                //TODO it.remove();
                // winners continue
                candidates.clear();
                candidates.addAll( passed );
            }
        }

        if( candidates != null )
        {
            // candidate with high priority is lucky one to live
            created = candidates.get( 0 );
            candidates.clear();
        }

        passed.clear();
        failed.clear();

        return created;
    }

    @Override
    public Iterator<Component> iterator()
    {
        cached = requested;
        return new Iterator<Component>()
        {
            @Override
            public boolean hasNext()
            {
                if( cached == null && !ended )
                    ended = ( cached = read() ) == null;
                return cached != null;
            }

            @Override
            public Component next()
            {
                if( hasNext() )
                {
                    requested = cached;
                    cached = null;
                    return requested;
                }
                else
                    throw new NoSuchElementException();
            }

            @Override
            public void remove()
            {
                if( requested != null )
                    requested = null;
                else
                    throw new IllegalStateException();
            }

        };
    }
}
