package qolskyblockmod.pizzaclient.util.rotation.helper;

import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.util.rotation.fake.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.rotation.*;
import net.minecraft.util.*;

public class BlockPosFakeRotationHelper
{
    public BlockPos bestPos;
    private double bestRotation;
    private final float maxDist;
    private final Vec3 player;
    
    public BlockPosFakeRotationHelper() {
        this.bestRotation = 9.9999999E7;
        this.maxDist = 20.25f;
        this.player = PlayerUtil.getPositionEyes();
        if (FakeRotation.lastPitch == 69420.0f) {
            FakeRotation.lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
            FakeRotation.lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
        }
    }
    
    public BlockPosFakeRotationHelper(final float dist) {
        this.bestRotation = 9.9999999E7;
        this.maxDist = dist * dist;
        this.player = PlayerUtil.getPositionEyes();
        if (FakeRotation.lastPitch == 69420.0f) {
            FakeRotation.setToDefault();
        }
    }
    
    public void compare(final Vec3 vecIn) {
        if (this.player.func_72436_e(vecIn) <= this.maxDist) {
            final double dist = Rotation.getRotationDifference(vecIn, FakeRotation.lastYaw, FakeRotation.lastPitch).sum();
            if (dist < this.bestRotation) {
                this.bestPos = new BlockPos(vecIn);
                this.bestRotation = dist;
            }
        }
    }
    
    public void compare(final MovingObjectPosition pos) {
        if (this.player.func_72436_e(pos.field_72307_f) <= this.maxDist) {
            final double dist = Rotation.getRotationDifference(pos.field_72307_f, FakeRotation.lastYaw, FakeRotation.lastPitch).sum();
            if (dist < this.bestRotation) {
                this.bestPos = pos.func_178782_a();
                this.bestRotation = dist;
            }
        }
    }
    
    public void compare(final BlockPos pos) {
        final Vec3 vecIn = new Vec3(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5);
        if (this.player.func_72436_e(vecIn) <= this.maxDist) {
            final double dist = Rotation.getRotationDifference(vecIn, FakeRotation.lastYaw, FakeRotation.lastPitch).sum();
            if (dist < this.bestRotation) {
                this.bestPos = pos;
                this.bestRotation = dist;
            }
        }
    }
    
    public boolean isNotNull() {
        return this.bestPos != null;
    }
}
