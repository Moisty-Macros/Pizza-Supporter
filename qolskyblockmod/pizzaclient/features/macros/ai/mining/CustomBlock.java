package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class CustomBlock
{
    public static final CustomBlock TITANIUM;
    public final PropertyEnum<?> property;
    public final Enum<?> value;
    
    public CustomBlock(final PropertyEnum<?> property, final Enum<?> value) {
        this.property = property;
        this.value = value;
    }
    
    public boolean equals(final IBlockState state) {
        return state.func_177229_b((IProperty)this.property) == this.value;
    }
    
    static {
        TITANIUM = new CustomBlock((PropertyEnum<?>)BlockStone.field_176247_a, (Enum<?>)BlockStone.EnumType.DIORITE_SMOOTH);
    }
}
