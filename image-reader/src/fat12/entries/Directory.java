package fat12.entries;

import arrays.Array;
import arrays.ConcreteArray;
import fat12.DirectoryEntry;
import fat12.DiskImage;

public class Directory extends Entry
{
    private final Array<Byte> rawData;

    private final Array<DirectoryEntry> entries;

    public static Directory createRootDirectory(DiskImage diskImage, Array<Byte> rawData)
    {
        return new Directory( ".", diskImage, null, rawData );
    }

    public static Directory fromDirectoryEntry(String path, DiskImage diskImage, DirectoryEntry entry)
    {
        Array<Byte> bytes = diskImage.chainClusters( entry.firstLogicalCluster() );

        return new Directory( path, diskImage, entry, bytes );
    }

    private Directory( String path, DiskImage diskImage, DirectoryEntry entry, Array<Byte> rawData )
    {
        super( path, diskImage, entry );

        this.rawData = rawData;
        this.entries = findEntries( this.rawData );
    }

    private static Array<DirectoryEntry> findEntries(Array<Byte> rawData)
    {
        int entryCount = rawData.length() / DirectoryEntry.SIZE_IN_BYTES;
        DirectoryEntry[] entries = new DirectoryEntry[entryCount];

        for ( int i = 0; i != entryCount; ++i )
        {
            int firstByte = rawData.get( i * DirectoryEntry.SIZE_IN_BYTES ) & 0xFF;

            if ( firstByte == 0 )
            {
                break;
            }
            else if ( firstByte == 0xE5 )
            {
                continue;
            }
            else
            {
                DirectoryEntry entry = new DirectoryEntry( rawData.slice( i * DirectoryEntry.SIZE_IN_BYTES, DirectoryEntry.SIZE_IN_BYTES ) );

                if ( entry.longFileName() )
                {
                    continue;
                }
                else
                {
                    entries[i] = entry;
                }
            }
        }

        return ConcreteArray.fromObjects( entries ).filter( x -> x != null );
    }

    public Array<DirectoryEntry> subdirectoryEntries()
    {
        return this.entries.filter( entry -> entry.attributes().directory() );
    }

    public Array<Directory> subdirectories()
    {
        return subdirectoryEntries().map( entry -> Directory.fromDirectoryEntry( path + "/" + entry.fullName(), diskImage, entry ) );
    }

    public Array<DirectoryEntry> fileEntries()
    {
        return this.entries.filter( entry -> !(entry.attributes().directory() || entry.attributes().volumeLabel()) );
    }

    public Array<File> files()
    {
        return subdirectoryEntries().map( entry -> File.fromDirectoryEntry( path + "/" + entry.fullName(), diskImage, entry ) );
    }

    @Override
    public Array<Entry> children()
    {
        return this.entries.filter( entry -> !isRelativeReference( entry ) ).map( entry -> Entry.fromDirectoryEntry( path + "/" + entry.fullName(), diskImage, entry ) );
    }

    private boolean isRelativeReference(DirectoryEntry entry)
    {
        return entry.name().trim().equals( "." ) || entry.name().trim().equals( ".." );
    }

    @Override
    public <T> T accept(EntryVisitor<T> visitor)
    {
        return visitor.visit( this );
    }
}
