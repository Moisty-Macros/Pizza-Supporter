package qolskyblockmod.pizzaclient.gui;

import qolskyblockmod.pizzaclient.gui.button.*;
import qolskyblockmod.pizzaclient.util.graphics.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;

public class StartGui extends GuiScreen
{
    public boolean func_73868_f() {
        return false;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(new BetterButton(0, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 100, 190, 20, "Config"));
        this.field_146292_n.add(new BetterButton(1, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 125, 190, 20, "Edit Button Locations"));
        this.field_146292_n.add(new BetterButton(2, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 150, 190, 20, "Send Commands List"));
        this.field_146292_n.add(new BetterButton(3, this.field_146294_l / 2 - 95, this.field_146295_m / 4 + 175, 190, 20, "Join the discord!"));
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        FontUtil.drawBackground(this.field_146294_l, this.field_146295_m);
        final float scale = 10.0f;
        GlStateManager.func_179152_a(scale, scale, 0.0f);
        FontUtil.drawCenteredString("PizzaClient", (float)(int)(this.field_146294_l / 2.0f / scale), (float)(int)((this.field_146295_m / 4.0f - 75.0f) / scale), 65535);
        GlStateManager.func_179152_a(1.0f / scale, 1.0f / scale, 0.0f);
        for (final GuiButton button : this.field_146292_n) {
            button.func_146112_a(this.field_146297_k, mouseX, mouseY);
        }
    }
    
    protected void func_146284_a(final GuiButton button) {
        switch (button.field_146127_k) {
            case 0: {
                this.field_146297_k.func_147108_a((GuiScreen)PizzaClient.config.gui());
                break;
            }
            case 1: {
                this.field_146297_k.func_147108_a((GuiScreen)new LocationEditGui());
                break;
            }
            case 2: {
                this.field_146297_k.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + PizzaClient.config.commandsList));
                this.field_146297_k.field_71439_g.func_71053_j();
                break;
            }
            case 3: {
                Utils.openUrl("https://discord.gg/NWeacCr3B8");
                break;
            }
        }
    }
}
