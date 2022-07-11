package qolskyblockmod.pizzaclient.features.dungeons.f7;

import net.minecraft.item.*;

public class ItemSlot
{
    public final ItemStack stack;
    public final int slot;
    
    public ItemSlot(final ItemStack stackIn, final int slotIn) {
        this.stack = stackIn;
        this.slot = slotIn;
    }
}
