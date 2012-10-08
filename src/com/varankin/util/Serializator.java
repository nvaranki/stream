package com.varankin.util;

import java.io.*;
import java.util.logging.*;

/**
 *
 *
 * @author &copy; 2012 Николай Варанкин
 */
public final class Serializator 
{
    static private final Logger LOGGER = Logger.getLogger( Serializator.class.getName() );

    private Serializator() {}
    
    static public byte[] objectToByteArray( Object объект )
    {
        ByteArrayOutputStream bas = null;
        ObjectOutputStream os = null;
        byte[] value = null;
        if( объект != null )
            try
            {
                bas = new ByteArrayOutputStream();
                os = new ObjectOutputStream( bas );
                os.writeObject( объект );
            }
            catch( IOException ex )
            {
                LOGGER.log( Level.WARNING, "Unable to convert object." );
                LOGGER.log( Level.FINE, "Unable to convert object.", ex );
            }
            finally
            {
                if( os != null ) 
                    try { os.close(); }
                    catch( IOException ex ) { LOGGER.log( Level.FINE, null, ex ); }
                if( bas != null ) 
                    try { value = bas.toByteArray(); bas.close(); }
                    catch( IOException ex ) { LOGGER.log( Level.FINE, null, ex ); }
            }
        
        return value;
    }
    
    static public Object byteArrayToObject( byte[] data )
    {
        ByteArrayInputStream bas = null;
        ObjectInputStream os = null;
        Object object = null;
        if( data != null && data.length > 0 )
            try
            {
                bas = new ByteArrayInputStream( data );
                os = new ObjectInputStream( bas );
                object = os.readObject();
            }
            catch( ClassNotFoundException | IOException ex )
            {
                LOGGER.log( Level.WARNING, "Unable to reconstruct object." );
                LOGGER.log( Level.FINE, "Unable to reconstruct object.", ex );
            }
            finally
            {
                if( os != null ) 
                    try { os.close(); }
                    catch( IOException ex ) { LOGGER.log( Level.FINE, null, ex ); }
                if( bas != null ) 
                    try { bas.close(); }
                    catch( IOException ex ) { LOGGER.log( Level.FINE, null, ex ); }
            }
        return object;
    }
    
}
