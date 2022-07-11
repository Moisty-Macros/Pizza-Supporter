package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding;

import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;

public class Pathfinding
{
    public static Pathfinding instance;
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (BasePathfinder.path == null || BasePathfinder.path.moves.size() == 0) {
            MinecraftForge.EVENT_BUS.unregister((Object)Pathfinding.instance);
            return;
        }
        RenderUtil.drawRainbowPath(BasePathfinder.path.moves, 2.0f);
    }
    
    public static void runPathfinder(final BetterBlockPos goal) {
        final PathFinder pathFinder;
        new Thread(() -> {
            new PathFinder(new Path(goal));
            pathFinder.run();
        }).start();
    }
    
    public static void runAStarPathfinder(final BetterBlockPos goal) {
        final AStarPathfinder aStarPathfinder;
        new Thread(() -> {
            new AStarPathfinder(new Path(goal));
            aStarPathfinder.run();
        }).start();
    }
    
    public static void unregister() {
        MinecraftForge.EVENT_BUS.unregister((Object)Pathfinding.instance);
    }
    
    public static void register() {
        MinecraftForge.EVENT_BUS.register((Object)Pathfinding.instance);
    }
}
