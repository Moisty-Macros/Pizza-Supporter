package qolskyblockmod.pizzaclient.features.macros.builder.macros;

import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.features.macros.builder.*;

public abstract class Macro
{
    public Thread miscThread;
    
    public Macro() {
        this.miscThread = new Thread(() -> {});
    }
    
    public void addToggleMessage(final String name) {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + name + " is now " + (MacroBuilder.toggled ? (EnumChatFormatting.GREEN + "On") : (EnumChatFormatting.RED + "Off"))));
    }
    
    public abstract void onTick();
    
    public abstract void onToggle(final boolean p0);
    
    public void onRender() {
    }
    
    public void onDisable() {
    }
    
    public void onMove() {
    }
    
    public void onChat(final String msg) {
    }
    
    public void warpBack() {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "No warp back failsafe for this macro."));
    }
    
    public void writeToWebhook(final String content) {
        Failsafes.writeToWebhook(content);
    }
    
    public abstract boolean applyFailsafes();
    
    public Locations getLocation() {
        return null;
    }
    
    public abstract boolean applyPositionFailsafe();
    
    public abstract boolean applyBedrockFailsafe();
    
    public abstract boolean applyPlayerFailsafes();
    
    public boolean applyRotationFailsafe() {
        return PizzaClient.config.rotationFailsafe;
    }
    
    public void enqueueThread(final Runnable runnable) {
        this.terminateIfAlive();
        MacroBuilder.state = MacroState.NEW_THREAD;
        (this.miscThread = new Thread(runnable)).start();
    }
    
    public void enqueueFailsafeThread(final Runnable runnable) {
        this.terminateIfAlive();
        MacroBuilder.state = MacroState.FAILSAFE;
        (this.miscThread = new Thread(runnable)).start();
    }
    
    public void terminateIfAlive() {
        if (this.miscThread.isAlive()) {
            this.terminate();
        }
    }
    
    public void terminate() {
        this.miscThread.stop();
    }
    
    public void onDeath() {
        this.onDisable();
        this.terminateIfAlive();
    }
}
