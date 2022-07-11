package qolskyblockmod.pizzaclient.features.macros.ai;

import qolskyblockmod.pizzaclient.*;
import net.minecraft.entity.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import qolskyblockmod.pizzaclient.features.macros.builder.*;
import qolskyblockmod.pizzaclient.util.remote.*;
import java.awt.*;
import java.util.*;

public class Failsafes
{
    public static final List<String> messages;
    public static final int TIMEOUT = 1800000;
    
    public static void checkForCaptchas() {
        if (SBInfo.inSkyblock) {
            for (int i = 0; i < 8; ++i) {
                final ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
                if (item == null || item.func_77973_b() != Items.field_151148_bJ) {
                    return;
                }
            }
            Utils.playOrbSound();
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Captcha!!"));
        }
    }
    
    public static void playerInRange() {
        if (PizzaClient.config.stopWhenNearPlayer && MacroBuilder.currentMacro.applyPlayerFailsafes()) {
            for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
                if (entity instanceof EntityOtherPlayerMP) {
                    final String name = entity.func_145748_c_().func_150254_d().trim();
                    if (!name.startsWith("§r§") || name.startsWith("§r§f") || StringUtils.func_76338_a(name).equals("Goblin")) {
                        continue;
                    }
                    if (entity.func_82150_aj() && !hasSorrow((EntityOtherPlayerMP)entity)) {
                        continue;
                    }
                    if (PlayerUtil.getPositionEyes().func_72436_e(new Vec3(entity.field_70165_t, entity.field_70163_u + 1.6200000047683716, entity.field_70161_v)) < PizzaClient.config.stopWhenNearPlayerRange * PizzaClient.config.stopWhenNearPlayerRange) {
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > Found player nearby, warping out. Player name: " + name));
                        disable();
                        unpressMovement();
                        PizzaClient.rotater = null;
                        MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
                            PizzaClient.mc.field_71439_g.func_71165_d("/hub");
                            Utils.sleep(5000 + PizzaClient.config.stopWhenNearPlayerTimeout * 1000);
                            reEnable();
                            return;
                        });
                        writeToWebhook("Player Detection (Name: " + StringUtils.func_76338_a(name) + ")");
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    public static void unpressMovement() {
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i(), false);
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
    }
    
    public static void checkBedrockBox() {
        if (PizzaClient.config.bedrockBoxFailsafe && MacroBuilder.currentMacro.applyBedrockFailsafe() && PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 1.0, PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150357_h) {
            disable();
            final EntityPlayerSP field_71439_g;
            final ChatComponentText chatComponentText;
            final int currentItem;
            MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
                field_71439_g = PizzaClient.mc.field_71439_g;
                new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Found bedrock box, applying failsafe.");
                field_71439_g.func_145747_a((IChatComponent)chatComponentText);
                sleepAndStop();
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
                Utils.sleep(1000 + Utils.random.nextInt(400));
                currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
                PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getAdjacentHotbarSlot();
                sendRandomMessage();
                warpBackAndCooldown();
                PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
                Utils.sleep(3000);
                reEnable();
                return;
            });
            writeToWebhook("Bedrock Box");
        }
    }
    
    public static void onPacketPosLook(final float yawIn) {
        if (MacroBuilder.currentMacro.applyFailsafes() && MacroBuilder.currentMacro.applyRotationFailsafe()) {
            final float yaw = PizzaClient.mc.field_71439_g.field_70177_z;
            final float pitch = PizzaClient.mc.field_71439_g.field_70125_A;
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Detected rotation packet, applying failsafe. Rotated yaw: " + yawIn));
            disable();
            final float n;
            final float goalYaw;
            final float n2;
            final float goalPitch;
            final EntityPlayerSP field_71439_g;
            final ChatComponentText chatComponentText;
            MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
                if (PizzaClient.rotater != null) {
                    Utils.sleep(250 + Utils.random.nextInt(150));
                }
                else {
                    Utils.sleep(500 + Utils.random.nextInt(250));
                }
                PizzaClient.rotater = null;
                Utils.sleep(100 + Utils.random.nextInt(50));
                unpressMovement();
                Utils.sleep(1000 + Utils.random.nextInt(200));
                goalYaw = MathHelper.func_76142_g(n - PizzaClient.mc.field_71439_g.field_70177_z);
                goalPitch = MathHelper.func_76142_g(n2 - PizzaClient.mc.field_71439_g.field_70125_A);
                new Rotater(goalYaw, goalPitch).rotate();
                Utils.sleep(10000 + Utils.random.nextInt(3000));
                field_71439_g = PizzaClient.mc.field_71439_g;
                new ChatComponentText(Utils.SUCCESS_MESSAGE + "re-enabling the macro!");
                field_71439_g.func_145747_a((IChatComponent)chatComponentText);
                MacroBuilder.updatePosition();
                return;
            });
            writeToWebhook("Received A Rotation Packet");
        }
    }
    
    public static void onChangePosition() {
        if (PizzaClient.config.positionChangeFailsafe && MacroBuilder.currentMacro.applyFailsafes() && MacroBuilder.currentMacro.applyPositionFailsafe()) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Detected position change, applying failsafe"));
            disable();
            final int currentItem;
            MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
                sleepAndStop(3000);
                KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
                Utils.sleep(1000 + Utils.random.nextInt(400));
                currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
                PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getAdjacentHotbarSlot();
                Utils.sleep(2000);
                warpBackAndCooldown();
                PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
                Utils.sleep(3000);
                reEnable();
                return;
            });
            writeToWebhook("Player Changed Position");
        }
    }
    
    public static void reEnable() {
        if (!PizzaClient.config.disableOnWorldLoad) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "re-enabling the macro!"));
            MacroBuilder.updatePosition();
        }
        else {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Not re-enabling the macro since \"Disable Macro On Switch World\" is enabled."));
        }
    }
    
    private static boolean hasSorrow(final EntityOtherPlayerMP entity) {
        for (final ItemStack item : entity.field_71071_by.field_70460_b) {
            if (item != null) {
                final Item item2 = item.func_77973_b();
                if (item2 == Items.field_151029_X || item2 == Items.field_151022_W || item2 == Items.field_151023_V || item2 == Items.field_151020_U) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void sleepAndStop() {
        sleepAndStop(4000);
    }
    
    private static void sleepAndStop(final int rotationAmt) {
        if (PizzaClient.rotater == null) {
            Utils.sleep(500 + Utils.random.nextInt(300));
        }
        else {
            Utils.sleep(300 + Utils.random.nextInt(150));
            PizzaClient.rotater = null;
            Utils.sleep(50 + Utils.random.nextInt(25));
        }
        unpressMovement();
        Utils.sleep(300 + Utils.random.nextInt(150));
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
        Utils.sleep(400 + Utils.random.nextInt(100));
        new FailsafeRotater(rotationAmt).rotate();
        while (Rotater.rotating) {
            Utils.sleep(1);
        }
        Utils.sleep(1500 + Utils.random.nextInt(500));
    }
    
    private static void sneak() {
        Utils.sleep(1000 + Utils.random.nextInt(400));
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
        Utils.sleep(100 + Utils.random.nextInt(50));
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
        Utils.sleep(110 + Utils.random.nextInt(60));
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
        Utils.sleep(100 + Utils.random.nextInt(50));
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
        Utils.sleep(400 + Utils.random.nextInt(100));
    }
    
    private static void sendRandomMessage() {
        Utils.sleep(2400 + Utils.random.nextInt(500));
        final String msg = "/ac " + Failsafes.messages.get(Utils.random.nextInt(Failsafes.messages.size()));
        PizzaClient.mc.field_71439_g.func_71165_d(msg);
        Utils.sleep(300 + Utils.random.nextInt(150));
    }
    
    private static void warpBackAndCooldown() {
        Utils.sleep(3300 + Utils.random.nextInt(1000));
        PizzaClient.mc.field_71439_g.func_71165_d("/hub");
        timeout();
    }
    
    public static void disable() {
        Utils.playOrbSound();
        MacroBuilder.currentMacro.terminateIfAlive();
        MacroBuilder.state = MacroState.FAILSAFE;
    }
    
    public static void writeToWebhook(final String reason) {
        if (PizzaClient.config.failsafeWebhook) {
            if (!PizzaClient.config.failsafeWebhookName.startsWith("https://discord.com")) {
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid webhook name."));
                return;
            }
            DiscordWebhook webhook;
            EntityPlayerSP field_71439_g;
            final ChatComponentText chatComponentText;
            new Thread(() -> {
                try {
                    webhook = new DiscordWebhook(PizzaClient.config.failsafeWebhookName);
                    webhook.setUsername("PizzaClient Failsafe");
                    if (PizzaClient.config.failsafeWebhookPing) {
                        webhook.setContent("@everyone");
                    }
                    webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("Failsafe Activated").setDescription(Utils.getFormattedDate()).addField("Username", PizzaClient.username, true).addField("Cause", reason, true).setColor(Color.CYAN));
                    webhook.execute();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    field_71439_g = PizzaClient.mc.field_71439_g;
                    new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to write to the webhook. See logs for more info.");
                    field_71439_g.func_145747_a((IChatComponent)chatComponentText);
                }
            }, "Failsafe Webhook").start();
        }
    }
    
    public static void writeToWebhookAndPing(final String reason) {
        if (PizzaClient.config.failsafeWebhook) {
            if (!PizzaClient.config.failsafeWebhookName.startsWith("https://discord.com")) {
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid webhook name."));
                return;
            }
            DiscordWebhook webhook;
            EntityPlayerSP field_71439_g;
            final ChatComponentText chatComponentText;
            new Thread(() -> {
                try {
                    webhook = new DiscordWebhook(PizzaClient.config.failsafeWebhookName);
                    webhook.setUsername("PizzaClient Failsafe");
                    webhook.setContent("@everyone");
                    webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("Failsafe Activated (IMPORTANT)").setDescription(Utils.getFormattedDate()).addField("Username", PizzaClient.username, true).addField("Cause", reason, true).setColor(Color.RED));
                    webhook.execute();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    field_71439_g = PizzaClient.mc.field_71439_g;
                    new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to write to the webhook. See logs for more info.");
                    field_71439_g.func_145747_a((IChatComponent)chatComponentText);
                }
            }, "Important Failsafe Webhook").start();
        }
    }
    
    public static void timeout() {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Disabling the macros, will re-enable in 30 minutes to make sure that the admin has left."));
        Utils.sleep(1800000);
    }
    
    public static void timeout(final int timeout) {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Disabling the macros, will re-enable after a while to make sure that the admin has left."));
        Utils.sleep(timeout);
    }
    
    private static String getScreenshotName() {
        return "PizzaClient Failsafe, at " + Utils.DATE_FORMAT.format(new Date());
    }
    
    static {
        messages = new ArrayList<String>(Arrays.asList("hi?", "wtf", "what", "???", "lol wtf?", "uhm what?", "huh??"));
    }
}
