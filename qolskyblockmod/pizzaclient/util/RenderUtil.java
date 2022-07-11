package qolskyblockmod.pizzaclient.util;

import net.minecraft.client.renderer.entity.*;
import java.awt.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.*;
import java.util.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import net.minecraft.util.*;

public class RenderUtil
{
    public static final RenderManager renderManager;
    
    public static void drawFilledEsp(final AxisAlignedBB aabb, final Color c, final float alphaMultiplier) {
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f * alphaMultiplier);
        drawFullAABB(aabb.func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
    }
    
    public static void drawFilledEsp(final AxisAlignedBB aabb, final Color c) {
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawFullAABB(aabb.func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
    }
    
    public static void drawFilledBoxNoESP(final AxisAlignedBB aabb, final Color c, final float alphaMultiplier) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f * alphaMultiplier);
        drawFullAABB(aabb.func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawFilledBoxNoESP(final AxisAlignedBB aabb, final Color c) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawFullAABB(aabb.func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawFilledEsp(final BlockPos pos, final Color c, final float alphaMultiplier) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f * alphaMultiplier);
        drawFullAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos).func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawFilledEsp(final BlockPos pos, final Color c, final float alphaMultiplier, final float expand) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f * alphaMultiplier);
        drawFullAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos).func_72314_b((double)expand, (double)expand, (double)expand).func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawFilledEsp(final BlockPos pos, final Color c) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawFullAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos).func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawOutlinedEsp(final BlockPos pos, final Color c, final float width) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GL11.glLineWidth(width);
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawOutlinedAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos).func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GL11.glLineWidth(1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
    }
    
    public static void drawOutlinedEsp(final BlockPos pos, final Color c) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawOutlinedAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos).func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
    }
    
    public static void drawOutlinedBoxNoESP(final BlockPos pos, final Color c, final float width) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GL11.glLineWidth(width);
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawOutlinedAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos).func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GL11.glLineWidth(1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
    }
    
    public static void drawOutlinedEsp(final AxisAlignedBB aabb, final Color c, final float width) {
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GL11.glLineWidth(width);
        GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
        drawOutlinedAABB(aabb.func_72317_d(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n));
        GlStateManager.func_179098_w();
        GL11.glLineWidth(1.0f);
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
    }
    
    private static void drawOutlinedAABB(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
    }
    
    private static void drawFullAABB(final AxisAlignedBB aabb) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }
    
    public static void drawOnSlot(final Slot slot, final int color) {
        Gui.func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, color);
    }
    
    public static void drawOnSlot(final Slot slot, final Color color) {
        Gui.func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, color.getRGB());
    }
    
    public static void drawRainbowPath(final List<BetterBlockPos> positions, final float width) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldRenderer = tessellator.func_178180_c();
        final long time = System.currentTimeMillis();
        GL11.glShadeModel(7425);
        GL11.glTranslated(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GL11.glLineWidth(width);
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        for (int i = positions.size() - 1; i >= 0; --i) {
            final BetterBlockPos pos = positions.get(i);
            final int color = Color.HSBtoRGB((time - i * 60) % 5000L / 5000.0f, 0.75f, 1.0f);
            worldRenderer.func_181662_b(pos.field_177962_a + 0.5, pos.field_177960_b + 0.1, pos.field_177961_c + 0.5).func_181666_a((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GL11.glLineWidth(1.0f);
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
        GL11.glTranslated(RenderUtil.renderManager.field_78730_l, RenderUtil.renderManager.field_78731_m, RenderUtil.renderManager.field_78728_n);
        GL11.glShadeModel(7424);
    }
    
    public static void drawRainbowPath(final List<BetterBlockPos> positions) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldRenderer = tessellator.func_178180_c();
        final long time = System.currentTimeMillis();
        GL11.glShadeModel(7425);
        GL11.glTranslated(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        for (int i = positions.size() - 1; i >= 0; --i) {
            final BetterBlockPos pos = positions.get(i);
            final int color = Color.HSBtoRGB((time - i * 60) % 5000L / 5000.0f, 0.75f, 1.0f);
            worldRenderer.func_181662_b(pos.field_177962_a + 0.5, pos.field_177960_b + 0.1, pos.field_177961_c + 0.5).func_181666_a((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
        GL11.glTranslated(RenderUtil.renderManager.field_78730_l, RenderUtil.renderManager.field_78731_m, RenderUtil.renderManager.field_78728_n);
        GL11.glShadeModel(7424);
    }
    
    public static void drawRainbowLines(final List<Vec3> positions) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldRenderer = tessellator.func_178180_c();
        final long time = System.currentTimeMillis();
        GL11.glShadeModel(7425);
        GL11.glTranslated(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        for (int i = positions.size() - 1; i >= 0; --i) {
            final Vec3 pos = positions.get(i);
            final int color = Color.HSBtoRGB((time - i * 60) % 5000L / 5000.0f, 0.75f, 1.0f);
            worldRenderer.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181666_a((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
        GL11.glTranslated(RenderUtil.renderManager.field_78730_l, RenderUtil.renderManager.field_78731_m, RenderUtil.renderManager.field_78728_n);
        GL11.glShadeModel(7424);
    }
    
    public static void drawRainbowLines(final List<Vec3> positions, final float width) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final WorldRenderer worldRenderer = tessellator.func_178180_c();
        final long time = System.currentTimeMillis();
        GL11.glShadeModel(7425);
        GL11.glTranslated(-RenderUtil.renderManager.field_78730_l, -RenderUtil.renderManager.field_78731_m, -RenderUtil.renderManager.field_78728_n);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GL11.glLineWidth(width);
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        for (int i = positions.size() - 1; i >= 0; --i) {
            final Vec3 pos = positions.get(i);
            final int color = Color.HSBtoRGB((time - i * 60) % 5000L / 5000.0f, 0.75f, 1.0f);
            worldRenderer.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181666_a((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, 1.0f).func_181675_d();
        }
        tessellator.func_78381_a();
        GL11.glLineWidth(1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179126_j();
        GL11.glTranslated(RenderUtil.renderManager.field_78730_l, RenderUtil.renderManager.field_78731_m, RenderUtil.renderManager.field_78728_n);
        GL11.glShadeModel(7424);
    }
    
    static {
        renderManager = PizzaClient.mc.func_175598_ae();
    }
}
