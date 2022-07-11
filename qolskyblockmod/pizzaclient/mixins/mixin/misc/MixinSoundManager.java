package qolskyblockmod.pizzaclient.mixins.mixin.misc;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.audio.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.listeners.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ SoundManager.class })
public class MixinSoundManager
{
    @Inject(method = { "playSound" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlaySound(final ISound p_sound, final CallbackInfo ci) {
        if (PizzaClient.mc.field_71441_e == null) {
            return;
        }
        if (SoundListener.onPlaySound(p_sound, p_sound.func_147650_b().func_110623_a())) {
            ci.cancel();
        }
    }
}
