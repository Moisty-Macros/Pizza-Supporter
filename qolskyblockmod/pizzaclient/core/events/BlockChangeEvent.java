package qolskyblockmod.pizzaclient.core.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockChangeEvent extends Event
{
    public final IBlockState oldState;
    public final IBlockState currentState;
    public final BlockPos pos;
    
    public BlockChangeEvent(final IBlockState oldState, final IBlockState currentState, final BlockPos pos) {
        this.currentState = currentState;
        this.oldState = oldState;
        this.pos = pos;
    }
}
