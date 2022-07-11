package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.*;
import java.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;

public class Path extends PathBase
{
    public boolean teleport;
    public double lastUnloadedChunkDistance;
    public PathNode unloaded;
    
    public Path(final Vec3 from, final Vec3 to) {
        super(from, to);
        this.lastUnloadedChunkDistance = 9.99999999E8;
    }
    
    public Path(final BetterBlockPos from, final BetterBlockPos to) {
        super(from, to);
        this.lastUnloadedChunkDistance = 9.99999999E8;
        this.teleport = PlayerUtil.hotbarHasEtherwarp();
    }
    
    public Path(final BetterBlockPos to) {
        super(to);
        this.lastUnloadedChunkDistance = 9.99999999E8;
    }
    
    public Path(final Vec3 to) {
        super(to);
        this.lastUnloadedChunkDistance = 9.99999999E8;
    }
    
    @Override
    public void update() {
        this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        this.checked.clear();
        this.moves = new ArrayList<BetterBlockPos>(Collections.singletonList(new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)));
        this.teleport = PlayerUtil.hotbarHasEtherwarp();
        this.unloaded = null;
    }
    
    @Override
    public void update(final BetterBlockPos goalPos) {
        this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        this.goalPos = goalPos;
        this.checked.clear();
        this.moves = new ArrayList<BetterBlockPos>(Collections.singletonList(new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)));
        this.teleport = PlayerUtil.hotbarHasEtherwarp();
        this.unloaded = null;
    }
    
    @Override
    public boolean addBlock(final PathNode node) {
        if (this.teleport) {
            final MovingObjectPosition position = VecUtil.rayTraceLook(node.currentPos.getPositionEyes(), this.goalPos.toVec(), 57.0f);
            if (position != null && position.func_178782_a().equals((Object)this.goalPos)) {
                this.moves = new ArrayList<BetterBlockPos>(node.moves);
                return this.finished = true;
            }
        }
        if (!node.currentPos.isBlockLoaded()) {
            final double dist = node.currentPos.distanceToSq(this.goalPos);
            if (dist < this.lastUnloadedChunkDistance) {
                this.lastUnloadedChunkDistance = dist;
                this.unloaded = node;
                return true;
            }
        }
        return this.getDefaultAddBlock(node);
    }
    
    public void moveUntilLoaded() {
        this.moves = new ArrayList<BetterBlockPos>(this.unloaded.moves);
        this.onBeginMove();
        while (!this.unloaded.currentPos.isBlockLoaded()) {
            this.closeScreenIfOpen();
            this.moveTo();
        }
        this.onEndMove();
        Movement.disableMovement();
        Utils.sleep(50);
        this.update();
    }
    
    @Override
    public void onTick() {
        if (this.unloaded != null) {
            this.moveUntilLoaded();
            PathFinder.nodes = new ArrayList<PathNode>(Collections.singletonList(new PathNode()));
            this.unloaded = null;
            Movement.disableMovement();
        }
    }
    
    public void moveTo() {
        this.useDefaultMovement();
        this.rotateIfNeeded();
    }
    
    @Override
    public void move() {
        this.moveTo();
    }
    
    @Override
    public void onBeginMove() {
        this.initRotater();
    }
    
    @Override
    public void onEndMove() {
        this.teleport();
    }
    
    public void teleport() {
        if (this.teleport) {
            final BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.func_174791_d());
            if (!this.goalPos.equals(player) && !this.goalPos.equals(player.up()) && !this.goalPos.equals(player.down())) {
                final Vec3 hit = VecUtil.getVeryAccurateHittableHitVec(this.goalPos);
                Utils.sleep(200);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
                new Rotater((hit != null) ? hit : this.goalPos.toVec()).rotate();
                KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
                Utils.sleep(250);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
            }
        }
    }
    
    @Override
    public void shutdown() {
        this.finished = true;
        PizzaClient.rotater = null;
    }
}
