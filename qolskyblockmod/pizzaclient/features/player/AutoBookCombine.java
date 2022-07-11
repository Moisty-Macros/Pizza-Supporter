package qolskyblockmod.pizzaclient.features.player;

import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import java.util.concurrent.atomic.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import java.util.concurrent.*;

public class AutoBookCombine
{
    private static final Map<String, Integer> books;
    private static boolean threadRunning;
    
    @SubscribeEvent
    public void onOpenGui(final GuiOpenEvent event) {
        AutoBookCombine.books.clear();
        AutoBookCombine.threadRunning = false;
    }
    
    public static void combineBooks(final List<Slot> invSlots) {
        if (AutoBookCombine.threadRunning) {
            return;
        }
        for (int i = 54; i <= 89; ++i) {
            final Slot slot = invSlots.get(i);
            if (slot.func_75211_c() != null && slot.func_75211_c().func_77973_b() == Items.field_151134_bR) {
                final NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(slot.func_75211_c());
                final NBTTagCompound enchantments = extraAttr.func_74775_l("enchantments");
                if (enchantments.func_150296_c().size() == 1) {
                    if (AutoBookCombine.books.containsKey(enchantments.toString()) && AutoBookCombine.books.get(enchantments.toString()) != i) {
                        if (invSlots.get(AutoBookCombine.books.get(enchantments.toString())).func_75211_c() != null) {
                            final AtomicInteger click = new AtomicInteger(i);
                            AutoBookCombine.threadRunning = true;
                            final NBTTagCompound nbtTagCompound;
                            final String pair;
                            final AtomicInteger atomicInteger;
                            new Thread(() -> {
                                Utils.sleep(150 + PizzaClient.config.autoBookCombineDelay);
                                pair = nbtTagCompound.toString();
                                if (PizzaClient.mc.field_71462_r != null) {
                                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, (int)AutoBookCombine.books.get(pair), 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                                }
                                AutoBookCombine.books.remove(pair);
                                Utils.sleep(300 + PizzaClient.config.autoBookCombineDelay);
                                if (invSlots.get(atomicInteger.get()).func_75211_c() == null) {
                                    atomicInteger.set(fix(pair, invSlots));
                                }
                                if (PizzaClient.mc.field_71462_r != null) {
                                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, atomicInteger.get(), 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                                }
                                while (invSlots.get(13).func_75211_c().func_77973_b() != Items.field_151134_bR) {
                                    if (PizzaClient.mc.field_71462_r == null) {
                                        return;
                                    }
                                }
                                Utils.sleep(50);
                                if (PizzaClient.mc.field_71462_r != null) {
                                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                                }
                                Utils.sleep(250 + PizzaClient.config.autoBookCombineDelay);
                                if (PizzaClient.mc.field_71462_r != null) {
                                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 13, 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                                }
                                Utils.sleep(50);
                                AutoBookCombine.threadRunning = false;
                            }).start();
                            return;
                        }
                        AutoBookCombine.books.remove(enchantments.toString());
                    }
                    else {
                        int value;
                        try {
                            value = Integer.parseInt(String.valueOf(enchantments.toString().charAt(enchantments.toString().indexOf(":") + 2)));
                        }
                        catch (Exception e) {
                            value = Integer.parseInt(String.valueOf(enchantments.toString().charAt(enchantments.toString().indexOf(":") + 1)));
                        }
                        if (enchantments.toString().contains("feather_falling") || enchantments.toString().contains("infinite_quiver")) {
                            if (value >= 10) {
                                continue;
                            }
                        }
                        else if (value >= 5) {
                            continue;
                        }
                        AutoBookCombine.books.put(enchantments.toString(), i);
                    }
                }
            }
        }
    }
    
    private static int fix(final String name, final List<Slot> invSlots) {
        for (int i = 54; i <= 89; ++i) {
            final Slot slot = invSlots.get(i);
            if (slot.func_75211_c() != null && slot.func_75211_c().func_77973_b() == Items.field_151134_bR) {
                final NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(slot.func_75211_c());
                final NBTTagCompound enchantments = extraAttr.func_74775_l("enchantments");
                if (enchantments.func_150296_c().size() == 1 && enchantments.toString().equals(name)) {
                    return i;
                }
            }
        }
        return 0;
    }
    
    public static void salvage(final List<Slot> invSlots) {
        if (AutoBookCombine.threadRunning) {
            return;
        }
        for (int i = 54; i <= 89; ++i) {
            final Slot slot = invSlots.get(i);
            if (invSlots.get(i).func_75211_c() != null) {
                final NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(slot.func_75211_c());
                final String sbId = ItemUtil.getSkyBlockItemID(slot.func_75211_c());
                if (!sbId.equals("ICE_SPRAY_WAND") && extraAttr != null && extraAttr.func_74764_b("baseStatBoostPercentage") && !extraAttr.func_74764_b("dungeon_item_level")) {
                    AutoBookCombine.threadRunning = true;
                    final int click = i;
                    final int n;
                    new Thread(() -> {
                        Utils.sleep(335 + PizzaClient.config.autoSalvageDelay);
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, n, 0, 1, (EntityPlayer)PizzaClient.mc.field_71439_g);
                        while (invSlots.get(13).func_75211_c() == null) {
                            if (PizzaClient.mc.field_71462_r == null) {
                                return;
                            }
                        }
                        Utils.sleep(200 + PizzaClient.config.autoSalvageDelay);
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                        AutoBookCombine.threadRunning = false;
                        return;
                    }).start();
                }
            }
        }
    }
    
    static {
        books = new ConcurrentHashMap<String, Integer>();
    }
}
