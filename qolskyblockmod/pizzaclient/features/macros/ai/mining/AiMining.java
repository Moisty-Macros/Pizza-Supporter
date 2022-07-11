package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import qolskyblockmod.pizzaclient.util.rotation.helper.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.*;
import net.minecraft.init.*;
import net.minecraft.client.settings.*;
import net.minecraft.block.*;
import java.awt.*;
import qolskyblockmod.pizzaclient.util.*;
import net.minecraft.util.*;
import java.util.*;

public class AiMining
{
    public Set<Block> miningBlocks;
    public BlockPos currentBlock;
    public Vec3 blockVec;
    public static final Set<Block> ores;
    public long lastBlockSwitch;
    private boolean fixing;
    public final Set<BlockPos> hittables;
    private final Map<Block, CustomBlock> customBlocks;
    
    public AiMining(final Block block) {
        this.miningBlocks = new HashSet<Block>();
        this.hittables = new HashSet<BlockPos>();
        this.miningBlocks.add(block);
        this.customBlocks = new HashMap<Block, CustomBlock>();
    }
    
    public AiMining(final Set<Block> blocks) {
        this.miningBlocks = new HashSet<Block>();
        this.hittables = new HashSet<BlockPos>();
        this.miningBlocks = blocks;
        this.customBlocks = new HashMap<Block, CustomBlock>();
    }
    
    public AiMining(final Block block, final Map<Block, CustomBlock> map) {
        this.miningBlocks = new HashSet<Block>();
        this.hittables = new HashSet<BlockPos>();
        this.miningBlocks.add(block);
        this.customBlocks = map;
    }
    
    public AiMining(final Set<Block> blocks, final Map<Block, CustomBlock> map) {
        this.miningBlocks = new HashSet<Block>();
        this.hittables = new HashSet<BlockPos>();
        this.miningBlocks = blocks;
        this.customBlocks = map;
    }
    
    public boolean findBlock(final Block block, final int rotateAmount) {
        return this.findBlock(block, rotateAmount, 9999999);
    }
    
