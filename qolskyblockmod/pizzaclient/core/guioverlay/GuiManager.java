package qolskyblockmod.pizzaclient.core.guioverlay;

import qolskyblockmod.pizzaclient.core.config.*;
import qolskyblockmod.pizzaclient.*;
import java.io.*;
import com.google.gson.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.*;
import qolskyblockmod.pizzaclient.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class GuiManager extends ConfigFile
{
    public static final Map<String, GuiLocation> GUIPOSITIONS;
    public static final List<GuiElement> elements;
    public static final File configFile;
    
    public GuiManager() {
        super(GuiManager.configFile);
    }
    
    public static void registerElement(final GuiElement e) {
        GuiManager.elements.add(e);
    }
    
    @Override
    public void read() {
        try (final FileReader in = new FileReader(GuiManager.configFile)) {
            final JsonObject file = (JsonObject)PizzaClient.gson.fromJson((Reader)in, (Class)JsonObject.class);
            for (final Map.Entry<String, JsonElement> e : file.entrySet()) {
                GuiManager.GUIPOSITIONS.put(e.getKey(), new GuiLocation(e.getValue().getAsJsonObject().get("x").getAsFloat(), e.getValue().getAsJsonObject().get("y").getAsFloat()));
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        for (final GuiElement e : GuiManager.elements) {
            GuiManager.GUIPOSITIONS.put(e.name, e.pos);
        }
        ConfigFile.write(GuiManager.GUIPOSITIONS, GuiManager.configFile);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
        if (PizzaClient.mc.field_71441_e == null || !(PizzaClient.mc.field_71456_v instanceof GuiIngameForge) || PizzaClient.mc.field_71462_r instanceof LocationEditGui || event.type != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        for (final GuiElement e : GuiManager.elements) {
            e.render();
        }
    }
    
    static {
        GUIPOSITIONS = new HashMap<String, GuiLocation>();
        elements = new ArrayList<GuiElement>();
        configFile = new File(PizzaClient.modDir, "guipositions.json");
    }
}
