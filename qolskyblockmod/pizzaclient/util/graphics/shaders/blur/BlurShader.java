package qolskyblockmod.pizzaclient.util.graphics.shaders.blur;

import qolskyblockmod.pizzaclient.util.graphics.shaders.*;
import qolskyblockmod.pizzaclient.util.graphics.shaders.uniform.*;
import net.minecraft.client.renderer.*;

public class BlurShader extends Shader
{
    public static BlurShader instance;
    
    public BlurShader() {
        super("blur/verticalBlurVertex", "blur/blurFragment");
    }
    
    @Override
    public void registerUniforms() {
        this.registerUniform(new FloatUniform(this.program, "targetHeight", () -> 10.0f));
    }
    
    @Override
    public void endShader() {
        OpenGlHelper.func_153161_d(0);
    }
    
    static {
        BlurShader.instance = new BlurShader();
    }
}
