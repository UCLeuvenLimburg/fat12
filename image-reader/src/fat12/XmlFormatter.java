package fat12;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import fat12.entries.Directory;
import fat12.entries.Entry;
import fat12.entries.EntryVisitor;
import fat12.entries.File;
import fat12.entries.VolumeLabel;
import static arrays.ArrayFunctions.*;

public class XmlFormatter
{
    public static Document generateXml(DiskImage diskImage)
    {
        XmlFormatter formatter = new XmlFormatter();

        try
        {
            return formatter.generate( diskImage );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    private Document document;

    private DiskImage diskImage;

    private Document generate(DiskImage image) throws ParserConfigurationException
    {
        this.diskImage = image;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        document.appendChild( generateRoot() );

        return document;
    }

    private Node generateRoot()
    {
        Element root = element( "disk-image" );

        root.appendChild( generateBootSector( diskImage.bootSector() ) );

        for ( Entry entry : diskImage.entries() )
        {
            root.appendChild( generateEntry( entry ) );
        }

        return root;
    }

    private Node generateEntry(Entry entry)
    {
        return entry.accept( new EntryVisitor<Node>()
        {
            @Override
            public Node visit(VolumeLabel volumeLabel)
            {
                return generateVolumeLabel( volumeLabel );
            }

            @Override
            public Node visit(File file)
            {
                return generateFile( file );
            }

            @Override
            public Node visit(Directory directory)
            {
                return generateDirectory( directory );
            }
        } );
    }

    private Node generateVolumeLabel(VolumeLabel volumeLabel)
    {
        return new ElementBuilder( element( "volume-label" ) )
        {
            @Override
            protected void build()
            {
                element( "name", volumeLabel.name() );
            }
        }.finish();
    }

    private Node generateFile(File file)
    {
        Element root = element( "file" );

        return new ElementBuilder( root )
        {
            @Override
            protected void build()
            {
                generateEntryData( root, file.entry() );
                element( "path", file.path() );
                element( "contents", hex( file.contents() ) );
            }
        }.finish();
    }

    private Node generateDirectory(Directory directory)
    {
        Element root = element( "directory" );

        return new ElementBuilder( root )
        {
            @Override
            protected void build()
            {
                generateEntryData( root, directory.entry() );
                element( "path", directory.path() );
            }
        }.finish();
    }

    private Node generateBootSector(BootSector bootSector)
    {
        return new ElementBuilder( element( "boot-sector" ) )
        {
            @Override
            protected void build()
            {
                element( "bytes-per-sector", bootSector.bytesPerSector() );
                element( "sectors-per-cluster", bootSector.sectorsPerCluster() );
                element( "fat-count", bootSector.fatCopies() );
                element( "root-entry-count", bootSector.rootEntryCount() );
                element( "small-sector-count", bootSector.smallSectorCount() );
                element( "large-sector-count", bootSector.largeSectorCount() );
                element( "sectors-per-fat", bootSector.sectorsPerFat() );
                element( "bytes-per-fat", bootSector.fatSizeInBytes() );
                element( "bootsector-byte-size", bootSector.bootSectorSizeInBytes() );
                element( "fat-offset", bootSector.fatOffset() );
                element( "root-directory-offset", bootSector.rootDirectoryOffset() );
                element( "data-offset", bootSector.dataOffset() );
                element( "cluster-size-in-bytes", bootSector.clusterSizeInBytes() );
            }
        }.finish();
    }

    private Node generateEntryData(Element root, DirectoryEntry entry)
    {
        return new ElementBuilder( root )
        {
            @Override
            protected void build()
            {
                element( "name", entry.name().trim() );
                element( "extension", entry.extension().trim() );
                element( "last-write-time", entry.lastWriteTime().toString() );
                element( "last-write-date", entry.lastWriteDate().toString() );
                element( "last-write-time-bits", bits( entry.lastWriteTime().bits() ) );
                element( "last-write-date-bits", bits( entry.lastWriteDate().bits() ) );
                element( "clusters", dec( diskImage.fat().clusterChain( entry.firstLogicalCluster() ) ) );
                element( "size", entry.fileSize() );
                element( "raw-bytes", hex( entry.rawBytes() ) );
            }
        }.finish();
    }

    private Element element(String tag)
    {
        return document.createElement( tag );
    }

    private Node element(String tag, String value)
    {
        Element elt = element( tag );
        Text txt = document.createTextNode( value );
        elt.appendChild( txt );

        return elt;
    }

    private Node element(String tag, int value)
    {
        return element( tag, Integer.toString( value ) );
    }

    private abstract class ElementBuilder
    {
        private final Element root;

        public ElementBuilder( Element root )
        {
            this.root = root;
        }

        protected void element(String tag, String value)
        {
            root.appendChild( XmlFormatter.this.element( tag, value ) );
        }

        protected void element(String tag, int value)
        {
            root.appendChild( XmlFormatter.this.element( tag, value ) );
        }

        protected abstract void build();

        protected Element finish()
        {
            build();

            return root;
        }
    }
}
