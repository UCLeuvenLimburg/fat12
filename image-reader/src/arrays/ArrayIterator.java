package arrays;

import java.util.Iterator;

class ArrayIterator<T> implements Iterator<T>
{
    private final Array<T> array;

    private int index;

    public ArrayIterator( Array<T> array )
    {
        this.array = array;
        this.index = 0;
    }

    @Override
    public boolean hasNext()
    {
        return this.index < this.array.length();
    }

    @Override
    public T next()
    {
        return this.array.get( this.index++ );
    }

}