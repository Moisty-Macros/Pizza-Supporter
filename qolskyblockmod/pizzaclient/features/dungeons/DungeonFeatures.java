package qolskyblockmod.pizzaclient.features.dungeons;

import java.awt.*;
import net.minecraft.entity.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import net.minecraft.entity.item.*;
import qolskyblockmod.pizzaclient.util.rotation.fake.*;
import net.minecraft.client.entity.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.client.event.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import java.util.*;

public class DungeonFeatures
{
    public static boolean shouldLividsSpawn;
    private static Color c;
    public static String lividName;
    public static long blindnessDuration;
    private final Set<String> bloodMobs;
    private final Set<Entity> killedMobs;
    
    public DungeonFeatures() {
        this.bloodMobs = new HashSet<String>(Arrays.asList("Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "WanderingSoul"));
        this.killedMobs = new HashSet<Entity>();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onRenderLiving(final RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (PizzaClient.location != Locations.DUNGEON) {
            return;
        }
        if (event.entity instanceof EntityArmorStand) {
            final String name = StringUtils.func_76338_a(event.entity.func_95999_t());
            if (PizzaClient.config.starredMobsEsp && name.startsWith("\u272f ") && name.contains("\u2764")) {
                final Vec3 entityPos = event.entity.func_174824_e(1.0f);
                if (Utils.getXandZDistanceSquared(entityPos.field_72450_a, entityPos.field_72449_c) < PizzaClient.config.starredMobsEspRange * PizzaClient.config.starredMobsEspRange) {
                    if (name.contains("Lurker") || name.contains("Dreadlord") || name.contains("Souleater") || name.contains("Zombie") || name.contains("Skeleton") || name.contains("Skeletor") || name.contains("Sniper") || name.contains("Super Archer") || name.contains("Spider") || name.contains("Fels") || name.contains("Withermancer")) {
                        RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u - 2.0, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u, event.entity.field_70161_v + 0.5), PizzaClient.config.starredMobsEspColor, 3.0f);
                    }
                    else if (!name.contains("Shadow Assassin")) {
                        RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u - 2.0, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u, event.entity.field_70161_v + 0.5), new Color(120, 0, 15), 4.5f);
                    }
                }
                return;
            }
            if (PizzaClient.config.dungeonKeyEsp) {
                if (name.equals("Wither Key")) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u + 2.4, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u + 0.9, event.entity.field_70161_v + 0.5), new Color(20, 20, 20), 3.0f);
                    return;
                }
                if (name.equals("Blood Key")) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u + 2.4, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u + 0.9, event.entity.field_70161_v + 0.5), new Color(180, 0, 0), 3.0f);
                    return;
                }
            }
            if (PizzaClient.config.hideSuperboom && (name.equals("Revive Stone") || name.startsWith("Superboom TNT"))) {
                PizzaClient.mc.field_71441_e.func_72900_e((Entity)event.entity);
                return;
            }
            if (DungeonFeatures.lividName != null && event.entity.func_70005_c_().startsWith(DungeonFeatures.lividName)) {
                if (PizzaClient.config.lividAura) {
                    FakeRotation.rotationVec = event.entity.func_174824_e(1.0f);
                }
                if (PizzaClient.config.lividEsp) {
                    RenderUtil.drawFilledEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u - 2.0, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u, event.entity.field_70161_v + 0.5), DungeonFeatures.c);
                }
            }
        }
        else if (event.entity instanceof EntityOtherPlayerMP) {
            final String entityName = event.entity.func_70005_c_();
            if (PizzaClient.config.bloodTriggerBot) {
                for (final String s : this.bloodMobs) {
                    if (entityName.contains(s)) {
                        if (this.killedMobs.contains(event.entity)) {
                            continue;
                        }
                        if (!VecUtil.isFacingAABB(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u + 2.0, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u - 1.0, event.entity.field_70161_v + 0.5), 25.0f)) {
                            continue;
                        }
                        KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i());
                        this.killedMobs.add((Entity)event.entity);
                        return;
                    }
                }
            }
            if (PizzaClient.config.alwaysAimAtSpiritBear && entityName.startsWith("Spirit Bear")) {
                FakeRotation.rotationVec = ((event.entity.field_70163_u % 1.0 == 0.0) ? event.entity.func_174824_e(1.0f) : new Vec3(event.entity.field_70165_t, 69.5, event.entity.field_70161_v));
                return;
            }
            if (entityName.startsWith("Shadow Assassin")) {
                if (PizzaClient.config.showHiddenMobs && event.entity.func_82150_aj()) {
                    event.entity.func_82142_c(false);
                }
                if (PizzaClient.config.starredMobsEsp) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5, event.entity.field_70163_u + 2.0, event.entity.field_70161_v - 0.5, event.entity.field_70165_t + 0.5, event.entity.field_70163_u, event.entity.field_70161_v + 0.5), new Color(120, 0, 15), 5.0f);
                }
            }
        }
        else if (event.entity instanceof EntityEnderman) {
            if (PizzaClient.config.showHiddenMobs && event.entity.func_82150_aj()) {
                event.entity.func_82142_c(false);
            }
        }
        else if (event.entity instanceof EntityBat && PizzaClient.config.batEsp && event.entity.func_110138_aP() == 100.0f) {
            RenderUtil.drawFilledEsp(event.entity.func_174813_aQ(), PizzaClient.config.batEspColor);
        }
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onEntityJoin(final EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityOtherPlayerMP && PizzaClient.location == Locations.DUNGEON && PizzaClient.config.spiritBearAura && event.entity.func_70005_c_().startsWith("Spirit Bear")) {
            FakeRotation.leftClick = true;
            FakeRotation.rotationVec = new Vec3(event.entity.field_70165_t, 69.5, event.entity.field_70161_v);
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        DungeonFeatures.lividName = null;
        DungeonFeatures.blindnessDuration = 0L;
        this.killedMobs.clear();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onFogDensity(final EntityViewRenderEvent.FogDensity event) {
        if (PizzaClient.mc.field_71441_e == null) {
            return;
        }
        if (PizzaClient.mc.field_71439_g.func_70644_a(Potion.field_76440_q) && (PizzaClient.config.lividEsp || PizzaClient.config.lividAura) && DungeonFeatures.shouldLividsSpawn) {
            if (DungeonFeatures.blindnessDuration == 0L) {
                DungeonFeatures.blindnessDuration = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - DungeonFeatures.blindnessDuration >= 600L) {
                getLivid();
            }
        }
        if (PizzaClient.config.antiBlindness) {
            event.density = 0.0f;
            event.setCanceled(true);
        }
    }
    
    private static void getLivid() {
        EnumChatFormatting chatColor = null;
        switch ((EnumDyeColor)PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(6, 109, 43)).func_177229_b((IProperty)BlockStainedGlass.field_176547_a)) {
            case WHITE: {
                chatColor = EnumChatFormatting.WHITE;
                DungeonFeatures.c = new Color(255, 250, 250);
                break;
            }
            case PINK: {
                chatColor = EnumChatFormatting.LIGHT_PURPLE;
                DungeonFeatures.c = Color.MAGENTA;
                break;
            }
            case RED: {
                chatColor = EnumChatFormatting.RED;
                DungeonFeatures.c = new Color(200, 0, 0);
                break;
            }
            case SILVER: {
                chatColor = EnumChatFormatting.GRAY;
                DungeonFeatures.c = new Color(180, 180, 180);
                break;
            }
            case GRAY: {
                chatColor = EnumChatFormatting.GRAY;
                DungeonFeatures.c = new Color(100, 100, 100);
                break;
            }
            case GREEN: {
                chatColor = EnumChatFormatting.DARK_GREEN;
                DungeonFeatures.c = new Color(34, 100, 34);
                break;
            }
            case LIME: {
                chatColor = EnumChatFormatting.GREEN;
                DungeonFeatures.c = new Color(0, 245, 0);
                break;
            }
            case BLUE: {
                chatColor = EnumChatFormatting.BLUE;
                DungeonFeatures.c = new Color(0, 0, 225);
                break;
            }
            case PURPLE: {
                chatColor = EnumChatFormatting.DARK_PURPLE;
                DungeonFeatures.c = new Color(128, 0, 160);
                break;
            }
            case YELLOW: {
                chatColor = EnumChatFormatting.YELLOW;
                DungeonFeatures.c = new Color(245, 245, 0);
                break;
            }
            case MAGENTA: {
                chatColor = EnumChatFormatting.LIGHT_PURPLE;
                DungeonFeatures.c = new Color(240, 0, 240);
                break;
            }
            default: {
                DungeonFeatures.c = null;
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.DARK_RED + "ERROR: " + EnumChatFormatting.RED + "Could not find the glass color! Please report this. The unknown Glass Color is: " + EnumChatFormatting.GOLD + ((EnumDyeColor)PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(205, 109, 242)).func_177229_b((IProperty)BlockStainedGlass.field_176547_a)).name().toLowerCase()));
                return;
            }
        }
        for (final Entity entity : PizzaClient.mc.field_71441_e.func_72910_y()) {
            if (entity.func_70005_c_().startsWith(chatColor + "\ufd3e " + chatColor + "§lLivid")) {
                DungeonFeatures.lividName = entity.func_70005_c_();
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > Real Livid: " + DungeonFeatures.lividName));
                DungeonFeatures.shouldLividsSpawn = false;
            }
        }
    }
    
    public static void clipGhostBlocks() {
        final List<BlockPos> ghostBlockCoords = new ArrayList<BlockPos>(Arrays.asList(new BlockPos(299, 168, 243), new BlockPos(299, 168, 244), new BlockPos(299, 168, 246), new BlockPos(299, 168, 247), new BlockPos(300, 168, 247), new BlockPos(300, 168, 246), new BlockPos(300, 168, 244), new BlockPos(300, 168, 243), new BlockPos(298, 168, 247), new BlockPos(298, 168, 246), new BlockPos(298, 168, 244), new BlockPos(298, 168, 243), new BlockPos(287, 167, 240), new BlockPos(288, 167, 240), new BlockPos(289, 167, 240), new BlockPos(290, 167, 240), new BlockPos(291, 167, 240), new BlockPos(292, 167, 240), new BlockPos(293, 167, 240), new BlockPos(294, 167, 240), new BlockPos(295, 167, 240), new BlockPos(290, 167, 239), new BlockPos(291, 167, 239), new BlockPos(292, 167, 239), new BlockPos(293, 167, 239), new BlockPos(294, 167, 239), new BlockPos(295, 167, 239), new BlockPos(290, 166, 239), new BlockPos(291, 166, 239), new BlockPos(292, 166, 239), new BlockPos(293, 166, 239), new BlockPos(294, 166, 239), new BlockPos(295, 166, 239), new BlockPos(290, 166, 240), new BlockPos(291, 166, 240), new BlockPos(292, 166, 240), new BlockPos(293, 166, 240), new BlockPos(294, 166, 240), new BlockPos(295, 166, 240)));
        for (final BlockPos pos : ghostBlockCoords) {
            PizzaClient.mc.field_71441_e.func_175698_g(new BlockPos(pos.func_177958_n() - 199, pos.func_177956_o(), pos.func_177952_p() - 199));
        }
    }
    
    static {
        DungeonFeatures.shouldLividsSpawn = false;
    }
}
