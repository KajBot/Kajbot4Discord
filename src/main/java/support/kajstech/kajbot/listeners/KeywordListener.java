package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.utils.KeywordManager;

public class KeywordListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(Bot.commandClient.getPrefix()) || event.getAuthor() == event.getJDA().getSelfUser())
            return;

        String string = event.getMessage().getContentRaw();
        String[] args = string.split("\\s+");
        for (int i = 0; i <= args.length - 1; i++) {
            if (KeywordManager.kws.containsKey(args[i])) {
                event.getMessage().getTextChannel().sendMessage(KeywordManager.kws.get(args[i])).queue();
            }
        }


    }
}
