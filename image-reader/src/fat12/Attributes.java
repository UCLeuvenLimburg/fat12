package fat12;

import arrays.*;

public class Attributes
{
    private final Array<Boolean> bits;

    public Attributes( Array<Boolean> bits )
    {
        if ( bits == null || bits.length() != 8 )
        {
            throw new IllegalArgumentException();
        }
        else
        {
            this.bits = bits;
        }
    }

    public boolean readOnly()
    {
        return bit( 0 );
    }

    public boolean hidden()
    {
        return bit( 1 );
    }

    public boolean systemFile()
    {
        return bit( 2 );
    }

    public boolean volumeLabel()
    {
        return bit( 3 );
    }

    public boolean directory()
    {
        return bit( 4 );
    }

    public boolean archive()
    {
        return bit( 5 );
    }

    private boolean bit(int i)
    {
        return this.bits.get( 7 - i );
    }

    public boolean longFileNameIndicator()
    {
        return this.bits.convertBitsToInteger() == 0x0F;
    }

    @Override
    public String toString()
    {
        Array<String> strings = new ConcreteArray<String>( 5, i -> bit( i ) ? label( i ) : null );
        Array<String> nonNullStrings = strings.filter( x -> x != null );

        return nonNullStrings.join( "," );
    }

    private static String label(int i)
    {
        switch ( i )
        {
        case 0:
            return "ReadOnly";
        case 1:
            return "Hidden";
        case 2:
            return "System File";
        case 3:
            return "Volume Label";
        case 4:
            return "Directory";
        case 5:
            return "Archive";
        default:
            throw new IllegalArgumentException();
        }
    }
}
