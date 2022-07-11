package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import qolskyblockmod.pizzaclient.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiOverlayDebug.class })
public class MixinGuiOverlayDebug
{
    @Redirect(method = { "call" }, at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 5))
    private String redirectWorldDate(final String s, final Object[] objects) {
        return "Local Difficulty: " + Utils.DECIMAL_FORMAT.format((float)objects[0]) + " (Day " + Utils.DECIMAL_FORMAT.format(Utils.getExactDay()) + ")";
    }
}
