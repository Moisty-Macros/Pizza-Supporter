package qolskyblockmod.pizzaclient.mixins.mixin.server;

import io.netty.channel.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import qolskyblockmod.pizzaclient.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { NetworkManager.class }, priority = 2000)
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler<Packet<?>>
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> packetIn, final CallbackInfo ci) {
        if (PacketUtil.stopPackets) {
            PacketUtil.pausedPackets.add(packetIn);
            ci.cancel();
        }
    }
}
