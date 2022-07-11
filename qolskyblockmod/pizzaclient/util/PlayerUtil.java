package qolskyblockmod.pizzaclient.util;

import net.minecraft.block.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.client.entity.*;
import java.lang.reflect.*;
import qolskyblockmod.pizzaclient.features.keybinds.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import qolskyblockmod.pizzaclient.util.rotation.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import qolskyblockmod.pizzaclient.util.handler.*;
import qolskyblockmod.pizzaclient.util.misc.distance.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;

public class PlayerUtil
{
    public static final Set<Block> secretBlocks;
    private static final float STEP_SIZE = 0.1f;
    private static final MethodReflectionHelper RIGHT_CLICK;
    private static final MethodReflectionHelper LEFT_CLICK;
    private static final MethodReflectionHelper CONTROLLER;
    public static final float EYE_HEIGHT = 1.62f;
    
    public static void rightClick() {
        PlayerUtil.RIGHT_CLICK.invokeMc();
    }
    
    public static void rightClick(final int clickamount) {
        PlayerUtil.RIGHT_CLICK.invokeMc(clickamount);
    }
    
    public static void leftClick() {
        PlayerUtil.LEFT_CLICK.invokeMc();
    }
    
    public static void leftClick(final int clickAmount) {
        PlayerUtil.LEFT_CLICK.invokeMc(clickAmount);
    }
    
    public static void forceUpdateController() {
        PlayerUtil.CONTROLLER.invoke(PizzaClient.mc.field_71442_b);
    }
    
    public static void updatePlayer() {
        try {
            final Method method = EntityPlayerSP.class.getDeclaredMethod("func_175161_p", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            method.invoke(PizzaClient.mc, new Object[0]);
        }
        catch (Exception ex) {}
    }
    
    public static void ghostBlock(final float dist) {
        Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
        Vec3 look = getLook();
        look = VecUtil.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        for (int i = 0; i < MathUtil.floor(dist / 0.1f) - 2; ++i) {
            final BlockPos blockAtPos = new BlockPos(position);
            if (!KeybindFeatures.notGhostBlockable.contains(PizzaClient.mc.field_71441_e.func_180495_p(blockAtPos).func_177230_c())) {
                if (PizzaClient.mc.field_71441_e.func_180495_p(blockAtPos).func_177230_c() == Blocks.field_150465_bP && Utils.isWitherEssence((TileEntitySkull)PizzaClient.mc.field_71441_e.func_175625_s(blockAtPos))) {
                    return;
                }
                PizzaClient.mc.field_71441_e.func_175698_g(blockAtPos);
            }
            position = position.func_178787_e(look);
        }
    }
    
    public static void ghostBlockIgnoreInteractables(final float dist) {
        Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
        Vec3 look = getLook();
        look = Utils.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        for (int i = 0; i < MathUtil.floor(dist / 0.1f) - 2; ++i) {
            final BlockPos blockAtPos = new BlockPos(position);
            if (!PlayerUtil.secretBlocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(blockAtPos).func_177230_c())) {
                PizzaClient.mc.field_71441_e.func_175698_g(blockAtPos);
            }
            position = position.func_178787_e(look);
        }
    }
    
    public static HashSet<BlockPos> getAllFacingBlockPos(final float range) {
        Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
        Vec3 look = getLook();
        look = Utils.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        final HashSet<BlockPos> facingBlocks = new HashSet<BlockPos>();
        for (int i = 0; i < MathUtil.floor(range / 0.1f) - 2; ++i) {
            final BlockPos blockAtPos = new BlockPos(position);
            facingBlocks.add(blockAtPos);
            position = position.func_178787_e(look);
        }
        return facingBlocks;
    }
    
