package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path;

import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.*;
import qolskyblockmod.pizzaclient.util.misc.distance.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.*;
import java.util.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import qolskyblockmod.pizzaclient.util.*;

public final class EntityPath extends PathBase
{
    private Entity entity;
    private final Class<? extends Entity> clazz;
    private final String displayName;
    private final float maxY;
    private final float minY;
    private boolean recalculate;
    
    public static EntityPath findClosest(final Class<? extends Entity> entity) {
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        for (final Entity en : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (en.getClass() == entity) {
                helper.compare(en);
            }
        }
        if (helper.isNotNull()) {
            return new EntityPath(entity, null, helper.closest);
        }
        return null;
    }
    
    public static EntityPath findClosest(final Class<? extends Entity> entity, final String displayName, final float maxY) {
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        for (final Entity en : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (en.getClass() == entity && en.func_70005_c_().contains(displayName) && en.func_174813_aQ().field_72338_b < maxY) {
                helper.compare(en);
            }
        }
        if (helper.isNotNull()) {
            return new EntityPath(entity, displayName, helper.closest, maxY);
        }
        return null;
    }
    
    public static EntityPath findClosest(final Class<? extends Entity> entity, final String displayName, final float minY, final float maxY) {
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        for (final Entity en : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (en.getClass() == entity && en.func_70005_c_().contains(displayName) && en.func_174813_aQ().field_72338_b < maxY && en.func_174813_aQ().field_72338_b > minY) {
                helper.compare(en);
            }
        }
        if (helper.isNotNull()) {
            return new EntityPath(entity, displayName, helper.closest, maxY);
        }
        return null;
    }
    
    public static List<EntityPath> getAllSorted(final Class<? extends Entity> entity, final String displayName, final float minY, final float maxY) {
        final List<EntityPath> paths = new ArrayList<EntityPath>();
        for (final Entity en : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (en.getClass() == entity && en.func_70005_c_().contains(displayName) && en.func_174813_aQ().field_72338_b < maxY && en.func_174813_aQ().field_72338_b > minY) {
                paths.add(new EntityPath(entity, displayName, en, minY, maxY));
            }
        }
        paths.sort(new PathComparator());
        return paths;
    }
    
    public static EntityPath findClosest(final Class<? extends Entity> entity, final String displayName) {
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        for (final Entity en : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (en.getClass() == entity && en.func_70005_c_().contains(displayName)) {
                helper.compare(en);
            }
        }
        if (helper.isNotNull()) {
            return new EntityPath(entity, displayName, helper.closest);
        }
        return null;
    }
    
    public EntityPath(final Class<? extends Entity> clazz, final String displayName, final Entity to) {
        super(new Vec3(to.field_70165_t, to.field_70163_u, to.field_70161_v));
        this.entity = to;
        this.clazz = clazz;
        this.displayName = displayName;
        this.maxY = 255.0f;
        this.minY = 0.0f;
    }
    
    public EntityPath(final Class<? extends Entity> clazz, final String displayName, final Entity to, final float maxY) {
        super(new Vec3(to.field_70165_t, to.field_70163_u, to.field_70161_v));
        this.entity = to;
        this.clazz = clazz;
        this.displayName = displayName;
        this.maxY = maxY;
        this.minY = 0.0f;
    }
    
    public EntityPath(final Class<? extends Entity> clazz, final String displayName, final Entity to, final float maxY, final float minY) {
        super(new Vec3(to.field_70165_t, to.field_70163_u, to.field_70161_v));
        this.entity = to;
        this.clazz = clazz;
        this.displayName = displayName;
        this.maxY = maxY;
        this.minY = minY;
    }
    
