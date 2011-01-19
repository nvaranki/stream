package com.varankin.io.stream;

import java.io.*;

/**
 * {@link java.lang.Iterable} wrapper over character 
 * {@link java.io.Reader}.
 *
 * @author &copy; 2011 Nikolai Varankine
 */
public class CharacterReader extends AtomicReader<Character>
{
    /**
     * @param aInput provider of characters.
     * @param aUnique true to provide unique objects, false to supply shared copies.
     */
    public CharacterReader( Reader aInput, boolean aUnique )
    {
        super( aInput, new CharacterFactory( aUnique ) );
    }
    
    private static class CharacterFactory implements Factory<Character>
    {
        private final boolean unique;

        private CharacterFactory( boolean aUnique )
        {
            unique = aUnique;
        }

        @Override
        public Character newInstance( char aAtom )
        {
            // chars of Basic Multilingual Plane only
            return unique ? new Character( aAtom ) : Character.valueOf( aAtom );
        }
    }
}
