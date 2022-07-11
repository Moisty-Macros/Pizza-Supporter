package qolskyblockmod.pizzaclient.features.macros.ai.movement;

import qolskyblockmod.pizzaclient.*;
import net.minecraft.client.settings.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;

public class Movement
{
    public static void setMovement(final MovementType movement) {
        switch (movement) {
            case FORWARDS: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
                break;
            }
            case BACKWARDS: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
                break;
            }
            case LEFT: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
                break;
            }
            case RIGHT: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
                break;
            }
        }
    }
    
    public static void setMovementToForward() {
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
    }
    
    public static void setMovement(final BetterBlockPos current, final BetterBlockPos next) {
        setMovement(calculateRequiredMovement(current, next));
    }
    
    public static void setMovement(final Vec3 current, final BetterBlockPos next) {
        setMovement(calculateRequiredMovement(new BetterBlockPos(current), next));
    }
    
    public static void move(final MovementType type) {
        switch (type) {
            case FORWARDS: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
                break;
            }
            case BACKWARDS: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
                break;
            }
            case LEFT: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
                break;
            }
            case RIGHT: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
                break;
            }
        }
    }
    
    public static void addMovement(final MovementType type) {
        switch (type) {
            case FORWARDS: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
                break;
            }
            case BACKWARDS: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
                break;
            }
            case LEFT: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
                break;
            }
            case RIGHT: {
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
                break;
            }
        }
    }
    
    public static void endMovement() {
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
    }
    
    public static void addMovement(final BetterBlockPos current, final BetterBlockPos next) {
        addMovement(calculateRequiredMovement(current, next));
    }
    
    public static void disableMovement() {
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
    }
    
    public static MovementType calculateRequiredMovement(final BetterBlockPos current, final BetterBlockPos next) {
        final EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
        final BetterBlockPos pos = current.offset(facing);
        if (pos.isSameXandZ(next)) {
            return MovementType.FORWARDS;
        }
        if (current.offset(Utils.getLeftEnumfacing(facing)).isSameXandZ(next)) {
            return MovementType.LEFT;
        }
        if (current.offset(Utils.getRightEnumfacing(facing)).isSameXandZ(next)) {
            return MovementType.RIGHT;
        }
        return MovementType.NULL;
    }
    
    public static boolean isMoving() {
        return PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d() || PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d() || PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d() || PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d();
    }
}
