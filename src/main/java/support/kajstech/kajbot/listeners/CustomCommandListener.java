package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Main;
import support.kajstech.kajbot.utils.CustomCommandsManager;

public class CustomCommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (!event.getMessage().getContentRaw().startsWith(Main.commandClient.getPrefix()) || event.getAuthor() == event.getJDA().getSelfUser())
            return;


        String command = event.getMessage().getContentRaw().substring(Main.commandClient.getPrefix().length());
        if (command.contains(" ")) {
            command = command.substring(0, command.indexOf(" "));
        }
        if (command.contains("\n")) {
            command = command.substring(0, command.indexOf("\n"));
        }

        if (CustomCommandsManager.cmds.containsKey(command)) {
            event.getMessage().getTextChannel().sendMessage(CustomCommandsManager.cmds.get(command)).queue();
        }


    }
}
