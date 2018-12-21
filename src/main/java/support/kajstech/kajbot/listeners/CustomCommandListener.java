package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;

public class CustomCommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.getMessage().getContentRaw().startsWith(Bot.commandClient.getPrefix()) || event.getAuthor() == event.getJDA().getSelfUser())
            return;


        String command = event.getMessage().getContentRaw().substring(Bot.commandClient.getPrefix().length());
        if (command.contains(" ")) {
            command = command.substring(0, command.indexOf(" "));
        }
        if (command.contains("\n")) {
            command = command.substring(0, command.indexOf("\n"));
        }

        if (CustomCommandsHandler.cmds.containsKey(command)) {
            event.getMessage().getTextChannel().sendMessage(CustomCommandsHandler.cmds.get(command)).queue();
        }


    }
}
