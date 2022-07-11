package qolskyblockmod.pizzaclient.mixins.mixin.misc;

import net.minecraftforge.fml.client.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import qolskyblockmod.pizzaclient.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { SplashProgress.class }, priority = 2000)
public class MixinSplashProgress
{
    @Unique
    private static final ResourceLocation troll;
    @Unique
    private static final ResourceLocation arabic;
    
    @ModifyVariable(method = { "start" }, at = @At("STORE"), ordinal = 1, remap = false)
    private static ResourceLocation setForgeTitle(final ResourceLocation original) {
        try {
            return (PizzaClient.config.funnyLoadingScreen && PizzaClient.config.funnyLoadingScreenTrollFace) ? MixinSplashProgress.troll : original;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to apply custom splash screen.");
            return original;
        }
    }
    
    @ModifyVariable(method = { "start" }, at = @At("STORE"), ordinal = 0, remap = false)
    private static ResourceLocation setForgeUnicode(final ResourceLocation original) {
        try {
            return (PizzaClient.config.funnyLoadingScreen && PizzaClient.config.funnyLoadingScreenArab) ? MixinSplashProgress.arabic : original;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to apply custom splash screen.");
            return original;
        }
    }
    
    static {
        troll = new ResourceLocation("pizzaclient", "splash/troll.png");
        arabic = new ResourceLocation("pizzaclient", "splash/arabic.png");
    }
}
