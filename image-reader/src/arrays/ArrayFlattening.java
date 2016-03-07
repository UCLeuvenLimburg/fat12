package arrays;

import java.util.Iterator;

public class ArrayFlattening<T> extends ArrayBase<T>
{
    private final Array<Array<? extends T>> arrays;

    public ArrayFlattening( Array<Array<? extends T>> arrays )
    {
        this.arrays = arrays;
    }

    @Override
    public int length()
    {
        return this.arrays.map( x -> x.length() ).foldLeft( (acc, x) -> acc + x, 0 );
    }

    @Override
    public T get(int index)
    {
        int arrayIndex = 0;

        while ( index >= this.arrays.get( arrayIndex ).length() )
        {
            index -= this.arrays.get( arrayIndex ).length();
            arrayIndex++;
        }

        return this.arrays.get( arrayIndex ).get( index );
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayFlatteningIterator();
    }

    private class ArrayFlatteningIterator implements Iterator<T>
    {
        private int currentArrayIndex;

        private int currentIndex;

        public ArrayFlatteningIterator()
        {
            this.currentArrayIndex = 0;
            this.currentIndex = 0;

            moveToNextValidPosition();
        }

        private void moveToNextValidPosition()
        {
            while ( this.currentArrayIndex < ArrayFlattening.this.arrays.length() && this.currentIndex >= ArrayFlattening.this.arrays.get( this.currentArrayIndex ).length() )
            {
                this.currentArrayIndex++;
                this.currentIndex = 0;
            }
        }

        @Override
        public boolean hasNext()
        {
            return this.currentArrayIndex < ArrayFlattening.this.arrays.length();
        }

        @Override
        public T next()
        {
            T value = ArrayFlattening.this.arrays.get( this.currentArrayIndex ).get( this.currentIndex );

            this.currentIndex++;
            moveToNextValidPosition();

            return value;
        }
    }
}
