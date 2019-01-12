package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;

public class KeywordListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(ConfigHandler.getProperty("Command prefix")) || event.getAuthor() == event.getJDA().getSelfUser())
            return;

        String string = event.getMessage().getContentRaw();
        String[] args = string.split("\\s+");
        for (int i = 0; i <= args.length - 1; i++) {
            if (KeywordHandler.kws.containsKey(args[i])) {
                event.getMessage().getTextChannel().sendMessage(KeywordHandler.kws.get(args[i])).queue();
            }
        }


    }
}
