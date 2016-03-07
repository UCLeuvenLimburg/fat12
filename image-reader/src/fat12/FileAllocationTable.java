package fat12;

import java.util.ArrayList;

import arrays.*;

public class FileAllocationTable extends ArrayBase<Integer>
{
    private final Array<Byte> rawData;

    public FileAllocationTable( Array<Byte> rawData )
    {
        this.rawData = rawData;
    }

    @Override
    public int length()
    {
        return this.rawData.length() / 3 * 2;
    }

    @Override
    public Integer get(int index)
    {
        final int i = index * 3 / 2;

        if ( index % 2 == 0 )
        {
            return ((this.rawData.get( i + 1 ) & 0xF) << 8) | (this.rawData.get( i ) & 0xFF);
        }
        else
        {
            int b1 = this.rawData.get( i );
            int b2 = this.rawData.get( i + 1 );

            return ((b1 & 0xF0) >> 4) | ((b2 & 0xFF) << 4);
        }
    }

    public static boolean lastClusterIndicator(int clusterIndex)
    {
        return 0xFF0 <= clusterIndex && clusterIndex <= 0xFFF;
    }

    public Array<Integer> clusterChain(int startIndex)
    {
        ArrayList<Integer> clusters = new ArrayList<>();
        int currentIndex = startIndex;

        while ( !FileAllocationTable.lastClusterIndicator( currentIndex ) )
        {
            clusters.add( currentIndex );
            currentIndex = this.get( currentIndex );
        }

        return ConcreteArray.fromIterable( clusters );
    }
}
