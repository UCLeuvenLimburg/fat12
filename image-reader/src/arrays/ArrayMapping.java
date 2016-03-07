package arrays;

import java.util.function.Function;

public class ArrayMapping<I, O> extends ArrayBase<O>
{
    private final Array<I> array;

    private final Function<I, O> mappingFunction;

    public ArrayMapping( Array<I> array, Function<I, O> mappingFunction )
    {
        this.array = array;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public int length()
    {
        return this.array.length();
    }

    @Override
    public O get(int index)
    {
        return this.mappingFunction.apply( this.array.get( index ) );
    }
}
