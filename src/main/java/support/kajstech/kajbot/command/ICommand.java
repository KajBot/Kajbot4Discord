package support.kajstech.kajbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public interface ICommand {

    void handle(List<String> args, MessageReceivedEvent event);

    String getHelp();

    String getName();

}