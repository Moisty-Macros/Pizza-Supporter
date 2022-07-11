package qolskyblockmod.pizzaclient.mixins.mixin.server;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.features.player.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.network.play.server.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.features.macros.builder.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.*;

@Mixin(value = { NetHandlerPlayClient.class }, priority = 2000)
public abstract class MixinReceivedPackets
{
    @Inject(method = { "handleParticles" }, at = { @At("HEAD") })
    private void onParticle(final S2APacketParticles packetIn, final CallbackInfo ci) {
        AutoPowderChest.onParticle(packetIn);
    }
    
    @Inject(method = { "handlePlayerPosLook" }, at = { @At("HEAD") })
    private void onPacketPosLook(final S08PacketPlayerPosLook packetIn, final CallbackInfo ci) {
        if (MacroBuilder.toggled && PizzaClient.location != Locations.NULL && MacroBuilder.state == MacroState.ACTIVE) {
            float yaw = packetIn.func_148931_f();
            if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
                yaw += PizzaClient.mc.field_71439_g.field_70177_z;
            }
            if (PlayerUtil.getPositionEyes().func_72436_e(new Vec3(packetIn.func_148932_c(), packetIn.func_148928_d(), packetIn.func_148933_e())) < 16.0) {
                if (PizzaClient.config.rotationFailsafe) {
                    final float diffYaw = MathUtil.abs(PizzaClient.mc.field_71439_g.field_70177_z - yaw);
                    if (diffYaw > 15.0f) {
                        Failsafes.onPacketPosLook(diffYaw);
                    }
                }
            }
            else {
                MacroBuilder.updatePosition();
                Failsafes.onChangePosition();
            }
        }
    }
}
