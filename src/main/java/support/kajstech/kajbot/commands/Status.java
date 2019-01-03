package support.kajstech.kajbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class Status extends Command {

    public Status() {
        this.name = "status";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
    }

    @Override
    protected void execute(CommandEvent e) {

        String[] args = e.getArgs().split("\\s+");
        if (args[0].length() < 1) return;


        if (args[0].equalsIgnoreCase("dnd")) args[0] = "DO_NOT_DISTURB";
        if (!(args[0].equalsIgnoreCase("OFFLINE") || args[0].equalsIgnoreCase("INVISIBLE") || args[0].equalsIgnoreCase("ONLINE") || args[0].equalsIgnoreCase("DO_NOT_DISTURB") || args[0].equalsIgnoreCase("IDLE")))
            return;


        Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(args[0].toUpperCase()));
        e.reply((Language.getMessage("Status.SET")).replace("%STATUS%", args[0].toUpperCase()));

    }
}
