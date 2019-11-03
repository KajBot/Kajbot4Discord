package dk.jensbot.kajbot4discord.command.commands;

import dk.jensbot.kajbot4discord.command.Command;
import dk.jensbot.kajbot4discord.command.CommandEvent;
import dk.jensbot.kajbot4discord.utils.Language;
import net.dv8tion.jda.api.OnlineStatus;

public class Status extends Command {
    public Status() {
        this.name = "status";
        this.adminCommand = true;
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgsSplit().get(0).length() < 1) return;


        if (e.getArgsSplit().get(0).equalsIgnoreCase("dnd"))
            e.getArgsSplit().set(0, OnlineStatus.DO_NOT_DISTURB.toString());
        if (!(e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || e.getArgsSplit().get(0).equalsIgnoreCase(OnlineStatus.IDLE.toString())))
            return;


        e.getJDA().getPresence().setStatus(OnlineStatus.valueOf(e.getArgsSplit().get(0).toUpperCase()));
        e.reply((Language.lang.get("Status.SET")).replace("%STATUS%", e.getArgsSplit().get(0).toUpperCase()));
    }
}
