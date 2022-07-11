package qolskyblockmod.pizzaclient.mixins.mixin.world;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraftforge.common.*;
import qolskyblockmod.pizzaclient.core.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Chunk.class })
public class MixinChunk
{
    @Inject(method = { "setBlockState" }, at = { @At("HEAD") })
    private void onBlockChange(final BlockPos pos, final IBlockState state, final CallbackInfoReturnable<IBlockState> cir) {
        if (PizzaClient.mc.field_71441_e == null) {
            return;
        }
        final IBlockState old = PizzaClient.mc.field_71441_e.func_180495_p(pos);
        if (!old.equals(state)) {
            MinecraftForge.EVENT_BUS.post((Event)new BlockChangeEvent(old, state, pos));
        }
    }
}
