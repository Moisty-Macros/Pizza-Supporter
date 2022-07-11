package qolskyblockmod.pizzaclient.features.skills;

import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import net.minecraft.entity.item.*;
import java.awt.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraftforge.event.world.*;
import qolskyblockmod.pizzaclient.core.events.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import qolskyblockmod.pizzaclient.core.guioverlay.*;
import qolskyblockmod.pizzaclient.util.graphics.*;

public class MiningFeatures
{
    private static boolean sayCoordsBal;
    protected static boolean sayCoordsFairyGrotto;
    private static boolean sayCoordsCorleone;
    public static boolean foundCorleone;
    protected static final ArrayList<String> miningCoords;
    
    public MiningFeatures() {
        new MiningElement();
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onRenderEntity(final RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (PizzaClient.location == Locations.CHOLLOWS || PizzaClient.location == Locations.DWARVENMINES) {
            final double x = event.entity.field_70165_t;
            final double y = event.entity.field_70163_u;
            final double z = event.entity.field_70161_v;
            if (event.entity instanceof EntityArmorStand) {
                final EntityArmorStand entity = (EntityArmorStand)event.entity;
                if (!entity.func_145818_k_()) {
                    return;
                }
                final String entityName = StringUtils.func_76338_a(entity.func_95999_t());
                if (PizzaClient.config.balEsp && entityName.equals("[Lv100] Bal ???\u2764")) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 4.0, y - 10.0, z - 4.0, x + 4.0, y, z + 4.0), Color.RED, 5.0f);
                    if (MiningFeatures.sayCoordsBal) {
                        Utils.playSound("random.orb", 0.5);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Bal in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
                        MiningFeatures.miningCoords.add("§cBal: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
                        MiningFeatures.sayCoordsBal = false;
                    }
                    return;
                }
                if (PizzaClient.config.butterflyEsp && entityName.startsWith("[Lv100] Butterfly ")) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.35, y + 0.75, z - 0.35, x + 0.35, y, z + 0.35), new Color(255, 100, 255), 2.0f);
                    if (MiningFeatures.sayCoordsFairyGrotto) {
                        Utils.playSound("random.orb", 0.5);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Fairy Grotto in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
                        MiningFeatures.miningCoords.add("§dFairy Grotto: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
                        MiningFeatures.sayCoordsFairyGrotto = false;
                    }
                    return;
                }
                if (PizzaClient.config.corleonePing && entityName.startsWith("[Lv200] Boss Corleone ") && MiningFeatures.sayCoordsCorleone) {
                    Utils.playSound("random.orb", 0.5);
                    PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Boss Corleone in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
                    MiningFeatures.miningCoords.add("§bBoss Corleone: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
                    MiningFeatures.sayCoordsCorleone = false;
                }
            }
            else if (event.entity instanceof EntityOtherPlayerMP) {
                final String entityName2 = event.entity.func_70005_c_();
                if (entityName2.equals("Team Treasurite") && PizzaClient.config.teamTresuriteEsp) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.5, y + 2.0, z - 0.5, x + 0.5, y, z + 0.5), new Color(255, 255, 51), 2.0f);
                }
                if (PizzaClient.config.goblinEsp) {
                    if (entityName2.startsWith("Weakling")) {
                        RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.5, y + 2.0, z - 0.5, x + 0.5, y, z + 0.5), new Color(130, 67, 0), 2.0f);
                        return;
                    }
                    if (entityName2.startsWith("Goblin")) {
                        RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.5, y + 2.0, z - 0.5, x + 0.5, y, z + 0.5), new Color(130, 67, 0), 2.0f);
                    }
                }
                if (PizzaClient.config.hideGoldenGoblin && entityName2.startsWith("Goblin") && Utils.isGoldenGoblin((EntityOtherPlayerMP)event.entity)) {
                    PizzaClient.mc.field_71441_e.func_72900_e((Entity)event.entity);
                }
            }
            else if (event.entity instanceof EntitySlime) {
                if (event.entity instanceof EntityMagmaCube) {
                    if (PizzaClient.config.yogEsp) {
                        RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.8, y + 1.66, z - 0.8, x + 0.8, y, z + 0.8), new Color(175, 34, 34), 2.0f);
                    }
                }
                else if (PizzaClient.config.sludgeEsp) {
                    RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.8, y + 1.66, z - 0.8, x + 0.8, y, z + 0.8), new Color(50, 200, 50), 2.0f);
                }
            }
            else if (event.entity instanceof EntityIronGolem && PizzaClient.config.ironGolemEsp) {
                RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.6, y + 2.5, z - 0.6, x + 0.6, y, z + 0.6), new Color(0, 0, 111), 2.0f);
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (PizzaClient.location == Locations.CHOLLOWS && PizzaClient.config.gemstoneScanner) {
            for (final Map.Entry<BlockPos, Color> map : WorldScanner.stainedGlass.entrySet()) {
                RenderUtil.drawOutlinedEsp(map.getKey(), map.getValue(), PizzaClient.config.gemstoneEspSize / 16.0f);
            }
            if (PizzaClient.config.glassPanesEsp) {
                for (final Map.Entry<BlockPos, Color> map : WorldScanner.glassPanes.entrySet()) {
                    RenderUtil.drawOutlinedEsp(map.getKey(), map.getValue(), PizzaClient.config.pinkGlassPanesEspSize / 16.0f);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        MiningFeatures.sayCoordsBal = true;
        MiningFeatures.sayCoordsFairyGrotto = true;
        MiningFeatures.sayCoordsCorleone = true;
        MiningFeatures.foundCorleone = false;
        MiningFeatures.miningCoords.clear();
        WorldScanner.stainedGlass.clear();
        WorldScanner.glassPanes.clear();
    }
    
    @SubscribeEvent
    public void onBlockChange(final BlockChangeEvent event) {
        if (PizzaClient.config.gemstoneScanner && PizzaClient.location == Locations.CHOLLOWS) {
            final Block block = event.oldState.func_177230_c();
            if (block == Blocks.field_150399_cn) {
                WorldScanner.stainedGlass.remove(event.pos);
            }
            else if (block == Blocks.field_150397_co) {
                WorldScanner.glassPanes.remove(event.pos);
            }
        }
    }
    
    static {
        MiningFeatures.sayCoordsBal = true;
        MiningFeatures.sayCoordsFairyGrotto = true;
        MiningFeatures.sayCoordsCorleone = true;
        MiningFeatures.foundCorleone = false;
        miningCoords = new ArrayList<String>();
    }
    
    public static class MiningElement extends GuiElement
    {
        private static final String[] locations;
        
        public MiningElement() {
            super("Mining List", new GuiLocation(100, 30));
            GuiManager.registerElement(this);
        }
        
        @Override
        public void render() {
            if (this.getToggled()) {
                for (int i = 0; i < MiningFeatures.miningCoords.size(); ++i) {
                    FontUtil.drawString(MiningFeatures.miningCoords.get(i), this.getActualX(), this.getActualY() + i * PizzaClient.mc.field_71466_p.field_78288_b, 16777215);
                }
            }
        }
        
        @Override
        public void demoRender() {
            for (int i = 0; i < MiningElement.locations.length; ++i) {
                FontUtil.drawString(MiningElement.locations[i], this.getActualX(), this.getActualY() + i * PizzaClient.mc.field_71466_p.field_78288_b, 16777215);
            }
        }
        
        @Override
        public boolean getToggled() {
            return PizzaClient.config.coordsList && PizzaClient.location == Locations.CHOLLOWS;
        }
        
        @Override
        public int getHeight() {
            return PizzaClient.mc.field_71466_p.field_78288_b * 3;
        }
        
        @Override
        public int getWidth() {
            return PizzaClient.mc.field_71466_p.func_78256_a("Boss Corleone: x -1000, y 200, z -1000 ");
        }
        
        static {
            locations = new String[] { "§dFairy Grotto: §ax 1000, y 256, z 1000", "§bBoss Corleone: §ax 1000, y 256, z 1000", "§cBal: §ax 1000, y 256, z 1000" };
        }
    }
}
