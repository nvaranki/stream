/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
    int IRRELEVANT = 0;
    int IDENTICAL = 1;

    /**
     * <p>Returns identity level of received elementary objects with expectation:
     * <ul type=circle>
     * <li>0 - objects do not meet expectation.</li>
     * <li>1 - objects meet expectation exactly.</li>
     * <li>any value > 1 - objects meet expectation with some sense of abstraction;
     *              the bigger value the less identity observed.</li>
     * </ul></p>
     * <p>Return value is not guaranteed to be true until {@link #add(java.lang.Object)} returns false.</p>
     *
     *
     * @return level of identity as non-negative number.
     */
    int identity();
}
