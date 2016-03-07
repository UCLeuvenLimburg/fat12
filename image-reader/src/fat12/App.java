package fat12;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import arrays.Array;
import arrays.ConcreteArray;

public class App
{
    private static DiskImage diskImage;

    public static void main(String[] args) throws IOException
    {
    	if ( args.length != 1 )
    	{
    		System.err.println("You need to specify the image file");
    		System.exit(1);
    	}
    	else
    	{
	        String path = args[0];
	
	        byte[] bytes = Files.readAllBytes( Paths.get( path ) );
	        Array<Byte> data = ConcreteArray.fromBytes( bytes );
	
	        diskImage = new DiskImage( data );
	
	        printXml( diskImage );
    	}
    }

    private static void printXml(DiskImage diskImage)
    {
        try
        {
            Document doc = XmlFormatter.generateXml( diskImage );
            DOMSource domSource = new DOMSource( doc );
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult( writer );
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
            transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "2" );
            transformer.transform( domSource, result );
            writer.flush();

            System.out.println( writer.toString() );
        }
        catch ( TransformerException ex )
        {
            ex.printStackTrace();
        }
    }
}
