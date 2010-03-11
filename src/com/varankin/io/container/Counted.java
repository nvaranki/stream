package com.varankin.io.container;

/**
 *
 * @author &copy; 2010 Nikolai Varankine
 */
@Deprecated
public interface Counted<Atom> {

//    void value( Atom aAtom );
//    Atom value();
    long[] counter();
}