    public boolean findBlock(final Block block, final int rotateAmount, final int maxRotation) {
        final VecRotationHelper helper = new VecRotationHelper();
        for (final BlockPos pos : this.hittables) {
            if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == block) {
                helper.compare(pos);
            }
        }
        if (helper.bestVec != null && helper.bestRotation < maxRotation) {
            this.setValues(helper.bestVec, rotateAmount);
            return true;
        }
        return false;
    }
    
    public boolean findBlock(final Block block, final PropertyEnum<?> property, final Enum<?> value, final int rotateAmount, final int maxRotation) {
        final VecRotationHelper helper = new VecRotationHelper();
        for (final BlockPos pos : this.hittables) {
            final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
            if (state.func_177230_c() == block && state.func_177229_b((IProperty)property) == value) {
                helper.compare(pos);
            }
        }
        if (helper.bestVec != null && helper.bestRotation < maxRotation) {
            this.setValues(helper.bestVec, rotateAmount);
            return true;
        }
        return false;
    }
    
    public boolean findBlock(final Block block, final PropertyEnum<?> property, final Enum<?> value, final int rotateAmount) {
        return this.findBlock(block, property, value, rotateAmount, 9999999);
    }
    
    public boolean findBlock(final Set<Block> blocks, final PropertyEnum<?> property, final Enum<?> value, final int rotateAmount, final int maxRotation) {
        final VecRotationHelper helper = new VecRotationHelper();
        for (final BlockPos pos : this.hittables) {
            final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
            if (blocks.contains(state.func_177230_c()) && state.func_177229_b((IProperty)property) == value) {
                helper.compare(pos);
            }
        }
        if (helper.bestVec != null && helper.bestRotation < maxRotation) {
            this.setValues(helper.bestVec, rotateAmount);
            return true;
        }
        return false;
    }
    
    public boolean findBlock(final Set<Block> blocks, final PropertyEnum<?> property, final Enum<?> value, final int rotateAmount) {
        return this.findBlock(blocks, property, value, rotateAmount, 9999999);
    }
    
    public boolean findBlock(final Set<Block> blocks, final int rotateAmount, final int maxRotation) {
        final VecRotationHelper helper = new VecRotationHelper();
        for (final BlockPos pos : this.hittables) {
            if (blocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
                helper.compare(pos);
            }
        }
        if (helper.bestVec != null && helper.bestRotation < maxRotation) {
            this.setValues(helper.bestVec, rotateAmount);
            return true;
        }
        return false;
    }
    
    public boolean findBlock(final Set<Block> blocks, final int rotateAmount) {
        return this.findBlock(blocks, rotateAmount, 9999999);
    }
    
    public boolean findBlock(final Block block, final Block custom, final CustomBlock customBlock, final int rotateAmount, final int maxRotation) {
        final VecRotationHelper helper = new VecRotationHelper();
        for (final BlockPos pos : this.hittables) {
            final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
            final Block bl = state.func_177230_c();
            if (block == bl) {
                helper.compare(pos);
            }
            else {
                if (bl != custom || state.func_177229_b((IProperty)customBlock.property) != customBlock.value) {
                    continue;
                }
                helper.compare(pos);
            }
        }
        if (helper.bestVec != null && helper.bestRotation < maxRotation) {
            this.setValues(helper.bestVec, rotateAmount);
            return true;
        }
        return false;
    }
    
    public boolean findBlock(final Block block, final Block custom, final CustomBlock customBlock, final int rotateAmount) {
        return this.findBlock(block, custom, customBlock, rotateAmount, 9999999);
    }
    
    public void disable() {
        this.currentBlock = null;
        this.fixing = false;
    }
    
    public void rotate(final Vec3 vec, final int rotation) {
        if (vec != null) {
            new Rotater(vec).antiSus((float)rotation).rotate();
        }
        else {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Vec returned null somehow."));
            this.currentBlock = null;
            this.onMove();
        }
    }
    
    public boolean onTick() {
        if (this.blockVec == null) {
            this.blockVec = PizzaClient.mc.field_71476_x.field_72307_f;
        }
        if (this.currentBlock != null && PizzaClient.mc.field_71441_e.func_180495_p(this.currentBlock).func_177230_c() == Blocks.field_150357_h) {
            this.currentBlock = null;
            return this.fixing;
        }
        if (this.fixing) {
            return true;
        }
        if (this.currentBlock != null && System.currentTimeMillis() - this.lastBlockSwitch >= PizzaClient.config.mithrilMacroFixTime * 1000L) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Detected not mining. If you were still mining a block, change the mithril macro fix time in the config."));
            this.fixing = true;
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
            new Thread(() -> {
                Utils.sleep(400 + Utils.random.nextInt(200));
                this.rotateToClosestHitttable();
                while (Rotater.rotating) {
                    Utils.sleep(1);
                }
                Utils.sleep(500);
                this.fixing = false;
                this.currentBlock = null;
                this.lastBlockSwitch = System.currentTimeMillis();
                return;
            }).start();
            return true;
        }
        return false;
    }
    
    public void onToggle() {
        this.blockVec = PizzaClient.mc.field_71476_x.field_72307_f;
        this.lastBlockSwitch = System.currentTimeMillis();
        PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getMiningTool();
        this.fixing = false;
    }
    
    public static boolean isBlockUnmineable(final Block blockIn) {
        return blockIn instanceof BlockFence || blockIn instanceof BlockStairs;
    }
    
    public void render() {
        if (this.currentBlock != null) {
            RenderUtil.drawFilledEsp(this.currentBlock, Color.CYAN, 0.5f);
        }
        for (final BlockPos pos : this.hittables) {
            RenderUtil.drawOutlinedEsp(pos, Color.CYAN);
        }
    }
    
    public void render(final Color c) {
        if (this.currentBlock != null) {
            RenderUtil.drawFilledEsp(this.currentBlock, c, 0.5f);
        }
        for (final BlockPos pos : this.hittables) {
            RenderUtil.drawOutlinedEsp(pos, c);
        }
    }
    
    public void onMove() {
        this.hittables.clear();
        if (this.customBlocks.size() == 0) {
            for (final BlockPos pos : PlayerUtil.getPlayerBlocks()) {
                if (this.miningBlocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()) && pos.func_177956_o() != PizzaClient.mc.field_71439_g.field_70163_u - 1.0 && !Utils.isBlockBlocked(pos) && VecUtil.isHittable(pos)) {
                    this.hittables.add(pos);
                }
            }
        }
        else {
            for (final BlockPos pos : PlayerUtil.getPlayerBlocks()) {
                final IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                final Block block = state.func_177230_c();
                if (this.miningBlocks.contains(block)) {
                    if (pos.func_177956_o() == PizzaClient.mc.field_71439_g.field_70163_u - 1.0 || Utils.isBlockBlocked(pos) || !VecUtil.isHittable(pos)) {
                        continue;
                    }
                    this.hittables.add(pos);
                }
                else {
                    if (!this.customBlocks.containsKey(block)) {
                        continue;
                    }
                    final CustomBlock customBlock = this.customBlocks.get(block);
                    if (state.func_177229_b((IProperty)customBlock.property) != customBlock.value || pos.func_177956_o() == PizzaClient.mc.field_71439_g.field_70163_u - 1.0 || Utils.isBlockBlocked(pos) || !VecUtil.isHittable(pos)) {
                        continue;
                    }
                    this.hittables.add(pos);
                }
            }
        }
    }
    
    public void setValues(final BlockPos closestBlock, final int rotateAmount) {
        if (closestBlock == null) {
            return;
        }
        this.currentBlock = closestBlock;
        this.lastBlockSwitch = System.currentTimeMillis();
        this.blockVec = VecUtil.getClosestHittableToMiddle(closestBlock);
        if (this.blockVec == null) {
            this.hittables.remove(closestBlock);
            return;
        }
        this.rotate(this.blockVec, rotateAmount);
    }
    
    public void setValues(final Vec3 closestBlock, final int rotateAmount) {
        this.setValues(new BlockPos(closestBlock), rotateAmount);
    }
    
    private void rotateToClosestHitttable() {
        final float rand = MathUtil.randomFloat(3.0f);
        final float pitch = MathUtil.randomFloat(3.0f);
        final MovingObjectPosition hittable = Utils.getHittablePosition(this.currentBlock);
        if (hittable != null && hittable.func_178782_a() != null) {
            new Rotater(hittable.field_72307_f).rotate();
            while (Rotater.rotating) {
                Utils.sleep(1);
            }
            Utils.sleep(300 + Utils.random.nextInt(200));
            new Rotater(VecUtil.getClosestHittableToMiddle(hittable.func_178782_a())).rotate();
        }
        else {
            new Rotater(60.0f + rand, pitch).rotate();
            while (Rotater.rotating) {
                Utils.sleep(1);
            }
            Utils.sleep(50 + Utils.random.nextInt(50));
            new Rotater(-(60.0f + rand), -pitch).rotate();
        }
    }
    
    public void update() {
        this.lastBlockSwitch = System.currentTimeMillis();
        this.fixing = false;
        this.onMove();
    }
    
    static {
        ores = new HashSet<Block>(Arrays.asList(Blocks.field_150365_q, Blocks.field_150366_p, Blocks.field_150450_ax, Blocks.field_150352_o, Blocks.field_150482_ag, Blocks.field_150348_b, Blocks.field_150350_a, Blocks.field_150369_x, Blocks.field_150412_bA));
    }
}
