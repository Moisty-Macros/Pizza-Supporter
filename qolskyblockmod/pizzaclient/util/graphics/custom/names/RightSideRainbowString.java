package qolskyblockmod.pizzaclient.util.graphics.custom.names;

import qolskyblockmod.pizzaclient.util.graphics.custom.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.graphics.*;

public class RightSideRainbowString implements CustomString
{
    private char[] rainbowChars;
    private int index;
    
    public RightSideRainbowString(final String text, final String rainbow, final String lowerCase) {
        this.rainbowChars = RainbowString.rgbNames.get(lowerCase);
        if (this.rainbowChars[0] == '§') {
            new NonRGBString(text, rainbow, lowerCase);
            return;
        }
        RainbowString.rgbList.put(text, this);
        this.index = text.indexOf(rainbow);
        if (this.rainbowChars[1] == '[') {
            if (text.indexOf(91) == -1 || text.indexOf(93) == -1) {
                for (int i = 2; i < this.rainbowChars.length; ++i) {
                    final char ch = this.rainbowChars[i];
                    if (ch == ']') {
                        final int from = i + 2;
                        final int newLength = this.rainbowChars.length - from;
                        final char[] copy = new char[newLength + 1];
                        System.arraycopy(this.rainbowChars, from, copy, 1, newLength);
                        copy[0] = this.rainbowChars[0];
                        this.rainbowChars = copy;
                        break;
                    }
                }
            }
            else if (this.index > 2 && text.charAt(this.index - 2) == ']') {
                this.index = text.indexOf(91);
            }
        }
    }
    
    @Override
    public int render(final String text, final int x, final int y, final int color, final boolean shadow) {
        FontUtil.drawRainbowName(this.rainbowChars, PizzaClient.mc.field_71466_p.func_175065_a(text.substring(0, this.index), (float)x, (float)y, color, shadow), y, shadow);
        return FontUtil.posX;
    }
}
