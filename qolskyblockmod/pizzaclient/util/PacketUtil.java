package qolskyblockmod.pizzaclient.util;

import net.minecraft.network.*;
import qolskyblockmod.pizzaclient.*;
import java.util.*;

public class PacketUtil
{
    public static List<Packet<?>> pausedPackets;
    public static boolean stopPackets;
    
    public static void sendPackets(final List<Packet<?>> packets) {
        final boolean bool = PacketUtil.stopPackets;
        PacketUtil.stopPackets = false;
        for (final Packet<?> packet : packets) {
            PacketUtil.pausedPackets.remove(packet);
            PizzaClient.mc.field_71439_g.field_71174_a.func_147298_b().func_179290_a((Packet)packet);
        }
        PacketUtil.stopPackets = bool;
    }
    
    public static void continueAndSendPackets() {
        PacketUtil.stopPackets = false;
        for (final Packet<?> packet : PacketUtil.pausedPackets) {
            PizzaClient.mc.field_71439_g.field_71174_a.func_147298_b().func_179290_a((Packet)packet);
        }
        PacketUtil.pausedPackets.clear();
    }
    
    static {
        PacketUtil.pausedPackets = new ArrayList<Packet<?>>();
    }
}
