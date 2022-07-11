package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.rotation.helper.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;

public class SimpleNuker implements INuker
{
    private final BlockFinder finder;
    public Vec3 vec;
    
    public SimpleNuker(final BlockFinder finder) {
        this.finder = finder;
    }
    
    @Override
    public boolean isBlockValid(final BlockPos pos) {
        return this.finder.isValid(pos);
    }
    
    @Override
    public boolean nuke(final boolean noSwing) {
        return this.nuke(this.vec, noSwing);
    }
    
    @Override
    public boolean isVecValid() {
        return this.finder.isValid(new BlockPos(this.vec));
    }
    
    @Override
    public boolean findVec() {
        final BlockPosFakeRotationHelper helper = new BlockPosFakeRotationHelper();
        for (int y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u); y < PizzaClient.mc.field_71439_g.field_70163_u + 2.0; ++y) {
            for (int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0); x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0; ++x) {
                for (int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0); z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0; ++z) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (this.finder.isValid(pos) && VecUtil.canReachBlock(pos)) {
                        helper.compare(pos);
                    }
                }
            }
        }
        if (helper.isNotNull()) {
            this.vec = MathUtil.randomAABBPoint(helper.bestPos);
            return true;
        }
        for (int y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight() - 5.0); y < PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight() + 5.0; ++y) {
            for (int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0); x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0; ++x) {
                for (int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0); z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0; ++z) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (this.finder.isValid(pos) && VecUtil.canReachBlock(pos)) {
                        helper.compare(pos);
                    }
                }
            }
        }
        if (helper.isNotNull()) {
            this.vec = MathUtil.randomAABBPoint(helper.bestPos);
            return true;
        }
        return false;
    }
    
    public static boolean hasBlock(final BlockFinder finder) {
        for (int y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight() - 5.0); y < PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight() + 5.0; ++y) {
            for (int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0); x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0; ++x) {
                for (int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0); z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0; ++z) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (finder.isValid(pos) && VecUtil.canReachBlock(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
