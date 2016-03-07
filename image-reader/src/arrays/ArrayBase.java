package arrays;

public abstract class ArrayBase<T> implements Array<T>
{
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object)
    {
        if ( object instanceof Array )
        {
            return this.sameItems( (Array<T>) object );
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return this.map( x -> x.hashCode() ).foldLeft( (acc, x) -> acc ^ x, 0 );
    }

    @Override
    public String toString()
    {
        return "[" + this.join( "," ) + "]";
    }
}
