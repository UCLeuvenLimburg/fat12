package fat12;

import arrays.Array;

public class BootSector
{
    private final Array<Byte> rawData;

    public BootSector( Array<Byte> rawData )
    {
        this.rawData = rawData;
    }

    @Field(name = "Bytes per Sector")
    public int bytesPerSector()
    {
        return this.rawData.slice( 0x0B, 2 ).asLittleEndianInteger();
    }

    @Field(name = "Sectors per Cluster")
    public int sectorsPerCluster()
    {
        return this.rawData.get( 0x0D );
    }

    @Field(name = "Number of FATs")
    public int fatCopies()
    {
        return this.rawData.get( 0x10 );
    }

    @Field(name = "Root Entry Count")
    public int rootEntryCount()
    {
        return this.rawData.slice( 0x11, 2 ).asLittleEndianInteger();
    }

    @Field(name = "Small Sector Count")
    public int smallSectorCount()
    {
        return this.rawData.slice( 0x13, 2 ).asLittleEndianInteger();
    }

    @Field(name = "Large Sector Count")
    public int largeSectorCount()
    {
        return this.rawData.slice( 0x20, 2 ).asLittleEndianInteger();
    }

    @Field(name = "Sectors per FAT")
    public int sectorsPerFat()
    {
        return this.rawData.slice( 0x16, 2 ).asLittleEndianInteger();
    }

    @Field(name = "FAT Size (bytes)")
    public int fatSizeInBytes()
    {
        return sectorsPerFat() * bytesPerSector();
    }

    @Field(name = "Boot Sector Size (Bytes)")
    public int bootSectorSizeInBytes()
    {
        return this.bytesPerSector();
    }

    @Field(name = "FAT offset")
    public int fatOffset()
    {
        return this.bootSectorSizeInBytes();
    }

    @Field(name = "Root Directory Offset")
    public int rootDirectoryOffset()
    {
        return this.fatOffset() + this.fatCopies() * this.fatSizeInBytes();
    }

    @Field(name = "Root Directory Size (Bytes)")
    public int rootDirectorySizeInBytes()
    {
        return this.rootEntryCount() * DirectoryEntry.SIZE_IN_BYTES;
    }

    @Field(name = "Data Offset")
    public int dataOffset()
    {
        return this.fatOffset() + this.fatCopies() * this.fatSizeInBytes() + rootDirectorySizeInBytes();
    }

    @Field(name = "Bytes per Cluster")
    public int clusterSizeInBytes()
    {
        return this.bytesPerSector() * this.sectorsPerCluster();
    }
}
