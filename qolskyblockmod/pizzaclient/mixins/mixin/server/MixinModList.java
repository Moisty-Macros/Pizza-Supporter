package qolskyblockmod.pizzaclient.mixins.mixin.server;

import net.minecraftforge.fml.common.network.handshake.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { FMLHandshakeMessage.ModList.class }, remap = false)
public class MixinModList
{
    @Shadow
    private Map<String, String> modTags;
    
    @Inject(method = { "<init>(Ljava/util/List;)V" }, at = { @At("RETURN") })
    private void removeMod(final CallbackInfo ci) {
        if (!PizzaClient.mc.func_71387_A()) {
            this.modTags.remove("pizzaclient");
        }
    }
}
