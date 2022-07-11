package qolskyblockmod.pizzaclient.core.config;

import java.io.*;
import qolskyblockmod.pizzaclient.*;
import java.util.*;
import com.google.gson.*;

public abstract class ConfigFile
{
    public ConfigFile(final File file) {
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong when trying to create a new config file.");
            }
        }
        try {
            this.read();
        }
        catch (Exception ex) {}
    }
    
    public static void write(final Map<?, ?> obj, final File configFile) {
        try (final FileWriter writer = new FileWriter(configFile)) {
            PizzaClient.gson.toJson((Object)obj, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(final List<?> obj, final File configFile) {
        try (final FileWriter writer = new FileWriter(configFile)) {
            PizzaClient.gson.toJson((Object)obj, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(final String obj, final File configFile) {
        try (final FileWriter writer = new FileWriter(configFile)) {
            PizzaClient.gson.toJson((Object)obj, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(final JsonObject obj, final File configFile) {
        try (final FileWriter writer = new FileWriter(configFile)) {
            PizzaClient.gson.toJson((JsonElement)obj, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(final JsonArray obj, final File configFile) {
        try (final FileWriter writer = new FileWriter(configFile)) {
            PizzaClient.gson.toJson((JsonElement)obj, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public abstract void read();
}
