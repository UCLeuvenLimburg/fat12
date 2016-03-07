package arrays;

public class Slice<T> extends ArrayBase<T>
{
    private final Array<T> array;

    private final int startIndex;

    private final int length;

    public Slice( Array<T> array, int startIndex, int length )
    {
        this.array = array;
        this.startIndex = startIndex;
        this.length = length;
    }

    @Override
    public int length()
    {
        return this.length;
    }

    @Override
    public T get(int index)
    {
        if ( index < 0 || index >= this.length )
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        else
        {
            return this.array.get( index + this.startIndex );
        }
    }
}
