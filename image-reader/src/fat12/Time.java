package fat12;

import arrays.*;

public class Time
{
    private final Array<Boolean> rawData;

    public Time( int rawData )
    {
        this.rawData = new BitArray( (short) rawData );
    }

    public int hours()
    {
        return this.rawData.slice( 0, 5 ).convertBitsToInteger();
    }

    public int minutes()
    {
        return this.rawData.slice( 5, 6 ).convertBitsToInteger();
    }

    public int seconds()
    {
        return this.rawData.slice( 11, 5 ).convertBitsToInteger() * 2;
    }
    
    public Array<Boolean> bits()
    {
        return rawData;
    }

    @Override
    public String toString()
    {
        return String.format( "%02d:%02d:%02d", hours(), minutes(), seconds() );
    }

    @Override
    public boolean equals(Object object)
    {
        if ( object instanceof Time )
        {
            Time that = (Time) object;

            return this.hours() == that.hours() && this.minutes() == that.minutes() && this.seconds() == that.seconds();
        }
        else
        {
            return false;
        }
    }
}
