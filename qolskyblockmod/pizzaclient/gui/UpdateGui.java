package qolskyblockmod.pizzaclient.gui;

import qolskyblockmod.pizzaclient.gui.button.*;
import net.minecraft.client.renderer.*;
import qolskyblockmod.pizzaclient.util.graphics.*;
import net.minecraft.client.gui.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.*;
import com.google.gson.*;

public class UpdateGui extends GuiScreen
{
    private static String[] splitString;
    private static String newVersion;
    private static boolean urlIssue;
    private static String link;
    public static boolean shownGui;
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void func_73866_w_() {
        final ScaledResolution sr = new ScaledResolution(this.field_146297_k);
        final float height = (float)sr.func_78328_b();
        super.func_73866_w_();
        this.field_146292_n.add(new BetterButton(1, this.field_146294_l / 2 - 95, (int)(height * 0.8), 190, 20, "Download"));
        this.field_146292_n.add(new BetterButton(0, this.field_146294_l / 2 - 95, (int)(height * 0.85), 190, 20, "Close"));
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        if (UpdateGui.splitString != null) {
            this.field_146292_n.get(1).func_146112_a(this.field_146297_k, mouseX, mouseY);
        }
        this.field_146292_n.get(0).func_146112_a(this.field_146297_k, mouseX, mouseY);
        float scale = 3.0f;
        GlStateManager.func_179152_a(scale, scale, 0.0f);
        FontUtil.drawCenteredString("Changes in the new Update (" + UpdateGui.newVersion + ") :", this.field_146294_l / 2.0f / scale, (this.field_146295_m / 4.0f - 75.0f) / scale, 65535);
        GlStateManager.func_179152_a(1.0f / scale, 1.0f / scale, 0.0f);
        if (UpdateGui.splitString == null || UpdateGui.urlIssue) {
            GlStateManager.func_179152_a(scale, scale, 0.0f);
            FontUtil.drawCenteredString("Something went wrong!", this.field_146294_l / 2.0f / scale, (this.field_146295_m / 4.0f + 110.0f) / scale, 16711680);
            GlStateManager.func_179152_a(1.0f / scale, 1.0f / scale, 0.0f);
            return;
        }
        int y = 12;
        scale = 1.25f;
        for (final String s : UpdateGui.splitString) {
            GlStateManager.func_179152_a(scale, scale, 0.0f);
            FontUtil.drawString(s, this.field_146294_l / 5.0f / scale, (this.field_146295_m / 4.0f + y) / scale, 16777215);
            GlStateManager.func_179152_a(1.0f / scale, 1.0f / scale, 0.0f);
            y += 15;
        }
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    protected void func_146284_a(final GuiButton button) {
        switch (button.field_146127_k) {
            case 0: {
                UpdateGui.shownGui = true;
                PizzaClient.displayScreen = (GuiScreen)new GuiMainMenu();
                break;
            }
            case 1: {
                try {
                    Utils.openUrl(UpdateGui.link);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    UpdateGui.urlIssue = true;
                    return;
                }
                UpdateGui.shownGui = true;
                PizzaClient.displayScreen = (GuiScreen)new GuiMainMenu();
                break;
            }
        }
    }
    
    public static void checkForUpdates() {
        if (PizzaClient.response != null) {
            try {
                final JsonObject obj = PizzaClient.response.get("updater").getAsJsonObject();
                UpdateGui.newVersion = obj.get("version").getAsString();
                if (!"1.1.3".equalsIgnoreCase(UpdateGui.newVersion)) {
                    UpdateGui.link = obj.get("url").getAsString();
                    final String featureString = obj.get("new").getAsString();
                    UpdateGui.splitString = featureString.split("\n");
                    for (int i = 0; i < UpdateGui.splitString.length; ++i) {
                        UpdateGui.splitString[i] = UpdateGui.splitString[i].replace("\n", "").replace("\r", "").replace("+ ", "§a+ ").replace("= ", "§f= ").replace("- ", "§c- ");
                    }
                    PizzaClient.displayScreen = new UpdateGui();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
