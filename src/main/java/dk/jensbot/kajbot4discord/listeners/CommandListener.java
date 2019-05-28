package dk.jensbot.kajbot4discord.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import dk.jensbot.kajbot4discord.Bot;
import dk.jensbot.kajbot4discord.command.CommandManager;
import dk.jensbot.kajbot4discord.utils.Config;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() && (event.getMessage().getContentRaw().startsWith(Config.cfg.get("Command-prefix")) || event.getMessage().getContentRaw().startsWith(Bot.jda.getSelfUser().getAsMention() + " "))) {
            new CommandManager().handleCommand(event);
        }
    }

}
