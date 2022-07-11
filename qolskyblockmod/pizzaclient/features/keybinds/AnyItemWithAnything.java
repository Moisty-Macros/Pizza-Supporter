package qolskyblockmod.pizzaclient.features.keybinds;

import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.item.*;

public class AnyItemWithAnything
{
    private static long lastInteract;
    
    public static void use() {
        if (System.currentTimeMillis() - AnyItemWithAnything.lastInteract >= PizzaClient.config.anyItemWithAnythingDelay) {
            AnyItemWithAnything.lastInteract = System.currentTimeMillis();
            final String name = PizzaClient.config.anyItemWithAnythingName.toLowerCase();
            for (int i = 0; i < 8; ++i) {
                final ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
                if (item != null && StringUtils.func_76338_a(item.func_82833_r().toLowerCase()).contains(name)) {
                    final int currentSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
                    PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                    if (PizzaClient.config.anyItemWithAnythingAction == 0) {
                        PizzaClient.mc.field_71442_b.func_78769_a((EntityPlayer)PizzaClient.mc.field_71439_g, (World)PizzaClient.mc.field_71441_e, item);
                    }
                    else {
                        PlayerUtil.forceUpdateController();
                        PlayerUtil.leftClick();
                    }
                    PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentSlot;
                    break;
                }
            }
        }
    }
}
