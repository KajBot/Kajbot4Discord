package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import support.kajstech.kajbot.command.ICommand;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class Ping implements ICommand {
    @Override
    public void handle(List<String> argsSplit, String args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Ping ...").queue(m -> {
            long ping = event.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS);
            m.editMessage("Ping: " + ping + "ms | Websocket: " + event.getJDA().getPing() + "ms").queue();
        });
    }

    @Override
    public String getName() {
        return "ping";
    }
}
