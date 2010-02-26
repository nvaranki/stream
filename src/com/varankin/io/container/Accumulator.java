package com.varankin.io.container;

/**
 * Mutable object to accept another objects of specified type.
 * It contains history of inserted objects, so called "tail".
 *
 * @param Atom elementary object described by syntax.
 *
 * @author &copy; 2009 Nikolai Varankine
 */
public interface Accumulator<Atom> extends Receiver<Atom> {

    /**
     * Adds next elementary object to the end of tail.
     *
     * @param aAtom elementary object to add.
     * @return true if object was added and false otherwise.
     */
    boolean tail( Atom aAtom );

    /**
     * @return current sequence of elementary objects in the tail; any update
     *          to returned list affects the tail of the Receiver.
     */
    java.util.List<Atom> tail();

    /**
     * @return true if Receiver stands in a good shape and false otherwise.
     */
    boolean isValid();

}
