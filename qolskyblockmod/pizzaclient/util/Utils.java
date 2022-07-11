package qolskyblockmod.pizzaclient.util;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.client.network.*;
import java.awt.datatransfer.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.tileentity.*;
import com.mojang.authlib.properties.*;
import java.awt.*;
import java.net.*;
import net.minecraft.client.*;
import com.google.gson.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import java.text.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.client.shader.*;
import java.nio.*;
import net.minecraftforge.fml.common.*;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.*;
import java.io.*;
import org.apache.commons.io.*;
import java.util.*;

public class Utils
{
    public static final Random random;
    public static final DecimalFormat DECIMAL_FORMAT;
    public static final Set<Block> uncollidables;
    public static final String ERROR_MESSAGE;
    public static final String SUCCESS_MESSAGE;
    public static final DateFormat DATE_FORMAT;
    
    public static boolean isInTablist(final EntityPlayer player) {
        for (final NetworkPlayerInfo pi : PizzaClient.mc.func_147114_u().func_175106_d()) {
            if (pi.func_178845_a().getName().equalsIgnoreCase(player.func_70005_c_())) {
                return true;
            }
        }
        return false;
    }
    
    public static String getColouredBoolean(final boolean bool) {
        return bool ? (EnumChatFormatting.GREEN + "On") : (EnumChatFormatting.RED + "Off");
    }
    
