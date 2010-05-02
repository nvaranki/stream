package com.varankin.io.container;

/**
 * Mutable object to accept another objects of specified type.
 *
 * @param <Atom> elementary object described by syntax.
 *
 * @author &copy; 2010 Nikolai Varankine
 */
public interface Receiver<Atom> {

    /**
     * A procedure to accept object.
     *
     * @param aPart a candidate object to accept or null to indicate break in delivery.
     * @return true if candidate has been accepted and false if it was rejected.
     */
    boolean add( Atom aPart );
}
