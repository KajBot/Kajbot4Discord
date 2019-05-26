package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.Config;

public class KeywordListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(Config.cfg.get("Command-prefix")) || event.getAuthor().isBot())
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
