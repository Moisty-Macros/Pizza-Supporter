package qolskyblockmod.pizzaclient.util.rotation;

import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.rotation.fake.*;
import qolskyblockmod.pizzaclient.util.*;

public class Rotation
{
    public final float yaw;
    public final float pitch;
    
    public Rotation(final float yawIn, final float pitchIn) {
        this.yaw = yawIn;
        this.pitch = pitchIn;
    }
    
    public static Rotation getRotationDifference(final Vec3 target) {
        final double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
        final double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight());
        final double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
        final double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
        return new Rotation((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232 - 90.0 - PizzaClient.mc.field_71439_g.field_70177_z), (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, dist) * 57.29577951308232) - PizzaClient.mc.field_71439_g.field_70125_A));
    }
    
    public static Rotation getRotationDifference(final Vec3 target, final float playerYaw, final float playerPitch) {
        final double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
        final double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight());
        final double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
        final double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
        return new Rotation((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232 - 90.0 - playerYaw), (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, dist) * 57.29577951308232) - playerPitch));
    }
    
    public static Rotation getRotation(final Vec3 target) {
        final double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
        final double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + PlayerUtil.fastEyeHeight());
        final double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
        final double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
        return new Rotation((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232 - 90.0 - PizzaClient.mc.field_71439_g.field_70177_z) + PizzaClient.mc.field_71439_g.field_70177_z, (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, dist) * 57.29577951308232) - PizzaClient.mc.field_71439_g.field_70125_A) + PizzaClient.mc.field_71439_g.field_70125_A);
    }
    
    public static float getYaw(final Vec3 target) {
        return (float)MathHelper.func_76138_g(MathHelper.func_181159_b(target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v, target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t) * 57.29577951308232 - 90.0 - PizzaClient.mc.field_71439_g.field_70177_z) + PizzaClient.mc.field_71439_g.field_70177_z;
    }
    
    public static float getYawDifference(final Vec3 target) {
        return (float)MathHelper.func_76138_g(MathHelper.func_181159_b(target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v, target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t) * 57.29577951308232 - 90.0 - PizzaClient.mc.field_71439_g.field_70177_z);
    }
    
    public static Rotation getLastReportedRotation() {
        return (FakeRotation.lastPitch != 69420.0f) ? new Rotation(FakeRotation.lastYaw, FakeRotation.lastPitch) : new Rotation(PizzaClient.mc.field_71439_g.field_70177_z, PizzaClient.mc.field_71439_g.field_70125_A);
    }
    
    public float max() {
        return MathUtil.max(MathUtil.abs(this.yaw), MathUtil.abs(this.pitch));
    }
    
    public float min() {
        return MathUtil.min(MathUtil.abs(this.yaw), MathUtil.abs(this.pitch));
    }
    
    public float sum() {
        return MathUtil.abs(this.yaw) + MathUtil.abs(this.pitch);
    }
    
    @Override
    public String toString() {
        return "{yaw=" + this.yaw + ", pitch=" + this.pitch + "}";
    }
}
