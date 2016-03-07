package fat12;

import arrays.Array;
import arrays.ArrayFunctions;
import fat12.entries.Directory;
import fat12.entries.Entry;

public class DiskImage
{
    private final Array<Byte> image;

    private final BootSector bootSector;

    private final FileAllocationTable fileAllocationTable;

    private final Directory rootDirectory;

    public DiskImage( Array<Byte> image )
    {
        this.image = image;
        this.bootSector = new BootSector( image );
        this.fileAllocationTable = new FileAllocationTable( firstFileAllocationTableBytes() );
        this.rootDirectory = Directory.createRootDirectory( this, rootDirectoryBytes() );
    }

    private Array<Byte> firstFileAllocationTableBytes()
    {
        return image.slice( this.bootSector.fatOffset(), this.bootSector.fatSizeInBytes() );
    }

    private Array<Byte> rootDirectoryBytes()
    {
        int startByteIndex = bootSector.rootDirectoryOffset();
        int length = bootSector.rootDirectorySizeInBytes();

        return image.slice( startByteIndex, length );
    }

    public Array<Byte> bytes()
    {
        return image;
    }

    public BootSector bootSector()
    {
        return this.bootSector;
    }

    public FileAllocationTable fat()
    {
        return this.fileAllocationTable;
    }

    public Directory rootDirectory()
    {
        return this.rootDirectory;
    }

    public int dataClusterOffset(int logicalIndex)
    {
        return this.bootSector.dataOffset() + (logicalIndex - 2) * this.bootSector.clusterSizeInBytes();
    }

    public Array<Byte> readDataCluster(int logicalIndex)
    {
        return this.image.slice( dataClusterOffset( logicalIndex ), this.bootSector.clusterSizeInBytes() );
    }

    public Array<Byte> chainClusters(int startIndex)
    {
        Array<Integer> clusterIndices = this.fileAllocationTable.clusterChain( startIndex );

        return ArrayFunctions.flatten( clusterIndices.map( this::readDataCluster ) );
    }

    public Array<Entry> entries()
    {
        return entries( rootDirectory );
    }

    private Array<Entry> entries(Entry entry)
    {
        Array<Entry> entries = entry.children();
        Array<Entry> subentries = ArrayFunctions.flatten( entries.map( this::entries ) );

        return entries.concatenate( subentries );
    }
}
