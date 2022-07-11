package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import qolskyblockmod.pizzaclient.features.macros.mining.nuker.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;

public abstract class MiningBot extends PathBase implements INuker
{
    public MiningBot(final Vec3 from, final Vec3 to) {
        super(from, to);
    }
    
    public MiningBot(final BetterBlockPos from, final BetterBlockPos to) {
        super(from, to);
    }
    
    public MiningBot(final BetterBlockPos to) {
        super(to);
    }
    
    public MiningBot(final Vec3 to) {
        super(to);
    }
}
