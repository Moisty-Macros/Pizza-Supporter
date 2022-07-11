package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraftforge.common.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.*;
import qolskyblockmod.pizzaclient.util.*;
import java.util.*;

public final class AStarPathfinder extends BasePathfinder
{
    public AStarPathfinder(final PathBase path) {
        super(path);
    }
    
    @Override
    public boolean run(final boolean messages) {
        if (AStarPathfinder.path == null) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Path returned null. Please report this."));
            return false;
        }
        if (AStarPathfinder.path.currentPos.equals(AStarPathfinder.path.goalPos)) {
            this.shutdown();
            return true;
        }
        try {
            AStarPathfinder.nodes = new ArrayList<PathNode>(Collections.singletonList(new PathNode()));
            while (!AStarPathfinder.path.finished) {
                final List<PathNode> newNodes = new ArrayList<PathNode>(AStarPathfinder.nodes);
                for (int i = 0; i < MathUtil.min(newNodes.size(), 4); ++i) {
                    final PathNode node = newNodes.get(i);
                    AStarPathfinder.nodes.remove(node);
                    if (AStarPathfinder.path.addBlock(node)) {
                        MinecraftForge.EVENT_BUS.register((Object)Pathfinding.instance);
                        break;
                    }
                }
                if (AStarPathfinder.nodes.size() == 0) {
                    if (messages) {
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Failed to find a path."));
                    }
                    this.shutdown();
                    return false;
                }
                AStarPathfinder.nodes.sort(new NodeCostComparator());
                AStarPathfinder.path.onTick();
            }
            if (messages) {
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Path!"));
            }
            AStarPathfinder.path.onBeginMove();
            while (AStarPathfinder.path.moves.size() != 0) {
                if (PizzaClient.mc.field_71462_r != null) {
                    Movement.disableMovement();
                    Utils.sleep(200);
                    PlayerUtil.closeScreen();
                }
                else {
                    AStarPathfinder.path.move();
                }
            }
            AStarPathfinder.path.onEndMove();
            Movement.disableMovement();
            this.shutdown();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "PizzaClient caught an logged an exception when calculating the path. Please report this."));
            this.shutdown();
            return false;
        }
    }
}
