package com.varankin.io.container;

/**
 * Abstract state machine.
 * 
 * @param <Atom> elementary object described by syntax.
 *
 * @author &copy 2010 Nikolai Varankine
 */
public interface Automaton<Atom> extends Receiver<Atom>
{
    /** Identity value to denote no match between objects. */
    int IRRELEVANT = 0;
    /** Identity value to denote exact match between objects. */
    int IDENTICAL = 1;

    /**
     * <p>Returns identity level of received elementary objects with expectation:
     * <ul type=circle>
     * <li>{@linkplain #IRRELEVANT IRRELEVANT} - objects do not meet expectation.</li>
     * <li>{@linkplain #IDENTICAL IDENTICAL} - objects meet expectation exactly.</li>
     * <li>any value > {@linkplain #IDENTICAL IDENTICAL} - objects meet expectation with some sense of abstraction;
     *              the bigger the value the less identity is observed. Same values returned on different
     *              instances of Automaton does not necessarily mean equal grade of identity.</li>
     * </ul></p>
     * <p>Return value is not guaranteed to be true until {@link #add(java.lang.Object)} returns false.</p>
     *
     *
     * @return level of identity as nonnegative integer.
     */
    int identity();
}
