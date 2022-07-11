package qolskyblockmod.pizzaclient.features.macros.ai.mining.finder;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.*;

public class MultipleBlockFinder implements BlockFinder
{
    public final Set<Block> blocks;
    
    public MultipleBlockFinder(final Set<Block> blocks) {
        this.blocks = blocks;
    }
    
    public MultipleBlockFinder(final Block... blocks) {
        this.blocks = new HashSet<Block>(Arrays.asList(blocks));
    }
    
    @Override
    public boolean isValid(final BlockPos pos) {
        return this.blocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
    }
}
