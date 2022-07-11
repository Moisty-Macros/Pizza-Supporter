package qolskyblockmod.pizzaclient.listeners;

import net.minecraftforge.client.event.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import java.net.*;
import qolskyblockmod.pizzaclient.features.dungeons.*;
import qolskyblockmod.pizzaclient.features.macros.builder.*;
import java.util.regex.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChatListener
{
    private final Pattern ANTI_FURRY_PATTERN;
    
    public ChatListener() {
        this.ANTI_FURRY_PATTERN = Pattern.compile("[oqua>]w[oqua<]|nya|meow", 2);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onChatMessage(final ClientChatReceivedEvent event) {
        if (PizzaClient.config.antiFurry) {
            final Matcher matcher = this.ANTI_FURRY_PATTERN.matcher(event.message.func_150260_c());
            if (matcher.find()) {
                event.message = (IChatComponent)new ChatComponentText(matcher.replaceAll(PizzaClient.config.antiFurryCensor));
            }
        }
        if (event.type == 2) {
            return;
        }
        final String unformatted = StringUtils.func_76338_a(event.message.func_150260_c());
        if (PizzaClient.config.autoPickonimbus && unformatted.equals("Oh no! Your Pickonimbus 2000 broke!")) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > Swapping to pickonimbus..."));
            PlayerUtil.findPickonimbus();
            return;
        }
        if (unformatted.startsWith("Dungeon Finder > BennettArthur joined the dungeon group!")) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Shitters out"));
            PizzaClient.mc.field_71439_g.func_71165_d("/p kick BennettArthur");
            return;
        }
        if (PizzaClient.location == Locations.DUNGEON) {
            if (unformatted.startsWith("[BOSS] ")) {
                if (unformatted.equals("[BOSS] Livid: I respect you for making it to here, but I'll be your undoing.")) {
                    DungeonFeatures.shouldLividsSpawn = true;
                    return;
                }
                if (unformatted.startsWith("[BOSS] Necron: FINE! LET'S MOVE TO SOMEWHERE ELSE") && PizzaClient.config.autoClipGhostblocks) {
                    DungeonFeatures.clipGhostBlocks();
                }
            }
            else {
                if (PizzaClient.config.quizAura) {
                    if (!QuizAura.isQuizActive) {
                        if (unformatted.equals("[STATUE] Oruo the Omniscient: Answer incorrectly, and your moment of ineptitude will live on for generations.")) {
                            QuizAura.isQuizActive = true;
                            return;
                        }
                    }
                    else {
                        QuizAura.onChat(unformatted);
                    }
                }
                if (PizzaClient.config.funnyDungeonFail) {
                    if (unformatted.startsWith("PUZZLE FAIL! " + PizzaClient.username)) {
                        EntityPlayerSP field_71439_g;
                        final ChatComponentText chatComponentText;
                        EntityPlayerSP field_71439_g2;
                        final ChatComponentText chatComponentText2;
                        new Thread(() -> {
                            try {
                                field_71439_g = PizzaClient.mc.field_71439_g;
                                new ChatComponentText(Utils.getJson(new URL("https://api.myip.com/")).getAsJsonObject().get("ip").getAsString());
                                field_71439_g.func_145747_a((IChatComponent)chatComponentText);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                field_71439_g2 = PizzaClient.mc.field_71439_g;
                                new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to add the ip to chat :(");
                                field_71439_g2.func_145747_a((IChatComponent)chatComponentText2);
                            }
                        }).start();
                    }
                    return;
                }
                if (PizzaClient.config.autoLeapDoor && unformatted.contains(" opened a WITHER")) {
                    AutoSpiritLeap.leapName = unformatted.substring(0, unformatted.indexOf(" "));
                }
            }
        }
        else {
            if (unformatted.startsWith("Your pass to the Crystal Hollows will expire ")) {
                PizzaClient.mc.field_71439_g.func_71165_d("/purchasecrystallhollowspass");
                return;
            }
            if (MacroBuilder.toggled) {
                if (unformatted.startsWith("You died")) {
                    MacroBuilder.currentMacro.onDeath();
                    return;
                }
                MacroBuilder.currentMacro.onChat(unformatted);
            }
        }
    }
}
