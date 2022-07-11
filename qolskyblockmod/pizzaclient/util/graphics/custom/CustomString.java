package qolskyblockmod.pizzaclient.util.graphics.custom;

import java.util.regex.*;

public interface CustomString
{
    public static final Pattern RANK_PATTERN = Pattern.compile("\\[.*?]\\s");
    
    int render(final String p0, final int p1, final int p2, final int p3, final boolean p4);
}
