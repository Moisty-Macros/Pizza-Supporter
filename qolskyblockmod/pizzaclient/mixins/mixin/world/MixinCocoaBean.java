package qolskyblockmod.pizzaclient.mixins.mixin.world;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.block.properties.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockCocoa.class })
public abstract class MixinCocoaBean extends BlockDirectional
{
    protected MixinCocoaBean(final Material materialIn) {
        super(materialIn);
    }
    
    @Inject(method = { "setBlockBoundsBasedOnState" }, at = { @At("HEAD") }, cancellable = true)
    private void changeBlockBounds(final IBlockAccess worldIn, final BlockPos pos, final CallbackInfo ci) {
        if (PizzaClient.config.cocoaBeanSize) {
            if ((int)worldIn.func_180495_p(pos).func_177229_b((IProperty)BlockCocoa.field_176501_a) < 2) {
                this.func_149676_a(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            }
            else {
                this.func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            ci.cancel();
        }
    }
}
