package qolskyblockmod.pizzaclient.features.skills;

import java.util.*;
import java.awt.*;
import qolskyblockmod.pizzaclient.core.events.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.util.misc.*;
import java.util.concurrent.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WorldScanner
{
    private int ticks;
    public static Map<BlockPos, Color> stainedGlass;
    public static Map<BlockPos, Color> glassPanes;
    public static final Color GEM_RED;
    public static final Color GEM_LIME;
    public static final Color GEM_ORANGE;
    public static final Color GEM_YELLOW;
    public static final Color GEM_LIGHTBLUE;
    public static final Color GEM_PURPLE;
    public static final Color GEM_MAGENTA;
    
    public WorldScanner() {
        this.ticks = 1;
    }
    
    @SubscribeEvent
    public void onTick(final TickStartEvent event) {
        if (PizzaClient.config.gemstoneScanner && PizzaClient.location == Locations.CHOLLOWS) {
            if (this.ticks % (PizzaClient.config.scannerDelay * 20) == 0) {
                this.ticks = 0;
                final int pY;
                int belowY;
                int aboveY;
                final ConcurrentHashMap<BlockPos, Color> foundGems;
                final ConcurrentHashMap<BlockPos, Color> foundPanes;
                int x;
                int y;
                int z;
                BlockPos pos;
                IBlockState state;
                EntityPlayerSP field_71439_g;
                final ChatComponentText chatComponentText;
                new Thread(() -> {
                    pY = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u);
                    belowY = pY - PizzaClient.config.scannerWidth;
                    aboveY = pY + PizzaClient.config.scannerWidth;
                    if (belowY < 31) {
                        belowY = 31;
                    }
                    if (aboveY > 189) {
                        aboveY = 189;
                    }
                    foundGems = new ConcurrentHashMap<BlockPos, Color>();
                    foundPanes = new ConcurrentHashMap<BlockPos, Color>();
                    for (x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t) - PizzaClient.config.scannerWidth; x < PizzaClient.mc.field_71439_g.field_70165_t + PizzaClient.config.scannerWidth; ++x) {
                        for (y = belowY; y < aboveY; ++y) {
                            for (z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v) - PizzaClient.config.scannerWidth; z < PizzaClient.mc.field_71439_g.field_70161_v + PizzaClient.config.scannerWidth; ++z) {
                                pos = new BlockPos(x, y, z);
                                state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                                if (state.func_177230_c() == Blocks.field_150399_cn) {
                                    switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlass.field_176547_a)) {
                                        case RED: {
                                            if (PizzaClient.config.redGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_RED);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case LIME: {
                                            if (PizzaClient.config.limeGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_LIME);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case ORANGE: {
                                            if (PizzaClient.config.orangeGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_ORANGE);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case YELLOW: {
                                            if (PizzaClient.config.yellowGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_YELLOW);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case LIGHT_BLUE: {
                                            if (PizzaClient.config.lightBlueGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_LIGHTBLUE);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case PURPLE: {
                                            if (PizzaClient.config.purpleGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_PURPLE);
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case MAGENTA: {
                                            if (PizzaClient.config.pinkGlassEsp) {
                                                foundGems.put(pos, WorldScanner.GEM_MAGENTA);
                                            }
                                            if (MiningFeatures.sayCoordsFairyGrotto) {
                                                Utils.playSound("random.orb", 0.5);
                                                field_71439_g = PizzaClient.mc.field_71439_g;
                                                new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Fairy Grotto in: " + EnumChatFormatting.RED + "X = " + x + ", Y = " + y + ", Z = " + z);
                                                field_71439_g.func_145747_a((IChatComponent)chatComponentText);
                                                MiningFeatures.miningCoords.add("§dFairy Grotto: §ax " + x + ", y " + y + ", z " + z);
                                                MiningFeatures.sayCoordsFairyGrotto = false;
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
                                else if (state.func_177230_c() == Blocks.field_150397_co && PizzaClient.config.glassPanesEsp) {
                                    switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlassPane.field_176245_a)) {
                                        case RED: {
                                            if (PizzaClient.config.redGlassEsp) {
                                                foundPanes.put(pos, new Color(255, 15, 45));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case LIME: {
                                            if (PizzaClient.config.limeGlassEsp) {
                                                foundPanes.put(pos, new Color(173, 255, 47));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case ORANGE: {
                                            if (PizzaClient.config.orangeGlassEsp) {
                                                foundPanes.put(pos, new Color(255, 110, 0));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case YELLOW: {
                                            if (PizzaClient.config.yellowGlassEsp) {
                                                foundPanes.put(pos, new Color(255, 215, 0));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case LIGHT_BLUE: {
                                            if (PizzaClient.config.lightBlueGlassEsp) {
                                                foundPanes.put(pos, new Color(102, 178, 255));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case PURPLE: {
                                            if (PizzaClient.config.purpleGlassEsp) {
                                                foundPanes.put(pos, new Color(153, 50, 204));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                        case MAGENTA: {
                                            if (PizzaClient.config.pinkGlassEsp) {
                                                foundPanes.put(pos, new Color(255, 0, 255));
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    WorldScanner.stainedGlass = foundGems;
                    WorldScanner.glassPanes = foundPanes;
                    return;
                }).start();
            }
            ++this.ticks;
        }
    }
    
    public static Color getGemstoneColor(final BlockPos pos) {
        final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
        if (state.func_177230_c() == Blocks.field_150399_cn) {
            switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlassPane.field_176245_a)) {
                case RED: {
                    return WorldScanner.GEM_RED;
                }
                case LIME: {
                    return WorldScanner.GEM_LIME;
                }
                case ORANGE: {
                    return WorldScanner.GEM_ORANGE;
                }
                case YELLOW: {
                    return WorldScanner.GEM_YELLOW;
                }
                case LIGHT_BLUE: {
                    return WorldScanner.GEM_LIGHTBLUE;
                }
                case PURPLE: {
                    return WorldScanner.GEM_PURPLE;
                }
                case MAGENTA: {
                    return WorldScanner.GEM_MAGENTA;
                }
            }
        }
        else if (state.func_177230_c() == Blocks.field_150397_co) {
            switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlassPane.field_176245_a)) {
                case RED: {
                    return WorldScanner.GEM_RED;
                }
                case LIME: {
                    return WorldScanner.GEM_LIME;
                }
                case ORANGE: {
                    return WorldScanner.GEM_ORANGE;
                }
                case YELLOW: {
                    return WorldScanner.GEM_YELLOW;
                }
                case LIGHT_BLUE: {
                    return WorldScanner.GEM_LIGHTBLUE;
                }
                case PURPLE: {
                    return WorldScanner.GEM_PURPLE;
                }
                case MAGENTA: {
                    return WorldScanner.GEM_MAGENTA;
                }
            }
        }
        return Color.BLACK;
    }
    
    public static int getGemstoneType(final BlockPos pos) {
        final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
        if (state.func_177230_c() == Blocks.field_150399_cn) {
            switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlass.field_176547_a)) {
                case RED: {
                    return 1;
                }
                case LIME: {
                    return 2;
                }
                case ORANGE: {
                    return 3;
                }
                case YELLOW: {
                    return 4;
                }
                case LIGHT_BLUE: {
                    return 5;
                }
                case PURPLE: {
                    return 6;
                }
                case MAGENTA: {
                    return 7;
                }
            }
        }
        else if (state.func_177230_c() == Blocks.field_150397_co) {
            switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlass.field_176547_a)) {
                case RED: {
                    return 1;
                }
                case LIME: {
                    return 2;
                }
                case ORANGE: {
                    return 3;
                }
                case YELLOW: {
                    return 4;
                }
                case LIGHT_BLUE: {
                    return 5;
                }
                case PURPLE: {
                    return 6;
                }
                case MAGENTA: {
                    return 7;
                }
            }
        }
        return -1;
    }
    
    public static int getGemstoneType(final IBlockState state) {
        if (state.func_177230_c() == Blocks.field_150399_cn) {
            switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlass.field_176547_a)) {
                case RED: {
                    return 1;
                }
                case LIME: {
                    return 2;
                }
                case ORANGE: {
                    return 3;
                }
                case YELLOW: {
                    return 4;
                }
                case LIGHT_BLUE: {
                    return 5;
                }
                case PURPLE: {
                    return 6;
                }
                case MAGENTA: {
                    return 7;
                }
            }
        }
        else if (state.func_177230_c() == Blocks.field_150397_co) {
            switch ((EnumDyeColor)state.func_177229_b((IProperty)BlockStainedGlass.field_176547_a)) {
                case RED: {
                    return 1;
                }
                case LIME: {
                    return 2;
                }
                case ORANGE: {
                    return 3;
                }
                case YELLOW: {
                    return 4;
                }
                case LIGHT_BLUE: {
                    return 5;
                }
                case PURPLE: {
                    return 6;
                }
                case MAGENTA: {
                    return 7;
                }
            }
        }
        return -1;
    }
    
    static {
        WorldScanner.stainedGlass = new ConcurrentHashMap<BlockPos, Color>();
        WorldScanner.glassPanes = new ConcurrentHashMap<BlockPos, Color>();
        GEM_RED = new Color(200, 15, 45);
        GEM_LIME = new Color(160, 255, 47);
        GEM_ORANGE = new Color(255, 110, 0);
        GEM_YELLOW = new Color(255, 215, 0);
        GEM_LIGHTBLUE = new Color(102, 178, 255);
        GEM_PURPLE = new Color(153, 50, 204);
        GEM_MAGENTA = new Color(250, 0, 250);
    }
}
