package fat12.entries;

import fat12.DirectoryEntry;
import fat12.DiskImage;
import arrays.Array;
import arrays.ConcreteArray;

public class VolumeLabel extends Entry
{
    public static VolumeLabel fromDirectoryEntry( DiskImage diskImage, DirectoryEntry entry )
    {
        return new VolumeLabel( diskImage, entry );
    }
    
    private VolumeLabel( DiskImage diskImage, DirectoryEntry entry )
    {
        super( null, diskImage, entry );
    }
    
    public String name()
    {
        return entry.fullName();
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
