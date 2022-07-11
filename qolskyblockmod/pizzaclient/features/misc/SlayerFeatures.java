package qolskyblockmod.pizzaclient.features.misc;

import java.awt.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.item.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import net.minecraft.init.*;
import net.minecraft.client.settings.*;
import net.minecraft.item.*;

public class SlayerFeatures
{
    private final Color LOW_LEVEL;
    private final Color HIGH_LEVEL;
    private final Color BOSS_COLOR;
    
    public SlayerFeatures() {
        this.LOW_LEVEL = Color.CYAN;
        this.HIGH_LEVEL = new Color(148, 0, 211);
        this.BOSS_COLOR = new Color(140, 0, 0);
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onRenderEntity(final RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (event.entity instanceof EntityArmorStand) {
            final EntityArmorStand entity = (EntityArmorStand)event.entity;
            if (!entity.func_145818_k_()) {
                return;
            }
            final String entityName = StringUtils.func_76338_a(entity.func_95999_t());
            final double x = event.entity.field_70165_t;
            final double y = event.entity.field_70163_u;
            final double z = event.entity.field_70161_v;
            if (PizzaClient.config.allMobEsp) {
                if (entityName.startsWith("[Lv")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 2.0, z - 0.5, x + 0.5, y, z + 0.5), Color.CYAN, 0.6f);
                }
                return;
            }
            if (PizzaClient.config.minibossEsp && !SBInfo.bossSpawned && !entityName.contains("0")) {
                if (entityName.startsWith("Voidcrazed Maniac")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 3.0, z - 0.5, x + 0.5, y, z + 0.5), this.BOSS_COLOR);
                    return;
                }
                if (entityName.startsWith("Voidling Radical")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 3.0, z - 0.5, x + 0.5, y, z + 0.5), this.HIGH_LEVEL);
                    return;
                }
                if (entityName.startsWith("Voidling Devotee")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 3.0, z - 0.5, x + 0.5, y, z + 0.5), this.LOW_LEVEL);
                    return;
                }
                if (entityName.startsWith("Revenant ")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 2.0, z - 0.5, x + 0.5, y, z + 0.5), this.LOW_LEVEL);
                    return;
                }
                if (entityName.startsWith("Atoned Champion")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 2.0, z - 0.5, x + 0.5, y, z + 0.5), this.LOW_LEVEL);
                    return;
                }
                if (entityName.startsWith("Atoned Revenant")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 2.0, z - 0.5, x + 0.5, y, z + 0.5), this.HIGH_LEVEL);
                    return;
                }
                if (entityName.startsWith("Deformed Revenant")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 2.0, z - 0.5, x + 0.5, y, z + 0.5), this.HIGH_LEVEL);
                    return;
                }
                if (entityName.startsWith("Tarantula ")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.66, y - 1.0, z - 0.66, x + 0.66, y - 0.25, z + 0.66), this.LOW_LEVEL);
                    return;
                }
                if (entityName.startsWith("Mutant Tarantula")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.66, y - 1.0, z - 0.66, x + 0.66, y - 0.25, z + 0.66), this.HIGH_LEVEL);
                    return;
                }
                if (entityName.startsWith("Pack Enforcer")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 1.0, z - 0.5, x + 0.5, y, z + 0.5), this.LOW_LEVEL);
                    return;
                }
                if (entityName.startsWith("Sven Follower")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 1.0, z - 0.5, x + 0.5, y, z + 0.5), this.LOW_LEVEL);
                    return;
                }
                if (entityName.startsWith("Sven Alpha")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 1.0, z - 0.5, x + 0.5, y, z + 0.5), this.HIGH_LEVEL);
                    return;
                }
            }
            if (PizzaClient.config.bossEsp && !SBInfo.bossSpawned && !entityName.contains("0")) {
                if (entityName.contains("Sven Packmaster")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 1.0, z - 0.5, x + 0.5, y, z + 0.5), this.BOSS_COLOR);
                }
                else if (entityName.contains("Tarantula Broodfather")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.66, y - 1.0, z - 0.66, x + 0.66, y - 0.25, z + 0.66), this.BOSS_COLOR);
                }
                else if (entityName.contains("Revenant Horror")) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(x - 0.5, y - 2.0, z - 0.5, x + 0.5, y, z + 0.5), this.BOSS_COLOR);
                }
            }
        }
        else if (event.entity instanceof EntityHorse && PizzaClient.config.hideHorses) {
            PizzaClient.mc.field_71441_e.func_72900_e((Entity)event.entity);
        }
    }
    
    public static void useSoulCry() {
        if (PizzaClient.location != Locations.END || !SBInfo.bossSpawned) {
            return;
        }
        final ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
        if (item.func_77973_b() == Items.field_151048_u) {
            final String displayName = item.func_82833_r();
            if (displayName.contains("Atomsplit Katana") || displayName.contains("Vorpal Katana")) {
                KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            }
        }
    }
}
