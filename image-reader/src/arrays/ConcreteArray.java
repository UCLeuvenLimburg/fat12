package arrays;

import java.util.ArrayList;
import java.util.function.Function;

public class ConcreteArray<T> extends ArrayBase<T>
{
    private final T[] array;

    public static Array<Byte> fromBytes(byte... xs)
    {
        return new ByteArray( xs );
    }

    public static ConcreteArray<Byte> fromBytes(int... xs)
    {
        return new ConcreteArray<Byte>( xs.length, i -> (byte) xs[i] );
    }

    public static ConcreteArray<Integer> fromIntegers(int... xs)
    {
        return new ConcreteArray<Integer>( xs.length, i -> xs[i] );
    }

    @SafeVarargs
    public static <T> ConcreteArray<T> fromObjects(T... objects)
    {
        return new ConcreteArray<T>( objects.length, i -> objects[i] );
    }

    public static <T> ConcreteArray<T> fromIterable(Iterable<T> iterable)
    {
        ArrayList<T> items = new ArrayList<T>();

        for ( T t : iterable )
        {
            items.add( t );
        }

        return new ConcreteArray<T>( items.size(), i -> items.get( i ) );
    }
    
    public static <T> ConcreteArray<T> empty()
    {
        return new ConcreteArray<>( 0, null );
    }

    @SuppressWarnings("unchecked")
    public ConcreteArray( int length, Function<Integer, T> initializer )
    {
        this.array = (T[]) new Object[length];

        for ( int i = 0; i != length; ++i )
        {
            this.array[i] = initializer.apply( i );
        }
    }

    @Override
    public int length()
    {
        return this.array.length;
    }

    @Override
    public T get(int index)
    {
        return this.array[index];
    }
}
