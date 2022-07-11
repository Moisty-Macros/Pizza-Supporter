package qolskyblockmod.pizzaclient.core.events;

import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.minecraft.inventory.*;

public class ChestBackgroundDrawnEvent extends Event
{
    public Container chest;
    public String displayName;
    public int chestSize;
    public List<Slot> slots;
    public IInventory chestInv;
    
    public ChestBackgroundDrawnEvent(final Container chest, final String displayName, final int chestSize, final List<Slot> slots, final IInventory chestInv) {
        this.chest = chest;
        this.displayName = displayName;
        this.chestSize = chestSize;
        this.slots = slots;
        this.chestInv = chestInv;
    }
}
