package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.*;
import qolskyblockmod.pizzaclient.*;

public interface IPath
{
    boolean addBlock(final PathNode p0);
    
    void move();
    
    default void onBeginMove() {
    }
    
    default void onEndMove() {
    }
    
    default void onTick() {
    }
    
    default void shutdown() {
        PathFinder.path = null;
        Pathfinding.unregister();
        PizzaClient.rotater = null;
    }
}
