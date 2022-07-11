package qolskyblockmod.pizzaclient.core.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class SendChatMessageEvent extends Event
{
    public String message;
    
    public SendChatMessageEvent(final String message) {
        this.message = message;
    }
}
