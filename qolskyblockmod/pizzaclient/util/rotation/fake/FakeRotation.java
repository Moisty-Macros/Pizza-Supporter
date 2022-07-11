package qolskyblockmod.pizzaclient.util.rotation.fake;

import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.rotation.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import qolskyblockmod.pizzaclient.util.*;

public class FakeRotation
{
    private static float actualYaw;
    private static float actualPitch;
    public static Vec3 rotationVec;
    public static BlockPos hitPos;
    public static float lastYaw;
    public static float lastPitch;
    public static boolean leftClick;
    public static int rightClick;
    public static boolean smoothRotate;
    public static boolean preventSnap;
    public static int itemSlot;
    private static boolean finishedSmoothRotate;
    
    public static void onPlayerMovePre() {
        if (FakeRotation.smoothRotate) {
            final Rotation rotation = smoothRotateToVec();
            FakeRotation.actualYaw = PizzaClient.mc.field_71439_g.field_70177_z;
            FakeRotation.actualPitch = PizzaClient.mc.field_71439_g.field_70125_A;
            PizzaClient.mc.field_71439_g.field_70177_z = rotation.yaw;
            PizzaClient.mc.field_71439_g.field_70125_A = rotation.pitch;
            if (FakeRotation.smoothRotate) {
                return;
            }
        }
        else {
            final Rotation rotation = Rotation.getRotation(FakeRotation.rotationVec);
            FakeRotation.actualYaw = PizzaClient.mc.field_71439_g.field_70177_z;
            FakeRotation.actualPitch = PizzaClient.mc.field_71439_g.field_70125_A;
            PizzaClient.mc.field_71439_g.field_70177_z = rotation.yaw;
            PizzaClient.mc.field_71439_g.field_70125_A = rotation.pitch;
        }
        if (FakeRotation.rightClick != 0) {
            BlockPos pos;
            if (FakeRotation.hitPos != null) {
                pos = FakeRotation.hitPos;
                FakeRotation.hitPos = null;
            }
            else {
                pos = new BlockPos(FakeRotation.rotationVec);
            }
            final MovingObjectPosition position = VecUtil.calculateInterceptLook(pos, FakeRotation.rotationVec, 4.5f);
            if (position != null) {
                if (FakeRotation.itemSlot != 0) {
                    final int lastSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
                    PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = FakeRotation.itemSlot;
                    for (int i = 0; i < FakeRotation.rightClick; ++i) {
                        if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), pos, position.field_178784_b, position.field_72307_f)) {
                            PizzaClient.mc.field_71439_g.func_71038_i();
                        }
                    }
                    PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
                    FakeRotation.itemSlot = 0;
                }
                else {
                    for (int j = 0; j < FakeRotation.rightClick; ++j) {
                        if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), pos, position.field_178784_b, position.field_72307_f)) {
                            PizzaClient.mc.field_71439_g.func_71038_i();
                        }
                    }
                }
                FakeRotation.rightClick = 0;
            }
        }
    }
    
    public static void onPlayerMovePost() {
        FakeRotation.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
        FakeRotation.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
        PizzaClient.mc.field_71439_g.field_70125_A = FakeRotation.actualPitch;
        PizzaClient.mc.field_71439_g.field_70177_z = FakeRotation.actualYaw;
        if (FakeRotation.leftClick) {
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i());
            FakeRotation.leftClick = false;
            return;
        }
        if (!FakeRotation.smoothRotate) {
            if (FakeRotation.finishedSmoothRotate) {
                FakeRotation.finishedSmoothRotate = false;
                return;
            }
            if (FakeRotation.preventSnap) {
                FakeRotation.smoothRotate = true;
                FakeRotation.rotationVec = PlayerUtil.getPositionEyes().func_178787_e(PlayerUtil.getLook(10.0f));
                FakeRotation.preventSnap = false;
                return;
            }
            FakeRotation.rotationVec = null;
        }
    }
    
    public static void smoothRotateTo(final Vec3 target) {
        FakeRotation.smoothRotate = true;
        FakeRotation.rotationVec = target;
        if (FakeRotation.lastPitch == 69420.0f) {
            FakeRotation.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
            FakeRotation.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
        }
    }
    
    public static void smoothRotateTo(final MovingObjectPosition pos) {
        FakeRotation.smoothRotate = true;
        FakeRotation.rotationVec = pos.field_72307_f;
        FakeRotation.hitPos = pos.func_178782_a();
        if (FakeRotation.lastPitch == 69420.0f) {
            FakeRotation.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
            FakeRotation.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
        }
    }
    
    public static void smoothRotateToIntercept(final MovingObjectPosition pos) {
        FakeRotation.smoothRotate = true;
        FakeRotation.rotationVec = pos.field_72307_f;
        FakeRotation.hitPos = Utils.getBlockFromHit(pos);
        if (FakeRotation.lastPitch == 69420.0f) {
            FakeRotation.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
            FakeRotation.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
        }
    }
    
    private static Rotation smoothRotateToVec() {
        final Rotation rotation = Rotation.getRotationDifference(FakeRotation.rotationVec, FakeRotation.lastYaw, FakeRotation.lastPitch);
        if (MathUtil.abs(rotation.yaw) < PizzaClient.config.cropAuraSmoothRotation && MathUtil.abs(rotation.pitch) < PizzaClient.config.cropAuraSmoothRotation) {
            FakeRotation.smoothRotate = false;
            FakeRotation.finishedSmoothRotate = true;
            return new Rotation(FakeRotation.lastYaw + rotation.yaw, FakeRotation.lastPitch + rotation.pitch);
        }
        float rotateYaw;
        float rotatePitch;
        if (MathUtil.abs(rotation.yaw) > MathUtil.abs(rotation.pitch)) {
            rotateYaw = PizzaClient.config.cropAuraSmoothRotation + MathUtil.randomFloat();
            rotatePitch = rotateYaw * MathUtil.abs(rotation.pitch / rotation.yaw);
        }
        else {
            rotatePitch = PizzaClient.config.cropAuraSmoothRotation + MathUtil.randomFloat();
            rotateYaw = rotatePitch * MathUtil.abs(rotation.yaw / rotation.pitch);
        }
        if (rotation.yaw < 0.0f) {
            rotateYaw = -rotateYaw;
        }
        if (rotation.pitch < 0.0f) {
            rotatePitch = -rotatePitch;
        }
        return new Rotation(FakeRotation.lastYaw + rotateYaw, FakeRotation.lastPitch + rotatePitch);
    }
    
    public static void setToDefault() {
        FakeRotation.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
        FakeRotation.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
    }
    
    public static void disable() {
        FakeRotation.rotationVec = null;
        FakeRotation.leftClick = false;
        FakeRotation.rightClick = 0;
        FakeRotation.smoothRotate = false;
        FakeRotation.hitPos = null;
    }
    
    static {
        FakeRotation.lastPitch = 69420.0f;
    }
}
