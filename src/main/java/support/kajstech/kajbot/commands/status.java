package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.OnlineStatus;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class status extends Command {

    public status() {
        this.name = "status";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot controller role");
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\W+");
        if (args[0].length() < 1) return;


        if (args[0].equalsIgnoreCase("dnd")) args[0] = "DO_NOT_DISTURB";
        if (!(args[0].equalsIgnoreCase("OFFLINE") || args[0].equalsIgnoreCase("INVISIBLE") || args[0].equalsIgnoreCase("ONLINE") || args[0].equalsIgnoreCase("DO_NOT_DISTURB") || args[0].equalsIgnoreCase("IDLE")))
            return;


        Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(args[0].toUpperCase()));
        e.getChannel().sendMessage((Language.getMessage("Status.SET")).replace("%STATUS%", args[0].toUpperCase())).queue();

    }
}
