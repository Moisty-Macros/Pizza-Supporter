package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import net.minecraft.entity.item.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.*;
import java.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.*;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;

public class Refuel
{
    public static EntityArmorStand drillNPC;
    
    public static EntityArmorStand findDrillNPC() {
        for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityArmorStand && entity.func_145748_c_().func_150260_c().contains("DRILL MECHANIC")) {
                return (EntityArmorStand)entity;
            }
        }
        return null;
    }
    
    public static void refuel() {
        if (PizzaClient.mc.field_71439_g.field_71070_bA instanceof ContainerChest) {
            Failsafes.writeToWebhook("Refuel");
            Utils.sleep(500);
            Slot drillSlot = null;
            Slot fuelSlot = null;
            float fuelPer = 0.0f;
            for (final Slot slot : PizzaClient.mc.field_71439_g.field_71070_bA.field_75151_b) {
                if (slot.func_75211_c() != null) {
                    final String displayName = StringUtils.func_76338_a(slot.func_75211_c().func_82833_r());
                    if (displayName.contains("Drill")) {
                        if (slot.func_75211_c().func_77973_b() != Items.field_179562_cC) {
                            continue;
                        }
                        drillSlot = slot;
                    }
                    else if (displayName.contains("Volta") || displayName.contains("Oil Barrel")) {
                        fuelSlot = slot;
                        fuelPer = 10.0f;
                    }
                    else {
                        if (!displayName.contains("Biofuel")) {
                            continue;
                        }
                        fuelSlot = slot;
                        fuelPer = 3.0f;
                    }
                }
            }
            if (drillSlot == null || fuelSlot == null) {
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Wasn't able to auto refuel, disabling."));
                PizzaClient.mc.field_71439_g.func_71053_j();
                return;
            }
            int uses = 1;
            for (final String s : ItemUtil.getItemLore(drillSlot.func_75211_c())) {
                if (s.contains("Fuel:")) {
                    final float fuel = (float)Integer.parseInt(s.split("/")[1].split("k")[0]);
                    uses = MathUtil.ceil(fuel / fuelPer);
                    break;
                }
            }
            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, fuelSlot.field_75222_d, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
            Utils.sleep(500);
            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 33, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
            Utils.sleep(1000);
            for (int i = 0; i < uses; ++i) {
                PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, drillSlot.field_75222_d, 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                Utils.sleep(500);
                PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                Utils.sleep(500);
                PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 13, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                Utils.sleep(500);
                PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, drillSlot.field_75222_d, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                Utils.sleep(1000);
            }
            Utils.sleep(2500);
            PizzaClient.mc.field_71439_g.func_71053_j();
            Utils.sleep(1000);
        }
    }
    
    public static void legitRefuel() {
        Utils.sleep(500);
        PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
        final BlockPos forge = new BlockPos(0, 149, -69);
        while (!new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v).equals((Object)forge)) {
            Utils.sleep(1000);
            PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
        }
        Utils.sleep(500);
        AOTVMovement.run(null, CommissionMacro.CRUCIBLE, new BlockPos(-7, 144, -22));
        final EntityArmorStand npc = findDrillNPC();
        if (npc == null) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to find the drill npc to refuel, report this."));
            return;
        }
        new Rotater(npc.func_174824_e(1.0f)).rotate();
        Utils.sleep(400);
        PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)npc);
        Utils.sleep(3000);
        refuel();
        Utils.sleep(1000);
    }
    
    public static void funnyRefuel() {
        Utils.sleep(500);
        PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)Refuel.drillNPC);
        Utils.sleep(3000);
        refuel();
        Utils.sleep(2000);
    }
    
    public enum RefuelState
    {
    }
}
