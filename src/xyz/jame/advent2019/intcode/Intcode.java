package xyz.jame.advent2019.intcode;

import java.util.*;
import java.util.stream.*;

public class Intcode
{
    private static final int DEFAULT_MEM_SIZE = 4096;

    // Tests! (don't break the computer)
    static
    {
        Tests.doAll();
    }

    private Program program;
    private Memory mem;
    private int pc;
    private boolean halt;
    private boolean verbose;

    public Intcode( Program program )
    {
        this.program = program;
        this.mem = new Memory( Intcode.DEFAULT_MEM_SIZE );
        prepare();
    }

    public Intcode( Program program, int memSize )
    {
        this.program = program;
        this.mem = new Memory( memSize );
        prepare();
    }

    private void prepare()
    {
        // copy program into memory
        mem.write( 0, program.getData() );
    }

    private void log( String msg )
    {
        System.out.println( "[computer] " + msg );
    }

    public int execute() throws IntcodeException
    {
        while ( !halt )
        {
            Opcode c = mem.readOp( pc );
            if ( verbose )
                log( String.format( "PC: %d, opcode: %s", pc, c.name() ) );
            switch ( c )
            {
                case Halt:
                    halt = true;
                    continue;
                case Add:
                {
                    int a = mem.read( mem.read( pc + 1 ) );
                    int b = mem.read( mem.read( pc + 2 ) );
                    mem.write( mem.read( pc + 3 ), a + b );
                    break;
                }
                case Mult:
                {
                    int a = mem.read( mem.read( pc + 1 ) );
                    int b = mem.read( mem.read( pc + 2 ) );
                    mem.write( mem.read( pc + 3 ), a * b );
                    break;
                }
            }

            pc += c.operandCount + 1;
        }
        return mem.read( 0 );
    }

    public void execute( int pc ) throws IntcodeException
    {
        this.pc = pc;
        execute();
    }

    //    public Memory getMemory()
    //    {
    //        return mem;
    //    }

    public void setVerbose( boolean v )
    {
        this.verbose = v;
    }

    enum Opcode
    {
        Add( 1, 3 ), Mult( 2, 3 ), Halt( 99, 0 );

        private static Opcode[] all;

        static
        {
            all = Opcode.values();
        }

        private final int operandCount;
        private final int code;

        Opcode( int code, int operandCount )
        {
            this.code = code;
            this.operandCount = operandCount;
        }

        public static Optional< Opcode > from( int op )
        {
            return Stream.of( all ).filter( o -> o.code == op ).findFirst();
        }

        public static Optional< Opcode > from( String name )
        {
            return Stream.of( all ).filter( o -> o.name().equals( name ) ).findFirst();
        }

        public int getCode()
        {
            return code;
        }

        public int getOperandCount()
        {
            return operandCount;
        }
    }
}
