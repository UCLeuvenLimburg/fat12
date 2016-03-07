package arrays;

public class Range extends ArrayBase<Integer>
{
    private final int from;

    private final int length;

    public static Range fromUntilInclusive(int from, int to)
    {
        return new Range( from, to - from + 1 );
    }

    public static Range fromUntilExclusive(int from, int to)
    {
        return new Range( from, to - from );
    }

    public static Range fromLength(int from, int length)
    {
        return new Range( from, length );
    }

    private Range( int from, int length )
    {
        this.from = from;
        this.length = length;
    }
    
    public int from()
    {
        return from;
    }
    
    public int to()
    {
        return from + length;
    }

    @Override
    public int length()
    {
        return this.length;
    }

    @Override
    public Integer get(int index)
    {
        if ( isValidIndex( index ) )
        {
            return this.from + index;
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
