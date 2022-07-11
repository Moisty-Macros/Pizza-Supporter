package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.*;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import java.util.*;
import qolskyblockmod.pizzaclient.util.rotation.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;

public abstract class PathBase implements IPath
{
    public BetterBlockPos goalPos;
    public BetterBlockPos currentPos;
    public boolean finished;
    public Set<BetterBlockPos> checked;
    public List<BetterBlockPos> moves;
    
    public PathBase(final Vec3 from, final Vec3 to) {
        this.checked = new HashSet<BetterBlockPos>();
        this.moves = new ArrayList<BetterBlockPos>();
        this.currentPos = new BetterBlockPos(from);
        this.goalPos = new BetterBlockPos(to);
    }
    
    public PathBase(final BetterBlockPos from, final BetterBlockPos to) {
        this.checked = new HashSet<BetterBlockPos>();
        this.moves = new ArrayList<BetterBlockPos>();
        this.currentPos = from;
        this.goalPos = to;
    }
    
    public PathBase(final BetterBlockPos to) {
        this.checked = new HashSet<BetterBlockPos>();
        this.moves = new ArrayList<BetterBlockPos>();
        this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        this.goalPos = to;
    }
    
    public PathBase(final Vec3 to) {
        this.checked = new HashSet<BetterBlockPos>();
        this.moves = new ArrayList<BetterBlockPos>();
        this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        this.goalPos = new BetterBlockPos(to);
    }
    
    @Override
    public void shutdown() {
        PathFinder.path = null;
        this.finished = true;
        Pathfinding.unregister();
        PizzaClient.rotater = null;
    }
    
