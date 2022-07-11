package qolskyblockmod.pizzaclient.features.dungeons;

import qolskyblockmod.pizzaclient.core.events.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.util.rotation.fake.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;

public class SuperboomAura
{
    private long lastInteractTime;
    
    @SubscribeEvent
    public void onTick(final TickStartEvent event) {
        Label_0409: {
            if (PizzaClient.config.superboomAura && PizzaClient.location == Locations.DUNGEON && System.currentTimeMillis() - this.lastInteractTime >= 1500L) {
                for (int x = (int)(PizzaClient.mc.field_71439_g.field_70165_t - 6.0); x < PizzaClient.mc.field_71439_g.field_70165_t + 6.0; ++x) {
                    for (int y = (int)(PizzaClient.mc.field_71439_g.field_70163_u - 6.0); y < PizzaClient.mc.field_71439_g.field_70163_u + 6.0; ++y) {
                        for (int z = (int)(PizzaClient.mc.field_71439_g.field_70161_v - 6.0); z < PizzaClient.mc.field_71439_g.field_70161_v + 6.0; ++z) {
                            final BlockPos pos = new BlockPos(x, y, z);
                            final IBlockState block = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                            if (block.func_177230_c() == Blocks.field_150417_aV) {
                                if (Blocks.field_150417_aV.func_176201_c(block) == BlockStoneBrick.field_176251_N && VecUtil.canReachBlock(pos) && VecUtil.isHittable(pos)) {
                                    int count = 0;
                                    for (final BlockPos adjacent : BlockPos.func_177980_a(new BlockPos(x - 1, y - 1, z - 1), new BlockPos(x + 1, y + 1, z + 1))) {
                                        final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(adjacent);
                                        if (state.func_177230_c() == Blocks.field_150417_aV && Blocks.field_150417_aV.func_176201_c(state) == BlockStoneBrick.field_176251_N) {
                                            ++count;
                                        }
                                    }
                                    if (count >= 5) {
                                        final MovingObjectPosition hit = VecUtil.getHittableMovingObjectPosition(pos);
                                        if (hit != null) {
                                            FakeRotation.rotationVec = hit.field_72307_f;
                                            FakeRotation.hitPos = hit.func_178782_a();
                                            FakeRotation.itemSlot = getBoom();
                                            FakeRotation.rightClick = 1;
                                            this.lastInteractTime = System.currentTimeMillis();
                                            break Label_0409;
                                        }
                                    }
                                }
                            }
                            else if (block.func_177230_c() == Blocks.field_180389_cP) {}
                        }
                    }
                }
            }
        }
    }
    
    private static int getBoom() {
        int superboom = 0;
        final Item tnt = Item.func_150898_a(Blocks.field_150335_W);
        for (int i = 0; i < 8; ++i) {
            final ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() == tnt) {
                final String displayName = stack.func_82833_r();
                if (displayName.contains("Infinityboom")) {
                    return i;
                }
                if (displayName.contains("Superboom")) {
                    superboom = i;
                }
            }
        }
        return superboom;
    }
}
