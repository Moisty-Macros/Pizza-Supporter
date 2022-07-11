package qolskyblockmod.pizzaclient.features.macros.mining.dwarven;

import qolskyblockmod.pizzaclient.core.config.*;
import qolskyblockmod.pizzaclient.*;
import java.io.*;
import com.google.gson.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.*;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;

public class MithrilMarkers extends ConfigFile
{
    public static final File configFile;
    public static final List<BlockPos> markers;
    
    public MithrilMarkers() {
        super(MithrilMarkers.configFile);
    }
    
    @Override
    public void read() {
        try (final FileReader in = new FileReader(MithrilMarkers.configFile)) {
            final JsonArray arr = (JsonArray)PizzaClient.gson.fromJson((Reader)in, (Class)JsonArray.class);
            for (int arraySize = arr.size(), i = 0; i < arraySize; ++i) {
                final JsonObject o = arr.get(i).getAsJsonObject();
                MithrilMarkers.markers.add(new BlockPos(o.get("x").getAsInt(), o.get("y").getAsInt(), o.get("z").getAsInt()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void write() {
        final JsonArray arr = new JsonArray();
        for (final BlockPos pos : MithrilMarkers.markers) {
            final JsonObject o = new JsonObject();
            o.addProperty("x", (Number)pos.func_177958_n());
            o.addProperty("y", (Number)pos.func_177956_o());
            o.addProperty("z", (Number)pos.func_177952_p());
            arr.add((JsonElement)o);
        }
        ConfigFile.write(arr, MithrilMarkers.configFile);
    }
    
    public static void run() {
        Locations.DWARVENMINES.warpTo();
        if (MithrilMarkers.markers.size() == 0) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "The Markers are empty. Try adding some markers."));
            return;
        }
        AOTVMovement.run(MithrilMarkers.markers, () -> {
            if (Refuel.drillNPC == null) {
                Refuel.drillNPC = Refuel.findDrillNPC();
            }
        });
    }
    
    public static void onKey() {
        final MovingObjectPosition position = VecUtil.rayTrace(61.0f);
        if (position == null) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid marker. Was the block out of range?"));
            return;
        }
        if (position.func_178782_a() == null) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "You must face a block in order to add it to the marker."));
        }
        else {
            final BlockPos pos = position.func_178782_a();
            if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a) {
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid marker. Was the block out of range?"));
                return;
            }
            if (MithrilMarkers.markers.contains(pos)) {
                MithrilMarkers.markers.remove(pos);
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Removed " + pos.toString() + " from the marker list."));
            }
            else {
                MithrilMarkers.markers.add(pos);
                write();
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Added " + pos.toString() + " to the marker list."));
            }
        }
    }
    
    static {
        configFile = new File(PizzaClient.modDir, "mithrilmarkers.json");
        markers = new ArrayList<BlockPos>();
    }
}
