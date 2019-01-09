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


        if (args[0].equalsIgnoreCase("dnd")) args[0] = OnlineStatus.DO_NOT_DISTURB.toString();
        if (!(args[0].equalsIgnoreCase(OnlineStatus.OFFLINE.toString()) || args[0].equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || args[0].equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || args[0].equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || args[0].equalsIgnoreCase(OnlineStatus.IDLE.toString())))
            return;


        Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(args[0].toUpperCase()));
        e.reply((Language.getMessage("Status.SET")).replace("%STATUS%", args[0].toUpperCase()));

    }
}
