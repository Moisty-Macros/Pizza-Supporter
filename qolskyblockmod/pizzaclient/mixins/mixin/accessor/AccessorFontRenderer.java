package qolskyblockmod.pizzaclient.mixins.mixin.accessor;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ FontRenderer.class })
public interface AccessorFontRenderer
{
    @Accessor("glyphWidth")
    byte[] getGlyphWidths();
    
    @Accessor("locationFontTexture")
    ResourceLocation getLocationFontTexture();
    
    @Invoker("getUnicodePageLocation")
    ResourceLocation getUnicodePageLocation(final int p0);
    
    @Accessor("colorCode")
    int[] getColorCodes();
    
    @Invoker("resetStyles")
    void resetStyles();
    
    @Invoker("renderString")
    int renderString(final String p0, final float p1, final float p2, final int p3, final boolean p4);
}
