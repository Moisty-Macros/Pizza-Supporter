package qolskyblockmod.pizzaclient.util.graphics.shaders.chroma;

import qolskyblockmod.pizzaclient.util.graphics.shaders.*;

public class TestShader extends Shader
{
    public static TestShader instance;
    
    public TestShader() {
        super(null, "testFragment");
    }
    
    @Override
    public void registerUniforms() {
    }
    
    static {
        TestShader.instance = new TestShader();
    }
}
