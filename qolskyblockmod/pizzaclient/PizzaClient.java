package qolskyblockmod.pizzaclient;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import java.io.*;
import qolskyblockmod.pizzaclient.core.config.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.*;
import qolskyblockmod.pizzaclient.core.guioverlay.*;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.*;
import qolskyblockmod.pizzaclient.listeners.*;
import qolskyblockmod.pizzaclient.features.skills.*;
import qolskyblockmod.pizzaclient.features.keybinds.*;
import qolskyblockmod.pizzaclient.features.dungeons.f7.*;
import qolskyblockmod.pizzaclient.features.macros.builder.*;
import qolskyblockmod.pizzaclient.features.misc.*;
import qolskyblockmod.pizzaclient.features.player.*;
import qolskyblockmod.pizzaclient.features.dungeons.*;
import net.minecraftforge.fml.client.registry.*;
import qolskyblockmod.pizzaclient.util.handler.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.command.*;
import qolskyblockmod.pizzaclient.commands.*;
import qolskyblockmod.pizzaclient.util.graphics.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.*;
import net.minecraftforge.common.*;
import qolskyblockmod.pizzaclient.core.events.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import net.minecraft.item.*;
import net.minecraftforge.event.world.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.*;
import net.minecraftforge.client.event.*;
import qolskyblockmod.pizzaclient.gui.*;
import net.minecraft.client.gui.*;
import qolskyblockmod.pizzaclient.util.rotation.fake.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.*;
import com.google.gson.*;

