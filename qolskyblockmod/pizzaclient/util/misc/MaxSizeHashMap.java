package qolskyblockmod.pizzaclient.util.misc;

import java.util.*;

public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V>
{
    private final int maxSize;
    
    public MaxSizeHashMap(final int maxSize) {
        this.maxSize = maxSize;
    }
    
    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        return this.size() > this.maxSize;
    }
}
