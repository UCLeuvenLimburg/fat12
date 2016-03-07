package arrays;

public class BitArray extends ArrayBase<Boolean>
{
    private final Array<Byte> rawData;

    public BitArray( Array<Byte> rawData )
    {
        this.rawData = rawData;
    }

    public BitArray( byte b )
    {
        this( ConcreteArray.fromBytes( b ) );
    }

    public BitArray( short b )
    {
        this( ConcreteArray.fromBytes( b >> 8, b & 0xFF ) );
    }

    public BitArray( int b )
    {
        this( ConcreteArray.fromBytes( b >> 24, (b >> 16) & 0xFF, (b >> 8) & 0xFF, b & 0xFF ) );
    }

    @Override
    public int length()
    {
        return this.rawData.length() * 8;
    }

    @Override
    public Boolean get(int index)
    {
        int i = index >> 3;
        int mask = 1 << (7 - (index & 7));

        return (this.rawData.get( i ).byteValue() & mask) != 0;
    }
}
