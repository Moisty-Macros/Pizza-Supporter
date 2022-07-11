package qolskyblockmod.pizzaclient.gui;

import qolskyblockmod.pizzaclient.gui.button.*;
import qolskyblockmod.pizzaclient.core.guioverlay.*;
import java.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class LocationEditGui extends GuiScreen
{
    private float xOffset;
    private float yOffset;
    private GuiElement dragging;
    private final Map<GuiElement, LocationButton> locationButtons;
    
    public LocationEditGui() {
        this.locationButtons = new HashMap<GuiElement, LocationButton>();
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        for (final GuiElement e : GuiManager.elements) {
            final LocationButton lb = new LocationButton(e);
            this.field_146292_n.add(lb);
            this.locationButtons.put(e, lb);
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.onMouseMove();
        this.func_146276_q_();
        for (final GuiButton button : this.field_146292_n) {
            if (button instanceof LocationButton) {
                if (!((LocationButton)button).element.getToggled()) {
                    continue;
                }
                button.func_146112_a(this.field_146297_k, mouseX, mouseY);
            }
            else {
                button.func_146112_a(this.field_146297_k, mouseX, mouseY);
            }
        }
    }
    
    public void func_146284_a(final GuiButton button) {
        if (button instanceof LocationButton) {
            final LocationButton lb = (LocationButton)button;
            this.dragging = lb.getElement();
            final ScaledResolution sr = new ScaledResolution(this.field_146297_k);
            final float minecraftScale = (float)sr.func_78325_e();
            final float floatMouseX = Mouse.getX() / minecraftScale;
            final float floatMouseY = (this.field_146297_k.field_71440_d - Mouse.getY()) / minecraftScale;
            this.xOffset = floatMouseX - this.dragging.getActualX();
            this.yOffset = floatMouseY - this.dragging.getActualY();
        }
    }
    
    protected void onMouseMove() {
        final ScaledResolution sr = new ScaledResolution(this.field_146297_k);
        final float minecraftScale = (float)sr.func_78325_e();
        final float floatMouseX = Mouse.getX() / minecraftScale;
        final float floatMouseY = (Display.getHeight() - Mouse.getY()) / minecraftScale;
        if (this.dragging != null) {
            final LocationButton lb = this.locationButtons.get(this.dragging);
            if (lb == null) {
                return;
            }
            final float x = (floatMouseX - this.xOffset) / sr.func_78326_a();
            final float y = (floatMouseY - this.yOffset) / sr.func_78328_b();
            this.dragging.setPos(x, y);
        }
    }
    
    protected void func_146286_b(final int mouseX, final int mouseY, final int state) {
        super.func_146286_b(mouseX, mouseY, state);
        this.dragging = null;
    }
    
    public void func_146281_b() {
        GuiManager.saveConfig();
    }
}
