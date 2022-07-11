package qolskyblockmod.pizzaclient.features.dungeons.f7;

import net.minecraftforge.client.event.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import qolskyblockmod.pizzaclient.core.events.*;
import net.minecraft.init.*;
import qolskyblockmod.pizzaclient.util.rotation.fake.*;
import net.minecraftforge.event.world.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;

public class FunnyDevice
{
    private final Set<BlockPos> clickedItemFrames;
    private static final Map<BlockPos, Integer> requiredClicksForEntity;
    private int ticks;
    private boolean foundPattern;
    private final List<BlockPos> simonSaysQueue;
    public static final BlockPos simonSaysStart;
    public static boolean clickedSimonSays;
    private long lastInteractTime;
    
    public FunnyDevice() {
        this.clickedItemFrames = new HashSet<BlockPos>();
        this.ticks = 1;
        this.simonSaysQueue = new ArrayList<BlockPos>();
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (PizzaClient.config.autoAlignment && this.foundPattern) {
            for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityItemFrame) {
                    final EntityItemFrame itemFrame = (EntityItemFrame)entity;
                    if (itemFrame.func_82335_i() == null || itemFrame.func_82335_i().func_77973_b() != Items.field_151032_g) {
                        continue;
                    }
                    final BlockPos pos = new BlockPos(itemFrame.field_70165_t, itemFrame.field_70163_u, itemFrame.field_70161_v);
                    if (!FunnyDevice.requiredClicksForEntity.containsKey(pos)) {
                        continue;
                    }
                    final double x = itemFrame.field_70165_t;
                    final double y = itemFrame.field_70163_u;
                    final double z = itemFrame.field_70161_v;
                    RenderUtil.drawFilledBoxNoESP(new AxisAlignedBB(x - 0.0025, y + 0.33, z - 0.33, x + 0.025, y - 0.33, z + 0.33), this.clickedItemFrames.contains(pos) ? new Color(0, 200, 0) : new Color(200, 0, 0));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickStartEvent event) {
        if (PizzaClient.location != Locations.DUNGEON || PizzaClient.mc.field_71462_r != null || PizzaClient.mc.field_71476_x == null) {
            return;
        }
        if (PizzaClient.config.autoAlignment) {
            if (this.foundPattern && PizzaClient.mc.field_71476_x.field_72308_g instanceof EntityItemFrame) {
                final EntityItemFrame itemFrame = (EntityItemFrame)PizzaClient.mc.field_71476_x.field_72308_g;
                if (itemFrame.func_82335_i() != null && itemFrame.func_82335_i().func_77973_b() == Items.field_151032_g) {
                    final BlockPos itemFrameFixedPos = new BlockPos(itemFrame.field_70165_t, itemFrame.field_70163_u, itemFrame.field_70161_v);
                    if (!this.clickedItemFrames.contains(itemFrameFixedPos) && FunnyDevice.requiredClicksForEntity.containsKey(itemFrameFixedPos)) {
                        final int requiredRotation = FunnyDevice.requiredClicksForEntity.get(itemFrameFixedPos);
                        final int currentRotation = itemFrame.func_82333_j();
                        if (currentRotation != requiredRotation) {
                            final int clickAmount = (currentRotation < requiredRotation) ? (requiredRotation - currentRotation) : (requiredRotation - currentRotation + 8);
                            PlayerUtil.rightClick(clickAmount);
                        }
                        this.clickedItemFrames.add(itemFrameFixedPos);
                    }
                }
            }
            if (this.ticks % 70 == 0) {
                this.calculatePattern();
                this.ticks = 0;
            }
            ++this.ticks;
        }
    }
    
    @SubscribeEvent
    public void onBlockChange(final BlockChangeEvent event) {
        if (PizzaClient.config.autoAimingDevice && event.currentState.func_177230_c() == Blocks.field_150475_bE) {
            if (event.oldState.func_177230_c() == Blocks.field_150406_ce) {
                FakeRotation.leftClick = true;
                FakeRotation.rotationVec = new Vec3(event.pos.func_177958_n() + 0.5, event.pos.func_177956_o() + 0.99, event.pos.func_177952_p() + 0.5);
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        this.foundPattern = false;
        this.clickedItemFrames.clear();
        FunnyDevice.requiredClicksForEntity.clear();
        this.simonSaysQueue.clear();
        FunnyDevice.clickedSimonSays = false;
    }
    
    private void calculatePattern() {
        FunnyDevice.requiredClicksForEntity.clear();
        final Map<BlockPos, Entity> itemFrames = new HashMap<BlockPos, Entity>();
        final List<BlockPos> currentItemFrames = new ArrayList<BlockPos>();
        final List<BlockPos> startItemFrames = new ArrayList<BlockPos>();
        final Set<BlockPos> endItemFrames = new HashSet<BlockPos>();
        for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityItemFrame) {
                final ItemStack displayed = ((EntityItemFrame)entity).func_82335_i();
                if (displayed == null) {
                    continue;
                }
                final Item item = displayed.func_77973_b();
                if (item == Items.field_151032_g) {
                    itemFrames.put(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), entity);
                }
                else {
                    if (item != Item.func_150898_a(Blocks.field_150325_L)) {
                        continue;
                    }
                    if (EnumDyeColor.func_176764_b(displayed.func_77952_i()) == EnumDyeColor.LIME) {
                        startItemFrames.add(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
                    }
                    else {
                        endItemFrames.add(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
                    }
                }
            }
        }
        if (itemFrames.size() >= 9 && startItemFrames.size() != 0) {
            for (final BlockPos pos : startItemFrames) {
                BlockPos adjacent = pos.func_177984_a();
                if (itemFrames.containsKey(adjacent)) {
                    currentItemFrames.add(adjacent);
                }
                adjacent = pos.func_177977_b();
                if (itemFrames.containsKey(adjacent)) {
                    currentItemFrames.add(adjacent);
                }
                adjacent = pos.func_177968_d();
                if (itemFrames.containsKey(adjacent)) {
                    currentItemFrames.add(adjacent);
                }
                adjacent = pos.func_177978_c();
                if (itemFrames.containsKey(adjacent)) {
                    currentItemFrames.add(adjacent);
                }
            }
            for (int i = 0; i < 200; ++i) {
                if (currentItemFrames.size() == 0) {
                    if (!this.foundPattern) {
                        this.foundPattern = true;
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Pattern!"));
                    }
                    return;
                }
                final List<BlockPos> list = new ArrayList<BlockPos>(currentItemFrames);
                currentItemFrames.clear();
                for (final BlockPos pos2 : list) {
                    BlockPos adjacent2 = pos2.func_177984_a();
                    if (endItemFrames.contains(adjacent2)) {
                        FunnyDevice.requiredClicksForEntity.put(pos2, 7);
                    }
                    else {
                        adjacent2 = pos2.func_177977_b();
                        if (endItemFrames.contains(adjacent2)) {
                            FunnyDevice.requiredClicksForEntity.put(pos2, 3);
                        }
                        else {
                            adjacent2 = pos2.func_177968_d();
                            if (endItemFrames.contains(adjacent2)) {
                                FunnyDevice.requiredClicksForEntity.put(pos2, 5);
                            }
                            else {
                                adjacent2 = pos2.func_177978_c();
                                if (endItemFrames.contains(adjacent2)) {
                                    FunnyDevice.requiredClicksForEntity.put(pos2, 1);
                                }
                                else {
                                    if (FunnyDevice.requiredClicksForEntity.containsKey(pos2)) {
                                        continue;
                                    }
                                    adjacent2 = pos2.func_177984_a();
                                    if (itemFrames.containsKey(adjacent2) && !FunnyDevice.requiredClicksForEntity.containsKey(adjacent2)) {
                                        currentItemFrames.add(adjacent2);
                                        FunnyDevice.requiredClicksForEntity.put(pos2, 7);
                                    }
                                    else {
                                        adjacent2 = pos2.func_177977_b();
                                        if (itemFrames.containsKey(adjacent2) && !FunnyDevice.requiredClicksForEntity.containsKey(adjacent2)) {
                                            currentItemFrames.add(adjacent2);
                                            FunnyDevice.requiredClicksForEntity.put(pos2, 3);
                                        }
                                        else {
                                            adjacent2 = pos2.func_177968_d();
                                            if (itemFrames.containsKey(adjacent2) && !FunnyDevice.requiredClicksForEntity.containsKey(adjacent2)) {
                                                currentItemFrames.add(adjacent2);
                                                FunnyDevice.requiredClicksForEntity.put(pos2, 5);
                                            }
                                            else {
                                                adjacent2 = pos2.func_177978_c();
                                                if (!itemFrames.containsKey(adjacent2) || FunnyDevice.requiredClicksForEntity.containsKey(adjacent2)) {
                                                    continue;
                                                }
                                                currentItemFrames.add(adjacent2);
                                                FunnyDevice.requiredClicksForEntity.put(pos2, 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.foundPattern = false;
        }
    }
    
    static {
        requiredClicksForEntity = new HashMap<BlockPos, Integer>();
        simonSaysStart = new BlockPos(110, 121, 91);
    }
}
