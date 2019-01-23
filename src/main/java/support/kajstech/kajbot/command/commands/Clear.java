package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.utils.LogHelper;

import java.util.List;

public class Clear extends Command {
    public Clear() {
        this.name = "clear";
        this.guildOnly = true;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        try {
            int AMOUNT = Integer.parseInt(e.getArgsSplit().get(0));
            MessageHistory history = new MessageHistory(e.getEvent().getTextChannel());
            List<Message> msgs;
            msgs = history.retrievePast(AMOUNT).complete();
            e.getChannel().deleteMessages(msgs).queue();
            e.reply((Language.getMessage("Clear.SUCCESS")).replace("%AMOUNT%", e.getArgsSplit().get(0)));
        } catch (Exception ex) {
            LogHelper.error(this.getClass(), ex, e.getMessage().getContentRaw());
            e.reply(Language.getMessage("Clear.ERROR"));
        }
    }

}
