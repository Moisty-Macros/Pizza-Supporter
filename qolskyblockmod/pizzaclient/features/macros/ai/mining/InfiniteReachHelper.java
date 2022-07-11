package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.*;
import qolskyblockmod.pizzaclient.util.misc.distance.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;

public class InfiniteReachHelper
{
    private static long lastMineTime;
    
    public static void update() {
        InfiniteReachHelper.lastMineTime = System.currentTimeMillis();
    }
    
    public static void reset() {
        InfiniteReachHelper.lastMineTime = 0L;
    }
    
    public static boolean isValid() {
        return System.currentTimeMillis() - InfiniteReachHelper.lastMineTime <= 2200L;
    }
    
    public static boolean mineClosest() {
        return System.currentTimeMillis() - InfiniteReachHelper.lastMineTime > 2200L;
    }
    
    public static Vec3 getClosest(final BlockFinder finder) {
        final BlockPosDistanceHelper helper = new BlockPosDistanceHelper();
        int y;
        int height;
        for (height = (y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight())); y < height + 5; ++y) {
            for (int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0); x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0; ++x) {
                for (int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0); z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0; ++z) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (finder.isValid(pos)) {
                        helper.compare(pos);
                    }
                }
            }
        }
        if (helper.isNotNull()) {
            return MathUtil.randomAABBPoint(helper.closest);
        }
        for (y = height - 5; y < height + 1; ++y) {
            for (int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0); x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0; ++x) {
                for (int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0); z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0; ++z) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (finder.isValid(pos)) {
                        helper.compare(pos);
                    }
                }
            }
        }
        if (helper.isNotNull()) {
            return MathUtil.randomAABBPoint(helper.closest);
        }
        return null;
    }
}
