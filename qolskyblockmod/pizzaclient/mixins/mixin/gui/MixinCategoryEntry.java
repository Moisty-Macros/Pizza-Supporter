package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.graphics.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiKeyBindingList.CategoryEntry.class })
public class MixinCategoryEntry
{
    @Shadow
    @Final
    private String field_148285_b;
    @Shadow
    @Final
    private int field_148286_c;
    
    @Inject(method = { "drawEntry" }, at = { @At("HEAD") }, cancellable = true)
    private void drawRainbow(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected, final CallbackInfo ci) {
        if (this.field_148285_b.equals("Pizza Client")) {
            FontUtil.drawRainbowText(this.field_148285_b, PizzaClient.mc.field_71462_r.field_146294_l / 2 - this.field_148286_c / 2, y + slotHeight - PizzaClient.mc.field_71466_p.field_78288_b - 1);
            ci.cancel();
        }
    }
}
