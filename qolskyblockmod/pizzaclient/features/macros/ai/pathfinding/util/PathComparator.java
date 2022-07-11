package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util;

import java.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;

public class PathComparator implements Comparator<PathBase>
{
    @Override
    public int compare(final PathBase o1, final PathBase o2) {
        return (int)(o1.currentPos.distanceToSq(o1.goalPos) - o2.currentPos.distanceToSq(o2.goalPos));
    }
}
