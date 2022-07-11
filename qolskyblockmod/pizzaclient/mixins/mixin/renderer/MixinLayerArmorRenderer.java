package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ LayerArmorBase.class })
public class MixinLayerArmorRenderer
{
    @Inject(method = { "doRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    private void onRender(final EntityLivingBase entity, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale, final CallbackInfo ci) {
        if (PizzaClient.config.auschwitzSimulator && entity instanceof EntityPlayer) {
            ci.cancel();
        }
    }
}
