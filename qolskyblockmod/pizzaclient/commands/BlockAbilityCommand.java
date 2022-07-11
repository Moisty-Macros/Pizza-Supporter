package qolskyblockmod.pizzaclient.commands;

import java.util.*;
import net.minecraft.command.*;
import net.minecraft.client.entity.*;
import qolskyblockmod.pizzaclient.features.misc.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.item.*;

public class BlockAbilityCommand extends CommandBase
{
    public String func_71517_b() {
        return "blockability";
    }
    
    public List<String> func_71514_a() {
        return Collections.singletonList("disableability");
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/blockability [clearall] or [list]";
    }
    
    public int func_82362_a() {
        return 0;
    }
    
    public List<String> func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) {
        final EntityPlayerSP player = (EntityPlayerSP)sender;
        if (args.length != 0) {
            final String lowerCase;
            final String subcommand = lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "clearall": {
                    BlockAbility.blockedItems.clear();
                    BlockAbility.writeSave();
                    sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Cleared all custom ability blocks."));
                    break;
                }
                case "list": {
                    sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "Blocked Abilities list: " + EnumChatFormatting.AQUA + BlockAbility.blockedItems));
                    break;
                }
                default: {
                    player.func_145747_a((IChatComponent)new ChatComponentText(this.func_71518_a(sender)));
                    break;
                }
            }
            return;
        }
        final ItemStack item = player.field_71071_by.func_70448_g();
        if (item == null) {
            sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Hold the item with the ability that you want to block."));
            return;
        }
        if (!ItemUtil.hasRightClickAbility(item)) {
            sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "That item isn't valid."));
            return;
        }
        final String itemId = ItemUtil.getSkyBlockItemID(item);
        if (BlockAbility.blockedItems.contains(itemId)) {
            BlockAbility.blockedItems.remove(itemId);
            BlockAbility.writeSave();
            sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "Removed the ability blocker on " + EnumChatFormatting.GOLD + itemId));
        }
        else {
            BlockAbility.blockedItems.add(itemId);
            BlockAbility.writeSave();
            sender.func_145747_a((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "Added the ability blocker for " + EnumChatFormatting.GOLD + itemId));
        }
    }
}
