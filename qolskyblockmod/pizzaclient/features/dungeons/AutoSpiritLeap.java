package qolskyblockmod.pizzaclient.features.dungeons;

import java.util.*;
import net.minecraft.inventory.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class AutoSpiritLeap
{
    public static String leapName;
    public static boolean leapToDoor;
    
    public static void onRenderLeap(final List<Slot> invSlots) {
        if (AutoSpiritLeap.leapToDoor && AutoSpiritLeap.leapName != null) {
            for (int i = 11; i < 16; ++i) {
                final ItemStack stack = invSlots.get(i).func_75211_c();
                if (stack != null && StringUtils.func_76338_a(stack.func_82833_r()).contains(AutoSpiritLeap.leapName)) {
                    PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Leaping to " + AutoSpiritLeap.leapName + "!"));
                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                    AutoSpiritLeap.leapToDoor = false;
                    break;
                }
            }
        }
    }
}
