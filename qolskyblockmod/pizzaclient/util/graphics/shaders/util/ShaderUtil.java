package qolskyblockmod.pizzaclient.util.graphics.shaders.util;

import org.lwjgl.opengl.*;

public class ShaderUtil
{
    public static final boolean ARB_SHADERS;
    
    public static void glUniform1f(final int location, final float v0) {
        if (ShaderUtil.ARB_SHADERS) {
            ARBShaderObjects.glUniform1fARB(location, v0);
        }
        else {
            GL20.glUniform1f(location, v0);
        }
    }
    
    public static void glUniform1f(final int location, final double v0) {
        if (ShaderUtil.ARB_SHADERS) {
            ARBShaderObjects.glUniform1fARB(location, (float)v0);
        }
        else {
            GL20.glUniform1f(location, (float)v0);
        }
    }
    
    public static void glUniform3f(final int location, final float v0, final float v1, final float v2) {
        if (ShaderUtil.ARB_SHADERS) {
            ARBShaderObjects.glUniform3fARB(location, v0, v1, v2);
        }
        else {
            GL20.glUniform3f(location, v0, v1, v2);
        }
    }
    
    public static void glUniform3d(final int location, final double v0, final double v1, final double v2) {
        if (ShaderUtil.ARB_SHADERS) {
            ARBShaderObjects.glUniform3fARB(location, (float)v0, (float)v1, (float)v2);
        }
        else {
            GL20.glUniform3f(location, (float)v0, (float)v1, (float)v2);
        }
    }
    
    static {
        ARB_SHADERS = !GLContext.getCapabilities().OpenGL21;
    }
}
