package fat12;

import arrays.Array;

public class DirectoryEntry
{
    public static final int SIZE_IN_BYTES = 32;

    private final Array<Byte> rawBytes;

    public DirectoryEntry( Array<Byte> rawBytes )
    {
        this.rawBytes = rawBytes;
    }

    @Field(name = "Name")
    public String name()
    {
        return this.rawBytes.slice( 0, 8 ).asString();
    }

    @Field(name = "Extension")
    public String extension()
    {
        return this.rawBytes.slice( 8, 3 ).asString();
    }

    public String fullName()
    {
        if ( extension().trim().length() > 0 )
        {
            return name().trim() + "." + extension().trim();
        }
        else
        {
            return name().trim();
        }
    }

    @Field(name = "Attributes")
    public Attributes attributes()
    {
        return new Attributes( this.rawBytes.slice( 0x0B, 1 ).bits() );
    }

    @Field(name = "Last Write Time")
    public Time lastWriteTime()
    {
        return new Time( this.rawBytes.slice( 0x16, 2 ).asLittleEndianInteger() );
    }

    @Field(name = "Last Write Date")
    public Date lastWriteDate()
    {
        return new Date( this.rawBytes.slice( 0x18, 2 ).asLittleEndianInteger() );
    }

    @Field(name = "First Logical Cluster")
    public int firstLogicalCluster()
    {
        return this.rawBytes.slice( 0x1A, 2 ).asLittleEndianInteger();
    }

    @Field(name = "File Size")
    public int fileSize()
    {
        return this.rawBytes.slice( 0x1C, 4 ).asLittleEndianInteger();
    }

    public boolean longFileName()
    {
        return attributes().longFileNameIndicator();
    }
    
    public Array<Byte> rawBytes()
    {
        return this.rawBytes;
    }

    @Override
    public String toString()
    {
        return String.format( "%s.%s", this.name(), this.extension() );
    }
}