    public static int checkHotBarForItem(final Item item) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == item) {
                return i;
            }
        }
        return 0;
    }
    
    public static int checkHotBarForEtherwarp() {
        int diamondSword = 0;
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final Item item = stack.func_77973_b();
                if (item == Items.field_151047_v) {
                    return i;
                }
                if (item == Items.field_151048_u) {
                    diamondSword = i;
                }
            }
        }
        return diamondSword;
    }
    
    public static boolean hotbarHasEtherwarp() {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && (stack.func_77973_b() == Items.field_151047_v || stack.func_77973_b() == Items.field_151048_u)) {
                return true;
            }
        }
        return false;
    }
    
    public static int checkHotbarForDisplayName(final String displayName) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && StringUtils.func_76338_a(stack.func_82833_r()).contains(displayName)) {
                return i;
            }
        }
        return 0;
    }
    
    public static boolean hotbarHasItem(final Item item) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == item) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hotbarHasDisplayName(final String displayName) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && StringUtils.func_76338_a(PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i].func_82833_r()).contains(displayName)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isHoldingAotv() {
        final ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
        return held != null && held.func_77973_b() == Items.field_151047_v;
    }
    
    private static Vec3 getVectorForRotation(final float pitch, final float yaw) {
        final float f2 = -MathHelper.func_76134_b(-pitch * 0.017453292f);
        return new Vec3((double)(MathHelper.func_76126_a(-yaw * 0.017453292f - 3.1415927f) * f2), (double)MathHelper.func_76126_a(-pitch * 0.017453292f), (double)(MathHelper.func_76134_b(-yaw * 0.017453292f - 3.1415927f) * f2));
    }
    
    private static Vec3 getVectorForRotation() {
        final float f2 = -MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292f);
        return new Vec3((double)(MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292f - 3.1415927f) * f2), (double)MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292f), (double)(MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292f - 3.1415927f) * f2));
    }
    
    private static Vec3 getVectorForRotation2D(final float yaw) {
        return new Vec3((double)MathHelper.func_76126_a(-yaw * 0.017453292f - 3.1415927f), 0.0, (double)MathHelper.func_76134_b(-yaw * 0.017453292f - 3.1415927f));
    }
    
    private static Vec3 getVectorForRotation2D() {
        return new Vec3((double)MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292f - 3.1415927f), 0.0, (double)MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292f - 3.1415927f));
    }
    
    public static Vec3 getLook(final Vec3 vec) {
        final double diffX = vec.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
        final double diffY = vec.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight());
        final double diffZ = vec.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
        final float yaw = (float)(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232 - 90.0);
        final float pitch = (float)(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232));
        return getVectorForRotation(pitch, yaw);
    }
    
    public static Vec3 getLook(final float yaw, final float pitch) {
        return getVectorForRotation(pitch, yaw);
    }
    
    public static Vec3 getLook() {
        return getVectorForRotation();
    }
    
    public static Vec3 getLook(final float multiplier) {
        final float f2 = -MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292f);
        return new Vec3((double)(MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292f - 3.1415927f) * f2 * multiplier), (double)(MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292f) * multiplier), (double)(MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292f - 3.1415927f) * f2 * multiplier));
    }
    
    public static Vec3 get2DLook(final Vec3 vec) {
        final double diffX = vec.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
        final double diffZ = vec.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
        return getVectorForRotation2D((float)(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232 - 90.0) + PizzaClient.mc.field_71439_g.field_70177_z);
    }
    
    public static Vec3 get2DLook(final float yaw) {
        return getVectorForRotation2D(yaw);
    }
    
    public static Vec3 get2DLook() {
        return getVectorForRotation2D();
    }
    
    public static Set<BlockPos> getBlocksInBetweenBlocks(final Vec3 endVec) {
        Vec3 position = getPositionEyes();
        Vec3 look = getLook(endVec);
        look = Utils.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        final HashSet<BlockPos> facingBlocks = new HashSet<BlockPos>();
        for (int i = 0; i < 400; ++i) {
            final BlockPos blockAtPos = new BlockPos(position);
            facingBlocks.add(blockAtPos);
            if (blockAtPos.equals((Object)new BlockPos(endVec.field_72450_a, endVec.field_72448_b, endVec.field_72449_c))) {
                return facingBlocks;
            }
            position = position.func_178787_e(look);
        }
        return facingBlocks;
    }
    
    public static boolean facingSameXAndZ(final BlockPos pos, final float range) {
        Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
        Vec3 look = getLook();
        look = Utils.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        for (int i = 0; i < MathUtil.floor(range / 0.1f) - 2; ++i) {
            final BlockPos blockAtPos = new BlockPos(position);
            if (blockAtPos.func_177958_n() == pos.func_177958_n() && blockAtPos.func_177952_p() == pos.func_177952_p()) {
                return true;
            }
            position = position.func_178787_e(look);
        }
        return false;
    }
    
    public static void sendSlotRightClickPacket(final int slot) {
        final int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
        PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        PizzaClient.mc.field_71442_b.func_78769_a((EntityPlayer)PizzaClient.mc.field_71439_g, (World)PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[slot]);
        PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
    }
    
    public static Iterable<BlockPos> getPlayerBlocks() {
        return (Iterable<BlockPos>)BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight() - 5.0, PizzaClient.mc.field_71439_g.field_70161_v - 5.0), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight() + 5.0, PizzaClient.mc.field_71439_g.field_70161_v + 5.0));
    }
    
    public static float fastEyeHeight() {
        return PizzaClient.mc.field_71439_g.func_70093_af() ? 1.54f : 1.62f;
    }
    
    public static float fastEyeHeight(final EntityPlayer playerIn) {
        return playerIn.func_70093_af() ? 1.54f : 1.62f;
    }
    
    public static Vec3 getPositionEyes() {
        return new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
    }
    
    public static EnumFacing getHorizontalFacing(final float yaw) {
        return EnumFacing.func_176731_b(MathUtil.floor(yaw * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    public static EnumFacing getHorizontalFacing(final Vec3 look) {
        return EnumFacing.func_176731_b(MathUtil.floor(Rotation.getRotation(look).yaw * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    public static boolean isStandingStill() {
        return PizzaClient.mc.field_71439_g.field_70159_w == 0.0 && PizzaClient.mc.field_71439_g.field_70179_y == 0.0;
    }
    
    public static boolean isStandingStill(final Entity entity) {
        return entity.field_70159_w == 0.0 && entity.field_70179_y == 0.0;
    }
    
    public static int getAdjacentHotbarSlot() {
        if (PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c == 0) {
            return 1;
        }
        if (PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c > 6) {
            return PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c - 1;
        }
        return (System.currentTimeMillis() % 2L == 0L) ? (PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c - 1) : (PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c + 1);
    }
    
    public static int getMiningTool() {
        int gauntlet = -1;
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null) {
                final String displayName = StringUtils.func_76338_a(stack.func_82833_r());
                if (stack.func_77973_b() == Items.field_179562_cC) {
                    if (displayName.contains("Drill")) {
                        return i;
                    }
                }
                else if (displayName.contains("Gemstone Gauntlet")) {
                    gauntlet = i;
                }
            }
        }
        if (gauntlet != -1) {
            return gauntlet;
        }
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() instanceof ItemPickaxe) {
                return i;
            }
        }
        return 0;
    }
    
    public static int getPickaxe() {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() instanceof ItemPickaxe) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean holdingMiningTool() {
        final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
        if (stack != null) {
            final Item item = stack.func_77973_b();
            return item == Items.field_179562_cC || item instanceof ItemPickaxe || StringUtils.func_76338_a(stack.func_82833_r()).contains("Gemstone Gauntlet");
        }
        return false;
    }
    
    public static boolean isInLimbo() {
        return !SBInfo.inSkyblock && PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g() == null && ScoreboardHandler.isScoreboardEmpty() && SBInfo.isOnHypixel();
    }
    
    public static boolean isInLobby() {
        final ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[0];
        return held != null && held.func_82833_r().equals("§aGame Menu §7(Right Click)");
    }
    
    public static boolean isInSkyblock() {
        return SBInfo.inSkyblock || SBInfo.isInSkyblock();
    }
    
    public static boolean warpToSkyblock() {
        return isInLimbo() || isInLobby() || !SBInfo.isInSkyblock();
    }
    
    public static Entity findClosestEntity(final Class<? extends Entity> clazz) {
        final EntityDistanceHelper helper = new EntityDistanceHelper();
        for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (entity.getClass() == clazz) {
                helper.compare(entity);
            }
        }
        return helper.closest;
    }
    
    public static void findPickonimbus() {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == Items.field_151046_w && stack.func_82833_r().contains("Pickonimbus 2000")) {
                PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                return;
            }
        }
        for (int i = 0; i < PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a.length; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == Items.field_151046_w && stack.func_82833_r().contains("Pickonimbus 2000")) {
                final int slot = i;
                final int n;
                int j;
                new Thread(() -> {
                    KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_151445_Q.func_151463_i());
                    Utils.sleep(500);
                    while (!(PizzaClient.mc.field_71462_r instanceof GuiInventory)) {
                        if (PizzaClient.mc.field_71462_r != null) {
                            PizzaClient.mc.field_71439_g.func_71053_j();
                            Utils.sleep(250);
                        }
                        KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_151445_Q.func_151463_i());
                        Utils.sleep(1000);
                    }
                    if (PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g() == null) {
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, n, 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                        Utils.sleep(500);
                        PizzaClient.mc.field_71439_g.func_71053_j();
                        return;
                    }
                    else {
                        j = 0;
                        while (j < 8) {
                            if (PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[j] == null) {
                                PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, n, 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                                Utils.sleep(500);
                                PizzaClient.mc.field_71439_g.func_71053_j();
                                return;
                            }
                            else {
                                ++j;
                            }
                        }
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, n, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                        Utils.sleep(500);
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 36, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                        Utils.sleep(500);
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, n, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                        Utils.sleep(500);
                        PizzaClient.mc.field_71439_g.func_71053_j();
                        return;
                    }
                }).start();
            }
        }
    }
    
    public static boolean isPassable(final AxisAlignedBB aabb) {
        return isPassable(aabb, getPositionEyes()) || isPassable(aabb, new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 0.6, PizzaClient.mc.field_71439_g.field_70161_v));
    }
    
    public static boolean isPassable(final AxisAlignedBB aabb, Vec3 position) {
        final Vec3 middle = Utils.getMiddleOfAABB(aabb);
        Vec3 look = getLook(middle);
        look = VecUtil.scaleVec(look, 0.10000000149011612 / look.func_72433_c());
        for (int i = 0; i < MathUtil.floor(position.func_72438_d(middle) / 0.10000000149011612) - 2; position = position.func_178787_e(look), ++i) {
            final BlockPos pos = new BlockPos(position);
            if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
                return false;
            }
            if (aabb.func_72318_a(position)) {
                return true;
            }
        }
        return true;
    }
    
    public static void useAbility() {
        if (PizzaClient.mc.field_71442_b.func_78769_a((EntityPlayer)PizzaClient.mc.field_71439_g, (World)PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g())) {
            PizzaClient.mc.field_71460_t.field_78516_c.func_78445_c();
        }
    }
    
    public static boolean switchDirection() {
        final double posX = Utils.formatDouble(MathUtil.abs(PizzaClient.mc.field_71439_g.field_70165_t));
        final double posZ = Utils.formatDouble(MathUtil.abs(PizzaClient.mc.field_71439_g.field_70161_v));
        final double x = Utils.formatDouble(posX - MathUtil.floor(posX));
        if (x == 0.3 || x == 0.7) {
            return true;
        }
        final double z = Utils.formatDouble(posZ - MathUtil.floor(posZ));
        return z == 0.3 || z == 0.7;
    }
    
    public static double getMovingDirection(final EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                return PizzaClient.mc.field_71439_g.field_70161_v;
            }
            case SOUTH: {
                return PizzaClient.mc.field_71439_g.field_70161_v;
            }
            default: {
                return PizzaClient.mc.field_71439_g.field_70165_t;
            }
        }
    }
    
    public static double getMovingDirection() {
        float yaw = PizzaClient.mc.field_71439_g.field_70177_z;
        if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d() || PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
            yaw += 90.0f;
        }
        switch (getHorizontalFacing(yaw)) {
            case NORTH: {
                return PizzaClient.mc.field_71439_g.field_70179_y;
            }
            case SOUTH: {
                return PizzaClient.mc.field_71439_g.field_70179_y;
            }
            default: {
                return PizzaClient.mc.field_71439_g.field_70159_w;
            }
        }
    }
    
    public static void windowClick(final int slot) {
        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, slot, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
    }
    
    public static void windowClick(final int slot, final int button, final int mode) {
        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, slot, button, mode, (EntityPlayer)PizzaClient.mc.field_71439_g);
    }
    
    public static void windowClick(final int extraId, final int slot, final int button, final int mode) {
        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c + extraId, slot, button, mode, (EntityPlayer)PizzaClient.mc.field_71439_g);
    }
    
    public static int getFishingRod() {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == Items.field_151112_aM) {
                return i;
            }
        }
        return 0;
    }
    
    public static int getRandomMovement() {
        return (Utils.random.nextInt(2) == 0) ? PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i() : PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i();
    }
    
    public static void moveOpposite() {
        if (PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d()) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
        }
        else if (PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d()) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
        }
        if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d()) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
        }
        else if (PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
        }
    }
    
    public static void rotateToClosestHitttable(final BlockPos currentBlock) {
        final float rand = MathUtil.randomFloat(3.0f);
        final float pitch = MathUtil.randomFloat(3.0f);
        final Vec3 hittable = Utils.getHittableAdjacent(currentBlock);
        if (hittable != null) {
            new Rotater(hittable).rotate();
            while (Rotater.rotating) {
                Utils.sleep(1);
            }
            Utils.sleep(50 + Utils.random.nextInt(50));
            new Rotater(VecUtil.getClosestHittableToMiddle(currentBlock)).rotate();
        }
        else {
            new Rotater(60.0f + rand, pitch).rotate();
            while (Rotater.rotating) {
                Utils.sleep(1);
            }
            Utils.sleep(50 + Utils.random.nextInt(50));
            new Rotater(-(60.0f + rand), -pitch).rotate();
        }
    }
    
    public static void rotateToClosestHitttable() {
        rotateToClosestHitttable(PizzaClient.mc.field_71476_x.func_178782_a());
    }
    
    public static void warpToChollows() {
        while (PizzaClient.mc.field_71462_r == null) {
            PizzaClient.mc.field_71439_g.func_71165_d("/sbmenu");
            Utils.sleep(1000);
        }
        Utils.sleep(300);
        if (PizzaClient.mc.field_71439_g.field_71070_bA instanceof ContainerChest && Utils.getContainerName().contains("SkyBlock Menu")) {
            while (!Utils.getContainerNameTrimmed().equals("Enter the Crystal Hollows?")) {
                if (!Utils.getContainerName().contains("SkyBlock Menu")) {
                    PizzaClient.mc.field_71439_g.func_71053_j();
                    Utils.sleep(500);
                    while (PizzaClient.mc.field_71462_r == null) {
                        PizzaClient.mc.field_71439_g.func_71165_d("/sbmenu");
                        Utils.sleep(1000);
                    }
                }
                PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 16, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                Utils.sleep(1000);
            }
            Utils.sleep(500);
            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 11, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
            Utils.sleep(2000);
            Utils.sleep(1000);
            if (PizzaClient.mc.field_71462_r != null) {
                PizzaClient.mc.field_71439_g.func_71053_j();
            }
            return;
        }
        PizzaClient.mc.field_71439_g.func_71053_j();
        Utils.sleep(400);
        warpToChollows();
    }
    
    public static boolean holdingItem(final Item item) {
        final ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
        return held != null && held.func_77973_b() == item;
    }
    
    public static void swapToSlot(final Item item) {
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == item) {
                PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                break;
            }
        }
    }
    
    public static void closeScreen() {
        while (PizzaClient.mc.field_71462_r != null) {
            PizzaClient.mc.field_71439_g.func_71053_j();
            Utils.sleep(500);
        }
    }
    
    static {
        secretBlocks = new HashSet<Block>(Arrays.asList((Block)Blocks.field_150486_ae, Blocks.field_150442_at, (Block)Blocks.field_150465_bP, Blocks.field_150447_bR));
        RIGHT_CLICK = new MethodReflectionHelper(Minecraft.class, "func_147121_ag", (Class<?>[])new Class[0]);
        LEFT_CLICK = new MethodReflectionHelper(Minecraft.class, "func_147116_af", (Class<?>[])new Class[0]);
        CONTROLLER = new MethodReflectionHelper(PlayerControllerMP.class, "func_78750_j", (Class<?>[])new Class[0]);
    }
}
