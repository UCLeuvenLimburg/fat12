package fat12;

import arrays.*;

public class Date
{
    private final Array<Boolean> rawData;

    public Date( int rawData )
    {
        this.rawData = new BitArray( (short) rawData );
    }

    public int year()
    {
        return this.rawData.slice( 0, 7 ).convertBitsToInteger() + 1980;
    }

    public int month()
    {
        return this.rawData.slice( 7, 4 ).convertBitsToInteger();
    }

    public int day()
    {
        return this.rawData.slice( 11, 5 ).convertBitsToInteger();
    }
    
    public Array<Boolean> bits()
    {
        return rawData;
    }

    @Override
    public String toString()
    {
        return String.format( "%02d/%02d/%04d", day(), month(), year() );
    }

    @Override
    public boolean equals(Object object)
    {
        if ( object instanceof Date )
        {
            Date that = (Date) object;

            return this.year() == that.year() && this.month() == that.month() && this.day() == that.day();
        }
        else
        {
            return false;
        }
    }
}
