package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import qolskyblockmod.pizzaclient.features.macros.builder.macros.*;
import net.minecraftforge.fml.relauncher.*;
import qolskyblockmod.pizzaclient.*;
import qolskyblockmod.pizzaclient.features.skills.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.features.macros.builder.*;

@SideOnly(Side.CLIENT)
public class Nuker extends NukerMacro
{
    private Set<Block> targets;
    
    @Override
    public boolean isBlockValid(final BlockPos pos) {
        switch (PizzaClient.config.nukerType) {
            case 0: {
                return !Nuker.avoid.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
            }
            case 1: {
                final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                return this.targets.contains(state.func_177230_c()) && ((Nuker.vec != null && pos.equals((Object)new BlockPos(Nuker.vec))) || PizzaClient.config.gemstoneNukerType == 0 || PizzaClient.config.gemstoneNukerType == WorldScanner.getGemstoneType(state));
            }
            case 2: {
                final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                final Block block = state.func_177230_c();
                return (block instanceof IGrowable && block != Blocks.field_150349_c && !(block instanceof BlockStem) && !(block instanceof BlockTallGrass) && !(block instanceof BlockDoublePlant) && !((IGrowable)block).func_176473_a((World)PizzaClient.mc.field_71441_e, pos, state, PizzaClient.mc.field_71441_e.field_72995_K)) || (block instanceof IPlantable && block.func_176201_c(state) > 0);
            }
            default: {
                return this.targets.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
            }
        }
    }
    
    private Set<Block> targetBlock() {
        switch (PizzaClient.config.nukerType) {
            case 1: {
                return new HashSet<Block>((Collection<? extends Block>)(PizzaClient.config.gemstoneNukerGlassPanes ? Arrays.asList((Block)Blocks.field_150399_cn, (Block)Blocks.field_150397_co) : Collections.singletonList(Blocks.field_150399_cn)));
            }
            case 2: {
                return new HashSet<Block>();
            }
            case 3: {
                return new HashSet<Block>(Collections.singletonList(Blocks.field_150340_R));
            }
            case 4: {
                return new HashSet<Block>(Collections.singletonList(Blocks.field_150324_C));
            }
            case 5: {
                return new HashSet<Block>(Arrays.asList(Blocks.field_150364_r, Blocks.field_150363_s));
            }
            case 6: {
                return new HashSet<Block>((Collection<? extends Block>)Collections.singletonList(Blocks.field_150354_m));
            }
            case 7: {
                return new HashSet<Block>(Arrays.asList(Blocks.field_150365_q, Blocks.field_150366_p, Blocks.field_150352_o, Blocks.field_150369_x, Blocks.field_150450_ax, Blocks.field_150439_ay, Blocks.field_150482_ag, Blocks.field_150412_bA, Blocks.field_150449_bY));
            }
            default: {
                return new HashSet<Block>();
            }
        }
    }
    
    @Override
    public void onTick() {
        this.targets = this.targetBlock();
        NukerBase.nuker = this;
    }
    
    @Override
    public void onToggle(final boolean toggled) {
        if (toggled && !PizzaClient.config.nuker) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Enable the nuker in order to activate this macro!"));
            MacroBuilder.toggled = false;
            return;
        }
        this.addToggleMessage("Nuker");
    }
    
    @Override
    public void onDisable() {
        Nuker.vec = null;
    }
    
    @Override
    public boolean findVec() {
        if (PizzaClient.config.nukerType == 1) {
            if (PizzaClient.config.gemstoneNukerGlassPanes) {
                this.targets = new HashSet<Block>((Collection<? extends Block>)Collections.singletonList(Blocks.field_150399_cn));
                if (!super.findVec()) {
                    this.targets = new HashSet<Block>((Collection<? extends Block>)Collections.singletonList(Blocks.field_150397_co));
                    if (!super.findVec()) {
                        if (PizzaClient.config.gemstoneNukerType == 0) {
                            return false;
                        }
                        final int type = PizzaClient.config.gemstoneNukerType;
                        PizzaClient.config.gemstoneNukerType = 0;
                        this.targets = new HashSet<Block>((Collection<? extends Block>)Collections.singletonList(Blocks.field_150399_cn));
                        if (!super.findVec()) {
                            this.targets = new HashSet<Block>((Collection<? extends Block>)Collections.singletonList(Blocks.field_150397_co));
                            final boolean found = super.findVec();
                            PizzaClient.config.gemstoneNukerType = type;
                            return found;
                        }
                        PizzaClient.config.gemstoneNukerType = type;
                    }
                }
            }
            else {
                this.targets = new HashSet<Block>((Collection<? extends Block>)Collections.singletonList(Blocks.field_150399_cn));
                if (!super.findVec()) {
                    if (PizzaClient.config.gemstoneNukerType == 0) {
                        return false;
                    }
                    final int type = PizzaClient.config.gemstoneNukerType;
                    PizzaClient.config.gemstoneNukerType = 0;
                    final boolean found = super.findVec();
                    PizzaClient.config.gemstoneNukerType = type;
                    return found;
                }
            }
            return true;
        }
        return super.findVec();
    }
}
