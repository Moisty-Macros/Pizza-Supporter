package qolskyblockmod.pizzaclient.features.keybinds;

import qolskyblockmod.pizzaclient.core.config.*;
import qolskyblockmod.pizzaclient.core.events.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import qolskyblockmod.pizzaclient.util.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;

public class MacroKeybind extends ConfigFile
{
    public static final Map<String, CustomKeybind> itemMacros;
    public static final File configFile;
    
    public MacroKeybind() {
        super(MacroKeybind.configFile);
    }
    
    @SubscribeEvent
    public void onTick(final TickStartEvent event) {
        if (PizzaClient.config.autoMacroKeyToggle && PizzaClient.mc.field_71462_r == null) {
            for (int i = 0; i < 8; ++i) {
                final ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
                if (item != null) {
                    final String displayName = StringUtils.func_76338_a(item.func_82833_r()).toLowerCase();
                    for (final Map.Entry<String, CustomKeybind> entry : MacroKeybind.itemMacros.entrySet()) {
                        if (displayName.contains(entry.getKey())) {
                            final CustomKeybind keybind = entry.getValue();
                            if (System.currentTimeMillis() - keybind.lastSwitch >= keybind.delay) {
                                keybind.useItemAndUpdate(i);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        PizzaClient.config.autoMacroKeyToggle = false;
    }
    
    public static void updateToggle() {
        Utils.addToggleMessage("Auto Item Keybind", PizzaClient.config.autoMacroKeyToggle = !PizzaClient.config.autoMacroKeyToggle);
    }
    
    @Override
    public void read() {
        try (final FileReader in = new FileReader(MacroKeybind.configFile)) {
            final JsonObject file = (JsonObject)PizzaClient.gson.fromJson((Reader)in, (Class)JsonObject.class);
            if (file == null) {
                ConfigFile.write(new JsonObject(), MacroKeybind.configFile);
                return;
            }
            for (final Map.Entry<String, JsonElement> e : file.entrySet()) {
                final JsonObject value = e.getValue().getAsJsonObject();
                MacroKeybind.itemMacros.put(e.getKey(), new CustomKeybind(value.get("delay").getAsInt(), KeybindAction.getActionFromString(value.get("actionType").getAsString())));
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        final JsonObject data = new JsonObject();
        for (final Map.Entry<String, CustomKeybind> entry : MacroKeybind.itemMacros.entrySet()) {
            final JsonObject obj = new JsonObject();
            final CustomKeybind value = entry.getValue();
            obj.addProperty("delay", (Number)value.delay);
            obj.addProperty("actionType", KeybindAction.getString(value.actionType));
            data.add((String)entry.getKey(), (JsonElement)obj);
        }
        ConfigFile.write(data, MacroKeybind.configFile);
    }
    
    static {
        itemMacros = new HashMap<String, CustomKeybind>();
        configFile = new File(PizzaClient.modDir, "automacrokeys.json");
    }
}