    public static void sleep(final int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void playSound(final String sound, final double pitch) {
        PizzaClient.mc.field_71439_g.func_85030_a(sound, 1.0f, (float)pitch);
    }
    
    public static void playOrbSound(final double pitch) {
        PizzaClient.mc.field_71439_g.func_85030_a("random.orb", 1.0f, (float)pitch);
    }
    
    public static void playOrbSound() {
        PizzaClient.mc.field_71439_g.func_85030_a("random.orb", 1.0f, 0.5f);
    }
    
    public static void writeToClipboard(final String text, final String successMessage) {
        try {
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            final StringSelection output = new StringSelection(text);
            clipboard.setContents(output, output);
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + successMessage));
        }
        catch (IllegalStateException exception) {
            PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Clipboard not available!"));
            exception.printStackTrace();
        }
    }
    
    public static void writeToClipboard(final String text) {
        try {
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            final StringSelection output = new StringSelection(text);
            clipboard.setContents(output, output);
        }
        catch (Exception e) {
            FMLLog.getLogger().log(Level.ERROR, "Failed to copy to clipboard.");
            e.printStackTrace();
        }
    }
    
    public static int betterRandom(int randomness) {
        int randomNumb = 0;
        while (randomness > 0) {
            randomNumb += (int)(Utils.random.nextGaussian() * randomness);
            --randomness;
        }
        return randomNumb;
    }
    
    public static <T> T firstOrNull(final Iterable<T> iterable) {
        return (T)Iterables.getFirst((Iterable)iterable, (Object)null);
    }
    
    public static boolean isWitherEssence(final TileEntitySkull skull) {
        if (skull.func_145904_a() != 3) {
            return false;
        }
        final Property property = firstOrNull((Iterable<Property>)skull.func_152108_a().getProperties().get((Object)"textures"));
        return property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=");
    }
    
    public static boolean isSkullTexture(final TileEntitySkull skull, final String texture) {
        if (skull.func_145904_a() != 3) {
            return false;
        }
        final Property property = firstOrNull((Iterable<Property>)skull.func_152108_a().getProperties().get((Object)"textures"));
        return property != null && property.getValue().equals(texture);
    }
    
    public static String readURL(final URL url) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (final InputStream in = url.openStream()) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
    
    public static String readFile(final String file) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }
    
    public static String readFile(final File file) {
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String readURL(final String url) {
        try {
            final StringBuilder sb = new StringBuilder();
            final URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            try (final InputStream in = connection.getInputStream()) {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
            }
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static void openUrl(final String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public static String[] getStringArrayFromJsonArray(final JsonArray jsonArray) {
        final int arraySize = jsonArray.size();
        final String[] stringArray = new String[arraySize];
        for (int i = 0; i < arraySize; ++i) {
            stringArray[i] = jsonArray.get(i).getAsString();
        }
        return stringArray;
    }
    
    public static List<String> getStringListFromJsonArray(final JsonArray jsonArray) {
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            list.add(jsonArray.get(i).getAsString());
        }
        return list;
    }
    
    public static Set<String> getStringSetFromJsonArray(final JsonArray jsonArray) {
        final Set<String> strings = new HashSet<String>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            strings.add(jsonArray.get(i).getAsString());
        }
        return strings;
    }
    
    public static Vec3 scaleVec(final Vec3 vec, final double scale) {
        return new Vec3(vec.field_72450_a * scale, vec.field_72448_b * scale, vec.field_72449_c * scale);
    }
    
    public static Vec3 divideVec(final Vec3 vec, final double scale) {
        return new Vec3(vec.field_72450_a / scale, vec.field_72448_b / scale, vec.field_72449_c / scale);
    }
    
    public static boolean isJar() {
        try {
            Minecraft.class.getDeclaredMethod("func_147121_ag", (Class<?>[])new Class[0]);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static JsonElement getJson(final String jsonUrl) {
        return new JsonParser().parse((Reader)getInputStream(jsonUrl));
    }
    
    public static JsonElement getJson(final File file) {
        return new JsonParser().parse(readFile(file));
    }
    
    public static JsonElement getJson(final URL url) {
        try {
            final URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            return new JsonParser().parse((Reader)new InputStreamReader(conn.getInputStream()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static InputStreamReader getInputStream(final String url) {
        try {
            final URLConnection conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            return new InputStreamReader(conn.getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static InputStreamReader getInputStream(final URL url) {
        try {
            final URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            return new InputStreamReader(conn.getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String formatValue(final long amount) {
        if (amount >= 1000000000L) {
            return formatValue(amount, 1000000000L, 'b');
        }
        if (amount >= 1000000L) {
            return formatValue(amount, 1000000L, 'm');
        }
        if (amount >= 1000L) {
            return formatValue(amount, 1000L, 'k');
        }
        return NumberFormat.getInstance().format(amount);
    }
    
    private static String formatValue(final long amount, final long div, final char suffix) {
        return new DecimalFormat(".##").format(amount / (double)div) + suffix;
    }
    
    public static boolean isGoldenGoblin(final EntityOtherPlayerMP entityIn) {
        for (final ItemStack item : entityIn.field_71071_by.field_70460_b) {
            if (item == null) {
                return false;
            }
            final Item armor = item.func_77973_b();
            if (armor != Items.field_151169_ag && armor != Items.field_151171_ah && armor != Items.field_151149_ai && armor != Items.field_151151_aj) {
                return false;
            }
        }
        return true;
    }
    
    public static AxisAlignedBB getBlockBoundingBox(final BlockPos pos) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        return block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos);
    }
    
    public static double getXandZDistance(final double x1, final double x2, final double z1, final double z2) {
        final double diffX = x1 - x2;
        final double diffZ = z1 - z2;
        return Math.sqrt(diffX * diffX + diffZ * diffZ);
    }
    
    public static double getXandZDistanceSquared(final double x1, final double x2, final double z1, final double z2) {
        final double diffX = x1 - x2;
        final double diffZ = z1 - z2;
        return diffX * diffX + diffZ * diffZ;
    }
    
    public static double getXandZDistance(final Vec3 position, final Vec3 goal) {
        final double diffX = position.field_72450_a - goal.field_72450_a;
        final double diffZ = position.field_72449_c - goal.field_72449_c;
        return Math.sqrt(diffX * diffX + diffZ * diffZ);
    }
    
    public static double getXandZDistanceSquared(final Vec3 position, final Vec3 goal) {
        final double diffX = position.field_72450_a - goal.field_72450_a;
        final double diffZ = position.field_72449_c - goal.field_72449_c;
        return diffX * diffX + diffZ * diffZ;
    }
    
    public static double getXandZDistance(final double x, final double z) {
        final double diffX = PizzaClient.mc.field_71439_g.field_70165_t - x;
        final double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - z;
        return Math.sqrt(diffX * diffX + diffZ * diffZ);
    }
    
    public static double getXandZDistance(final Vec3 vec) {
        final double diffX = PizzaClient.mc.field_71439_g.field_70165_t - vec.field_72450_a;
        final double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - vec.field_72449_c;
        return Math.sqrt(diffX * diffX + diffZ * diffZ);
    }
    
    public static double getXandZDistanceSquared(final double x, final double z) {
        final double diffX = PizzaClient.mc.field_71439_g.field_70165_t - x;
        final double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - z;
        return diffX * diffX + diffZ * diffZ;
    }
    
    public static double getXandZDistanceSquared(final Vec3 vec) {
        final double diffX = PizzaClient.mc.field_71439_g.field_70165_t - vec.field_72450_a;
        final double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - vec.field_72449_c;
        return diffX * diffX + diffZ * diffZ;
    }
    
    public static Vec3 getMiddleOfAABB(final AxisAlignedBB aabb) {
        return new Vec3((aabb.field_72336_d + aabb.field_72340_a) / 2.0, (aabb.field_72337_e + aabb.field_72338_b) / 2.0, (aabb.field_72334_f + aabb.field_72339_c) / 2.0);
    }
    
    public static Vec3 getMiddleOfAABB(final BlockPos pos) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        return getMiddleOfAABB(block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos));
    }
    
    public static AxisAlignedBB getBlockAABB(final BlockPos pos) {
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        return block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos);
    }
    
    public static AxisAlignedBB getBlockAABB(final Vec3 vec) {
        final BlockPos pos = new BlockPos(vec);
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        return block.func_180646_a((World)PizzaClient.mc.field_71441_e, pos);
    }
    
    public static boolean isAdjacentToWater(final BlockPos pos) {
        return PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p())).func_177230_c() == Blocks.field_150355_j || PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p())).func_177230_c() == Blocks.field_150355_j || PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 1)).func_177230_c() == Blocks.field_150355_j || PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() + 1)).func_177230_c() == Blocks.field_150355_j;
    }
    
    public static BlockPos getBlockFromHit(final MovingObjectPosition hit) {
        switch (hit.field_178784_b) {
            case UP: {
                return new BlockPos(hit.field_72307_f.field_72450_a, hit.field_72307_f.field_72448_b - 1.0, hit.field_72307_f.field_72449_c);
            }
            case EAST: {
                return new BlockPos(hit.field_72307_f.field_72450_a - 1.0, hit.field_72307_f.field_72448_b, hit.field_72307_f.field_72449_c);
            }
            case SOUTH: {
                return new BlockPos(hit.field_72307_f.field_72450_a, hit.field_72307_f.field_72448_b, hit.field_72307_f.field_72449_c - 1.0);
            }
            default: {
                return new BlockPos(hit.field_72307_f);
            }
        }
    }
    
    public static EnumFacing getRightEnumfacing(final EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                return EnumFacing.EAST;
            }
            case EAST: {
                return EnumFacing.SOUTH;
            }
            case SOUTH: {
                return EnumFacing.WEST;
            }
            case WEST: {
                return EnumFacing.NORTH;
            }
            default: {
                return EnumFacing.NORTH;
            }
        }
    }
    
    public static EnumFacing getLeftEnumfacing(final EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                return EnumFacing.WEST;
            }
            case EAST: {
                return EnumFacing.NORTH;
            }
            case SOUTH: {
                return EnumFacing.EAST;
            }
            case WEST: {
                return EnumFacing.SOUTH;
            }
            default: {
                return EnumFacing.NORTH;
            }
        }
    }
    
    public static String getEnumfacingString(final EnumFacing enumfacing) {
        switch (enumfacing) {
            case NORTH: {
                return "Towards negative Z";
            }
            case SOUTH: {
                return "Towards positive Z";
            }
            case WEST: {
                return "Towards negative X";
            }
            case EAST: {
                return "Towards positive X";
            }
            default: {
                return "Invalid";
            }
        }
    }
    
    public static void addToggleMessage(final String name, final boolean condition) {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + name + " is now " + (condition ? (EnumChatFormatting.GREEN + "On") : (EnumChatFormatting.RED + "Off"))));
    }
    
    public static boolean isBlockInAir(BlockPos pos) {
        pos = pos.func_177977_b();
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        return Utils.uncollidables.contains(block);
    }
    
    public static boolean isBlockBlocked(final BlockPos pos) {
        Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c();
        if (!block.func_149730_j() || Utils.uncollidables.contains(block)) {
            return false;
        }
        block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c();
        if (!block.func_149730_j() || Utils.uncollidables.contains(block)) {
            return false;
        }
        block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c();
        if (!block.func_149730_j() || Utils.uncollidables.contains(block)) {
            return false;
        }
        block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c();
        if (!block.func_149730_j() || Utils.uncollidables.contains(block)) {
            return false;
        }
        block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c();
        if (!block.func_149730_j() || Utils.uncollidables.contains(block)) {
            return false;
        }
        block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c();
        return block.func_149730_j() && !Utils.uncollidables.contains(block);
    }
    
    public static boolean isAdjacentToUncollidable(final BlockPos pos) {
        return PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() != Blocks.field_150350_a || PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() != Blocks.field_150350_a || PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() != Blocks.field_150350_a || PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() != Blocks.field_150350_a;
    }
    
    public static String getContainerName() {
        return ((ContainerChest)PizzaClient.mc.field_71439_g.field_71070_bA).func_85151_d().func_145748_c_().func_150260_c();
    }
    
    public static String getContainerNameTrimmed() {
        return StringUtils.func_76338_a(((ContainerChest)PizzaClient.mc.field_71439_g.field_71070_bA).func_85151_d().func_145748_c_().func_150260_c().trim());
    }
    
    public static String getFormattedDate() {
        final Date date = new Date();
        return new SimpleDateFormat("dd/MM/yyyy").format(date) + ", at " + new SimpleDateFormat("HH:mm aa").format(date);
    }
    
    public static InputStream getResourceInputStream(final ResourceLocation location) throws IOException {
        return PizzaClient.mc.func_110442_L().func_110536_a(location).func_110527_b();
    }
    
    public static boolean isChatOpen() {
        return PizzaClient.mc.field_71462_r instanceof GuiChat;
    }
    
    public static int getChatWidth() {
        return calculateChatboxWidth(PizzaClient.mc.field_71474_y.field_96692_F);
    }
    
    public static int getChatHeight() {
        return calculateChatboxHeight(isChatOpen() ? PizzaClient.mc.field_71474_y.field_96694_H : PizzaClient.mc.field_71474_y.field_96693_G);
    }
    
    public static float getChatScale() {
        return PizzaClient.mc.field_71474_y.field_96691_E;
    }
    
    public static int calculateChatboxWidth(final float scale) {
        final int i = 320;
        final int j = 40;
        return MathHelper.func_76141_d(scale * (i - j) + j);
    }
    
    public static int calculateChatboxHeight(final float scale) {
        final int i = 180;
        final int j = 20;
        return MathHelper.func_76141_d(scale * (i - j) + j);
    }
    
    public static int getLineCount() {
        return getChatHeight() / 9;
    }
    
    public static BufferedImage readBufferedImage(final ResourceLocation location) throws IOException {
        return TextureUtil.func_177053_a(PizzaClient.mc.func_110442_L().func_110536_a(location).func_110527_b());
    }
    
    public static Vec3 toVec(final BlockPos pos) {
        return new Vec3(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5);
    }
    
    @Deprecated
    public static boolean onGround() {
        if (!PizzaClient.mc.field_71439_g.field_70122_E) {
            return false;
        }
        final Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
        final BlockPos pos = new BlockPos(player.field_72450_a, (double)MathUtil.ceil(player.field_72448_b), player.field_72449_c).func_177977_b();
        final Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        block.func_180654_a((IBlockAccess)PizzaClient.mc.field_71441_e, pos);
        return !Utils.uncollidables.contains(block);
    }
    
    public static int getSkyblockYear() {
        return MathUtil.floor(System.currentTimeMillis() / 1000.0 - 1.560276E9) / 446400 + 1;
    }
    
    public static <T> T getLastElement(final List<T> list) {
        return list.get(list.size() - 1);
    }
    
    public static double formatDouble(final double value) {
        return MathUtil.round(value * 10000.0) / 10000.0;
    }
    
    public static String takeScreenshot(final String screenshotName) {
        try {
            final Framebuffer buffer = PizzaClient.mc.func_147110_a();
            int width = PizzaClient.mc.field_71443_c;
            int height = PizzaClient.mc.field_71440_d;
            final File directory = new File(PizzaClient.modDir, "screenshots");
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (OpenGlHelper.func_148822_b()) {
                width = buffer.field_147622_a;
                height = buffer.field_147620_b;
            }
            final int i = width * height;
            final IntBuffer pixelBuffer = BufferUtils.createIntBuffer(i);
            final int[] pixelValues = new int[i];
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            pixelBuffer.clear();
            if (OpenGlHelper.func_148822_b()) {
                GlStateManager.func_179144_i(buffer.field_147617_g);
                GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
            }
            else {
                GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
            }
            pixelBuffer.get(pixelValues);
            TextureUtil.func_147953_a(pixelValues, width, height);
            BufferedImage bufferedimage;
            if (OpenGlHelper.func_148822_b()) {
                bufferedimage = new BufferedImage(buffer.field_147621_c, buffer.field_147618_d, 1);
                int k;
                for (int j = k = buffer.field_147620_b - buffer.field_147618_d; k < buffer.field_147620_b; ++k) {
                    for (int l = 0; l < buffer.field_147621_c; ++l) {
                        bufferedimage.setRGB(l, k - j, pixelValues[k * buffer.field_147622_a + l]);
                    }
                }
            }
            else {
                bufferedimage = new BufferedImage(width, height, 1);
                bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
            }
            final File file2 = (screenshotName == null) ? getTimestampedPNGFileForDirectory(directory) : new File(directory, screenshotName);
            ImageIO.write(bufferedimage, "png", file2);
            return file2.getAbsolutePath();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static File getTimestampedPNGFileForDirectory(final File gameDirectory) {
        final String s = Utils.DATE_FORMAT.format(new Date());
        int i = 1;
        File file1;
        while (true) {
            file1 = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
            if (!file1.exists()) {
                break;
            }
            ++i;
        }
        return file1;
    }
    
    public static float formatFloat(final float value) {
        return MathUtil.round(value * 1000.0f) / 1000.0f;
    }
    
    public static Set<String> getModIDs() {
        final Set<String> ids = new HashSet<String>();
        for (final ModContainer container : Loader.instance().getActiveModList()) {
            ids.add(container.getModId());
        }
        return ids;
    }
    
    public static List<BlockPos> getAdjacents(final BlockPos pos) {
        final List<BlockPos> adjacents = new ArrayList<BlockPos>();
        adjacents.add(pos.func_177984_a());
        adjacents.add(pos.func_177977_b());
        adjacents.add(pos.func_177978_c());
        adjacents.add(pos.func_177974_f());
        adjacents.add(pos.func_177968_d());
        adjacents.add(pos.func_177976_e());
        return adjacents;
    }
    
    public static Vec3 getHittableAdjacent(final BlockPos pos) {
        Vec3 hit = VecUtil.getHittableHitVec(pos.func_177984_a());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableHitVec(pos.func_177978_c());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableHitVec(pos.func_177974_f());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableHitVec(pos.func_177968_d());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableHitVec(pos.func_177976_e());
        if (hit != null) {
            return hit;
        }
        return VecUtil.getHittableHitVec(pos.func_177977_b());
    }
    
    public static MovingObjectPosition getHittablePosition(final BlockPos pos) {
        MovingObjectPosition hit = VecUtil.getHittableMovingObjectPosition(pos.func_177984_a());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableMovingObjectPosition(pos.func_177978_c());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableMovingObjectPosition(pos.func_177974_f());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableMovingObjectPosition(pos.func_177968_d());
        if (hit != null) {
            return hit;
        }
        hit = VecUtil.getHittableMovingObjectPosition(pos.func_177976_e());
        if (hit != null) {
            return hit;
        }
        return VecUtil.getHittableMovingObjectPosition(pos.func_177977_b());
    }
    
    public static double squareDistanceToBlockPos(final Vec3 vec, final BlockPos pos) {
        final double d0 = vec.field_72450_a - pos.func_177958_n();
        final double d2 = vec.field_72448_b - pos.func_177956_o();
        final double d3 = vec.field_72449_c - pos.func_177952_p();
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public static double squareDistanceToBlockPos(final BlockPos pos1, final BlockPos pos) {
        final double d0 = pos1.func_177958_n() - pos.func_177958_n();
        final double d2 = pos1.func_177956_o() - pos.func_177956_o();
        final double d3 = pos1.func_177952_p() - pos.func_177952_p();
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public static float getExactDay() {
        return PizzaClient.mc.field_71441_e.func_72820_D() / 24000.0f;
    }
    
    public static BlockPos getClosestInRange(final BlockPos original) {
        if (!Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(original).func_177230_c())) {
            final Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
            BlockPos closest = null;
            double bestDist = 9.99999999E8;
            for (final BlockPos pos : BlockPos.func_177980_a(original.func_177982_a(-3, -3, -3), original.func_177982_a(3, 3, 3))) {
                if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()) && Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c()) && !Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c())) {
                    final double dist = squareDistanceToBlockPos(player, pos);
                    if (closest != null && dist >= bestDist) {
                        continue;
                    }
                    closest = pos;
                    bestDist = dist;
                }
            }
            return (closest == null) ? original : closest;
        }
        return original;
    }
    
    public static BetterBlockPos getClosestInRange(final BetterBlockPos original) {
        if (!Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p((BlockPos)original).func_177230_c())) {
            final Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
            BlockPos closest = null;
            double bestDist = 9.99999999E8;
            for (final BlockPos pos : BlockPos.func_177980_a((BlockPos)original.add(-3, -3, -3), (BlockPos)original.add(3, 3, 3))) {
                if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()) && Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c()) && !Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c())) {
                    final double dist = squareDistanceToBlockPos(player, pos);
                    if (closest != null && dist >= bestDist) {
                        continue;
                    }
                    closest = pos;
                    bestDist = dist;
                }
            }
            return (closest == null) ? original : new BetterBlockPos(closest);
        }
        return original;
    }
    
    public static byte[] toByteArray(final BufferedInputStream inputStream) throws IOException {
        try {
            return IOUtils.toByteArray((InputStream)inputStream);
        }
        finally {
            inputStream.close();
        }
    }
    
    static {
        random = new Random();
        DECIMAL_FORMAT = new DecimalFormat("0.00");
        uncollidables = new HashSet<Block>(Arrays.asList(Blocks.field_150350_a, Blocks.field_180393_cK, Blocks.field_180394_cL, Blocks.field_150456_au, Blocks.field_150443_bT, Blocks.field_150445_bS, Blocks.field_150452_aw, Blocks.field_150430_aB, Blocks.field_150471_bO, (Block)Blocks.field_150480_ab, Blocks.field_150442_at, (Block)Blocks.field_150353_l, (Block)Blocks.field_150356_k, (Block)Blocks.field_150355_j, (Block)Blocks.field_150358_i, (Block)Blocks.field_150427_aO, Blocks.field_150448_aq, Blocks.field_150408_cc, Blocks.field_150319_E, Blocks.field_150318_D, (Block)Blocks.field_150488_af, (Block)Blocks.field_150436_aH, Blocks.field_150472_an, Blocks.field_150444_as, Blocks.field_150478_aa, Blocks.field_150395_bd, (Block)Blocks.field_150329_H, (Block)Blocks.field_150330_I, (Block)Blocks.field_150337_Q, (Block)Blocks.field_150338_P, Blocks.field_150464_aj, Blocks.field_150388_bm, Blocks.field_150459_bM, Blocks.field_150469_bN, Blocks.field_150345_g, Blocks.field_150393_bb, Blocks.field_150394_bc));
        ERROR_MESSAGE = "PizzaClient > " + EnumChatFormatting.RED;
        SUCCESS_MESSAGE = "PizzaClient > " + EnumChatFormatting.GREEN;
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
}
