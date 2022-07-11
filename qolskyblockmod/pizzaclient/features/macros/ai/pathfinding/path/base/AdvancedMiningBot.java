package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.*;
import net.minecraft.block.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.init.*;

public abstract class AdvancedMiningBot extends MiningBot
{
    private Vec3 vec;
    private boolean isPathFree;
    
    public AdvancedMiningBot(final Vec3 from, final Vec3 to) {
        super(from, to);
    }
    
    public AdvancedMiningBot(final BetterBlockPos from, final BetterBlockPos to) {
        super(from, to);
    }
    
    public AdvancedMiningBot(final BetterBlockPos to) {
        super(to);
    }
    
    public AdvancedMiningBot(final Vec3 to) {
        super(to);
    }
    
    public abstract BlockFinder getFinder();
    
    @Override
    public boolean addBlock(final PathNode node) {
        return false;
    }
    
    @Override
    public void move() {
        if (this.moves.size() == 1) {
            this.moves.clear();
            this.mineTargetBlock();
            return;
        }
        if (!this.isPathFree) {
            if (NukerBase.nuker == null) {
                NukerBase.nuker = this;
            }
            return;
        }
        if (this.moves.get(0).equals(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v))) {
            this.isPathFree = false;
            this.moves.remove(0);
            return;
        }
        this.useDefaultMovement();
    }
    
    public void mineTargetBlock() {
        final BlockFinder finder = this.getFinder();
        while (true) {
            if (NukerBase.nuker == null) {
                if (!SimpleNuker.hasBlock(finder)) {
                    break;
                }
                new SimpleNuker(finder).enqueue();
            }
        }
        this.shutdown();
    }
    
    public Block getBlockToMine() {
        return PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)this.goalPos).func_177230_c();
    }
    
    @Override
    public boolean isBlockValid(final BlockPos pos) {
        return this.getFinder().isValid(pos);
    }
    
    @Override
    public boolean nuke(final boolean noSwing) {
        return this.nuke(this.vec, noSwing);
    }
    
    @Override
    public boolean isVecValid() {
        if (this.vec == null) {
            return false;
        }
        final BlockPos pos = new BlockPos(this.vec);
        return VecUtil.canReachBlock(pos) && PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a && this.getFinder().isValid(pos);
    }
    
    @Override
    public boolean findVec() {
        if (this.moves.size() == 0) {
            return false;
        }
        final BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        BetterBlockPos next = this.moves.get(0);
        if (next.equals(player)) {
            this.moves.remove(0);
            if (this.moves.size() == 0) {
                return false;
            }
            next = this.moves.get(0);
        }
        if (player.isSameXandZ(next)) {
            for (int i = 0; i < 5; ++i) {
                final BlockPos pos = next.down(i);
                if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a && VecUtil.canReachBlock(pos)) {
                    this.vec = this.getRandomAABBPoint(pos);
                    return true;
                }
            }
            return false;
        }
        if (next.field_177960_b > player.field_177960_b) {
            for (int i = 0; i < 2; ++i) {
                final BlockPos pos = next.up(i);
                if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
                    this.vec = this.getRandomAABBPoint(pos);
                    return true;
                }
            }
            final BlockPos pos2 = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 2.0, PizzaClient.mc.field_71439_g.field_70161_v);
            if (PizzaClient.mc.field_71441_e.func_180495_p(pos2).func_177230_c() != Blocks.field_150350_a) {
                this.vec = this.getRandomAABBPoint(pos2);
                return true;
            }
            return false;
        }
        else {
            if (next.field_177960_b == player.field_177960_b) {
                for (int i = 0; i < 2; ++i) {
                    final BetterBlockPos pos3 = next.up(i);
                    if (PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)pos3).func_177230_c() != Blocks.field_150350_a) {
                        this.vec = this.getRandomAABBPoint(pos3);
                        return true;
                    }
                }
                return false;
            }
            for (int i = 0; i < 3; ++i) {
                final BetterBlockPos pos3 = next.down(i);
                if (PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)pos3).func_177230_c() != Blocks.field_150350_a) {
                    this.vec = this.getRandomAABBPoint(pos3);
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public void onNoVecAvailable() {
        this.isPathFree = true;
    }
    
    private boolean isMineable(final Block block) {
        return true;
    }
}
