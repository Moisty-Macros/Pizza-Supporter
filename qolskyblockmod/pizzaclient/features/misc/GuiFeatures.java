package qolskyblockmod.pizzaclient.features.misc;

import qolskyblockmod.pizzaclient.core.events.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import java.util.*;
import net.minecraft.inventory.*;
import qolskyblockmod.pizzaclient.features.dungeons.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import qolskyblockmod.pizzaclient.features.player.*;
import net.minecraftforge.event.world.*;

public class GuiFeatures
{
    private static long lastClick;
    private static boolean clicked;
    
    @SubscribeEvent
    public void onContainerBackground(final ChestBackgroundDrawnEvent event) {
        final String displayName = event.displayName;
        switch (displayName) {
            case "Chest": {
                if (PizzaClient.location == Locations.DUNGEON && PizzaClient.config.autoCloseDungChest) {
                    closeChest();
                    break;
                }
                break;
            }
            case "Anvil": {
                if (PizzaClient.config.autoBookCombine) {
                    AutoBookCombine.combineBooks(event.chest.field_75151_b);
                    break;
                }
                break;
            }
            case "Loot Chest": {
                if (PizzaClient.config.autoCloseLootChest && PizzaClient.location == Locations.CHOLLOWS) {
                    closeChest();
                    break;
                }
                break;
            }
            case "Treasure Chest": {
                if (PizzaClient.config.autoCloseLootChest && PizzaClient.location == Locations.CHOLLOWS) {
                    closeChest();
                    break;
                }
                break;
            }
            case "Salvage Dungeon Item": {
                if (PizzaClient.config.autoSalvage) {
                    AutoBookCombine.salvage(event.chest.field_75151_b);
                    break;
                }
                break;
            }
            case "Spirit Leap": {
                if (PizzaClient.config.autoLeapDoor) {
                    AutoSpiritLeap.onRenderLeap(event.slots);
                    break;
                }
                break;
            }
            case "Start Dungeon?": {
                if (!PizzaClient.config.autoReady) {
                    break;
                }
                if (GuiFeatures.lastClick == 0L) {
                    GuiFeatures.lastClick = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() - GuiFeatures.lastClick >= 125L) {
                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 13, 2, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                    GuiFeatures.lastClick = 0L;
                    break;
                }
                break;
            }
            default: {
                if (PizzaClient.config.autoReady && !GuiFeatures.clicked) {
                    if (event.displayName.startsWith("Catacombs - ")) {
                        if (GuiFeatures.lastClick == 0L) {
                            GuiFeatures.lastClick = System.currentTimeMillis();
                            return;
                        }
                        if (System.currentTimeMillis() - GuiFeatures.lastClick >= 150L) {
                            for (int i = 2; i < 7; ++i) {
                                if (event.slots.get(i).func_75211_c() != null && event.slots.get(i).func_75211_c().func_82833_r().contains(PizzaClient.username)) {
                                    final ItemStack below = event.slots.get(i + 9).func_75211_c();
                                    if (below == null || below.func_77952_i() == 14) {
                                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i + 9, 2, 0, (EntityPlayer)PizzaClient.mc.field_71439_g);
                                        GuiFeatures.lastClick = 0L;
                                        GuiFeatures.clicked = true;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    return;
                }
                break;
            }
        }
    }
    
    private static void closeChest() {
        if (GuiFeatures.lastClick == 0L) {
            GuiFeatures.lastClick = System.currentTimeMillis();
        }
        else if (System.currentTimeMillis() - GuiFeatures.lastClick >= 40L) {
            PizzaClient.mc.field_71439_g.func_71053_j();
            GuiFeatures.lastClick = 0L;
        }
    }
    
    @SubscribeEvent
    public void onOpenGui(final GuiOpenEvent event) {
        HarpSolver.click = false;
    }
    
    @SubscribeEvent
    public void onWorldChange(final WorldEvent.Load event) {
        GuiFeatures.clicked = false;
    }
}
