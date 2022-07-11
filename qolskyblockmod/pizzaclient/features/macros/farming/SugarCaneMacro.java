package qolskyblockmod.pizzaclient.features.macros.farming;

import qolskyblockmod.pizzaclient.features.macros.builder.macros.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.client.settings.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;

public class SugarCaneMacro extends FarmingMacro
{
    private boolean caneMacroKey;
    
    @Override
    public void onTick() {
        KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
        if (PlayerUtil.isStandingStill()) {
            this.caneMacroKey = !this.caneMacroKey;
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), this.caneMacroKey);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), !this.caneMacroKey);
        }
        super.onTick();
    }
    
    @Override
    public void onToggle(final boolean toggled) {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Sugar Cane Macro is now " + Utils.getColouredBoolean(toggled)));
        super.onToggle();
    }
    
    @Override
    public boolean applyPositionFailsafe() {
        return false;
    }
    
    @Override
    public boolean applyBedrockFailsafe() {
        return true;
    }
}
