package qolskyblockmod.pizzaclient.mixins.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.item.*;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { Minecraft.class }, priority = 999)
public class MixinMinecraftt
{
    @Inject(method = { "clickMouse" }, at = { @At("HEAD") }, cancellable = true)
    private void onClickMouse(final CallbackInfo ci) {
        if (PizzaClient.mc.field_71439_g != null && PizzaClient.config.terminatorValk) {
            final ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
            if (held != null && held.func_82833_r().toLowerCase().contains("valkyrie")) {
                for (int i = 0; i < 8; ++i) {
                    final ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
                    if (item != null && item.func_82833_r().toLowerCase().contains("terminator")) {
                        PlayerUtil.sendSlotRightClickPacket(i);
                        ci.cancel();
                        break;
                    }
                }
            }
        }
    }
    
    @Inject(method = { "sendClickBlockToController" }, at = { @At("HEAD") }, cancellable = true)
    private void sendClickBlockToController(final boolean leftClick, final CallbackInfo ci) {
        if (PizzaClient.mc.field_71462_r == null && NukerBase.nukedThisTick()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "startGame" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;setFramebufferColor(FFFF)V", shift = At.Shift.AFTER) })
    private void onInit(final CallbackInfo ci) {
        PizzaClient.onStartGame();
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", ordinal = 0) })
    private void onTick(final CallbackInfo ci) {
        PizzaClient.onTick();
    }
    
    @Redirect(method = { "setIngameFocus" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;isActive()Z"))
    private boolean redirectIsActive() {
        return true;
    }
}
