package qolskyblockmod.pizzaclient.core.guioverlay;

import net.minecraft.client.gui.*;
import qolskyblockmod.pizzaclient.*;

public class GuiLocation
{
    private static final ScaledResolution sr;
    public float x;
    public float y;
    
    public GuiLocation(final int x, final int y) {
        this(x / (float)GuiLocation.sr.func_78328_b(), y / (float)GuiLocation.sr.func_78328_b());
    }
    
    public GuiLocation(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean equals(final GuiLocation loc) {
        return loc.x == this.x && loc.y == this.y;
    }
    
    @Override
    public String toString() {
        return "{x=" + this.x + ", y=" + this.y + "}";
    }
    
    static {
        sr = new ScaledResolution(PizzaClient.mc);
    }
}
