package qolskyblockmod.pizzaclient.features.macros.builder;

import net.minecraftforge.fml.relauncher.*;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.*;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.*;
import qolskyblockmod.pizzaclient.features.macros.farming.*;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.*;
import qolskyblockmod.pizzaclient.features.macros.misc.*;
import qolskyblockmod.pizzaclient.core.events.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.features.macros.ai.*;
import net.minecraft.client.settings.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;

@SideOnly(Side.CLIENT)
public class MacroBuilder
{
    public static final Macro[] macros;
    public static final FarmingMacro[] farmingMacros;
    public static Macro currentMacro;
    public static boolean toggled;
    public static Vec3 lastPos;
    public static MacroState state;
    
    public MacroBuilder() {
        this.registerFarmingMacro(new AutoF11Macro(), 1);
        this.registerFarmingMacro(new SugarCaneMacro(), 2);
        this.registerFarmingMacro(new SShapedMacro(), 3);
        this.registerFarmingMacro(new CocoaBeanMacro(), 4);
        this.registerMacro(new MithrilMacro(), 2);
        this.registerMacro(new ForagingMacro(), 3);
        this.registerMacro(new PowderMacro(), 4);
        this.registerMacro(new Nuker(), 5);
        this.registerMacro(new CropAura(), 6);
        this.registerMacro(new CommissionMacro(), 7);
        this.registerMacro(new FishingMacro(), 8);
    }
    
    private void registerMacro(final Macro macro, final int mode) {
        MacroBuilder.macros[mode] = macro;
    }
    
    private void registerFarmingMacro(final FarmingMacro macro, final int mode) {
        MacroBuilder.farmingMacros[mode] = macro;
    }
    
