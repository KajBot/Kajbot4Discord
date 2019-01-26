package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.api.OnlineStatus;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;

public class Status extends Command {
    public Status() {
        this.name = "status";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;


        if (e.getArgsSplit().get(0).equalsIgnoreCase("dnd"))
            e.getArgsSplit().set(0, OnlineStatus.DO_NOT_DISTURB.toString());
        if (!(e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.IDLE.toString())))
            return;


        Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(e.getArgsSplit().get(0).toUpperCase()));
        e.reply((Language.getMessage("Status.SET")).replace("%STATUS%", e.getArgsSplit().get(0).toUpperCase()));
    }
}
