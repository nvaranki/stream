package com.varankin.util;

import static java.util.ResourceBundle.Control.*;
import java.util.ResourceBundle.Control;
import java.util.*;

/**
 * Common localized text storage and formatter.
 *
 * @author &copy; 2011 Nikolai Varankine
 */
public final class LocalizedText
{
    private static final Control CONTROL = getControl( FORMAT_PROPERTIES );
    private static final String PACKAGE_NAME = ".package-text";
    private static final Map<Object, ResourceBundle> cache = new HashMap<Object, ResourceBundle>();

    private LocalizedText() {}
    
    public static Bundle getBundle( Object solicitor )
    {
        return new Bundle( solicitor );
    }
    
    public final static class Bundle
    {
        private final Object solicitor;
        private ResourceBundle rb;
        
        private Bundle( Object solicitor )
        {
            this.solicitor = solicitor;
        }

        public String format( String aKey, Object... aArgs )
        {
            String property = text( aKey );
            return property != null ? String.format( property, aArgs ) : "";
        }

        public String text( String key )
        {
            String format, none = "";
            if( rb == null )
                rb = getResourceBundle( solicitor );
            if( rb == null )
                format = none;
            else
                try { format = rb.getString( key ); }
                catch( NullPointerException ex ) { format = none; } //TODO
                catch( MissingResourceException ex ) { format = none; }//TODO
            return format;
        }

    }

    public static String format( Object solicitor, String aKey, Object... aArgs )
    {
        String property = text( solicitor, aKey );
        return property != null ? String.format( property, aArgs ) : "";
    }

    public static String text( Object solicitor, String key )
    {
        ResourceBundle rb = getResourceBundle( solicitor );
        String format, none = "";
        if( rb == null )
            format = none;
        else
            try { format = rb.getString( key ); }
            catch( NullPointerException ex ) { format = none; } //TODO
            catch( MissingResourceException ex ) { format = none; }//TODO
        return format;
    }
    
    public static ResourceBundle getResourceBundle( Object solicitor )
    {
        if( solicitor instanceof ResourceBundle ) return (ResourceBundle)solicitor;

        ResourceBundle rb = null;
        if( solicitor instanceof String  ) rb = getResourceBundle( (String )solicitor );
        if( solicitor instanceof Class   ) rb = getResourceBundle( (Class  )solicitor );
        if( solicitor instanceof Package ) rb = getResourceBundle( (Package)solicitor );
        if( rb == null ) rb = cache.get( solicitor );
        
        if( rb == null )
            if( solicitor == null ) 
            {
                cache.put( solicitor, rb = null );
            }
            else if( solicitor instanceof String )
            {
                // no way to work-around
            }
            else if( solicitor instanceof Class )
            {
                // try class package
                rb = getResourceBundle( ((Class)solicitor).getPackage() );
                if( rb != null ) cache.put( solicitor, rb );
            }
            else if( solicitor instanceof Package )
            {
                do
                {
                    // try parent package
                    String name = ((Package)solicitor).getName();
                    int last_dot = name.lastIndexOf( '.' );
                    if( last_dot > 0 )
                    {
                        rb = getResourceBundle( Package.getPackage( name.substring( 0, last_dot ) ) );
                        if( rb != null )
                        {
                            cache.put( solicitor, rb );
                            break;
                        }
                    }
                    else
                        break;
                }
                while( true );
            }
            else
            {
                rb = getResourceBundle( solicitor.getClass() );
                if( rb != null ) cache.put( solicitor, rb );
                else
                {
                    rb = getResourceBundle( solicitor.getClass().getPackage() );
                    if( rb != null ) cache.put( solicitor, rb );
                }
            }
        
        return rb;
    }

    public static ResourceBundle getResourceBundle( Package solicitor )
    {
        ResourceBundle rb = cache.get( solicitor );
        if( rb == null )
            try
            {
                cache.put( solicitor, rb = ResourceBundle.getBundle( solicitor.getName() + PACKAGE_NAME, CONTROL ));
            }
            catch( MissingResourceException ex )
            {
                rb = null;
            }
            catch( NullPointerException ex )
            {
                rb = null;
            }
        return rb;
    }

    public static ResourceBundle getResourceBundle( Class solicitor )
    {
        ResourceBundle rb = cache.get( solicitor );
        if( rb == null )
            try
            {
                cache.put( solicitor, rb = ResourceBundle.getBundle( solicitor.getCanonicalName(), CONTROL ));
            }
            catch( MissingResourceException ex )
            {
                rb = null;
            }
            catch( NullPointerException ex )
            {
                rb = null;
            }
        return rb;
    }

    public static ResourceBundle getResourceBundle( String solicitor )
    {
        ResourceBundle rb = cache.get( solicitor );
        if( rb == null )
            try
            {
                cache.put( solicitor, rb = ResourceBundle.getBundle( solicitor, CONTROL ));
            }
            catch( MissingResourceException ex )
            {
                rb = null;
            }
            catch( NullPointerException ex )
            {
                rb = null;
            }
        return rb;
    }

}