    @SubscribeEvent
    public void onTick(final TickStartEvent event) {
        if (MacroBuilder.toggled) {
            if (PizzaClient.mc.field_71462_r != null) {
                unpressMovement();
                return;
            }
            if (MacroBuilder.state == null) {
                MacroBuilder.state = MacroState.ACTIVE;
            }
            switch (MacroBuilder.state) {
                case ACTIVE: {
                    if (MacroBuilder.currentMacro.miscThread.isAlive()) {
                        MacroBuilder.state = MacroState.NEW_THREAD;
                        return;
                    }
                    if (PlayerUtil.warpToSkyblock() || (MacroBuilder.currentMacro.getLocation() != null && PizzaClient.location != MacroBuilder.currentMacro.getLocation())) {
                        MacroBuilder.currentMacro.enqueueFailsafeThread(MacroBuilder::warpBack);
                        return;
                    }
                    checkFailsafes();
                    checkAndUpdatePosition();
                    Failsafes.playerInRange();
                    if (PizzaClient.config.sneakWhenUsingMacro) {
                        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
                    }
                    MacroBuilder.currentMacro.onTick();
                    break;
                }
                case NEW_THREAD: {
                    if (!MacroBuilder.currentMacro.miscThread.isAlive()) {
                        MacroBuilder.state = MacroState.ACTIVE;
                        return;
                    }
                    if (PlayerUtil.warpToSkyblock() || (MacroBuilder.currentMacro.getLocation() != null && PizzaClient.location != MacroBuilder.currentMacro.getLocation())) {
                        MacroBuilder.currentMacro.enqueueFailsafeThread(MacroBuilder::warpBack);
                        return;
                    }
                    checkFailsafes();
                    checkAndUpdatePosition();
                    break;
                }
                case FAILSAFE: {
                    if (!MacroBuilder.currentMacro.miscThread.isAlive()) {
                        MacroBuilder.state = MacroState.ACTIVE;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public static void onKey() {
        MacroBuilder.state = MacroState.ACTIVE;
        if (MacroBuilder.currentMacro != null) {
            if (MacroBuilder.currentMacro.miscThread == null) {
                MacroBuilder.currentMacro.miscThread = new Thread(() -> {});
            }
            else {
                MacroBuilder.currentMacro.terminateIfAlive();
            }
        }
        if (PizzaClient.config.macroKey == 1 || (PizzaClient.config.macroKey == 0 && PizzaClient.config.farmingMacroKey != 0)) {
            MacroBuilder.currentMacro = MacroBuilder.farmingMacros[PizzaClient.config.farmingMacroKey];
        }
        else {
            MacroBuilder.currentMacro = MacroBuilder.macros[PizzaClient.config.macroKey];
        }
        if (PizzaClient.location == Locations.DUNGEON) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Can't use macros in dungeons."));
            MacroBuilder.toggled = false;
            return;
        }
        if (MacroBuilder.currentMacro == null) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "You need to change the macro key mode in order for the macro to work!"));
            MacroBuilder.toggled = false;
            return;
        }
        MacroBuilder.toggled = !MacroBuilder.toggled;
        MacroBuilder.currentMacro.onToggle(MacroBuilder.toggled);
        if (!MacroBuilder.toggled) {
            unpressMovement();
            PizzaClient.rotater = null;
            MacroBuilder.currentMacro.onDisable();
        }
        else {
            updatePosition();
            MacroBuilder.currentMacro.onMove();
            MacroBuilder.currentMacro.onTick();
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        if (MacroBuilder.toggled) {
            if (MacroBuilder.state != MacroState.FAILSAFE) {
                MacroBuilder.currentMacro.terminateIfAlive();
                MacroBuilder.currentMacro.onDisable();
            }
            unpressMovement();
            if (PizzaClient.config.disableOnWorldLoad) {
                MacroBuilder.toggled = false;
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Detected world change, disabling the macro."));
                new Thread(() -> Failsafes.writeToWebhook("Detected World Change")).start();
            }
        }
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onOpenGui(final GuiOpenEvent event) {
        if (event.gui instanceof GuiDisconnected && (MacroBuilder.toggled || PizzaClient.config.autoReconnect)) {
            this.reconnect();
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (MacroBuilder.toggled) {
            MacroBuilder.currentMacro.onRender();
        }
    }
    
    private static void unpressMovement() {
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
    }
    
    private void reconnect() {
        FMLClientHandler.instance().connectToServer((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), new ServerData("Hypixel", "mc.hypixel.net", false));
    }
    
    private static void warpBack() {
        Locations.warpToSb();
        if (MacroBuilder.currentMacro.getLocation() != null) {
            Utils.sleep(2000);
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Going back to " + MacroBuilder.currentMacro.getLocation().toString() + "..."));
            Failsafes.writeToWebhook("Warped Out");
            MacroBuilder.currentMacro.warpBack();
            Utils.sleep(1500);
        }
        else {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "No warp back for the current macro, disabling macro."));
            MacroBuilder.currentMacro.terminateIfAlive();
            MacroBuilder.toggled = false;
        }
    }
    
    public static void updatePosition() {
        MacroBuilder.lastPos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
    }
    
    private static void checkFailsafes() {
        if (MacroBuilder.currentMacro.applyFailsafes()) {
            if (PizzaClient.config.optimalFailsafes) {
                PizzaClient.config.bedrockBoxFailsafe = MacroBuilder.currentMacro.applyBedrockFailsafe();
                PizzaClient.config.positionChangeFailsafe = MacroBuilder.currentMacro.applyPositionFailsafe();
                PizzaClient.config.rotationFailsafe = MacroBuilder.currentMacro.applyRotationFailsafe();
            }
            Failsafes.checkBedrockBox();
        }
    }
    
    private static void checkAndUpdatePosition() {
        if (MacroBuilder.lastPos == null) {
            MacroBuilder.lastPos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
            MacroBuilder.currentMacro.onMove();
        }
        else if (MacroBuilder.lastPos.field_72450_a != PizzaClient.mc.field_71439_g.field_70165_t || MacroBuilder.lastPos.field_72449_c != PizzaClient.mc.field_71439_g.field_70161_v || MacroBuilder.lastPos.field_72448_b != PizzaClient.mc.field_71439_g.field_70163_u) {
            MacroBuilder.lastPos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
            MacroBuilder.currentMacro.onMove();
        }
    }
    
    public static void disable() {
        MacroBuilder.currentMacro.terminateIfAlive();
        MacroBuilder.toggled = false;
    }
    
    static {
        macros = new Macro[10];
        farmingMacros = new FarmingMacro[10];
    }
}
