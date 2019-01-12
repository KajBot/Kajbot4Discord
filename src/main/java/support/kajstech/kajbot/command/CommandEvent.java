package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.List;

public class CommandEvent {

    private final MessageReceivedEvent event;
    private String args;
    private List<String> argsSplit;

    public CommandEvent(List<String> argsSplit, String args, MessageReceivedEvent event) {
        this.event = event;
        this.args = args;
        this.argsSplit = argsSplit;
    }

    public String getArgs() {
        return args;
    }

    public List<String> getArgsSplit() {
        return argsSplit;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public boolean isOwner() {
        return event.getAuthor().getId().equals(ConfigHandler.getProperty("Bot owner ID"));
    }


}
