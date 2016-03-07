package fat12.entries;


public interface EntryVisitor<T>
{
    T visit(Directory directory);
    
    T visit(File file);
    
    T visit(VolumeLabel volumeLabel);
}
