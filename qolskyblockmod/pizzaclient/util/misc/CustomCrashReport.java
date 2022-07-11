package qolskyblockmod.pizzaclient.util.misc;

import net.minecraft.crash.*;

public class CustomCrashReport extends CrashReport
{
    public CustomCrashReport(final String descriptionIn) {
        super(descriptionIn, (Throwable)new Exception());
    }
}
