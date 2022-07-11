package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder;

import java.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import net.minecraftforge.common.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.*;

public abstract class BasePathfinder
{
    public static List<PathNode> nodes;
    public static PathBase path;
    
    public BasePathfinder(final PathBase path) {
        BasePathfinder.path = path;
        MinecraftForge.EVENT_BUS.unregister((Object)Pathfinding.instance);
        BasePathfinder.nodes = null;
    }
    
    public void run() {
        this.run(true);
    }
    
    public void runNewThread() {
        new Thread(this::run).start();
    }
    
    public void shutdown() {
        BasePathfinder.path = null;
        Pathfinding.unregister();
    }
    
    public abstract boolean run(final boolean p0);
}
