package qolskyblockmod.pizzaclient.commands;

import java.util.*;
import com.google.common.collect.*;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.*;
import com.google.gson.*;
import qolskyblockmod.pizzaclient.core.config.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import net.minecraft.command.*;

public class AotvTestCommand extends CommandBase
{
    public String func_71517_b() {
        return "aotvtest";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return null;
    }
    
    public List<String> func_71514_a() {
        return (List<String>)Lists.newArrayList((Object[])new String[] { "markertest" });
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 1 && args[0].equals("clear")) {
            MithrilMarkers.markers.clear();
            ConfigFile.write(new JsonObject(), MithrilMarkers.configFile);
            sender.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Cleared all the aotv marks!"));
            return;
        }
        sender.func_145747_a((IChatComponent)new ChatComponentText("Current list of markers: \n" + MithrilMarkers.markers));
        new Thread(MithrilMarkers::run).start();
    }
    
    public int func_82362_a() {
        return 0;
    }
}
