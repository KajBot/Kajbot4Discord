package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;

public class KeywordListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(ConfigHandler.getProperty("Command prefix")) || event.getAuthor().isBot())
            return;

        String string = event.getMessage().getContentRaw();
        String[] args = string.split("\\s+");

        for (final String arg : args) {
            if (KeywordHandler.kws.containsKey(arg)) {
                event.getTextChannel().sendMessage(KeywordHandler.kws.get(arg)).queue();
            }
        }


    }
}
