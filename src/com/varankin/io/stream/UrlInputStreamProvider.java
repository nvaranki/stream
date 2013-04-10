package com.varankin.io.stream;

import com.varankin.io.container.Provider;
import java.io.*;
import java.net.*;
import java.util.Objects;

/**
 * Поставщик входных потоков.
 *
 * @author &copy; 2013 Николай Варанкин
 */
public class UrlInputStreamProvider implements Provider<InputStream>, Serializable
{
    private final URL url;

    public UrlInputStreamProvider( URL url )
    {
        this.url = url;
    }

    @Override
    public InputStream newInstance()
    {
        try
        {
            //URLConnection connection = url.openConnection();
            //connection.setRequestProperty( "Accept", "text/xml" );
            return url.openStream(); //connection.getInputStream();
        } 
        catch( IOException ex )
        {
            throw new RuntimeException( "Unable to provide input stream.", ex );
        }
    }

    @Override
    public boolean equals( Object o )
    {
        if( o instanceof UrlInputStreamProvider )
        {
            UrlInputStreamProvider p = (UrlInputStreamProvider)o;
            return url == null ? p.url == null : url.equals( p.url );
        }
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 8;
        hash = 68 * hash + Objects.hashCode( url );
        return hash;
    }
    
    @Override
    public String toString()
    {
        return url != null ? url.toString() : "";
    }
    
}
