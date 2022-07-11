package qolskyblockmod.pizzaclient.features.dungeons;

import net.minecraft.entity.item.*;
import qolskyblockmod.pizzaclient.util.*;
import qolskyblockmod.pizzaclient.*;
import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.core.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import com.google.gson.*;
import java.util.*;

public class QuizAura
{
    public static final Map<String, List<String>> solutions;
    public static EntityArmorStand correctAnswer;
    public static Thread fetcher;
    public static List<EntityArmorStand> armorStands;
    private static final List<String> unansweredQuestions;
    private static boolean checkQuestion;
    private static String question;
    private static long timeUntilClick;
    public static boolean isQuizActive;
    
    public static void onChat(final String msg) {
        if (msg.contains(" answered Question ")) {
            QuizAura.correctAnswer = null;
            QuizAura.timeUntilClick = 0L;
            return;
        }
        if (QuizAura.checkQuestion) {
            QuizAura.question = msg.trim();
            QuizAura.checkQuestion = false;
            return;
        }
        if (QuizAura.question != null) {
            if (msg.contains("\u24d0")) {
                if (isEmpty()) {
                    if (QuizAura.unansweredQuestions.size() == 3) {
                        QuizAura.unansweredQuestions.clear();
                    }
                    QuizAura.unansweredQuestions.add(msg);
                    return;
                }
                if (msg.contains("Year ")) {
                    if (msg.contains("Year " + Utils.getSkyblockYear())) {
                        QuizAura.correctAnswer = QuizAura.armorStands.get(0);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: \u24d0"));
                    }
                    return;
                }
                for (final String names : QuizAura.solutions.get(QuizAura.question)) {
                    if (msg.contains(names)) {
                        QuizAura.correctAnswer = QuizAura.armorStands.get(0);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: \u24d0"));
                        break;
                    }
                }
                return;
            }
            else if (msg.contains("\u24d1")) {
                if (isEmpty()) {
                    if (QuizAura.unansweredQuestions.size() == 3) {
                        QuizAura.unansweredQuestions.clear();
                    }
                    QuizAura.unansweredQuestions.add(msg);
                    return;
                }
                if (msg.contains("Year ")) {
                    if (msg.contains("Year " + Utils.getSkyblockYear())) {
                        QuizAura.correctAnswer = QuizAura.armorStands.get(1);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: \u24d1"));
                    }
                    return;
                }
                for (final String names : QuizAura.solutions.get(QuizAura.question)) {
                    if (msg.contains(names)) {
                        QuizAura.correctAnswer = QuizAura.armorStands.get(1);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: \u24d1"));
                        break;
                    }
                }
                return;
            }
            else if (msg.contains("\u24d2")) {
                if (isEmpty()) {
                    if (QuizAura.unansweredQuestions.size() == 3) {
                        QuizAura.unansweredQuestions.clear();
                    }
                    QuizAura.unansweredQuestions.add(msg);
                    return;
                }
                if (msg.contains("Year ")) {
                    if (msg.contains("Year " + Utils.getSkyblockYear())) {
                        QuizAura.correctAnswer = QuizAura.armorStands.get(2);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: \u24d2"));
                    }
                    return;
                }
                for (final String names : QuizAura.solutions.get(QuizAura.question)) {
                    if (msg.contains(names)) {
                        QuizAura.correctAnswer = QuizAura.armorStands.get(2);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: \u24d2"));
                        break;
                    }
                }
                return;
            }
        }
        if (msg.contains(" answered the final question correctly!")) {
            QuizAura.isQuizActive = false;
            QuizAura.armorStands.clear();
            QuizAura.question = null;
            QuizAura.unansweredQuestions.clear();
            return;
        }
        if (msg.contains("Question ")) {
            QuizAura.timeUntilClick = System.currentTimeMillis();
            QuizAura.checkQuestion = true;
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickStartEvent event) {
        if (QuizAura.isQuizActive) {
            if (isEmpty()) {
                for (final Entity entity : PizzaClient.mc.field_71441_e.field_72996_f) {
                    if (entity instanceof EntityArmorStand && !QuizAura.armorStands.contains(entity) && entity.func_145818_k_()) {
                        final String name = entity.func_95999_t();
                        if (name.contains("\u24d0")) {
                            QuizAura.armorStands.set(0, (EntityArmorStand)entity);
                            if (!isEmpty()) {
                                sortArmorStands();
                                return;
                            }
                            continue;
                        }
                        else if (name.contains("\u24d1")) {
                            QuizAura.armorStands.set(1, (EntityArmorStand)entity);
                            if (!isEmpty()) {
                                sortArmorStands();
                                return;
                            }
                            continue;
                        }
                        else {
                            if (!name.contains("\u24d2")) {
                                continue;
                            }
                            QuizAura.armorStands.set(2, (EntityArmorStand)entity);
                            if (!isEmpty()) {
                                sortArmorStands();
                                return;
                            }
                            continue;
                        }
                    }
                }
            }
            else if (QuizAura.correctAnswer != null && QuizAura.timeUntilClick != 0L) {
                if (System.currentTimeMillis() - QuizAura.timeUntilClick >= 4350L) {
                    QuizAura.timeUntilClick += 1500L;
                    PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.correctAnswer);
                    PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicking Quiz!"));
                }
                else {
                    final EntityArmorStand armorStand = QuizAura.armorStands.get(2);
                    if (armorStand.func_145818_k_() && armorStand.func_95999_t().contains("\u24d2")) {
                        PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.correctAnswer);
                        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicking Quiz"));
                        QuizAura.correctAnswer = null;
                        QuizAura.timeUntilClick = 0L;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
        if (QuizAura.solutions.size() == 0 && !QuizAura.fetcher.isAlive()) {
            (QuizAura.fetcher = new Thread(QuizAura::loadSolutions)).start();
        }
        QuizAura.correctAnswer = null;
        QuizAura.armorStands.clear();
        QuizAura.isQuizActive = false;
        QuizAura.question = null;
        QuizAura.unansweredQuestions.clear();
    }
    
    public static void loadSolutions() {
        try {
            final JsonObject creditsToSkytils = Utils.getJson("https://cdn.jsdelivr.net/gh/Skytils/SkytilsMod-Data@main/solvers/oruotrivia.json").getAsJsonObject();
            for (final Map.Entry<String, JsonElement> entry : creditsToSkytils.entrySet()) {
                QuizAura.solutions.put(entry.getKey(), Utils.getStringListFromJsonArray(entry.getValue().getAsJsonArray()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong when trying to load the quiz solutions.");
            if (PizzaClient.mc.field_71439_g != null) {
                PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.ERROR_MESSAGE + "Couldn't load the quiz solutions."));
            }
        }
    }
    
    private static void sortArmorStands() {
        PizzaClient.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText(Utils.SUCCESS_MESSAGE + "Loaded in all armor stands, using inf reach now."));
        if (QuizAura.unansweredQuestions.size() != 0) {
            if (QuizAura.question != null) {
                if (QuizAura.question.contains("What SkyBlock year")) {
                    final String year = String.valueOf(Utils.getSkyblockYear());
                    for (final String msg : QuizAura.unansweredQuestions) {
                        if (msg.contains("\u24d0")) {
                            if (msg.contains(year)) {
                                PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.armorStands.get(0));
                                break;
                            }
                            continue;
                        }
                        else if (msg.contains("\u24d1")) {
                            if (msg.contains(year)) {
                                PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.armorStands.get(1));
                                break;
                            }
                            continue;
                        }
                        else {
                            if (msg.contains("\u24d2") && msg.contains(year)) {
                                PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.armorStands.get(2));
                                break;
                            }
                            continue;
                        }
                    }
                }
                else {
                    for (final String msg2 : QuizAura.unansweredQuestions) {
                        if (msg2.contains("\u24d0")) {
                            for (final String names : QuizAura.solutions.get(QuizAura.question)) {
                                if (msg2.contains(names)) {
                                    PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.armorStands.get(0));
                                    break;
                                }
                            }
                        }
                        else if (msg2.contains("\u24d1")) {
                            for (final String names : QuizAura.solutions.get(QuizAura.question)) {
                                if (msg2.contains(names)) {
                                    PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.armorStands.get(1));
                                    break;
                                }
                            }
                        }
                        else {
                            if (!msg2.contains("\u24d2")) {
                                continue;
                            }
                            for (final String names : QuizAura.solutions.get(QuizAura.question)) {
                                if (msg2.contains(names)) {
                                    PizzaClient.mc.field_71442_b.func_78768_b((EntityPlayer)PizzaClient.mc.field_71439_g, (Entity)QuizAura.armorStands.get(2));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            QuizAura.unansweredQuestions.clear();
        }
    }
    
    private static boolean isEmpty() {
        if (QuizAura.armorStands.size() != 3) {
            QuizAura.armorStands = new ArrayList<EntityArmorStand>(Arrays.asList(new EntityArmorStand[3]));
            return true;
        }
        return QuizAura.armorStands.get(0) == null || QuizAura.armorStands.get(1) == null || QuizAura.armorStands.get(2) == null;
    }
    
    static {
        solutions = new HashMap<String, List<String>>();
        QuizAura.fetcher = new Thread(QuizAura::loadSolutions);
        QuizAura.armorStands = new ArrayList<EntityArmorStand>();
        unansweredQuestions = new ArrayList<String>();
    }
}
