package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fat12.Time;

public class TimeTests
{
    @Test
    public void test()
    {
        checkTime( 0x1B85, 3, 28, 10 );
    }

    private void checkTime(int bits, int hours, int minutes, int seconds)
    {
        Time time = new Time( (short) bits );

        assertEquals( hours, time.hours() );
        assertEquals( minutes, time.minutes() );
        assertEquals( seconds, time.seconds() );
    }
}
