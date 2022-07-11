package qolskyblockmod.pizzaclient.features.macros.mining;

import qolskyblockmod.pizzaclient.features.macros.builder.macros.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;

public class GemstoneMacro extends Macro
{
    @Override
    public void onTick() {
        if (PizzaClient.location == Locations.CHOLLOWS) {}
    }
    
    @Override
    public void onToggle(final boolean toggled) {
        this.addToggleMessage("Gemstone Macro");
    }
    
    @Override
    public boolean applyFailsafes() {
        return true;
    }
    
    @Override
    public boolean applyPositionFailsafe() {
        return true;
    }
    
    @Override
    public boolean applyBedrockFailsafe() {
        return false;
    }
    
    @Override
    public boolean applyPlayerFailsafes() {
        return PizzaClient.config.stopWhenNearPlayer;
    }
}