    public final void addMovementJumping(final BetterBlockPos current) {
        final double nextTickX = PizzaClient.mc.field_71439_g.field_70165_t;
        final double nextTickZ = PizzaClient.mc.field_71439_g.field_70161_v;
        final double nextX = PizzaClient.mc.field_71439_g.field_70165_t + PizzaClient.mc.field_71439_g.field_70159_w;
        final double nextZ = PizzaClient.mc.field_71439_g.field_70161_v + PizzaClient.mc.field_71439_g.field_70179_y;
        if (MathUtil.inBetween(nextX, current.field_177962_a + 0.35, current.field_177962_a + 0.65) && MathUtil.inBetween(nextZ, current.field_177961_c + 0.35, current.field_177961_c + 0.65)) {
            Movement.disableMovement();
            return;
        }
        final EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
        if (!MathUtil.inBetween(nextTickZ, current.field_177961_c + 0.35, current.field_177961_c + 0.65)) {
            if (nextTickZ <= current.field_177961_c + 0.35) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                }
            }
            else if (nextTickZ >= current.field_177961_c + 0.65) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                }
            }
        }
        if (!MathUtil.inBetween(nextTickX, current.field_177962_a + 0.35, current.field_177962_a + 0.65)) {
            if (nextTickX <= current.field_177962_a + 0.35) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                }
            }
            else if (nextTickX >= current.field_177962_a + 0.65) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                }
            }
        }
        Movement.endMovement();
    }
    
    public final void addMovement(final BetterBlockPos current) {
        final double nextTickX = PizzaClient.mc.field_71439_g.field_70165_t;
        final double nextTickZ = PizzaClient.mc.field_71439_g.field_70161_v;
        if (!PizzaClient.mc.field_71439_g.field_70122_E) {
            this.addMovementJumping(current);
            return;
        }
        Movement.endMovement();
        final EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
        if (!MathUtil.inBetween(nextTickZ, current.field_177961_c + 0.35, current.field_177961_c + 0.65)) {
            if (nextTickZ <= current.field_177961_c + 0.35) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                }
            }
            else if (nextTickZ >= current.field_177961_c + 0.65) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                }
            }
        }
        if (!MathUtil.inBetween(nextTickX, current.field_177962_a + 0.35, current.field_177962_a + 0.65)) {
            if (nextTickX <= current.field_177962_a + 0.35) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                }
            }
            else if (nextTickX >= current.field_177962_a + 0.65) {
                switch (facing) {
                    case NORTH: {
                        Movement.addMovement(MovementType.LEFT);
                        break;
                    }
                    case SOUTH: {
                        Movement.addMovement(MovementType.RIGHT);
                        break;
                    }
                    case WEST: {
                        Movement.addMovement(MovementType.FORWARDS);
                        break;
                    }
                    case EAST: {
                        Movement.addMovement(MovementType.BACKWARDS);
                        break;
                    }
                }
            }
        }
    }
    
    public final void rotateIfNeeded() {
        if (this.moves.size() >= 2) {
            final BetterBlockPos current = this.moves.get(0);
            if (PizzaClient.mc.field_71439_g.field_70122_E || !MathUtil.inBetween(PizzaClient.mc.field_71439_g.field_70165_t, current.field_177962_a + 0.3, current.field_177962_a + 0.7) || !MathUtil.inBetween(PizzaClient.mc.field_71439_g.field_70161_v, current.field_177961_c + 0.3, current.field_177961_c + 0.7)) {
                final BetterBlockPos next = this.moves.get(1);
                final EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
                if (!current.offset(facing).isSameXandZ(next) && !this.moves.contains(next.offset(facing)) && !this.moves.contains(next.up().offset(facing)) && !this.moves.contains(next.down().offset(facing))) {
                    this.rotateToBlock(next);
                }
            }
        }
    }
    
    public final boolean isVerticalPassable(final AxisAlignedBB aabb) {
        Vec3 position = PlayerUtil.getPositionEyes();
        final Vec3 middle = Utils.getMiddleOfAABB(aabb);
        Vec3 look = PlayerUtil.getLook(middle);
        look = VecUtil.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        BetterBlockPos last = new BetterBlockPos(position);
        for (int i = 0; i < MathUtil.floor(position.func_72438_d(middle) / 0.10000000149011612); ++i) {
            final BetterBlockPos pos = new BetterBlockPos(position);
            if (pos.equals(last)) {
                position = position.func_178787_e(look);
            }
            else {
                if (aabb.func_72318_a(position)) {
                    return true;
                }
                final BetterBlockPos calculated = this.calculatePos(pos);
                if (PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)calculated).func_177230_c() != Blocks.field_150350_a) {
                    return false;
                }
                final BetterBlockPos collidable = calculated.down();
                final Block block = PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)collidable).func_177230_c();
                final Block lastBlock = PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)last).func_177230_c();
                if (block.func_180646_a((World)PizzaClient.mc.field_71441_e, (BlockPos)collidable).field_72337_e > lastBlock.func_180646_a((World)PizzaClient.mc.field_71441_e, (BlockPos)last).field_72337_e + 0.5) {
                    return false;
                }
                last = collidable;
                position = position.func_178787_e(look);
            }
        }
        return true;
    }
    
    public final BetterBlockPos calculatePos(BetterBlockPos pos) {
        BetterBlockPos down = pos.down();
        Block block = PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)down).func_177230_c();
        if (!Utils.uncollidables.contains(block)) {
            return pos;
        }
        if (block instanceof BlockLiquid) {
            return pos.down();
        }
        while (Utils.uncollidables.contains(block = PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)(down = pos.down())).func_177230_c())) {
            if (!down.isBlockLoaded()) {
                return down;
            }
            if (block instanceof BlockLiquid) {
                return down.down();
            }
            pos = down;
        }
        return pos;
    }
    
    public final void useDefaultMovement() {
        final BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        final BetterBlockPos current = this.moves.get(0);
        BetterBlockPos pos = new BetterBlockPos(player.field_177962_a, PizzaClient.mc.field_71439_g.field_70163_u, player.field_177961_c);
        if (this.moves.contains(pos) || this.moves.contains(pos.up()) || this.moves.contains(pos.down())) {
            while (!this.moves.get(0).isSameXandZ(player)) {
                this.moves.remove(0);
            }
            this.moves.remove(0);
            Movement.disableMovement();
            return;
        }
        this.addMovement(current);
        pos = current.down();
        final Block block = pos.getBlock();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, (BlockPos)pos);
        if (PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)player).func_177230_c() instanceof BlockLiquid) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
            Movement.addMovement(player, current);
            return;
        }
        if (PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 0.4, PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockLiquid) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
            Movement.addMovement(player, current);
            return;
        }
        if (block.func_180646_a((World)PizzaClient.mc.field_71441_e, (BlockPos)pos).field_72337_e > PizzaClient.mc.field_71439_g.field_70163_u + 0.6 && PizzaClient.mc.field_71439_g.field_70122_E && !Utils.uncollidables.contains(block)) {
            final double lastMotion = MathUtil.abs(PizzaClient.mc.field_71439_g.field_70159_w) + MathUtil.abs(PizzaClient.mc.field_71439_g.field_70179_y) + 1.0E-9;
            Movement.disableMovement();
            while (lastMotion <= MathUtil.abs(PizzaClient.mc.field_71439_g.field_70159_w) + MathUtil.abs(PizzaClient.mc.field_71439_g.field_70179_y) && PizzaClient.mc.field_71439_g.field_70159_w + PizzaClient.mc.field_71439_g.field_70179_y != 0.0) {
                Movement.disableMovement();
            }
            Utils.sleep(10);
            final double posY = PizzaClient.mc.field_71439_g.field_70163_u;
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
            while (posY == PizzaClient.mc.field_71439_g.field_70163_u) {
                Utils.sleep(1);
            }
            return;
        }
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
        Movement.addMovement(player, current);
    }
    
    public void update() {
        this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        this.checked.clear();
        this.moves.clear();
    }
    
    public void update(final BetterBlockPos goalPos) {
        this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        this.goalPos = goalPos;
        this.checked.clear();
        this.moves.clear();
    }
    
    public final boolean getDefaultAddBlock(final PathNode node) {
        for (final Vec3 directions : BetterBlockPos.FLAT_DIRECTIONS) {
            final BetterBlockPos pos = new BetterBlockPos(node.currentPos.field_177962_a + directions.field_72450_a, node.currentPos.field_177960_b + directions.field_72448_b, node.currentPos.field_177961_c + directions.field_72449_c);
            if (!this.checked.contains(pos)) {
                this.checked.add(pos);
                final BetterBlockPos posUp = pos.up();
                if (this.goalPos.isSameXandZ(pos) && (this.goalPos.equals(pos) || this.goalPos.equals(posUp) || this.goalPos.equals(pos.down()))) {
                    (this.moves = new ArrayList<BetterBlockPos>(node.moves)).add(this.goalPos);
                    return this.finished = true;
                }
                if (!Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)posUp).func_177230_c())) {
                    continue;
                }
                if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)pos).func_177230_c())) {
                    PathFinder.nodes.add(new PathNode(this.calculatePos(pos), node.moves));
                }
                else {
                    if (!Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(node.currentPos.field_177962_a, node.currentPos.field_177960_b + 2, node.currentPos.field_177961_c)).func_177230_c())) {
                        continue;
                    }
                    final Block block = PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.field_177962_a, pos.field_177960_b + 2, pos.field_177961_c)).func_177230_c();
                    if (!Utils.uncollidables.contains(block) || block.func_149669_A() > 1.0) {
                        continue;
                    }
                    PathFinder.nodes.add(new PathNode(posUp, node.moves));
                }
            }
        }
        return false;
    }
    
    public final boolean addBlockForAllDirections(final PathNode node) {
        for (final Vec3 directions : BetterBlockPos.ALL_DIRECTIONS) {
            final BetterBlockPos pos = new BetterBlockPos(node.currentPos.field_177962_a + directions.field_72450_a, node.currentPos.field_177960_b + directions.field_72448_b, node.currentPos.field_177961_c + directions.field_72449_c);
            if (!this.checked.contains(pos)) {
                this.checked.add(pos);
                final BetterBlockPos posUp = pos.up();
                if (this.goalPos.isSameXandZ(pos) && (this.goalPos.equals(pos) || this.goalPos.equals(posUp) || this.goalPos.equals(pos.down()))) {
                    (this.moves = new ArrayList<BetterBlockPos>(node.moves)).add(this.goalPos);
                    return this.finished = true;
                }
                if (!Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)posUp).func_177230_c())) {
                    continue;
                }
                if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)pos).func_177230_c())) {
                    PathFinder.nodes.add(new PathNode(pos, node.moves));
                }
                else {
                    if (!Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(node.currentPos.field_177962_a, node.currentPos.field_177960_b + 2, node.currentPos.field_177961_c)).func_177230_c())) {
                        continue;
                    }
                    final Block block = PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.field_177962_a, pos.field_177960_b + 2, pos.field_177961_c)).func_177230_c();
                    if (!Utils.uncollidables.contains(block) || block.func_149669_A() > 1.0) {
                        continue;
                    }
                    PathFinder.nodes.add(new PathNode(posUp, node.moves));
                }
            }
        }
        return false;
    }
    
    public void initRotater() {
        new Rotater(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotation(this.moves.get(0).toVec()).yaw) - PizzaClient.mc.field_71439_g.field_70177_z, 0.0f).randomPitch().setRotationAmount(17).rotate();
        while (PizzaClient.rotater != null) {
            Utils.sleep(5);
        }
        Utils.sleep(100);
    }
    
    public final void rotateToBlock(final BetterBlockPos next) {
        SnapRotater.snapTo(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotationDifference(next.toVec()).yaw + PizzaClient.mc.field_71439_g.field_70177_z), MathUtil.randomFloat(5.0f));
    }
    
    public final void rotateToBlock() {
        SnapRotater.snapTo(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotationDifference(this.moves.get(1).toVec()).yaw + PizzaClient.mc.field_71439_g.field_70177_z), MathUtil.randomFloat(5.0f));
    }
    
    public final void closeScreenIfOpen() {
        while (PizzaClient.mc.field_71462_r != null) {
            Movement.disableMovement();
            PizzaClient.mc.field_71439_g.func_71053_j();
            Utils.sleep(600);
        }
    }
    
    public final void normalizeMotion() {
    }
}
