package qolskyblockmod.pizzaclient.util.graphics.custom.names;

import qolskyblockmod.pizzaclient.util.graphics.custom.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.graphics.*;

public class NonRGBString implements CustomString
{
    public String name;
    public final String oldName;
    public final boolean renderRank;
    
    public NonRGBString(final String text, final String custom, final String lowerCase) {
        this.oldName = custom;
        this.name = new String(RainbowString.rgbNames.get(lowerCase));
        RainbowString.rgbList.put(text, this);
        if (this.name.charAt(2) == '[') {
            final String stripped = StringUtils.func_76338_a(text);
            if (stripped.indexOf(93) != -1 && (stripped.contains("[VIP") || stripped.contains("[MVP"))) {
                final int index = text.indexOf(58);
                if (stripped.contains("] " + StringUtils.func_76338_a(this.oldName)) && (index == -1 || index > text.indexOf(lowerCase))) {
                    this.renderRank = true;
                }
                else {
                    this.name = NonRGBString.RANK_PATTERN.matcher(this.name).replaceAll("");
                    this.renderRank = false;
                }
                return;
            }
        }
        this.name = NonRGBString.RANK_PATTERN.matcher(this.name).replaceAll("");
        this.renderRank = false;
    }
    
    @Override
    public int render(final String text, final int x, final int y, final int color, final boolean shadow) {
        final int index = text.indexOf(this.oldName);
        final String before = text.substring(0, index);
        final String after = text.substring(index + this.oldName.length());
        if (this.renderRank) {
            return PizzaClient.mc.field_71466_p.func_175065_a(after, FontUtil.renderString(this.name, PizzaClient.mc.field_71466_p.func_175065_a(NonRGBString.RANK_PATTERN.matcher(before).replaceAll(""), (float)x, (float)y, color, shadow), y, color, shadow), (float)y, color, shadow);
        }
        return PizzaClient.mc.field_71466_p.func_175065_a(after, FontUtil.renderString(this.name, PizzaClient.mc.field_71466_p.func_175065_a(before, (float)x, (float)y, color, shadow), y, color, shadow), (float)y, color, shadow);
    }
}
