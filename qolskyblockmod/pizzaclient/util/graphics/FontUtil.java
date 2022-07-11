package qolskyblockmod.pizzaclient.util.graphics;

import qolskyblockmod.pizzaclient.mixins.mixin.accessor.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.util.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
import qolskyblockmod.pizzaclient.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class FontUtil
{
    public static final char[] modMessageChars;
    public static int posX;
    public static int posY;
    public static final int[] CHAR_WIDTH;
    public static final float RGB_SPEED = 3000.0f;
    public static final AccessorFontRenderer fontRenderer;
    public static int[] colorCode;
    
    public static void init() {
        FontUtil.colorCode = FontUtil.fontRenderer.getColorCodes();
        BufferedImage bufferedimage;
        try {
            bufferedimage = Utils.readBufferedImage(new ResourceLocation("textures/font/ascii.png"));
        }
        catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }
        final int i = bufferedimage.getWidth();
        final int j = bufferedimage.getHeight();
        final int[] aint = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
        final int k = j / 16;
        final int l = i / 16;
        final int i2 = 1;
        final float f = 8.0f / l;
        for (int j2 = 0; j2 < 256; ++j2) {
            final int k2 = j2 % 16;
            final int l2 = j2 / 16;
            if (j2 == 32) {
                FontUtil.CHAR_WIDTH[j2] = 3 + i2;
            }
            int i3;
            for (i3 = l - 1; i3 >= 0; --i3) {
                final int j3 = k2 * l + i3;
                boolean flag = true;
                for (int k3 = 0; k3 < k; ++k3) {
                    final int l3 = (l2 * l + k3) * i;
                    if ((aint[j3 + l3] >> 24 & 0xFF) != 0x0) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            ++i3;
            FontUtil.CHAR_WIDTH[j2] = (int)(0.5 + i3 * f) + i2;
        }
    }
    
    public static int[] loadCharacters() {
        final int[] characters = new int[256];
        Arrays.fill(characters, -1);
        final char[] chars = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            characters[chars[i]] = i;
        }
        return characters;
    }
    
    public static void drawCenteredString(final String text, final float x, final float y, final int color) {
        PizzaClient.mc.field_71466_p.func_175065_a(text, x - PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0f, y, color, false);
    }
    
    public static void drawCenteredRainbowModMessage(final float x, final float y) {
        drawRainbowModMessage((int)(x - PizzaClient.mc.field_71466_p.func_78256_a("PizzaClient > ") / 2.0f), (int)y);
    }
    
    public static void drawCenteredRainbowString(final String text, final float x, final float y) {
        drawRainbowText(text, (int)(x - PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0f), (int)y);
    }
    
    public static void drawString(final String text, final float x, final float y, final int color) {
        PizzaClient.mc.field_71466_p.func_175065_a(text, x, y, color, false);
    }
    
    public static void drawBackground(final int width, final int height) {
        Gui.func_73734_a(0, 0, width, height, new Color(0, 0, 0, 90).getRGB());
    }
    
    public static void drawBackground(final int width, final int height, final int alpha) {
        Gui.func_73734_a(0, 0, width, height, new Color(0, 0, 0, alpha).getRGB());
    }
    
    public static void drawBackground(final float width, final float height) {
        Gui.func_73734_a(0, 0, (int)width, (int)height, new Color(0, 0, 0, 90).getRGB());
    }
    
    public static void drawRect(final double left, final double top, final double right, final double bottom, final int color) {
        Gui.func_73734_a((int)left, (int)top, (int)right, (int)bottom, color);
    }
    
    public static void drawRect(final double left, final double top, final double right, final double bottom, final Color color) {
        Gui.func_73734_a((int)left, (int)top, (int)right, (int)bottom, color.getRGB());
    }
    
    public static void drawRainbowText(final String input, final float x, final float y) {
        drawRainbowText(input.toCharArray(), (int)x, (int)y);
    }
    
    public static void drawRainbowText(final String input, final int x, final int y) {
        drawRainbowText(input.toCharArray(), x, y);
    }
    
    public static void drawRainbowText(final char[] input, final int x, final int y) {
        GlStateManager.func_179141_d();
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        FontUtil.posY = y;
        final long time = System.currentTimeMillis();
        if (Character.isDigit(input[0])) {
            final int color = Character.getNumericValue(input[0]);
            for (int i = 1; i < input.length; ++i) {
                FontUtil.posX += (int)renderRainbowChar(input[i], time, color, true);
            }
        }
        else {
            for (final char ch : input) {
                FontUtil.posX += (int)renderRainbowChar(ch, time, 0, true);
            }
        }
        GlStateManager.func_179117_G();
    }
    
    public static void drawRainbowText(final char[] input, final int x, final int y, final boolean shadow) {
        GlStateManager.func_179141_d();
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        FontUtil.posY = y;
        final long time = System.currentTimeMillis();
        if (Character.isDigit(input[0])) {
            final int color = Character.getNumericValue(input[0]);
            for (int i = 1; i < input.length; ++i) {
                FontUtil.posX += (int)renderRainbowChar(input[i], time, color, shadow);
            }
        }
        else {
            for (final char ch : input) {
                FontUtil.posX += (int)renderRainbowChar(ch, time, 0, shadow);
            }
        }
        GlStateManager.func_179117_G();
    }
    
    public static float drawRainbowName(final char[] input, final int x, final int y) {
        GlStateManager.func_179141_d();
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        FontUtil.posY = y;
        if (input[0] == '4') {
            drawColoredName(input);
        }
        else {
            final long time = System.currentTimeMillis();
            final int color = Character.getNumericValue(input[0]);
            for (int i = 1; i < input.length; ++i) {
                FontUtil.posX += (int)renderRainbowChar(input[i], time, color, true);
            }
        }
        GlStateManager.func_179117_G();
        return (float)FontUtil.posX;
    }
    
    public static float drawRainbowName(final char[] input, final int x, final int y, final boolean shadow) {
        GlStateManager.func_179141_d();
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        FontUtil.posY = y;
        if (input[0] == '4') {
            drawColoredName(input);
        }
        else {
            final long time = System.currentTimeMillis();
            final int color = Character.getNumericValue(input[0]);
            for (int i = 1; i < input.length; ++i) {
                FontUtil.posX += (int)renderRainbowChar(input[i], time, color, shadow);
            }
        }
        GlStateManager.func_179117_G();
        return (float)FontUtil.posX;
    }
    
    public static float drawRainbowModMessage(final int y) {
        return drawRainbowModMessage(0, y);
    }
    
    public static float drawRainbowModMessage(final int x, final int y) {
        GlStateManager.func_179141_d();
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        FontUtil.posY = y;
        final long time = System.currentTimeMillis();
        for (final char c : FontUtil.modMessageChars) {
            FontUtil.posX += (int)renderRainbowChar(c, time, true);
        }
        FontUtil.posX += (int)4.0f;
        final char c2 = '>';
        FontUtil.posX += (int)renderDefaultModMessageChar(c2);
        GlStateManager.func_179117_G();
        return FontUtil.posX + 4.0f;
    }
    
    public static float drawRainbowModMessage(final int x, final int y, final boolean shadow) {
        GlStateManager.func_179141_d();
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        FontUtil.posY = y;
        final long time = System.currentTimeMillis();
        for (final char c : FontUtil.modMessageChars) {
            FontUtil.posX += (int)renderRainbowChar(c, time, shadow);
        }
        FontUtil.posX += (int)4.0f;
        final char c2 = '>';
        FontUtil.posX += (int)renderDefaultModMessageChar(c2);
        GlStateManager.func_179117_G();
        return FontUtil.posX + 4.0f;
    }
    
    public static void renderChar(final char ch) {
        if (ch == ' ') {
            return;
        }
        if (ch == ' ') {
            return;
        }
        renderDefaultChar(ch);
    }
    
    public static void renderCharNoReturn(final char ch) {
        if (ch == ' ') {
            return;
        }
        if (ch == ' ') {
            return;
        }
        renderDefaultCharNoReturn(ch);
    }
    
    public static float renderDefaultChar(final int ch) {
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        final int l = FontUtil.CHAR_WIDTH[ch];
        final float f = l - 0.01f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (float)l;
    }
    
    public static float renderDefaultModMessageChar(final int ch) {
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        final int l = FontUtil.CHAR_WIDTH[ch];
        final float f = l - 0.01f;
        ++FontUtil.posX;
        ++FontUtil.posY;
        GL11.glColor4f(0.0f, 0.25f, 0.25f, 1.0f);
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glEnd();
        --FontUtil.posX;
        --FontUtil.posY;
        GL11.glColor3f(0.0f, 1.0f, 1.0f);
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (float)l;
    }
    
    public static void renderDefaultCharNoReturn(final int ch) {
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        final float f = FontUtil.CHAR_WIDTH[ch] - 0.01f;
        GL11.glShadeModel(7425);
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f((i + f - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + f - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glEnd();
    }
    
    public static float renderRainbowChar(final int ch, final long time, final boolean shadow) {
        final int l = FontUtil.CHAR_WIDTH[ch];
        final float width = l - 0.01f;
        final long y = (long)(FontUtil.posY * 11.0f);
        long position = time - ((long)(FontUtil.posX * 11.0f) - y);
        int color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
        float red = 0.0f;
        float blue = 0.0f;
        float green = 0.0f;
        float red2 = 0.0f;
        float blue2 = 0.0f;
        float green2 = 0.0f;
        switch (PizzaClient.config.modMessageColor) {
            case 0: {
                red = (color >> 16 & 0xFF) / 255.0f;
                blue = (color >> 8 & 0xFF) / 255.0f;
                green = (color & 0xFF) / 255.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = (color >> 16 & 0xFF) / 255.0f;
                blue2 = (color >> 8 & 0xFF) / 255.0f;
                green2 = (color & 0xFF) / 255.0f;
                break;
            }
            case 1: {
                red = 1.0f;
                blue = (color >> 8 & 0xFF) / 255.0f;
                green = (color & 0xFF) / 255.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = 1.0f;
                blue2 = (color >> 8 & 0xFF) / 255.0f;
                green2 = (color & 0xFF) / 255.0f;
                break;
            }
            case 2: {
                red = (color >> 16 & 0xFF) / 255.0f;
                blue = 1.0f;
                green = (color & 0xFF) / 255.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = (color >> 16 & 0xFF) / 255.0f;
                blue2 = 1.0f;
                green2 = (color & 0xFF) / 255.0f;
                break;
            }
            default: {
                red = (color >> 16 & 0xFF) / 255.0f;
                blue = (color >> 8 & 0xFF) / 255.0f;
                green = 1.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = (color >> 16 & 0xFF) / 255.0f;
                blue2 = (color >> 8 & 0xFF) / 255.0f;
                green2 = 1.0f;
                break;
            }
        }
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        if (shadow) {
            ++FontUtil.posX;
            ++FontUtil.posY;
            GL11.glColor4f(red / 4.0f, green / 4.0f, blue / 4.0f, 1.0f);
            GL11.glBegin(5);
            GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
            GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
            GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
            GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
            GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, j / 128.0f);
            GL11.glVertex3f(FontUtil.posX + width - 1.0f, (float)FontUtil.posY, 0.0f);
            GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
            GL11.glVertex3f(FontUtil.posX + width - 1.0f, FontUtil.posY + 7.99f, 0.0f);
            GL11.glEnd();
            --FontUtil.posX;
            --FontUtil.posY;
        }
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        GL11.glColor3f(red, green, blue);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glColor3f(red2, green2, blue2);
        GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + width - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + width - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        return (float)l;
    }
    
    public static float renderRainbowChar(final int ch, final long time, final int rgb, final boolean shadow) {
        if (ch == 32 || ch == 160) {
            return 4.0f;
        }
        if (ch > 255) {
            return 0.0f;
        }
        final int l = FontUtil.CHAR_WIDTH[ch];
        final float width = l - 0.01f;
        final long y = (long)(FontUtil.posY * 11.0f);
        long position = time - ((long)(FontUtil.posX * 11.0f) - y);
        int color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
        float red = 0.0f;
        float blue = 0.0f;
        float green = 0.0f;
        float red2 = 0.0f;
        float blue2 = 0.0f;
        float green2 = 0.0f;
        switch (rgb) {
            case 0: {
                red = (color >> 16 & 0xFF) / 255.0f;
                blue = (color >> 8 & 0xFF) / 255.0f;
                green = (color & 0xFF) / 255.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = (color >> 16 & 0xFF) / 255.0f;
                blue2 = (color >> 8 & 0xFF) / 255.0f;
                green2 = (color & 0xFF) / 255.0f;
                break;
            }
            case 1: {
                red = 1.0f;
                blue = (color >> 8 & 0xFF) / 255.0f;
                green = (color & 0xFF) / 255.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = 1.0f;
                blue2 = (color >> 8 & 0xFF) / 255.0f;
                green2 = (color & 0xFF) / 255.0f;
                break;
            }
            case 2: {
                red = (color >> 16 & 0xFF) / 255.0f;
                blue = 1.0f;
                green = (color & 0xFF) / 255.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = (color >> 16 & 0xFF) / 255.0f;
                blue2 = 1.0f;
                green2 = (color & 0xFF) / 255.0f;
                break;
            }
            default: {
                red = (color >> 16 & 0xFF) / 255.0f;
                blue = (color >> 8 & 0xFF) / 255.0f;
                green = 1.0f;
                position = time - ((long)((FontUtil.posX + l) * 11.0f) - y);
                color = Color.HSBtoRGB(position % 3000L / 3000.0f, PizzaClient.config.rgbBrightness, 1.0f);
                red2 = (color >> 16 & 0xFF) / 255.0f;
                blue2 = (color >> 8 & 0xFF) / 255.0f;
                green2 = 1.0f;
                break;
            }
        }
        final int i = ch % 16 * 8;
        final int j = ch / 16 * 8;
        if (shadow) {
            ++FontUtil.posX;
            ++FontUtil.posY;
            GL11.glColor4f(red / 4.0f, green / 4.0f, blue / 4.0f, 1.0f);
            GL11.glBegin(5);
            GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
            GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
            GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
            GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
            GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, j / 128.0f);
            GL11.glVertex3f(FontUtil.posX + width - 1.0f, (float)FontUtil.posY, 0.0f);
            GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
            GL11.glVertex3f(FontUtil.posX + width - 1.0f, FontUtil.posY + 7.99f, 0.0f);
            GL11.glEnd();
            --FontUtil.posX;
            --FontUtil.posY;
        }
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        GL11.glColor3f(red, green, blue);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glColor3f(red2, green2, blue2);
        GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + width - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + width - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        return (float)l;
    }
    
    public static int getStringWidth(final char[] chars) {
        int i = 0;
        boolean flag = false;
        for (int j = 0; j < chars.length; ++j) {
            char c0 = chars[j];
            int k = getCharWidth(c0);
            if (k < 0 && j < chars.length - 1) {
                ++j;
                c0 = chars[j];
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag = false;
                    }
                }
                else {
                    flag = true;
                }
                k = 0;
            }
            i += k;
            if (flag && k > 0) {
                ++i;
            }
        }
        return i;
    }
    
    public static int getRainbowStringWidth(final char[] chars) {
        int i = 0;
        boolean flag = false;
        for (int j = 1; j < chars.length; ++j) {
            char c0 = chars[j];
            int k = getCharWidth(c0);
            if (k < 0 && j < chars.length - 1) {
                ++j;
                c0 = chars[j];
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag = false;
                    }
                }
                else {
                    flag = true;
                }
                k = 0;
            }
            i += k;
            if (flag && k > 0) {
                ++i;
            }
        }
        return i;
    }
    
    public static int getCharWidth(final char ch) {
        if (ch == ' ') {
            return 4;
        }
        if (ch > '\u00ff') {
            return FontUtil.fontRenderer.getGlyphWidths()[ch];
        }
        return FontUtil.CHAR_WIDTH[ch];
    }
    
    public static void bindUnicodeTexture(final int page) {
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getUnicodePageLocation(page));
    }
    
    public static float drawColoredName(final char[] chars, final int x) {
        PizzaClient.mc.field_71446_o.func_110577_a(FontUtil.fontRenderer.getLocationFontTexture());
        FontUtil.posX = x;
        return drawColoredName(chars);
    }
    
    public static float drawColoredName(final char[] chars) {
        drawShadowedText(chars);
        for (int i = 1; i < chars.length; ++i) {
            final char ch = chars[i];
            if (ch == ' ') {
                FontUtil.posX += (int)4.0f;
            }
            else if (ch == '§') {
                final int color = FontUtil.colorCode["0123456789abcdefklmnor".indexOf(chars[i + 1])];
                GL11.glColor3f((color >> 16) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f);
                ++i;
            }
            else {
                FontUtil.posX += (int)renderDefaultChar(ch);
            }
        }
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
        return (float)FontUtil.posX;
    }
    
    public static void drawShadowedText(final char[] chars) {
        final int originX = FontUtil.posX;
        for (int i = 1; i < chars.length; ++i) {
            final char ch = chars[i];
            if (ch == ' ') {
                FontUtil.posX += (int)4.0f;
            }
            else if (ch == '§') {
                final int color = FontUtil.colorCode["0123456789abcdefklmnor".indexOf(chars[i + 1]) + 16];
                GL11.glColor3f((color >> 16) / 1020.0f, (color >> 8 & 0xFF) / 1020.0f, (color & 0xFF) / 1020.0f);
                ++i;
            }
            else {
                FontUtil.posX += (int)drawShadowedChar(ch);
            }
        }
        FontUtil.posX = originX;
    }
    
    public static float drawShadowedChar(final char ch) {
        final int l = FontUtil.CHAR_WIDTH[ch];
        final float width = l - 0.01f;
        final int i = ch % '\u0010' * 8;
        final int j = ch / '\u0010' * 8;
        ++FontUtil.posX;
        ++FontUtil.posY;
        GL11.glBegin(5);
        GL11.glTexCoord2f(i / 128.0f, j / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f(i / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f((float)FontUtil.posX, FontUtil.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, j / 128.0f);
        GL11.glVertex3f(FontUtil.posX + width - 1.0f, (float)FontUtil.posY, 0.0f);
        GL11.glTexCoord2f((i + width - 1.0f) / 128.0f, (j + 7.99f) / 128.0f);
        GL11.glVertex3f(FontUtil.posX + width - 1.0f, FontUtil.posY + 7.99f, 0.0f);
        GL11.glEnd();
        --FontUtil.posX;
        --FontUtil.posY;
        return (float)l;
    }
    
    public static float renderShadowedString(final String text, final int x, final int y, final int color) {
        GlStateManager.func_179141_d();
        FontUtil.fontRenderer.resetStyles();
        return (float)Math.max(FontUtil.fontRenderer.renderString(text, x + 1.0f, y + 1.0f, color, true), FontUtil.fontRenderer.renderString(text, (float)x, (float)y, color, false));
    }
    
    public static float renderString(final String text, final int x, final int y, final int color, final boolean shadow) {
        GlStateManager.func_179141_d();
        FontUtil.fontRenderer.resetStyles();
        if (shadow) {
            return (float)Math.max(FontUtil.fontRenderer.renderString(text, x + 1.0f, y + 1.0f, color, true), FontUtil.fontRenderer.renderString(text, (float)x, (float)y, color, false));
        }
        return (float)FontUtil.fontRenderer.renderString(text, (float)x, (float)y, color, false);
    }
    
    static {
        modMessageChars = "PizzaClient".toCharArray();
        CHAR_WIDTH = new int[256];
        fontRenderer = (AccessorFontRenderer)PizzaClient.mc.field_71466_p;
    }
}
