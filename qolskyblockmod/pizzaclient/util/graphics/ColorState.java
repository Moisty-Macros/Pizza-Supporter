package qolskyblockmod.pizzaclient.util.graphics;

import org.lwjgl.opengl.*;

public class ColorState
{
    public static float red;
    public static float green;
    public static float blue;
    
    public static void setColor(final int redIn, final int greenIn, final int blueIn) {
        ColorState.red = (float)redIn;
        ColorState.green = (float)greenIn;
        ColorState.blue = (float)blueIn;
    }
    
    public static void setColorAndRender(final int redIn, final int greenIn, final int blueIn) {
        ColorState.red = (float)redIn;
        ColorState.green = (float)greenIn;
        ColorState.blue = (float)blueIn;
        GL11.glColor3f((float)redIn, (float)greenIn, (float)blueIn);
    }
    
    public static void setColor(final float redIn, final float greenIn, final float blueIn) {
        ColorState.red = redIn;
        ColorState.green = greenIn;
        ColorState.blue = blueIn;
    }
    
    public static void setColorAndRender(final float redIn, final float greenIn, final float blueIn) {
        GL11.glColor3f(ColorState.red = redIn, ColorState.green = greenIn, ColorState.blue = blueIn);
    }
    
    public static void setColor() {
        GL11.glColor3f(ColorState.red, ColorState.blue, ColorState.green);
    }
}
