package fat12.entries;

import fat12.DirectoryEntry;
import fat12.DiskImage;
import arrays.Array;

public abstract class Entry
{
    protected final DiskImage diskImage;

    protected final DirectoryEntry entry;

    protected final String path;

    public static Entry fromDirectoryEntry(String path, DiskImage diskImage, DirectoryEntry entry)
    {
        if ( entry.attributes().directory() )
        {
            return Directory.fromDirectoryEntry( path, diskImage, entry );
        }
        else if ( entry.attributes().volumeLabel() )
        {
            return VolumeLabel.fromDirectoryEntry( diskImage, entry );
        }
        else
        {
            return File.fromDirectoryEntry( path, diskImage, entry );
        }
    }

    protected Entry( String path, DiskImage diskImage, DirectoryEntry entry )
    {
        this.path = path;
        this.diskImage = diskImage;
        this.entry = entry;
    }

    public DirectoryEntry entry()
    {
        return entry;
    }

    public String path()
    {
        return path;
    }

    public abstract Array<Entry> children();

    public abstract <T> T accept(EntryVisitor<T> visitor);
}
