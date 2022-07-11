package qolskyblockmod.pizzaclient.features.macros.ai.mining.finder;

import net.minecraft.block.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.*;

public class SingleBlockFinder implements BlockFinder
{
    public final Block block;
    
    public SingleBlockFinder(final Block block) {
        this.block = block;
    }
    
    @Override
    public boolean isValid(final BlockPos pos) {
        return PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == this.block;
    }
}
