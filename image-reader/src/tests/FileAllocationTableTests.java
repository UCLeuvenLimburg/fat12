package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import arrays.*;
import fat12.FileAllocationTable;

public class FileAllocationTableTests
{
    @Test
    public void indexing1()
    {
        checkIndexing( ConcreteArray.fromBytes( 0x12, 0x34, 0x45 ), ConcreteArray.fromIntegers( 0x412, 0x453 ) );
    }

    @Test
    public void indexing2()
    {
        checkIndexing( ConcreteArray.fromBytes( 0x67, 0x89, 0xAB ), ConcreteArray.fromIntegers( 0x967, 0xAB8 ) );
    }

    @Test
    public void indexing3()
    {
        checkIndexing( ConcreteArray.fromBytes( 0x12, 0x34, 0x45, 0x67, 0x89, 0xAB ), ConcreteArray.fromIntegers( 0x412, 0x453, 0x967, 0xAB8 ) );
    }

    private void checkIndexing(Array<Byte> bs, Array<Integer> ns)
    {
        FileAllocationTable fat = new FileAllocationTable( bs );

        for ( int i = 0; i != ns.length(); ++i )
        {
            assertEquals( String.format( "Item at index %d was expected to be 0x%x but was 0x%x", i, ns.get( i ), fat.get( i ) ), ns.get( i ), fat.get( i ) );
        }
    }
}
