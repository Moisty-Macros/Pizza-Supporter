package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ LayerCustomHead.class })
public class MixinLayerCustomHead
{
    @Inject(method = { "doRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    private void onRender(final EntityLivingBase entity, final float f2, final float s, final float nbttagcompound, final float f3, final float gameprofile, final float item, final float minecraft, final CallbackInfo ci) {
        if (PizzaClient.config.auschwitzSimulator && entity instanceof EntityPlayer) {
            ci.cancel();
        }
    }
}
