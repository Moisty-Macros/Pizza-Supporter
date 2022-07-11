package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import qolskyblockmod.pizzaclient.features.macros.builder.macros.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.features.player.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.init.*;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.*;
import qolskyblockmod.pizzaclient.util.rotation.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public class PowderMacro extends NukerMacro
{
    private static int ticks;
    public static final Set<BlockPos> clicked;
    
    @Override
    public void onTick() {
        if (PizzaClient.location == Locations.CHOLLOWS) {
            if (AutoPowderChest.particleVec == null) {
                NukerBase.nuker = this;
                if (PowderMacro.vec != null && VecUtil.calculateInterceptLook(PowderMacro.vec, 4.5f) == null) {
                    PowderMacro.vec = null;
                }
                if (PizzaClient.mc.field_71476_x.func_178782_a() != null) {
                    KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), AutoPowderChest.particleVec == null);
                    if (PizzaClient.mc.field_71462_r == null) {
                        final Block mouse = PizzaClient.mc.field_71441_e.func_180495_p(PizzaClient.mc.field_71476_x.func_178782_a()).func_177230_c();
                        if (mouse == Blocks.field_150486_ae || (mouse == Blocks.field_150357_h && PizzaClient.mc.field_71476_x.func_178782_a().func_177956_o() != 30) || AiMining.isBlockUnmineable(mouse)) {
                            new Rotater(RotationUtil.getYawDifference(RotationUtil.getYawClosestTo90Degrees()) + 90.0f, RotationUtil.getPitchDifference(5.0f)).setRotationAmount(16).rotate();
                        }
                    }
                }
            }
            else {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
            }
        }
    }
    
    @Override
    public void onToggle(final boolean toggled) {
        PowderMacro.clicked.clear();
        if (!PizzaClient.config.nuker) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "You need to enable nuker in order for the hardstone macro to work!"));
            return;
        }
        if (toggled) {
            PizzaClient.config.nukerRotationPackets = true;
            new Rotater(RotationUtil.getYawDifference(RotationUtil.getYawClosestTo90Degrees()), RotationUtil.getPitchDifference(5.0f)).setRotationAmount(16).rotate();
        }
        this.addToggleMessage("Powder Macro");
    }
    
    @Override
    public void onDisable() {
        PowderMacro.vec = null;
    }
    
    @Override
    public void onNukePre() {
        if (PowderMacro.vec != null) {
            final BlockPos pos = new BlockPos(PowderMacro.vec);
            if (AiMining.ores.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
                if (PowderMacro.ticks >= PizzaClient.config.powderMacroTicks) {
                    PowderMacro.clicked.add(pos);
                    PowderMacro.vec = null;
                    PowderMacro.ticks = 0;
                }
                else {
                    ++PowderMacro.ticks;
                }
            }
        }
    }
    
    @Override
    public boolean isBlockValid(final BlockPos pos) {
        if (pos.func_177956_o() <= PizzaClient.mc.field_71439_g.field_70163_u - 1.0) {
            return false;
        }
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        return !PowderMacro.avoid.contains(block) && !PowderMacro.clicked.contains(pos) && !AiMining.isBlockUnmineable(block);
    }
    
    @Override
    public void onNoVecAvailable() {
        PowderMacro.clicked.clear();
    }
    
    @Override
    public Locations getLocation() {
        return Locations.CHOLLOWS;
    }
    
    @Override
    public void warpBack() {
        Locations.CHOLLOWS.warpTo();
    }
    
    static {
        clicked = new HashSet<BlockPos>();
    }
}
