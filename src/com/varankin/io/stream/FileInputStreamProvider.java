package com.varankin.io.stream;

import com.varankin.io.container.Provider;
import java.io.*;
import java.util.Objects;

/**
 * Поставщик входных потоков.
 *
 * @author &copy; 2012 Николай Варанкин
 */
public class FileInputStreamProvider implements Provider<InputStream>
{
    private final File file;

    public FileInputStreamProvider( File file )
    {
        this.file = file;
    }

    @Override
    public InputStream newInstance()
    {
        try
        {
            return new FileInputStream( file );
        }
        catch( NullPointerException | FileNotFoundException ex )
        {
            throw new RuntimeException( "Unable to provide input stream.", ex );
        }
    }

    @Override
    public boolean equals( Object o )
    {
        if( o instanceof FileInputStreamProvider )
        {
            FileInputStreamProvider p = (FileInputStreamProvider)o;
            return file == null ? p.file == null : file.equals( p.file );
        }
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode( file );
        return hash;
    }
    
    @Override
    public String toString()
    {
        return file != null ? file.toString() : "";
    }
    
}
