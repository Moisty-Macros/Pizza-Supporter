package qolskyblockmod.pizzaclient.mixins.mixin.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractPlayerClient extends EntityPlayer
{
    @Unique
    private final ResourceLocation skin;
    @Unique
    private final ResourceLocation nice_guy;
    
    public MixinAbstractPlayerClient(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
        this.skin = new ResourceLocation("pizzaclient", "skin/skin.png");
        this.nice_guy = new ResourceLocation("pizzaclient", "skin/auschwitz/nice_guy.png");
    }
    
    @Inject(method = { "getLocationSkin()Lnet/minecraft/util/ResourceLocation;" }, at = { @At("RETURN") }, cancellable = true)
    private void replaceSkin(final CallbackInfoReturnable<ResourceLocation> cir) {
        if (PizzaClient.config.auschwitzSimulator) {
            if (this.func_70005_c_().equals(PizzaClient.username)) {
                cir.setReturnValue((Object)this.nice_guy);
            }
            else {
                cir.setReturnValue((Object)new ResourceLocation("pizzaclient", "skin/auschwitz/jew" + MathUtil.abs(this.func_70005_c_().hashCode() % 2) + ".png"));
            }
        }
        else if (PizzaClient.config.replaceSkin) {
            cir.setReturnValue((Object)this.skin);
        }
    }
}
