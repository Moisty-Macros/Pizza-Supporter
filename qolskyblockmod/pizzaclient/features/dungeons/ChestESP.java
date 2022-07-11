package qolskyblockmod.pizzaclient.features.dungeons;

import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.tileentity.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class ChestESP
{
    public static final Set<BlockPos> clickedBlocks;
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (PizzaClient.config.secretChestEsp) {
            for (final TileEntity tileEntity : PizzaClient.mc.field_71441_e.field_147482_g) {
                if (tileEntity instanceof TileEntityChest) {
                    final TileEntityChest chest = (TileEntityChest)tileEntity;
                    if (chest.func_145980_j() != 0 || ChestESP.clickedBlocks.contains(chest.func_174877_v())) {
                        continue;
                    }
                    if (PizzaClient.config.chestEspMode == 0) {
                        RenderUtil.drawFilledEsp(chest.func_174877_v(), PizzaClient.config.secretChestEspColor, 0.7f);
                    }
                    else {
                        RenderUtil.drawOutlinedEsp(chest.func_174877_v(), PizzaClient.config.secretChestEspColor, 2.2f);
                    }
                }
            }
        }
    }
    
    static {
        clickedBlocks = new HashSet<BlockPos>();
    }
}
