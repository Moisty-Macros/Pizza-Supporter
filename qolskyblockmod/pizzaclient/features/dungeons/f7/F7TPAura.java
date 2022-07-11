package qolskyblockmod.pizzaclient.features.dungeons.f7;

import qolskyblockmod.pizzaclient.features.player.*;
import qolskyblockmod.pizzaclient.util.misc.distance.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;

public class F7TPAura
{
    public static void onFlyChecksDisabled() {
        TPAuraHelper.update();
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        if (PizzaClient.mc.field_71439_g.field_70163_u > 210.0) {
            for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("CLICK HERE")) {
                    helper.compare(entity);
                }
            }
            if (helper.isNotNull()) {
                final Entity entity2 = helper.closest;
                final Entity entity3;
                TPAuraHelper.runPathfinder(new BetterBlockPos(entity2.field_70165_t, entity2.field_70163_u, entity2.field_70161_v), () -> {
                    SnapRotater.snapTo(entity3);
                    PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, entity3);
                    TPAuraHelper.runPathfinder(getClosestPlate());
                });
            }
        }
        else {
            for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("CLICK HERE")) {
                    final double x = entity.field_70165_t;
                    final double y = entity.field_70163_u;
                    final double z = entity.field_70161_v;
                    if (PizzaClient.mc.field_71441_e.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB(x - 3.0, y - 3.0, z - 3.0, x + 3.0, y + 3.0, z + 3.0)).size() != 0) {
                        continue;
                    }
                    helper.compare(entity);
                }
            }
            if (helper.isNotNull()) {
                SnapRotater.snapTo(helper.closest);
                PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, helper.closest);
            }
        }
    }
    
    private static BetterBlockPos getClosestPlate() {
        final BlockPos left = new BlockPos(94, 224, 41);
        final BlockPos right = new BlockPos(52, 224, 41);
        final Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        return (Utils.squareDistanceToBlockPos(player, left) > Utils.squareDistanceToBlockPos(player, right)) ? new BetterBlockPos(right) : new BetterBlockPos(left);
    }
}
