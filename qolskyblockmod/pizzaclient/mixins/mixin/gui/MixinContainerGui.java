package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.core.events.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiContainer.class }, priority = 999)
public abstract class MixinContainerGui extends GuiScreen
{
    @Shadow
    public Container field_147002_h;
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") })
    private void backgroundDrawn(final CallbackInfo ci) {
        if (this.field_147002_h instanceof ContainerChest) {
            final IInventory chest = ((ContainerChest)this.field_147002_h).func_85151_d();
            MinecraftForge.EVENT_BUS.post((Event)new ChestBackgroundDrawnEvent(this.field_147002_h, StringUtils.func_76338_a(chest.func_145748_c_().func_150260_c().trim()), this.field_147002_h.field_75151_b.size(), this.field_147002_h.field_75151_b, chest));
        }
    }
}
