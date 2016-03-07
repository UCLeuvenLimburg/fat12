package arrays;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Array<T> extends Iterable<T>
{
    public int length();

    public T get(int index);

    public default Array<T> slice(int startIndex, int length)
    {
        return new Slice<T>( this, startIndex, length );
    }

    public default Array<T> slice(Range range)
    {
        return new Slice<T>( this, range.from(), range.length() );
    }

    public default boolean all(Predicate<? super T> predicate)
    {
        for ( T x : this )
        {
            if ( !predicate.test( x ) )
            {
                return false;
            }
        }

        return true;
    }

    public default boolean any(Predicate<? super T> predicate)
    {
        for ( T x : this )
        {
            if ( predicate.test( x ) )
            {
                return true;
            }
        }

        return false;
    }

    public default <R> R foldLeft(BiFunction<R, T, R> function, R initial)
    {
        R current = initial;

        for ( T t : this )
        {
            current = function.apply( current, t );
        }

        return current;
    }

    public default Range indexRange()
    {
        return Range.fromLength( 0, this.length() );
    }

    public default boolean sameItems(Array<T> xs)
    {
        if ( xs != null && this.length() == xs.length() )
        {
            return this.indexRange().all( i -> this.get( i ) == null ? xs.get( i ) == null : this.get( i ).equals( xs.get( i ) ) );
        }
        else
        {
            return false;
        }
    }

    public default <R> Array<R> map(Function<T, R> function)
    {
        return new ArrayMapping<T, R>( this, function );
    }

    public default String join(String separator)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for ( T t : this )
        {
            if ( first )
            {
                first = false;
            }
            else
            {
                sb.append( separator );
            }

            sb.append( t.toString() );
        }

        return sb.toString();
    }

    public default boolean isValidIndex(int index)
    {
        return 0 <= index && index < this.length();
    }

    @Override
    public default Iterator<T> iterator()
    {
        return new ArrayIterator<T>( this );
    }

    public default byte[] toByteArray()
    {
        byte[] result = new byte[this.length()];

        for ( int i = 0; i != result.length; ++i )
        {
            result[i] = ((Byte) this.get( i )).byteValue();
        }

        return result;
    }

    public default String asString(String encoding)
    {
        try
        {
            return new String( this.toByteArray(), encoding );
        }
        catch ( UnsupportedEncodingException e )
        {
            throw new RuntimeException( e );
        }
    }

    public default String asString()
    {
        return asString( "UTF-8" );
    }

    public default int asLittleEndianInteger()
    {
        ByteBuffer buffer = ByteBuffer.wrap( this.toByteArray() );
        buffer.order( ByteOrder.LITTLE_ENDIAN );

        switch ( this.length() )
        {
        case 2:
            return buffer.getShort();
        case 4:
            return buffer.getInt();
        default:
            throw new IllegalArgumentException();
        }
    }

    public default int convertBitsToInteger()
    {
        @SuppressWarnings({ "unchecked" })
        Array<Boolean> me = (Array<Boolean>) this;

        int result = 0;

        for ( boolean b : me )
        {
            result <<= 1;
            result |= b ? 1 : 0;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public default Array<Boolean> bits()
    {
        return new BitArray( (Array<Byte>) this );
    }

    public default Array<T> filter(Predicate<? super T> predicate)
    {
        @SuppressWarnings("unchecked")
        T[] selection = (T[]) new Object[this.length()];
        int nextIndex = 0;

        for ( T t : this )
        {
            if ( predicate.test( t ) )
            {
                selection[nextIndex] = t;
                nextIndex++;
            }
        }

        return new ConcreteArray<T>( nextIndex, i -> selection[i] );
    }

    public default void iterate(Consumer<T> consumer)
    {
        for ( T t : this )
        {
            consumer.accept( t );
        }
    }
    
    public default Array<T> concatenate(Array<T> other)
    {
        return Range.fromLength( 0, this.length() + other.length() ).map( i -> {
            if ( i < this.length() )
            {
                return this.get( i );
            }
            else
            {
                return other.get(i - this.length() );
            }
        });
    }
}
