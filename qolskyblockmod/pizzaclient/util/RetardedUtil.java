package qolskyblockmod.pizzaclient.util;

import net.minecraft.util.*;
import com.google.gson.*;
import qolskyblockmod.pizzaclient.*;
import java.nio.charset.*;
import org.apache.commons.io.*;

public class RetardedUtil
{
    public static final JsonObject cryptOverlay;
    
    public static void isCrypt(final BlockPos pos) {
    }
    
    public static JsonObject getCrypts() {
        try {
            final ResourceLocation resource = new ResourceLocation("pizzaclient", "json.crypts_data.json");
            return new JsonParser().parse(IOUtils.toString(PizzaClient.mc.func_110442_L().func_110536_a(resource).func_110527_b(), StandardCharsets.UTF_8)).getAsJsonObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    static {
        cryptOverlay = getCrypts();
    }
}
