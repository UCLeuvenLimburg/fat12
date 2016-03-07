package arrays;

public class ArrayFunctions
{
    public static <T> Array<T> flatten(Array<Array<? extends T>> arrays)
    {
        return new ArrayFlattening<>( arrays );
    }

    public static <T> String hex(Array<T> ns)
    {
        return ns.map( b -> String.format( "%02X", b ) ).join( " " );
    }
    
    public static <T> String dec(Array<T> ns)
    {
        return ns.map( b -> String.format( "%02d", b ) ).join( " " );
    }
    
    public static <T> String bits(Array<Boolean> bs)
    {
        return bs.map( b -> b ? "1" : "0" ).join( " " );
    }
}
