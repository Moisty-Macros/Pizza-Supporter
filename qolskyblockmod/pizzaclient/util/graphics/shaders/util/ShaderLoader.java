package qolskyblockmod.pizzaclient.util.graphics.shaders.util;

import qolskyblockmod.pizzaclient.util.graphics.shaders.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.*;
import java.io.*;
import qolskyblockmod.pizzaclient.util.*;
import org.lwjgl.*;
import net.minecraft.client.renderer.*;
import java.nio.*;

public class ShaderLoader
{
    public static void load(final Shader shader, final String vertex, final String fragment) {
        try {
            if (vertex != null) {
                final ResourceLocation resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + vertex + ".vsh");
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
                final byte[] bytes = Utils.toByteArray(bufferedInputStream);
                final ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
                buffer.put(bytes);
                buffer.position(0);
                final int shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153209_q);
                OpenGlHelper.func_153169_a(shaderID, buffer);
                OpenGlHelper.func_153170_c(shaderID);
                OpenGlHelper.func_153178_b(shader.program, shaderID);
                if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
                    throw new RuntimeException();
                }
            }
            if (fragment != null) {
                final ResourceLocation resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + fragment + ".fsh");
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
                final byte[] bytes = Utils.toByteArray(bufferedInputStream);
                final ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
                buffer.put(bytes);
                buffer.position(0);
                final int shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153210_r);
                OpenGlHelper.func_153169_a(shaderID, buffer);
                OpenGlHelper.func_153170_c(shaderID);
                OpenGlHelper.func_153178_b(shader.program, shaderID);
                if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
                    throw new RuntimeException();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static void load(final Shader shader, final String location) {
        try {
            if (location != null) {
                ResourceLocation resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + location + ".vsh");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
                byte[] bytes = Utils.toByteArray(bufferedInputStream);
                ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
                buffer.put(bytes);
                buffer.position(0);
                int shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153209_q);
                OpenGlHelper.func_153169_a(shaderID, buffer);
                OpenGlHelper.func_153170_c(shaderID);
                OpenGlHelper.func_153178_b(shader.program, shaderID);
                if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
                    throw new RuntimeException();
                }
                resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + location + ".fsh");
                bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
                bytes = Utils.toByteArray(bufferedInputStream);
                buffer = BufferUtils.createByteBuffer(bytes.length);
                buffer.put(bytes);
                buffer.position(0);
                shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153210_r);
                OpenGlHelper.func_153169_a(shaderID, buffer);
                OpenGlHelper.func_153170_c(shaderID);
                OpenGlHelper.func_153178_b(shader.program, shaderID);
                if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
                    throw new RuntimeException();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
