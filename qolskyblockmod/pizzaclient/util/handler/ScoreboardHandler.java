package qolskyblockmod.pizzaclient.util.handler;

import net.minecraft.util.*;
import qolskyblockmod.pizzaclient.*;
import java.util.stream.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.util.*;

public class ScoreboardHandler
{
    public static String cleanSB(final String scoreboard) {
        final char[] nvString = StringUtils.func_76338_a(scoreboard).toCharArray();
        final StringBuilder cleaned = new StringBuilder();
        for (final char c : nvString) {
            if (c > '\u0014' && c < '\u007f') {
                cleaned.append(c);
            }
        }
        return cleaned.toString();
    }
    
    public static Set<String> getSidebarLines() {
        final Set<String> lines = new HashSet<String>();
        final Scoreboard scoreboard = PizzaClient.mc.field_71441_e.func_96441_U();
        if (scoreboard == null) {
            return lines;
        }
        final ScoreObjective objective = scoreboard.func_96539_a(1);
        if (objective == null) {
            return lines;
        }
        Collection<Score> scores = (Collection<Score>)scoreboard.func_96534_i(objective);
        final List<Score> list = scores.stream().filter(input -> input != null && input.func_96653_e() != null && !input.func_96653_e().startsWith("#")).collect((Collector<? super Score, ?, List<Score>>)Collectors.toList());
        scores = ((list.size() > 15) ? Lists.newArrayList(Iterables.skip((Iterable)list, scores.size() - 15)) : list);
        for (final Score score : scores) {
            final ScorePlayerTeam team = scoreboard.func_96509_i(score.func_96653_e());
            lines.add(ScorePlayerTeam.func_96667_a((Team)team, score.func_96653_e()));
        }
        return lines;
    }
    
    public static boolean isScoreboardEmpty() {
        return PizzaClient.mc.field_71441_e.func_96441_U().func_96539_a(1) == null;
    }
}
