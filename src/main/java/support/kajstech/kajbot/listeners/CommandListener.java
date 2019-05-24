package support.kajstech.kajbot.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.command.CommandManager;
import support.kajstech.kajbot.utils.Config;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage() && (event.getMessage().getContentRaw().startsWith(Config.cfg.get("Command prefix")) || event.getMessage().getContentRaw().startsWith(Bot.jda.getSelfUser().getAsMention() + " "))) {
            new CommandManager().handleCommand(event);
        }
    }

}
