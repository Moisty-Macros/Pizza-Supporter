package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import qolskyblockmod.pizzaclient.core.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiScreen.class })
public abstract class MixinGuiScreen extends Gui
{
    @Inject(method = { "sendChatMessage(Ljava/lang/String;Z)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendChatMessage(final String message, final boolean addToChat, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new SendChatMessageEvent(message))) {
            ci.cancel();
        }
    }
}
