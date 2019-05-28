package dk.jensbot.kajbot4discord.listeners;

import dk.jensbot.kajbot4discord.utils.LogHelper;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import dk.jensbot.kajbot4discord.utils.Config;

public class MessageLogger extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (!Config.cfg.get("Message-logging").equalsIgnoreCase("true") || event.getMessage().isWebhookMessage() || !event.getChannelType().isGuild() || event.getAuthor().isBot() || event.getMessage().isWebhookMessage())
            return;

        LogHelper.logToFile("(" + event.getGuild().getName() + " - #" + event.getChannel().getName() + ") " + event.getAuthor().getAsTag() + ": " + event.getMessage().getContentRaw(), "chat.log");


    }

}