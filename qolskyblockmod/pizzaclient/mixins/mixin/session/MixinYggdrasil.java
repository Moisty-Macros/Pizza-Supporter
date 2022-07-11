package qolskyblockmod.pizzaclient.mixins.mixin.session;

import org.spongepowered.asm.mixin.*;
import com.mojang.authlib.yggdrasil.*;
import qolskyblockmod.pizzaclient.features.misc.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { YggdrasilMinecraftSessionService.class }, priority = Integer.MAX_VALUE, remap = false)
public class MixinYggdrasil
{
    @ModifyVariable(method = { "joinServer" }, at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String onJoinServer(final String value) {
        return SessionProtection.changedToken ? SessionProtection.changed : value;
    }
}