    @Override
    public void move() {
        this.recheckForEntity();
        if (this.entity == null || this.entity.field_70128_L || ((EntityLivingBase)this.entity).field_70725_aQ > 0 || !PizzaClient.mc.field_71441_e.field_72996_f.contains(this.entity)) {
            this.shutdown();
            return;
        }
        this.attackEntity();
        if (this.moves.size() == 0) {
            return;
        }
        final BetterBlockPos pos = new BetterBlockPos(this.entity.field_70165_t, this.entity.field_70163_u, this.entity.field_70161_v);
        final BetterBlockPos last = this.moves.get(this.moves.size() - 1);
        if (!pos.equals(last)) {
            if (this.moves.contains(pos)) {
                this.moves.remove(last);
            }
            this.moves.add(pos);
        }
        if (this.isVerticalPassable(this.entity.func_174813_aQ())) {
            this.recalculate = true;
            if (PizzaClient.rotater == null) {
                if (!VecUtil.isFacingAABB(this.entity.func_174813_aQ().func_72314_b(0.25, 0.5, 0.25), 200.0f)) {
                    new Rotater(new Vec3(this.entity.field_70165_t, this.entity.field_70163_u + 1.3, this.entity.field_70161_v)).rotate();
                }
                else {
                    SnapRotater.snapTo(new Vec3(this.entity.field_70165_t, this.entity.field_70163_u + 1.3, this.entity.field_70161_v));
                }
                this.moveToEntity();
            }
            else {
                Movement.disableMovement();
            }
            return;
        }
        if (this.recalculate) {
            Movement.disableMovement();
            this.update();
            this.recalculate = false;
            return;
        }
        if (this.moves.size() != 0) {
            this.useDefaultMovement();
            this.rotateIfNeeded();
        }
    }
    
    public void moveToEntity() {
        Movement.setMovementToForward();
    }
    
    private void recheckForEntity() {
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        if (this.displayName == null) {
            for (final Entity en : new ArrayList<Entity>(PizzaClient.mc.field_71441_e.field_72996_f)) {
                if (en != null) {
                    final AxisAlignedBB aabb = en.func_174813_aQ();
                    if (aabb == null || en.getClass() != this.clazz || aabb.field_72338_b >= this.maxY || aabb.field_72338_b <= this.minY) {
                        continue;
                    }
                    helper.compare(en);
                }
            }
        }
        else {
            for (final Entity entity : new ArrayList<Entity>(PizzaClient.mc.field_71441_e.field_72996_f)) {
                if (entity != null) {
                    final String name = entity.func_70005_c_();
                    if (name == null) {
                        continue;
                    }
                    final AxisAlignedBB aabb2 = entity.func_174813_aQ();
                    if (aabb2 == null || entity.getClass() != this.clazz || !name.contains(this.displayName) || aabb2.field_72338_b >= this.maxY || aabb2.field_72338_b <= this.minY) {
                        continue;
                    }
                    helper.compare(entity);
                }
            }
        }
        if (helper.isNotNull() && !this.entity.equals((Object)helper.closest)) {
            this.update(new BetterBlockPos(helper.closest.field_70165_t, helper.closest.field_70163_u, helper.closest.field_70161_v));
            this.entity = helper.closest;
        }
    }
    
    @Override
    public void initRotater() {
        if (!this.isVerticalPassable(this.entity.func_174813_aQ())) {
            super.initRotater();
        }
    }
    
    @Override
    public boolean addBlock(final PathNode node) {
        return this.getDefaultAddBlock(node);
    }
    
    public void attackEntity() {
        if (PizzaClient.mc.field_71476_x != null && PizzaClient.mc.field_71476_x.field_72308_g != null) {
            if (PizzaClient.mc.field_71476_x.field_72308_g.equals((Object)this.entity)) {
                PlayerUtil.leftClick();
                PizzaClient.mc.field_71441_e.func_72900_e(this.entity);
                this.shutdown();
                Utils.sleep(50);
            }
            else if (PizzaClient.mc.field_71476_x.field_72308_g.getClass() == this.clazz && (this.displayName == null || PizzaClient.mc.field_71476_x.field_72308_g.func_70005_c_().contains(this.displayName))) {
                PlayerUtil.leftClick();
                if (PizzaClient.mc.field_71476_x.field_72308_g != null) {
                    PizzaClient.mc.field_71441_e.func_72900_e(PizzaClient.mc.field_71476_x.field_72308_g);
                }
                Utils.sleep(100);
            }
        }
    }
}
