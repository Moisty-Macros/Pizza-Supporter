package qolskyblockmod.pizzaclient.features.player;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.*;
import java.util.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import net.minecraft.client.entity.*;

public class TPAuraHelper
{
    public static final int FLY_DURATION = 1300;
    public static TPAuraPath path;
    private static long lastFlyDisable;
    
    public static boolean isFlyActive() {
        return System.currentTimeMillis() - TPAuraHelper.lastFlyDisable < 1300L;
    }
    
    public static void update() {
        TPAuraHelper.lastFlyDisable = System.currentTimeMillis();
    }
    
    public static void teleport() {
        if (TPAuraHelper.path.moves.size() == 0) {
            TPAuraHelper.path.onEnd();
            disable();
            return;
        }
        final List<BetterBlockPos> subList = TPAuraHelper.path.moves.subList(0, MathUtil.min(TPAuraHelper.path.moves.size(), TPAuraHelper.path.getSpeed() - 1));
        final BetterBlockPos pos = subList.get(subList.size() - 1);
        PizzaClient.mc.field_71439_g.func_70107_b(pos.field_177962_a + 0.5, (double)pos.field_177960_b, pos.field_177961_c + 0.5);
        subList.clear();
    }
    
    public static void runPathfinder(final BetterBlockPos pos) {
        final PathFinderNoMovement pathFinderNoMovement;
        new Thread(() -> {
            new PathFinderNoMovement(new TPAuraPath(Utils.getClosestInRange(pos)));
            TPAuraHelper.path = (TPAuraPath)pathFinderNoMovement.calculateAndGetPath();
        }).start();
    }
    
    public static void runPathfinder(final BetterBlockPos pos, final Runnable runnable) {
        final PathFinderNoMovement pathFinderNoMovement;
        new Thread(() -> {
            new PathFinderNoMovement(new TPAuraPath(Utils.getClosestInRange(pos)).setRunnable(runnable));
            TPAuraHelper.path = (TPAuraPath)pathFinderNoMovement.calculateAndGetPath();
        }).start();
    }
    
    public static void disable() {
        Pathfinding.unregister();
        BasePathfinder.path = null;
        TPAuraHelper.path = null;
        final EntityPlayerSP field_71439_g = PizzaClient.mc.field_71439_g;
        final EntityPlayerSP field_71439_g2 = PizzaClient.mc.field_71439_g;
        final double n = 0.0;
        field_71439_g2.field_70179_y = n;
        field_71439_g.field_70159_w = n;
    }
}
