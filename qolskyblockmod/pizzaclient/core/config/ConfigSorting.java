package qolskyblockmod.pizzaclient.core.config;

import java.util.*;
import gg.essential.vigilance.data.*;

public class ConfigSorting extends SortingBehavior
{
    private final List<String> categories;
    
    public ConfigSorting() {
        this.categories = Arrays.asList("Dungeons", "F7 Boss", "Macros", "Player", "Mining", "Keybinds", "Slayer", "Visuals", "Jokes", "Other");
    }
    
    public Comparator<Category> getCategoryComparator() {
        return Comparator.comparingInt(o -> this.categories.indexOf(o.getName()));
    }
}
