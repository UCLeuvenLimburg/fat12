package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import arrays.*;

public class BitArrayTests
{
    @Test
    public void testByte0()
    {
        BitArray bs = new BitArray( (byte) 0 );

        assertEquals( 8, bs.length() );
        assertEquals( 0, bs.convertBitsToInteger() );
        assertTrue( bs.all( b -> !b ) );
    }

    @Test
    public void testByte255()
    {
        BitArray bs = new BitArray( (byte) 0xFF );

        assertEquals( 8, bs.length() );
        assertEquals( 0xFF, bs.convertBitsToInteger() );
        assertTrue( bs.all( b -> b ) );
    }

    @Test
    public void testShort0()
    {
        BitArray bs = new BitArray( (short) 0x0000 );

        assertEquals( 16, bs.length() );
        assertEquals( 0x0000, bs.convertBitsToInteger() );
        assertTrue( bs.all( b -> !b ) );
    }

    @Test
    public void testShort65535()
    {
        BitArray bs = new BitArray( (short) 0xFFFF );

        assertEquals( 16, bs.length() );
        assertEquals( 0xFFFF, bs.convertBitsToInteger() );
        assertTrue( bs.all( b -> b ) );
    }

    @Test
    public void testShort256()
    {
        BitArray bs = new BitArray( (short) 0x00FF );

        assertEquals( 16, bs.length() );
        assertEquals( 0x00FF, bs.convertBitsToInteger() );
        assertTrue( bs.slice( 0, 8 ).all( b -> !b ) );
        assertTrue( bs.slice( 8, 8 ).all( b -> b ) );
    }

    @Test
    public void testInt0()
    {
        BitArray bs = new BitArray( 0x0000 );

        assertEquals( 32, bs.length() );
        assertEquals( 0x0000, bs.convertBitsToInteger() );
        assertTrue( bs.all( b -> !b ) );
    }

    @Test
    public void testInt65535()
    {
        BitArray bs = new BitArray( 0xFFFF );

        assertEquals( 32, bs.length() );
        assertEquals( 0xFFFF, bs.convertBitsToInteger() );
        assertTrue( bs.slice( 0, 16 ).all( b -> !b ) );
        assertTrue( bs.slice( 16, 16 ).all( b -> b ) );
    }

    @Test
    public void testInt256()
    {
        BitArray bs = new BitArray( 0x00FF );

        assertEquals( 32, bs.length() );
        assertEquals( 0x00FF, bs.convertBitsToInteger() );
        assertTrue( bs.slice( 0, 24 ).all( b -> !b ) );
        assertTrue( bs.slice( 24, 8 ).all( b -> b ) );
    }

    @Test
    public void testIntMax()
    {
        BitArray bs = new BitArray( 0xFFFFFFFF );

        assertEquals( 32, bs.length() );
        assertEquals( 0xFFFFFFFF, bs.convertBitsToInteger() );
        assertTrue( bs.all( b -> b ) );
    }

    @Test
    public void testShort0x335A()
    {
        BitArray bs = new BitArray( (short) 0x335A );

        assertEquals( 16, bs.length() );
        assertEquals( 0x335A, bs.convertBitsToInteger() );
        assertEquals( ConcreteArray.fromIntegers( 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0 ).map( x -> x == 1 ), bs );
    }

    @Test
    public void testShort0x0001()
    {
        BitArray bs = new BitArray( (short) 0x0001 );

        assertEquals( 16, bs.length() );
        assertEquals( 0x0001, bs.convertBitsToInteger() );
        assertEquals( ConcreteArray.fromIntegers( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ).map( x -> x == 1 ), bs );
    }
}
