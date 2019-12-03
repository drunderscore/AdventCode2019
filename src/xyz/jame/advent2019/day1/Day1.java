package xyz.jame.advent2019.day1;

import java.io.*;
import java.nio.file.*;
import java.util.*;

// https://adventofcode.com/2019/day/1
public class Day1
{
    public static final File INPUT = new File( "day1.txt" );

    public static int getRequiredFuelForMass( int mass )
    {
        if ( mass <= 0 )
            return 0;

        return Math.max( Math.floorDiv( mass, 3 ) - 2, 0 );
    }

    public static int getRealRequiredFuelForMass( int mass )
    {
        int next = mass;
        int total = 0;
        while ( ( next = getRequiredFuelForMass( next ) ) != 0 )
            total += next;

        return total;
    }

    public static void findTotalFuel()
    {
        int total = 0;
        try
        {
            List< String > input = Files.readAllLines( INPUT.toPath() );
            for ( String s : input )
                try
                {
                    total += getRequiredFuelForMass( Integer.parseInt( s ) );
                }
                catch ( NumberFormatException e )
                {
                    System.out.println( "Couldn't parse number from input somehow? (" + s + ")" );
                }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        System.out.println( "Total required fuel: " + total );
    }

    public static void findRealTotalFuel()
    {
        int realTotal = 0;
        try
        {
            List< String > input = Files.readAllLines( INPUT.toPath() );
            for ( String s : input )
            {
                try
                {
                    int mass = Integer.parseInt( s );
                    realTotal += getRealRequiredFuelForMass( mass );
                }
                catch ( NumberFormatException e )
                {
                    System.out.println( "Couldn't parse number from input somehow? (" + s + ")" );
                }
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        System.out.println( "Total required fuel: " + realTotal );
    }

    public static void main( String[] args )
    {
        findTotalFuel();
        findRealTotalFuel();
    }
}
