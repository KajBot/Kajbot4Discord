package dk.jensbot.kajbot4discord.listeners;

import dk.jensbot.kajbot4discord.handlers.KeywordHandler;
import dk.jensbot.kajbot4discord.utils.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KeywordListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(Config.cfg.get("Bot.prefix")) || event.getAuthor().isBot())
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
