package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.util.List;

public class Clear extends Command {
    public Clear() {
        this.name = "clear";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        try {
            int AMOUNT = Integer.parseInt(e.getArgsSplit().get(0));
            MessageHistory history = new MessageHistory(e.getEvent().getTextChannel());
            List<Message> msgs;
            msgs = history.retrievePast(AMOUNT).complete();
            e.getEvent().getTextChannel().deleteMessages(msgs).queue();
            e.getEvent().getChannel().sendMessage((Language.getMessage("Clear.SUCCESS")).replace("%AMOUNT%", e.getArgsSplit().get(0))).queue();
        } catch (Exception ignored) {
            e.getEvent().getChannel().sendMessage(Language.getMessage("Clear.ERROR")).queue();
        }
    }

}