@Mod(name = "Pizza Client", modid = "pizzaclient", version = "1.1.3", acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
@SideOnly(Side.CLIENT)
public class PizzaClient
{
    public static final String MODID = "pizzaclient";
    public static final String VERSION = "1.1.3";
    public static final String NAME = "Pizza Client";
    public static final Gson gson;
    public static final Minecraft mc;
    public static final String username;
    public static final KeyBinding[] keyBindings;
    public static final String modMessage = "PizzaClient > ";
    public static final File modDir;
    public static Config config;
    public static GuiScreen displayScreen;
    public static Locations location;
    public static JsonObject response;
    public static IRotater rotater;
    private static boolean joinSkyblock;
    
    public static void onStartGame() {
        if (!PizzaClient.modDir.exists()) {
            PizzaClient.modDir.mkdirs();
        }
        PizzaClient.config = new Config();
        PizzaClient.mc.func_110432_I().func_148254_d();
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        new Thread(() -> {
            try {
                PizzaClient.response = Utils.getJson("https://gist.githubusercontent.com/PizzaboiBestLegit/e86e41c230949e51309c621548be55aa/raw/pizzaclient").getAsJsonObject();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            QuizAura.loadSolutions();
            RainbowString.updateList();
        }).start();
    }
    
    private void initConfigFiles() {
        register(new GuiManager());
        register(new MacroKeybind());
        new MithrilMarkers();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        this.initConfigFiles();
        register(this);
        register(new BlockAbility());
        register(new ChatListener());
        register(new GuiFeatures());
        register(new DungeonFeatures());
        register(new MiningFeatures());
        register(new FunnyTerminals());
        register(new FunnyEnchanting());
        register(new WorldScanner());
        register(new SlayerFeatures());
        register(new SBInfo());
        register(new KeybindFeatures());
        register(new ChestESP());
        register(new FunnyDevice());
        register(new AutoPowderChest());
        register(new AutoBookCombine());
        register(new MacroBuilder());
        register(new HarpSolver());
        register(new MonolithESP());
        register(new IceFillQol());
        register(new QuizAura());
        register(new AutoJerryBox());
        register(new SuperboomAura());
        PizzaClient.keyBindings[0] = new KeyBinding("Right Click Autoclicker", 45, "Pizza Client");
        PizzaClient.keyBindings[1] = new KeyBinding("Ghost Blocks", 34, "Pizza Client");
        PizzaClient.keyBindings[2] = new KeyBinding("Auto Wardrobe", 48, "Pizza Client");
        PizzaClient.keyBindings[3] = new KeyBinding("ToggleSprint", 23, "Pizza Client");
        PizzaClient.keyBindings[4] = new KeyBinding("Quick Open Config", 54, "Pizza Client");
        PizzaClient.keyBindings[5] = new KeyBinding("Auto Item Key Toggle", 35, "Pizza Client");
        PizzaClient.keyBindings[6] = new KeyBinding("Macro Key", 33, "Pizza Client");
        PizzaClient.keyBindings[7] = new KeyBinding("Mithril Markers", 50, "Pizza Client");
        PizzaClient.keyBindings[8] = new KeyBinding("Any Item With Anything", 19, "Pizza Client");
        for (final KeyBinding keyBinding : PizzaClient.keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
        if (PizzaClient.response != null) {
            Blacklist.shittersOut();
        }
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        registerCommand((ICommand)new PizzaClientGuiCommand());
        registerCommand((ICommand)new SilencerCommand());
        registerCommand((ICommand)new CustomToggleSprintCommand());
        registerCommand((ICommand)new ChatSpammerCommand());
        registerCommand((ICommand)new ItemMacroCommand());
        registerCommand((ICommand)new BlockAbilityCommand());
        registerCommand((ICommand)new ArabFunnyCommand());
        registerCommand((ICommand)new PathfindCommand());
        registerCommand((ICommand)new AotvTestCommand());
        registerCommand((ICommand)new AutoPetCommand());
        registerCommand((ICommand)new SetYawCommand());
        registerCommand((ICommand)new ReloadNamesCommand());
        FontUtil.init();
        Pathfinding.instance = new Pathfinding();
    }
    
    public static void onTick() {
        if (PizzaClient.displayScreen != null) {
            PizzaClient.mc.func_147108_a(PizzaClient.displayScreen);
            PizzaClient.displayScreen = null;
            return;
        }
        if (PizzaClient.mc.field_71441_e == null) {
            return;
        }
        MinecraftForge.EVENT_BUS.post((Event)new TickStartEvent());
        if (PizzaClient.mc.field_71462_r == null) {
            if (Rotater.rotating && PizzaClient.rotater == null) {
                Rotater.rotating = false;
            }
            final ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
            if (held != null) {
                final String displayName = held.func_82833_r();
                if (PizzaClient.config.autoJerryBox) {
                    if (displayName.contains("Jerry Box")) {
                        AutoJerryBox.useJerryBox();
                        return;
                    }
                    if (PizzaClient.config.autoJerryBoxSwapSlot) {
                        AutoJerryBox.swapToBox();
                    }
                }
                if (PizzaClient.config.autoSkyblock && PizzaClient.joinSkyblock && displayName.equals("§aGame Menu §7(Right Click)")) {
                    if (!MacroBuilder.toggled) {
                        Utils.sleep(400);
                    }
                    PizzaClient.joinSkyblock = false;
                    PizzaClient.mc.field_71439_g.func_71165_d("/play sb");
                    return;
                }
                if (PizzaClient.config.autoSoulcry) {
                    SlayerFeatures.useSoulCry();
                }
            }
            else if (PizzaClient.config.autoJerryBox && PizzaClient.config.autoJerryBoxSwapSlot) {
                AutoJerryBox.swapToBox();
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        PizzaClient.location = Locations.NULL;
        PacketUtil.stopPackets = false;
        ChestESP.clickedBlocks.clear();
        PizzaClient.joinSkyblock = true;
        Refuel.drillNPC = null;
        RainbowString.updateList();
        BasePathfinder.path = null;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onOpenGui(final GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu) {
            if (!UpdateGui.shownGui) {
                UpdateGui.checkForUpdates();
            }
            return;
        }
        if (!(event.gui instanceof GuiIngameMenu) && FakeRotation.lastPitch != 69420.0f && FakeRotation.preventSnap && PizzaClient.mc.field_71439_g != null) {
            PizzaClient.mc.field_71439_g.field_70177_z = FakeRotation.lastYaw;
            PizzaClient.mc.field_71439_g.field_70125_A = FakeRotation.lastPitch;
            FakeRotation.disable();
        }
    }
    
    private static void register(final Object target) {
        MinecraftForge.EVENT_BUS.register(target);
    }
    
    private static void registerCommand(final ICommand command) {
        ClientCommandHandler.instance.func_71560_a(command);
    }
    
    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
        mc = Minecraft.func_71410_x();
        username = PizzaClient.mc.func_110432_I().func_111285_a();
        keyBindings = new KeyBinding[9];
        modDir = new File(new File(PizzaClient.mc.field_71412_D, "config"), "pizzaclient");
        PizzaClient.location = Locations.NULL;
    }
}
