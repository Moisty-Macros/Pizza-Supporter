package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame
{
    @Redirect(method = { "renderScoreboard" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 1))
    private int cancelRenderingNumbers(final FontRenderer instance, final String text, final int x, final int y, final int color) {
        return 0;
    }
}
