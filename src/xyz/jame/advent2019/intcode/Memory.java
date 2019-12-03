package xyz.jame.advent2019.intcode;

import java.util.*;

// TODO: more checks?
public class Memory
{
    private int[] buf;
    private boolean verbose;

    public Memory( int size )
    {
        buf = new int[size];
    }

    public void setVerbose( boolean v )
    {
        this.verbose = v;
    }

    public int getSize()
    {
        return buf.length;
    }

    // James: don't make this into a method like 'verboseLog'; check if we're verbose each time for performance when not verbose.
    private void log( String msg )
    {
        System.out.println( "[mem] " + msg );
    }

    public int read( int pos )
    {
        if ( verbose )
            log( String.format( "Reading at %d: %d", pos, buf[pos] ) );
        return buf[pos];
    }

    public void write( int pos, int val )
    {
        if ( verbose )
            log( String.format( "Writing %d (prev. %d) to %d", val, buf[pos], pos ) );
        buf[pos] = val;
    }

    public void write( int pos, int[] val )
    {
        if ( verbose )
            log( String.format( "Writing %d bytes to %d", val.length, pos ) );
        System.arraycopy( val, 0, buf, 0, val.length );
    }

    public Intcode.Opcode readOp( int pos ) throws IntcodeException
    {
        int val = read( pos );
        Optional< Intcode.Opcode > op = Intcode.Opcode.from( val );
        if ( !op.isPresent() )
            throw new IntcodeException( String.format( "Expecting valid opcode at position %d, but got '%d'.", pos, val ) );

        return op.get();
    }

    @Override
    public String toString()
    {
        return "Memory{" + "buf=" + Arrays.toString( buf ) + '}';
    }
}
