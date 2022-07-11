package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util;

import java.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;

public class NodeCostComparator implements Comparator<PathNode>
{
    @Override
    public int compare(final PathNode o1, final PathNode o2) {
        return o1.cost - o2.cost;
    }
}
