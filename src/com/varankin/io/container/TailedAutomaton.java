/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.varankin.io.container;

/**
 * Abstract state machine with a queue of last elementary objects that
 * should be returned back to provider of objects (so called "tail").
 * 
 * @param <Atom> elementary object described by syntax.
 *
 * @author &copy 2010 Nikolai Varankine
 */
public interface TailedAutomaton<Atom> extends Automaton<Atom>
{
    /**
     * Returns a queue of last elementary objects that should be
     * returned back to provider of objects (so called "tail").
     * 
     * @return current sequence of elementary objects in the tail; any update
     *          to returned list affects the tail.
     */
    java.util.List<Atom> tail();
}
