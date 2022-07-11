package qolskyblockmod.pizzaclient.core.guioverlay;

import qolskyblockmod.pizzaclient.*;
import net.minecraft.client.gui.*;

public abstract class GuiElement
{
    public String name;
    public float scale;
    public GuiLocation pos;
    
    public GuiElement(final String name, final GuiLocation location) {
        this(name, 1.0f, location);
    }
    
    public GuiElement(final String name, final float scale, final GuiLocation location) {
        this.name = name;
        this.scale = scale;
        this.pos = GuiManager.GUIPOSITIONS.getOrDefault(name, location);
    }
    
    public abstract void render();
    
    public abstract void demoRender();
    
    public abstract boolean getToggled();
    
    public void setPos(final float x, final float y) {
        this.pos.x = x;
        this.pos.y = y;
    }
    
    public float getActualX() {
        final int maxX = new ScaledResolution(PizzaClient.mc).func_78326_a();
        return maxX * this.pos.x;
    }
    
    public float getActualY() {
        final int maxY = new ScaledResolution(PizzaClient.mc).func_78328_b();
        return maxY * this.pos.y;
    }
    
    public abstract int getHeight();
    
    public abstract int getWidth();
}
