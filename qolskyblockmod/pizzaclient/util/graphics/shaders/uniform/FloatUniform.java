package qolskyblockmod.pizzaclient.util.graphics.shaders.uniform;

import java.util.function.*;
import net.minecraft.client.renderer.*;
import qolskyblockmod.pizzaclient.util.graphics.shaders.util.*;

public class FloatUniform implements IUniform
{
    public final Supplier<Float> supplier;
    public final int id;
    public float lastValue;
    
    public FloatUniform(final int program, final String name, final Supplier<Float> supplier) {
        this.id = OpenGlHelper.func_153194_a(program, (CharSequence)name);
        this.supplier = supplier;
        this.lastValue = supplier.get();
    }
    
    @Override
    public void update() {
        final float current = this.supplier.get();
        if (current != this.lastValue) {
            ShaderUtil.glUniform1f(this.id, current);
            this.lastValue = current;
        }
    }
}
