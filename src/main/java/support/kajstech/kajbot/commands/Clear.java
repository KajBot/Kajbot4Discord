package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class Clear extends Command {

    public Clear() {
        this.name = "clear";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {
        try {
            String[] args = e.getArgs().split("\\s+");
            int AMOUNT = Integer.parseInt(args[0]);
            MessageHistory history = new MessageHistory(e.getTextChannel());
            List<Message> msgs;
            msgs = history.retrievePast(AMOUNT).complete();
            e.getTextChannel().deleteMessages(msgs).queue();
            e.reply((Language.getMessage("Clear.SUCCESS")).replace("%AMOUNT%", args[0]));
        } catch (Exception ignored) {
            e.reply(Language.getMessage("Clear.ERROR"));
        }

    }
}
