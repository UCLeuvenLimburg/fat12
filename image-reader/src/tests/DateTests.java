package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fat12.Date;

public class DateTests
{
    @Test
    public void test()
    {
        checkDate( 0x335A, 2005, 10, 26 );
    }

    private void checkDate(int bits, int year, int month, int day)
    {
        Date date = new Date( (short) bits );

        assertEquals( year, date.year() );
        assertEquals( month, date.month() );
        assertEquals( day, date.day() );
    }
}
