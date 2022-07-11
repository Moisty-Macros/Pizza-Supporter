package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;

public class GoBackRotater extends Rotater
{
    public GoBackRotater(final float yawIn, final float pitchIn) {
        super(yawIn, pitchIn);
    }
    
    @Override
    public void add() {
        final float elapsed = (float)(System.currentTimeMillis() - this.timeElapsed);
        if (elapsed >= this.changedDivider + 200.0f) {
            new Rotater(this.startYaw - PizzaClient.mc.field_71439_g.field_70177_z + MathUtil.randomFloat(), MathUtil.randomFloat()).setRotationAmount((int)(this.divider / 10.0f)).rotate();
            return;
        }
        if (this.goalPitch != 999.0f && elapsed >= this.changedDivider) {
            PizzaClient.mc.field_71439_g.field_70177_z = this.goalYaw;
            PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.goalPitch);
            this.goalPitch = 999.0f;
            return;
        }
        if (elapsed > 80.0f) {
            final float diff = (float)Math.sqrt((elapsed - 80.0f) / this.changedDivider + 1.0f) * 1.25f;
            this.changedYaw = this.yaw / diff;
            this.changedPitch = this.pitch / diff;
            this.changedDivider = this.divider * diff;
        }
        PizzaClient.mc.field_71439_g.field_70177_z = this.startYaw + this.changedYaw * elapsed;
        PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.startPitch + this.changedPitch * elapsed);
    }
}
