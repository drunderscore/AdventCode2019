package xyz.jame.advent2019.intcode;

import java.io.*;

public class Tests
{
    public static void doAll()
    {
        testDay2Program();
        testSamplePrograms();
    }

    private static void verifyProgramOutput( Program p, int expected )
    {
        Intcode i = new Intcode( p );
        try
        {
            if ( i.execute() != expected )
                throw new Exception( "Intcode test FAILED!" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }

    private static void testSamplePrograms()
    {
        verifyProgramOutput( new Program( new int[]{ 1, 0, 0, 0, 99 } ), 2 );
        verifyProgramOutput( new Program( new int[]{ 1, 1, 1, 4, 99, 5, 6, 0, 99 } ), 30 );
    }

    private static void testDay2Program()
    {
        try
        {
            Program p = Program.parse( new File( "day2.txt" ) );
            p.setInputs( 12, 2 );
            verifyProgramOutput( p, 6568671 );
        }
        catch ( IntcodeException | IOException e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }
}
