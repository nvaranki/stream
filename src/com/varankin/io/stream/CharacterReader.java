package com.varankin.io.stream;

import java.io.*;

/**
 * {@link java.lang.Iterable} wrapper over character 
 * {@link java.io.Reader}.
 *
 * @author &copy; 2009 Nikolai Varankine
 */
public class CharacterReader extends AtomicReader<Character>
{
    public static Factory<Character> FACTORY = new Factory<Character>() 
    {
        @Override public Character newInstance( char aAtom )
        {
            return new Character( aAtom );
        }
    };
    
    /**
     * @param aInput provider of characters.
     */
    public CharacterReader( Reader aInput )
    {
        super( aInput, FACTORY );
    }
}
