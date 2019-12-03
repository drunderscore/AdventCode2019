package xyz.jame.advent2019.intcode;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Program
{
    private int[] data;

    public Program( int[] data )
    {
        this.data = data;
    }

    public static Program parse( String input ) throws IntcodeException
    {
        try
        {
            return new Program( Stream.of( input.split( "," ) ).mapToInt( s -> Integer.parseInt( s.trim() ) ).toArray() );
        }
        catch ( NumberFormatException e )
        {
            throw new IntcodeException( String.format( "Invalid Intcode program supplied to parse. (%s)", e.getMessage() ) );
        }
    }

    public static Program parse( File input ) throws IntcodeException, IOException
    {
        try
        {
            return new Program( Stream.of( new String( Files.readAllBytes( input.toPath() ) ).split( "," ) ).mapToInt( s -> Integer.parseInt( s.trim() ) ).toArray() );
        }
        catch ( NumberFormatException e )
        {
            throw new IntcodeException( String.format( "Invalid Intcode program supplied to parse. (%s)", e.getMessage() ) );
        }
    }

    public String toEnglish() throws IntcodeException
    {
        StringBuilder builder = new StringBuilder( "" );
        int pc = 0;
        while ( pc != data.length )
        {
            Optional< Intcode.Opcode > op = Intcode.Opcode.from( data[pc] );
            if ( !op.isPresent() )
            {
                builder.append( data[pc] ).append( "\n" );
                continue;
            }

            builder.append( op.get().name() );

            if ( op.get().getOperandCount() > 0 )
            {
                builder.append( " " );
                for ( int i = 1; i <= op.get().getOperandCount(); i++ )
                {
                    builder.append( "[" ).append( data[pc + i] ).append( "]" );
                    if ( i != op.get().getOperandCount() )
                        builder.append( ", " );
                }
            }

            builder.append( "\n" );

            pc += op.get().getOperandCount() + 1;
        }

        return builder.toString();
    }

    public void setInputs( int noun, int verb )
    {
        data[1] = noun;
        data[2] = verb;
    }

    public int[] getData()
    {
        return data;
    }
}
