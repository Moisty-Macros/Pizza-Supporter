package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;

public final class TPAuraPath extends PathBase
{
    private Runnable runnable;
    
    public TPAuraPath(final Vec3 from, final Vec3 to) {
        super(from, to);
    }
    
    public TPAuraPath(final BetterBlockPos from, final BetterBlockPos to) {
        super(from, to);
    }
    
    public TPAuraPath(final BetterBlockPos to) {
        super(to);
    }
    
    public TPAuraPath(final Vec3 to) {
        super(to);
    }
    
    public TPAuraPath setRunnable(final Runnable runnable) {
        this.runnable = runnable;
        return this;
    }
    
    public void onEnd() {
        if (this.runnable != null) {
            this.runnable.run();
        }
    }
    
    public int getSpeed() {
        return 3;
    }
    
    @Override
    public boolean addBlock(final PathNode node) {
        return this.addBlockForAllDirections(node);
    }
    
    @Override
    public void move() {
    }
}
