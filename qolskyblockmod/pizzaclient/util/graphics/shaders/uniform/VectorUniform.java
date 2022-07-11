package qolskyblockmod.pizzaclient.util.graphics.shaders.uniform;

import java.util.function.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import qolskyblockmod.pizzaclient.util.graphics.shaders.util.*;

public class VectorUniform implements IUniform
{
    public final Supplier<Vec3> supplier;
    public final int id;
    public Vec3 lastValue;
    
    public VectorUniform(final int program, final String name, final Supplier<Vec3> supplier) {
        this.id = OpenGlHelper.func_153194_a(program, (CharSequence)name);
        this.supplier = supplier;
        this.lastValue = supplier.get();
    }
    
    @Override
    public void update() {
        final Vec3 current = this.supplier.get();
        if (!current.equals(this.lastValue)) {
            ShaderUtil.glUniform3f(this.id, (float)current.field_72450_a, (float)current.field_72448_b, (float)current.field_72449_c);
            this.lastValue = current;
        }
    }
}
