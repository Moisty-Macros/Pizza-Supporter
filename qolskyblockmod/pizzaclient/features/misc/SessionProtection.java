package qolskyblockmod.pizzaclient.features.misc;

import qolskyblockmod.pizzaclient.util.exceptions.*;
import qolskyblockmod.pizzaclient.*;
import java.io.*;
import qolskyblockmod.pizzaclient.util.*;
import com.google.gson.*;
import qolskyblockmod.pizzaclient.core.config.*;
import java.util.*;

public class SessionProtection
{
    public static String changed;
    public static boolean changedToken;
    
    public SessionProtection(final SessionProtection a) {
        throw new RatException();
    }
    
    public static void changeLauncherAccounts() {
        final File file = new File(PizzaClient.mc.field_71412_D, "launcher_accounts.json");
        if (!file.exists()) {
            return;
        }
        try {
            final JsonObject json = new JsonObject();
            for (final Map.Entry<String, JsonElement> entry : Utils.getJson(file).getAsJsonObject().get("accounts").getAsJsonObject().entrySet()) {
                final JsonElement e = entry.getValue();
                if (e instanceof JsonObject) {
                    final JsonObject object = e.getAsJsonObject();
                    if (object.has("accessToken")) {
                        final JsonObject sub = new JsonObject();
                        for (final Map.Entry<String, JsonElement> entry2 : object.entrySet()) {
                            final String s = entry2.getKey();
                            if (s.equals("accessToken")) {
                                sub.addProperty(s, "");
                            }
                            else {
                                sub.add(s, object.get(s));
                            }
                        }
                        json.add((String)entry.getKey(), (JsonElement)sub);
                    }
                    else {
                        json.add((String)entry.getKey(), (JsonElement)object);
                    }
                }
            }
            final JsonObject content = new JsonObject();
            content.add("accounts", (JsonElement)json);
            ConfigFile.write(content, file);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
