package xyz.jame.advent2019.day2;

import java.io.*;
import xyz.jame.advent2019.intcode.*;

public class Day2
{
    public static void main( String[] args )
    {
        try
        {
            Program p = Program.parse( new File( "day2.txt" ) );
            p.setInputs( 12, 2 );
            Intcode code = new Intcode( p );
            System.out.println( "Part 1: " + code.execute() );

            for ( int x = 0; x < 100; x++ )
                for ( int y = 0; y < 100; y++ )
                {
                    p.setInputs( x, y );
                    Intcode brute = new Intcode( p );
                    if ( brute.execute() == 19690720 )
                    {
                        System.out.println( String.format( "Part 2: noun is %d, verb is %d, solution is %d", x, y, 100 * x + y ) );
                    }
                }
        }
        catch ( IntcodeException | IOException e )
        {
            e.printStackTrace();
        }
    }
}
