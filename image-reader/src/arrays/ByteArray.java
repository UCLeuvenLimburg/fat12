package arrays;

public class ByteArray extends ArrayBase<Byte>
{
    private final byte[] array;

    public ByteArray( byte... array )
    {
        this.array = array;
    }

    @Override
    public int length()
    {
        return this.array.length;
    }

    @Override
    public Byte get(int index)
    {
        return this.array[index];
    }

    // public byte[] toByteArray()
    // {
    // byte[] result = new byte[this.length()];
    //
    // for ( int i = 0; i != result.length; ++i )
    // {
    // result[i] = this.get( i ).byteValue();
    // }
    //
    // return result;
    // }
    //
    // public String convertToString( String encoding )
    // {
    // try
    // {
    // return new String( this.toByteArray(), "encoding" );
    // }
    // catch ( Exception e )
    // {
    // throw new RuntimeException( e );
    // }
    // }
    //
    // public String asString()
    // {
    // return convertToString( "UTF-8" );
    // }
    //
    // public int asLittleEndianInteger()
    // {
    // ByteBuffer buffer = ByteBuffer.wrap( this.toByteArray() );
    // buffer.order( ByteOrder.LITTLE_ENDIAN );
    //
    // switch ( this.length() )
    // {
    // case 2:
    // return buffer.getShort();
    // case 4:
    // return buffer.getInt();
    // default:
    // throw new IllegalArgumentException();
    // }
    // }
    //
    // public IArray<Boolean> bits()
    // {
    // return new BitArray( this );
    // }
}
