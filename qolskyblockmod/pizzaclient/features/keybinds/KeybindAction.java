package qolskyblockmod.pizzaclient.features.keybinds;

public enum KeybindAction
{
    LEFT, 
    RIGHT, 
    UNKNOWN;
    
    public static KeybindAction getActionFromString(final String s) {
        return s.equalsIgnoreCase("left") ? KeybindAction.LEFT : (s.equalsIgnoreCase("right") ? KeybindAction.RIGHT : KeybindAction.UNKNOWN);
    }
    
    public static String getString(final KeybindAction action) {
        return (action == KeybindAction.LEFT) ? "left" : ((action == KeybindAction.RIGHT) ? "right" : "null");
    }
}
