package fat12.entries;

import fat12.DirectoryEntry;
import fat12.DiskImage;
import arrays.Array;
import arrays.ConcreteArray;

public class File extends Entry
{
    public static File fromDirectoryEntry(String path, DiskImage diskImage, DirectoryEntry entry)
    {
        return new File( path, diskImage, entry );
    }

    private File( String path, DiskImage diskImage, DirectoryEntry entry )
    {
        super( path, diskImage, entry );
    }

    public Array<Byte> contents()
    {
        Array<Byte> bytes = diskImage.chainClusters( entry.firstLogicalCluster() );

        return bytes.slice( 0, entry.fileSize() );
    }
    
    @Override
    public Array<Entry> children()
    {
        return ConcreteArray.<Entry>empty();
    }
    
    @Override
    public <T> T accept(EntryVisitor<T> visitor)
    {
        return visitor.visit( this );
    }
}
